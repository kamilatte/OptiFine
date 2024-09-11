package srg.net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.Log;
import net.optifine.RandomEntities;
import net.optifine.RandomEntityContext;
import net.optifine.RandomEntityProperties;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.entity.model.CustomEntityRenderer;
import net.optifine.entity.model.CustomModelRegistry;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.entity.model.VirtualEntityRenderer;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;
import net.optifine.util.StrUtils;

public class CustomEntityModels {
  private static boolean active = false;
  
  private static Map<EntityType, RandomEntityProperties<IEntityRenderer>> mapEntityProperties = new HashMap<>();
  
  private static Map<BlockEntityType, RandomEntityProperties<IEntityRenderer>> mapBlockEntityProperties = new HashMap<>();
  
  private static int matchingRuleIndex;
  
  private static Map<EntityType, EntityRenderer> originalEntityRenderMap = null;
  
  private static Map<BlockEntityType, BlockEntityRenderer> originalTileEntityRenderMap = null;
  
  private static Map<SkullBlock.Type, SkullModelBase> originalSkullModelMap = null;
  
  private static List<BlockEntityType> customTileEntityTypes = new ArrayList<>();
  
  private static BookModel customBookModel;
  
  private static boolean debugModels = Boolean.getBoolean("cem.debug.models");
  
  public static final String PREFIX_OPTIFINE_CEM = "optifine/cem/";
  
  public static final String SUFFIX_JEM = ".jem";
  
  public static final String SUFFIX_PROPERTIES = ".properties";
  
  public static void update() {
    Map<EntityType, EntityRenderer> entityRenderMap = getEntityRenderMap();
    Map<BlockEntityType, BlockEntityRenderer> tileEntityRenderMap = getTileEntityRenderMap();
    Map<SkullBlock.Type, SkullModelBase> skullModelMap = getSkullModelMap();
    if (entityRenderMap == null) {
      Config.warn("Entity render map not found, custom entity models are DISABLED.");
      return;
    } 
    if (tileEntityRenderMap == null) {
      Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
      return;
    } 
    active = false;
    entityRenderMap.clear();
    tileEntityRenderMap.clear();
    skullModelMap.clear();
    customTileEntityTypes.clear();
    entityRenderMap.putAll(originalEntityRenderMap);
    tileEntityRenderMap.putAll(originalTileEntityRenderMap);
    skullModelMap.putAll(originalSkullModelMap);
    BlockEntityWithoutLevelRenderer blockEntityRenderer = Minecraft.getInstance().getItemRenderer().getBlockEntityRenderer();
    blockEntityRenderer.tridentModel = new TridentModel(ModelAdapter.bakeModelLayer(ModelLayers.TRIDENT));
    blockEntityRenderer.shieldModel = new ShieldModel(ModelAdapter.bakeModelLayer(ModelLayers.SHIELD));
    ParrotOnShoulderLayer.customParrotModel = null;
    customBookModel = null;
    BlockEntityRenderer.CACHED_TYPES.clear();
    if ((Minecraft.getInstance()).level != null) {
      Iterable<Entity> entities = (Minecraft.getInstance()).level.entitiesForRendering();
      for (Entity entity : entities) {
        Map modelVariables = (entity.getEntityData()).modelVariables;
        if (modelVariables == null)
          continue; 
        modelVariables.clear();
      } 
    } 
    mapEntityProperties.clear();
    mapBlockEntityProperties.clear();
    if (!Config.isCustomEntityModels())
      return; 
    RandomEntityContext.Models context = new RandomEntityContext.Models();
    RendererCache rendererCache = context.getRendererCache();
    ResourceLocation[] locs = getModelLocations();
    int i = 0;
    while (true) {
      if (i < locs.length) {
        ResourceLocation loc = locs[i];
        Config.dbg("CustomEntityModel: " + loc.getPath());
        IEntityRenderer rc = parseEntityRender(loc, rendererCache, 0);
        if (rc != null) {
          Either<EntityType, BlockEntityType> type = rc.getType();
          if (rc instanceof EntityRenderer) {
            entityRenderMap.put(type.getLeft().get(), (EntityRenderer)rc);
            rendererCache.put(type.getLeft().get(), 0, (EntityRenderer)rc);
            if (rc instanceof ThrownTridentRenderer) {
              ThrownTridentRenderer tr = (ThrownTridentRenderer)rc;
              TridentModel tm = (TridentModel)Reflector.getFieldValue(tr, Reflector.RenderTrident_modelTrident);
              if (tm != null)
                blockEntityRenderer.tridentModel = tm; 
            } 
            if (rc instanceof ParrotRenderer) {
              ParrotRenderer pr = (ParrotRenderer)rc;
              ParrotModel pm = (ParrotModel)pr.getModel();
              if (pm != null)
                ParrotOnShoulderLayer.customParrotModel = pm; 
            } 
          } else if (rc instanceof BlockEntityRenderer) {
            tileEntityRenderMap.put(type.getRight().get(), (BlockEntityRenderer)rc);
            rendererCache.put(type.getRight().get(), 0, (BlockEntityRenderer)rc);
            if (rc instanceof EnchantTableRenderer) {
              EnchantTableRenderer etr = (EnchantTableRenderer)rc;
              BookModel bm = (BookModel)Reflector.getFieldValue(etr, Reflector.TileEntityEnchantmentTableRenderer_modelBook);
              setEnchantmentScreenBookModel(bm);
            } 
            customTileEntityTypes.add(type.getRight().get());
          } else if (rc instanceof VirtualEntityRenderer) {
            VirtualEntityRenderer ver = (VirtualEntityRenderer)rc;
            if (ver.getModel() instanceof ShieldModel) {
              ShieldModel sm = (ShieldModel)ver.getModel();
              blockEntityRenderer.shieldModel = sm;
            } 
          } else {
            Config.warn("Unknown renderer type: " + rc.getClass().getName());
            i++;
          } 
          active = true;
        } 
      } else {
        break;
      } 
      i++;
    } 
    updateRandomProperties(context);
  }
  
  private static void updateRandomProperties(RandomEntityContext.Models context) {
    String[] prefixes = { "optifine/cem/" };
    String[] suffixes = { ".jem", ".properties" };
    String[] names = CustomModelRegistry.getModelNames();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
      Either<EntityType, BlockEntityType> type = modelAdapter.getType();
      RandomEntityProperties<IEntityRenderer> props = makeProperties(name, context);
      if (props == null)
        props = makeProperties(name + "/" + name, context); 
      if (props != null) {
        if (type != null && type.getLeft().isPresent())
          mapEntityProperties.put(type.getLeft().get(), props); 
        if (type != null && type.getRight().isPresent())
          mapBlockEntityProperties.put(type.getRight().get(), props); 
      } 
    } 
  }
  
  private static RandomEntityProperties makeProperties(String name, RandomEntityContext.Models context) {
    ResourceLocation locJem = new ResourceLocation("optifine/cem/" + name + ".jem");
    ResourceLocation locProps = new ResourceLocation("optifine/cem/" + name + ".properties");
    if (Config.hasResource(locProps)) {
      RandomEntityProperties randomEntityProperties = RandomEntityProperties.parse(locProps, locJem, (RandomEntityContext)context);
      if (randomEntityProperties != null)
        return randomEntityProperties; 
    } 
    if (!Config.hasResource(locJem))
      return null; 
    int[] variants = RandomEntities.getLocationsVariants(locJem, false, (RandomEntityContext)context);
    if (variants == null)
      return null; 
    RandomEntityProperties<IEntityRenderer> props = new RandomEntityProperties(locJem.getPath(), locJem, variants, (RandomEntityContext)context);
    if (!props.isValid(locJem.getPath()))
      return null; 
    return props;
  }
  
  private static void setEnchantmentScreenBookModel(BookModel bookModel) {
    customBookModel = bookModel;
  }
  
  private static Map<EntityType, EntityRenderer> getEntityRenderMap() {
    EntityRenderDispatcher rm = Minecraft.getInstance().getEntityRenderDispatcher();
    Map<EntityType, EntityRenderer> entityRenderMap = rm.getEntityRenderMap();
    if (entityRenderMap == null)
      return null; 
    if (originalEntityRenderMap == null)
      originalEntityRenderMap = new HashMap<>(entityRenderMap); 
    return entityRenderMap;
  }
  
  private static Map<BlockEntityType, BlockEntityRenderer> getTileEntityRenderMap() {
    BlockEntityRenderDispatcher blockEntityRenderDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
    Map<BlockEntityType, BlockEntityRenderer> tileEntityRenderMap = blockEntityRenderDispatcher.getBlockEntityRenderMap();
    if (originalTileEntityRenderMap == null)
      originalTileEntityRenderMap = new HashMap<>(tileEntityRenderMap); 
    return tileEntityRenderMap;
  }
  
  private static Map<SkullBlock.Type, SkullModelBase> getSkullModelMap() {
    Map<SkullBlock.Type, SkullModelBase> skullModelMap = SkullBlockRenderer.models;
    if (skullModelMap == null) {
      Config.warn("Field not found: SkullBlockRenderer.MODELS");
      skullModelMap = new HashMap<>();
    } 
    if (originalSkullModelMap == null)
      originalSkullModelMap = new HashMap<>(skullModelMap); 
    return skullModelMap;
  }
  
  private static ResourceLocation[] getModelLocations() {
    String prefix = "optifine/cem/";
    String suffix = ".jem";
    List<ResourceLocation> resourceLocations = new ArrayList<>();
    String[] names = CustomModelRegistry.getModelNames();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      String path = prefix + prefix + name;
      ResourceLocation loc = new ResourceLocation(path);
      if (Config.hasResource(loc) || debugModels)
        resourceLocations.add(loc); 
    } 
    ResourceLocation[] locs = resourceLocations.<ResourceLocation>toArray(new ResourceLocation[resourceLocations.size()]);
    return locs;
  }
  
  public static IEntityRenderer parseEntityRender(ResourceLocation location, RendererCache rendererCache, int index) {
    try {
      if (debugModels && index == 0)
        return makeDebugEntityRenderer(location, rendererCache, index); 
      JsonObject jo = CustomEntityModelParser.loadJson(location);
      IEntityRenderer render = parseEntityRender(jo, location.getPath(), rendererCache, index);
      return render;
    } catch (IOException e) {
      Config.error(e.getClass().getName() + ": " + e.getClass().getName());
      return null;
    } catch (JsonParseException e) {
      Config.error(e.getClass().getName() + ": " + e.getClass().getName());
      return null;
    } catch (Exception e) {
      Log.warn("Error loading CEM: " + String.valueOf(location), e);
      return null;
    } 
  }
  
  private static IEntityRenderer makeDebugEntityRenderer(ResourceLocation loc, RendererCache rendererCache, int index) {
    String path = loc.getPath();
    String nameJem = StrUtils.removePrefix(path, "optifine/cem/");
    String name = StrUtils.removeSuffix(nameJem, ".jem");
    ModelAdapter ma = CustomModelRegistry.getModelAdapter(name);
    Model model = ma.makeModel();
    DyeColor[] colors = DyeColor.values();
    int offset = Math.abs(loc.hashCode()) % 256;
    String[] partNames = ma.getModelRendererNames();
    for (int i = 0; i < partNames.length; i++) {
      String partName = partNames[i];
      ModelPart part = ma.getModelRenderer(model, partName);
      if (part != null) {
        DyeColor col = colors[(i + offset) % colors.length];
        ResourceLocation locTexture = new ResourceLocation("textures/block/" + col.getSerializedName() + "_stained_glass.png");
        part.setTextureLocation(locTexture);
        Config.dbg("  " + partName + ": " + col.getSerializedName());
      } 
    } 
    IEntityRenderer er = ma.makeEntityRender(model, ma.getShadowSize(), rendererCache, index);
    if (er == null)
      return null; 
    er.setType(ma.getType());
    return er;
  }
  
  private static IEntityRenderer parseEntityRender(JsonObject obj, String path, RendererCache rendererCache, int index) {
    CustomEntityRenderer cer = CustomEntityModelParser.parseEntityRender(obj, path);
    String name = cer.getName();
    name = StrUtils.trimTrailing(name, "0123456789");
    ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
    checkNull(modelAdapter, "Entity not found: " + name);
    Either<EntityType, BlockEntityType> type = modelAdapter.getType();
    IEntityRenderer render = makeEntityRender(modelAdapter, cer, rendererCache, index);
    if (render == null)
      return null; 
    render.setType(type);
    return render;
  }
  
  private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer, RendererCache rendererCache, int index) {
    ResourceLocation textureLocation = cer.getTextureLocation();
    CustomModelRenderer[] modelRenderers = cer.getCustomModelRenderers();
    float shadowSize = cer.getShadowSize();
    if (shadowSize < 0.0F)
      shadowSize = modelAdapter.getShadowSize(); 
    Model model = modelAdapter.makeModel();
    if (model == null)
      return null; 
    ModelResolver mr = new ModelResolver(modelAdapter, model, modelRenderers);
    if (!modifyModel(modelAdapter, model, modelRenderers, mr))
      return null; 
    IEntityRenderer r = modelAdapter.makeEntityRender(model, shadowSize, rendererCache, index);
    if (r == null)
      throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName()); 
    if (textureLocation != null)
      setTextureLocation(modelAdapter, model, r, textureLocation); 
    return r;
  }
  
  private static void setTextureLocation(ModelAdapter modelAdapter, Model model, IEntityRenderer er, ResourceLocation textureLocation) {
    if (modelAdapter.setTextureLocation(er, textureLocation))
      return; 
    if (er instanceof net.minecraft.client.renderer.entity.LivingEntityRenderer) {
      er.setLocationTextureCustom(textureLocation);
      return;
    } 
    setTextureTopModelRenderers(modelAdapter, model, textureLocation);
  }
  
  public static void setTextureTopModelRenderers(ModelAdapter modelAdapter, Model model, ResourceLocation textureLocation) {
    String[] parts = modelAdapter.getModelRendererNames();
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      ModelPart modelRenderer = modelAdapter.getModelRenderer(model, part);
      if (modelRenderer != null)
        if (modelRenderer.getTextureLocation() == null)
          modelRenderer.setTextureLocation(textureLocation);  
    } 
  }
  
  private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
    List<ModelVariableUpdater> listVariableUpdaters = new ArrayList<>();
    for (int i = 0; i < modelRenderers.length; i++) {
      CustomModelRenderer cmr = modelRenderers[i];
      if (!modifyModel(modelAdapter, model, cmr, mr))
        return false; 
      if (cmr.getModelRenderer().getModelUpdater() != null)
        listVariableUpdaters.addAll(Arrays.asList(cmr.getModelRenderer().getModelUpdater().getModelVariableUpdaters())); 
    } 
    ModelVariableUpdater[] mvus = listVariableUpdaters.<ModelVariableUpdater>toArray(new ModelVariableUpdater[listVariableUpdaters.size()]);
    ModelUpdater globvalUpdater = new ModelUpdater(mvus);
    int j;
    for (j = 0; j < modelRenderers.length; j++) {
      CustomModelRenderer cmr = modelRenderers[j];
      if (cmr.getModelRenderer().getModelUpdater() != null)
        cmr.getModelRenderer().setModelUpdater(globvalUpdater); 
    } 
    for (j = 0; j < mvus.length; j++) {
      ModelVariableUpdater mvu = mvus[j];
      IModelVariable mv = mvu.getModelVariable();
      if (mv instanceof IModelRendererVariable) {
        IModelRendererVariable mrv = (IModelRendererVariable)mv;
        mrv.getModelRenderer().setModelUpdater(globvalUpdater);
      } 
    } 
    return true;
  }
  
  private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
    String modelPart = customModelRenderer.getModelPart();
    ModelPart parent = modelAdapter.getModelRenderer(model, modelPart);
    if (parent == null) {
      Config.warn("Model part not found: " + modelPart + ", model: " + String.valueOf(model));
      return false;
    } 
    if (!customModelRenderer.isAttach()) {
      if (parent.cubes != null)
        parent.cubes.clear(); 
      if (parent.spriteList != null)
        parent.spriteList.clear(); 
      if (parent.children != null) {
        ModelPart[] mrs = modelAdapter.getModelRenderers(model);
        Set<ModelPart> setMrs = Collections.newSetFromMap(new IdentityHashMap<>());
        setMrs.addAll(Arrays.asList(mrs));
        Set<String> childModelKeys = new HashSet<>(parent.children.keySet());
        for (String key : childModelKeys) {
          ModelPart mr = (ModelPart)parent.children.get(key);
          if (setMrs.contains(mr))
            continue; 
          parent.children.remove(key);
        } 
      } 
    } 
    String childName = parent.getUniqueChildModelName("CEM-" + modelPart);
    parent.addChildModel(childName, customModelRenderer.getModelRenderer());
    ModelUpdater mu = customModelRenderer.getModelUpdater();
    if (mu != null) {
      modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
      modelResolver.setPartModelRenderer(parent);
      if (!mu.initialize((IModelResolver)modelResolver))
        return false; 
      customModelRenderer.getModelRenderer().setModelUpdater(mu);
    } 
    return true;
  }
  
  private static void checkNull(Object obj, String msg) {
    if (obj == null)
      throw new JsonParseException(msg); 
  }
  
  public static boolean isActive() {
    return active;
  }
  
  public static boolean isCustomModel(BlockState blockStateIn) {
    for (int i = 0; i < customTileEntityTypes.size(); i++) {
      BlockEntityType type = customTileEntityTypes.get(i);
      if (type.isValid(blockStateIn))
        return true; 
    } 
    return false;
  }
  
  public static void onRenderScreen(Screen screen) {
    if (customBookModel != null)
      if (screen instanceof EnchantmentScreen) {
        EnchantmentScreen es = (EnchantmentScreen)screen;
        Reflector.GuiEnchantment_bookModel.setValue(es, customBookModel);
      }  
  }
  
  public static EntityRenderer getEntityRenderer(Entity entityIn, EntityRenderer renderer) {
    if (mapEntityProperties.isEmpty())
      return renderer; 
    IRandomEntity randomEntity = RandomEntities.getRandomEntity(entityIn);
    if (randomEntity == null)
      return renderer; 
    RandomEntityProperties<IEntityRenderer> props = mapEntityProperties.get(entityIn.getType());
    if (props == null)
      return renderer; 
    IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
    if (!(ier instanceof EntityRenderer))
      return null; 
    matchingRuleIndex = props.getMatchingRuleIndex();
    EntityRenderer er = (EntityRenderer)ier;
    return er;
  }
  
  public static BlockEntityRenderer getBlockEntityRenderer(BlockEntity entityIn, BlockEntityRenderer renderer) {
    if (mapBlockEntityProperties.isEmpty())
      return renderer; 
    IRandomEntity randomEntity = RandomEntities.getRandomBlockEntity(entityIn);
    if (randomEntity == null)
      return renderer; 
    RandomEntityProperties<IEntityRenderer> props = mapBlockEntityProperties.get(entityIn.getType());
    if (props == null)
      return renderer; 
    IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
    if (!(ier instanceof BlockEntityRenderer))
      return null; 
    matchingRuleIndex = props.getMatchingRuleIndex();
    BlockEntityRenderer ber = (BlockEntityRenderer)ier;
    return ber;
  }
  
  public static int getMatchingRuleIndex() {
    return matchingRuleIndex;
  }
}
