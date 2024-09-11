package notch.net.optifine.entity.model;

import akr;
import bsr;
import bsx;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import cti;
import dnb;
import dqh;
import dqj;
import dtc;
import fgo;
import fod;
import fpn;
import fus;
import fwg;
import fwk;
import fxb;
import fxh;
import fxp;
import fyj;
import fyk;
import gem;
import ggy;
import ggz;
import ghi;
import gho;
import gkh;
import gki;
import glw;
import gmw;
import gos;
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
  
  private static Map<bsx, RandomEntityProperties<IEntityRenderer>> mapEntityProperties = new HashMap<>();
  
  private static Map<dqj, RandomEntityProperties<IEntityRenderer>> mapBlockEntityProperties = new HashMap<>();
  
  private static int matchingRuleIndex;
  
  private static Map<bsx, gki> originalEntityRenderMap = null;
  
  private static Map<dqj, ggz> originalTileEntityRenderMap = null;
  
  private static Map<dnb.a, fxh> originalSkullModelMap = null;
  
  private static List<dqj> customTileEntityTypes = new ArrayList<>();
  
  private static fus customBookModel;
  
  private static boolean debugModels = Boolean.getBoolean("cem.debug.models");
  
  public static final String PREFIX_OPTIFINE_CEM = "optifine/cem/";
  
  public static final String SUFFIX_JEM = ".jem";
  
  public static final String SUFFIX_PROPERTIES = ".properties";
  
  public static void update() {
    Map<bsx, gki> entityRenderMap = getEntityRenderMap();
    Map<dqj, ggz> tileEntityRenderMap = getTileEntityRenderMap();
    Map<dnb.a, fxh> skullModelMap = getSkullModelMap();
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
    gem blockEntityRenderer = fgo.Q().ar().getBlockEntityRenderer();
    blockEntityRenderer.k = new fxp(ModelAdapter.bakeModelLayer(fyj.bJ));
    blockEntityRenderer.j = new fxb(ModelAdapter.bakeModelLayer(fyj.bk));
    gos.customParrotModel = null;
    customBookModel = null;
    ggz.CACHED_TYPES.clear();
    if ((fgo.Q()).r != null) {
      Iterable<bsr> entities = (fgo.Q()).r.e();
      for (bsr entity : entities) {
        Map modelVariables = (entity.ar()).modelVariables;
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
    akr[] locs = getModelLocations();
    int i = 0;
    while (true) {
      if (i < locs.length) {
        akr loc = locs[i];
        Config.dbg("CustomEntityModel: " + loc.a());
        IEntityRenderer rc = parseEntityRender(loc, rendererCache, 0);
        if (rc != null) {
          Either<bsx, dqj> type = rc.getType();
          if (rc instanceof gki) {
            entityRenderMap.put(type.getLeft().get(), (gki)rc);
            rendererCache.put(type.getLeft().get(), 0, (gki)rc);
            if (rc instanceof gmw) {
              gmw tr = (gmw)rc;
              fxp tm = (fxp)Reflector.getFieldValue(tr, Reflector.RenderTrident_modelTrident);
              if (tm != null)
                blockEntityRenderer.k = tm; 
            } 
            if (rc instanceof glw) {
              glw pr = (glw)rc;
              fwk pm = (fwk)pr.a();
              if (pm != null)
                gos.customParrotModel = pm; 
            } 
          } else if (rc instanceof ggz) {
            tileEntityRenderMap.put(type.getRight().get(), (ggz)rc);
            rendererCache.put(type.getRight().get(), 0, (ggz)rc);
            if (rc instanceof ghi) {
              ghi etr = (ghi)rc;
              fus bm = (fus)Reflector.getFieldValue(etr, Reflector.TileEntityEnchantmentTableRenderer_modelBook);
              setEnchantmentScreenBookModel(bm);
            } 
            customTileEntityTypes.add(type.getRight().get());
          } else if (rc instanceof VirtualEntityRenderer) {
            VirtualEntityRenderer ver = (VirtualEntityRenderer)rc;
            if (ver.getModel() instanceof fxb) {
              fxb sm = (fxb)ver.getModel();
              blockEntityRenderer.j = sm;
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
      Either<bsx, dqj> type = modelAdapter.getType();
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
    akr locJem = new akr("optifine/cem/" + name + ".jem");
    akr locProps = new akr("optifine/cem/" + name + ".properties");
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
    RandomEntityProperties<IEntityRenderer> props = new RandomEntityProperties(locJem.a(), locJem, variants, (RandomEntityContext)context);
    if (!props.isValid(locJem.a()))
      return null; 
    return props;
  }
  
  private static void setEnchantmentScreenBookModel(fus bookModel) {
    customBookModel = bookModel;
  }
  
  private static Map<bsx, gki> getEntityRenderMap() {
    gkh rm = fgo.Q().ap();
    Map<bsx, gki> entityRenderMap = rm.getEntityRenderMap();
    if (entityRenderMap == null)
      return null; 
    if (originalEntityRenderMap == null)
      originalEntityRenderMap = new HashMap<>(entityRenderMap); 
    return entityRenderMap;
  }
  
  private static Map<dqj, ggz> getTileEntityRenderMap() {
    ggy blockEntityRenderDispatcher = fgo.Q().aq();
    Map<dqj, ggz> tileEntityRenderMap = blockEntityRenderDispatcher.getBlockEntityRenderMap();
    if (originalTileEntityRenderMap == null)
      originalTileEntityRenderMap = new HashMap<>(tileEntityRenderMap); 
    return tileEntityRenderMap;
  }
  
  private static Map<dnb.a, fxh> getSkullModelMap() {
    Map<dnb.a, fxh> skullModelMap = gho.models;
    if (skullModelMap == null) {
      Config.warn("Field not found: SkullBlockRenderer.MODELS");
      skullModelMap = new HashMap<>();
    } 
    if (originalSkullModelMap == null)
      originalSkullModelMap = new HashMap<>(skullModelMap); 
    return skullModelMap;
  }
  
  private static akr[] getModelLocations() {
    String prefix = "optifine/cem/";
    String suffix = ".jem";
    List<akr> resourceLocations = new ArrayList<>();
    String[] names = CustomModelRegistry.getModelNames();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      String path = prefix + prefix + name;
      akr loc = new akr(path);
      if (Config.hasResource(loc) || debugModels)
        resourceLocations.add(loc); 
    } 
    akr[] locs = resourceLocations.<akr>toArray(new akr[resourceLocations.size()]);
    return locs;
  }
  
  public static IEntityRenderer parseEntityRender(akr location, RendererCache rendererCache, int index) {
    try {
      if (debugModels && index == 0)
        return makeDebugEntityRenderer(location, rendererCache, index); 
      JsonObject jo = CustomEntityModelParser.loadJson(location);
      IEntityRenderer render = parseEntityRender(jo, location.a(), rendererCache, index);
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
  
  private static IEntityRenderer makeDebugEntityRenderer(akr loc, RendererCache rendererCache, int index) {
    String path = loc.a();
    String nameJem = StrUtils.removePrefix(path, "optifine/cem/");
    String name = StrUtils.removeSuffix(nameJem, ".jem");
    ModelAdapter ma = CustomModelRegistry.getModelAdapter(name);
    fwg model = ma.makeModel();
    cti[] colors = cti.values();
    int offset = Math.abs(loc.hashCode()) % 256;
    String[] partNames = ma.getModelRendererNames();
    for (int i = 0; i < partNames.length; i++) {
      String partName = partNames[i];
      fyk part = ma.getModelRenderer(model, partName);
      if (part != null) {
        cti col = colors[(i + offset) % colors.length];
        akr locTexture = new akr("textures/block/" + col.c() + "_stained_glass.png");
        part.setTextureLocation(locTexture);
        Config.dbg("  " + partName + ": " + col.c());
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
    Either<bsx, dqj> type = modelAdapter.getType();
    IEntityRenderer render = makeEntityRender(modelAdapter, cer, rendererCache, index);
    if (render == null)
      return null; 
    render.setType(type);
    return render;
  }
  
  private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer, RendererCache rendererCache, int index) {
    akr textureLocation = cer.getTextureLocation();
    CustomModelRenderer[] modelRenderers = cer.getCustomModelRenderers();
    float shadowSize = cer.getShadowSize();
    if (shadowSize < 0.0F)
      shadowSize = modelAdapter.getShadowSize(); 
    fwg model = modelAdapter.makeModel();
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
  
  private static void setTextureLocation(ModelAdapter modelAdapter, fwg model, IEntityRenderer er, akr textureLocation) {
    if (modelAdapter.setTextureLocation(er, textureLocation))
      return; 
    if (er instanceof glk) {
      er.setLocationTextureCustom(textureLocation);
      return;
    } 
    setTextureTopModelRenderers(modelAdapter, model, textureLocation);
  }
  
  public static void setTextureTopModelRenderers(ModelAdapter modelAdapter, fwg model, akr textureLocation) {
    String[] parts = modelAdapter.getModelRendererNames();
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      fyk modelRenderer = modelAdapter.getModelRenderer(model, part);
      if (modelRenderer != null)
        if (modelRenderer.getTextureLocation() == null)
          modelRenderer.setTextureLocation(textureLocation);  
    } 
  }
  
  private static boolean modifyModel(ModelAdapter modelAdapter, fwg model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
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
  
  private static boolean modifyModel(ModelAdapter modelAdapter, fwg model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
    String modelPart = customModelRenderer.getModelPart();
    fyk parent = modelAdapter.getModelRenderer(model, modelPart);
    if (parent == null) {
      Config.warn("Model part not found: " + modelPart + ", model: " + String.valueOf(model));
      return false;
    } 
    if (!customModelRenderer.isAttach()) {
      if (parent.m != null)
        parent.m.clear(); 
      if (parent.spriteList != null)
        parent.spriteList.clear(); 
      if (parent.n != null) {
        fyk[] mrs = modelAdapter.getModelRenderers(model);
        Set<fyk> setMrs = Collections.newSetFromMap(new IdentityHashMap<>());
        setMrs.addAll(Arrays.asList(mrs));
        Set<String> childModelKeys = new HashSet<>(parent.n.keySet());
        for (String key : childModelKeys) {
          fyk mr = (fyk)parent.n.get(key);
          if (setMrs.contains(mr))
            continue; 
          parent.n.remove(key);
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
  
  public static boolean isCustomModel(dtc blockStateIn) {
    for (int i = 0; i < customTileEntityTypes.size(); i++) {
      dqj type = customTileEntityTypes.get(i);
      if (type.a(blockStateIn))
        return true; 
    } 
    return false;
  }
  
  public static void onRenderScreen(fod screen) {
    if (customBookModel != null)
      if (screen instanceof fpn) {
        fpn es = (fpn)screen;
        Reflector.GuiEnchantment_bookModel.setValue(es, customBookModel);
      }  
  }
  
  public static gki getEntityRenderer(bsr entityIn, gki renderer) {
    if (mapEntityProperties.isEmpty())
      return renderer; 
    IRandomEntity randomEntity = RandomEntities.getRandomEntity(entityIn);
    if (randomEntity == null)
      return renderer; 
    RandomEntityProperties<IEntityRenderer> props = mapEntityProperties.get(entityIn.am());
    if (props == null)
      return renderer; 
    IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
    if (!(ier instanceof gki))
      return null; 
    matchingRuleIndex = props.getMatchingRuleIndex();
    gki er = (gki)ier;
    return er;
  }
  
  public static ggz getBlockEntityRenderer(dqh entityIn, ggz renderer) {
    if (mapBlockEntityProperties.isEmpty())
      return renderer; 
    IRandomEntity randomEntity = RandomEntities.getRandomBlockEntity(entityIn);
    if (randomEntity == null)
      return renderer; 
    RandomEntityProperties<IEntityRenderer> props = mapBlockEntityProperties.get(entityIn.r());
    if (props == null)
      return renderer; 
    IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
    if (!(ier instanceof ggz))
      return null; 
    matchingRuleIndex = props.getMatchingRuleIndex();
    ggz ber = (ggz)ier;
    return ber;
  }
  
  public static int getMatchingRuleIndex() {
    return matchingRuleIndex;
  }
}
