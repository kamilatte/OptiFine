package srg.net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.optifine.Config;
import net.optifine.DynamicLight;
import net.optifine.DynamicLightsMap;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityTypeNameLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;

public class DynamicLights {
  private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
  
  private static Map<String, Integer> mapEntityLightLevels = new HashMap<>();
  
  private static Map<Item, Integer> mapItemLightLevels = new HashMap<>();
  
  private static long timeUpdateMs = 0L;
  
  private static final double MAX_DIST = 7.5D;
  
  private static final double MAX_DIST_SQ = 56.25D;
  
  private static final int LIGHT_LEVEL_MAX = 15;
  
  private static final int LIGHT_LEVEL_FIRE = 15;
  
  private static final int LIGHT_LEVEL_BLAZE = 10;
  
  private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
  
  private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
  
  private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
  
  private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
  
  private static final int LIGHT_LEVEL_GLOW_SQUID = 11;
  
  private static final int LIGHT_LEVEL_GLOW_INK_SAC = 8;
  
  private static final int LIGHT_LEVEL_GLOW_LICHEN = 6;
  
  private static final int LIGHT_LEVEL_GLOW_BERRIES = 12;
  
  private static final int LIGHT_LEVEL_GLOW_ITEM_FRAME = 8;
  
  private static final EntityDataAccessor<ItemStack> PARAMETER_ITEM_STACK = (EntityDataAccessor<ItemStack>)Reflector.EntityItem_ITEM.getValue();
  
  private static boolean initialized;
  
  public static void entityAdded(Entity entityIn, LevelRenderer renderGlobal) {}
  
  public static void entityRemoved(Entity entityIn, LevelRenderer renderGlobal) {
    synchronized (mapDynamicLights) {
      DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.getId());
      if (dynamicLight != null)
        dynamicLight.updateLitChunks(renderGlobal); 
    } 
  }
  
  public static void update(LevelRenderer renderGlobal) {
    long timeNowMs = System.currentTimeMillis();
    if (timeNowMs < timeUpdateMs + 50L)
      return; 
    timeUpdateMs = timeNowMs;
    if (!initialized)
      initialize(); 
    synchronized (mapDynamicLights) {
      updateMapDynamicLights(renderGlobal);
      if (mapDynamicLights.size() <= 0)
        return; 
      List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
      for (int i = 0; i < dynamicLights.size(); i++) {
        DynamicLight dynamicLight = dynamicLights.get(i);
        dynamicLight.update(renderGlobal);
      } 
    } 
  }
  
  private static void initialize() {
    initialized = true;
    mapEntityLightLevels.clear();
    mapItemLightLevels.clear();
    String[] modIds = ReflectorForge.getForgeModIds();
    for (int i = 0; i < modIds.length; i++) {
      String modId = modIds[i];
      try {
        ResourceLocation loc = new ResourceLocation(modId, "optifine/dynamic_lights.properties");
        InputStream in = Config.getResourceStream(loc);
        loadModConfiguration(in, loc.toString(), modId);
      } catch (IOException iOException) {}
    } 
    if (mapEntityLightLevels.size() > 0)
      Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size()); 
    if (mapItemLightLevels.size() > 0)
      Config.dbg("DynamicLights items: " + mapItemLightLevels.size()); 
  }
  
  private static void loadModConfiguration(InputStream in, String path, String modId) {
    if (in == null)
      return; 
    try {
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      Config.dbg("DynamicLights: Parsing " + path);
      ConnectedParser cp = new ConnectedParser("DynamicLights");
      loadModLightLevels(propertiesOrdered.getProperty("entities"), mapEntityLightLevels, (IObjectLocator<String>)new EntityTypeNameLocator(), cp, path, modId);
      loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, (IObjectLocator<Item>)new ItemLocator(), cp, path, modId);
    } catch (IOException e) {
      Config.warn("DynamicLights: Error reading " + path);
    } 
  }
  
  private static <T> void loadModLightLevels(String prop, Map<T, Integer> mapLightLevels, IObjectLocator<T> ol, ConnectedParser cp, String path, String modId) {
    if (prop == null)
      return; 
    String[] parts = Config.tokenize(prop, " ");
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      String[] tokens = Config.tokenize(part, ":");
      if (tokens.length != 2) {
        cp.warn("Invalid entry: " + part + ", in:" + path);
      } else {
        String name = tokens[0];
        String light = tokens[1];
        String nameFull = modId + ":" + modId;
        ResourceLocation loc = new ResourceLocation(nameFull);
        T obj = (T)ol.getObject(loc);
        if (obj == null) {
          cp.warn("Object not found: " + nameFull);
        } else {
          int lightLevel = cp.parseInt(light, -1);
          if (lightLevel < 0 || lightLevel > 15) {
            cp.warn("Invalid light level: " + part);
          } else {
            mapLightLevels.put(obj, new Integer(lightLevel));
          } 
        } 
      } 
    } 
  }
  
  private static void updateMapDynamicLights(LevelRenderer renderGlobal) {
    ClientLevel world = renderGlobal.getWorld();
    if (world == null)
      return; 
    Iterable<Entity> entities = world.entitiesForRendering();
    for (Entity entity : entities) {
      int lightLevel = getLightLevel(entity);
      if (lightLevel > 0) {
        int i = entity.getId();
        DynamicLight dynamicLight1 = mapDynamicLights.get(i);
        if (dynamicLight1 == null) {
          dynamicLight1 = new DynamicLight(entity);
          mapDynamicLights.put(i, dynamicLight1);
        } 
        continue;
      } 
      int key = entity.getId();
      DynamicLight dynamicLight = mapDynamicLights.remove(key);
      if (dynamicLight != null)
        dynamicLight.updateLitChunks(renderGlobal); 
    } 
  }
  
  public static int getCombinedLight(BlockPos pos, int combinedLight) {
    double lightPos = getLightLevel(pos);
    combinedLight = getCombinedLight(lightPos, combinedLight);
    return combinedLight;
  }
  
  public static int getCombinedLight(Entity entity, int combinedLight) {
    double lightPos = getLightLevel(entity.blockPosition());
    if (entity == (Config.getMinecraft()).player) {
      double lightOwn = getLightLevel(entity);
      lightPos = Math.max(lightPos, lightOwn);
    } 
    combinedLight = getCombinedLight(lightPos, combinedLight);
    return combinedLight;
  }
  
  public static int getCombinedLight(double lightPlayer, int combinedLight) {
    if (lightPlayer > 0.0D) {
      int lightPlayerFF = (int)(lightPlayer * 16.0D);
      int lightBlockFF = combinedLight & 0xFF;
      if (lightPlayerFF > lightBlockFF) {
        combinedLight &= 0xFFFFFF00;
        combinedLight |= lightPlayerFF;
      } 
    } 
    return combinedLight;
  }
  
  public static double getLightLevel(BlockPos pos) {
    double lightLevelMax = 0.0D;
    synchronized (mapDynamicLights) {
      List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
      int dynamicLightsSize = dynamicLights.size();
      for (int i = 0; i < dynamicLightsSize; i++) {
        DynamicLight dynamicLight = dynamicLights.get(i);
        int dynamicLightLevel = dynamicLight.getLastLightLevel();
        if (dynamicLightLevel > 0) {
          double px = dynamicLight.getLastPosX();
          double py = dynamicLight.getLastPosY();
          double pz = dynamicLight.getLastPosZ();
          double dx = pos.getX() - px;
          double dy = pos.getY() - py;
          double dz = pos.getZ() - pz;
          double distSq = dx * dx + dy * dy + dz * dz;
          if (distSq <= 56.25D) {
            double dist = Math.sqrt(distSq);
            double light = 1.0D - dist / 7.5D;
            double lightLevel = light * dynamicLightLevel;
            if (lightLevel > lightLevelMax)
              lightLevelMax = lightLevel; 
          } 
        } 
      } 
    } 
    double lightPlayer = Config.limit(lightLevelMax, 0.0D, 15.0D);
    return lightPlayer;
  }
  
  public static int getLightLevel(ItemStack itemStack) {
    if (itemStack == null)
      return 0; 
    Item item = itemStack.getItem();
    if (item instanceof BlockItem) {
      BlockItem itemBlock = (BlockItem)item;
      Block block = itemBlock.getBlock();
      if (block != null) {
        if (block == Blocks.LIGHT)
          return 0; 
        if (block == Blocks.GLOW_LICHEN)
          return 6; 
        if (block == Blocks.CAVE_VINES)
          return 12; 
        return block.defaultBlockState().getLightEmission();
      } 
    } 
    if (item == Items.LAVA_BUCKET)
      return Blocks.LAVA.defaultBlockState().getLightEmission(); 
    if (item == Items.BLAZE_ROD || item == Items.BLAZE_POWDER)
      return 10; 
    if (item == Items.GLOWSTONE_DUST)
      return 8; 
    if (item == Items.PRISMARINE_CRYSTALS)
      return 8; 
    if (item == Items.MAGMA_CREAM)
      return 8; 
    if (item == Items.NETHER_STAR)
      return Blocks.BEACON.defaultBlockState().getLightEmission() / 2; 
    if (item == Items.GLOW_INK_SAC)
      return 8; 
    if (item == Items.GLOW_ITEM_FRAME)
      return 8; 
    if (!mapItemLightLevels.isEmpty()) {
      Integer level = mapItemLightLevels.get(item);
      if (level != null)
        return level.intValue(); 
    } 
    return 0;
  }
  
  public static int getLightLevel(Entity entity) {
    if (entity == Config.getMinecraft().getCameraEntity())
      if (!Config.isDynamicHandLight())
        return 0;  
    if (entity instanceof Player) {
      Player player = (Player)entity;
      if (player.isSpectator())
        return 0; 
    } 
    if (entity.isOnFire())
      return 15; 
    if (!mapEntityLightLevels.isEmpty()) {
      String typeName = EntityTypeNameLocator.getEntityTypeName(entity);
      Integer level = mapEntityLightLevels.get(typeName);
      if (level != null)
        return level.intValue(); 
    } 
    if (entity instanceof net.minecraft.world.entity.projectile.AbstractHurtingProjectile)
      return 15; 
    if (entity instanceof net.minecraft.world.entity.item.PrimedTnt)
      return 15; 
    if (entity instanceof Blaze) {
      Blaze entityBlaze = (Blaze)entity;
      if (entityBlaze.isOnFire())
        return 15; 
      return 10;
    } 
    if (entity instanceof MagmaCube) {
      MagmaCube emc = (MagmaCube)entity;
      if (emc.squish > 0.6D)
        return 13; 
      return 8;
    } 
    if (entity instanceof Creeper) {
      Creeper entityCreeper = (Creeper)entity;
      if (entityCreeper.getSwelling(0.0F) > 0.001D)
        return 15; 
    } 
    if (entity instanceof GlowSquid) {
      GlowSquid glowSquid = (GlowSquid)entity;
      int squidLight = (int)Mth.clampedLerp(0.0F, 11.0F, 1.0F - glowSquid.getDarkTicksRemaining() / 10.0F);
      return squidLight;
    } 
    if (entity instanceof net.minecraft.world.entity.decoration.GlowItemFrame)
      return 8; 
    if (entity instanceof LivingEntity) {
      LivingEntity player = (LivingEntity)entity;
      ItemStack stackMain = player.getMainHandItem();
      int levelMain = getLightLevel(stackMain);
      ItemStack stackOff = player.getOffhandItem();
      int levelOff = getLightLevel(stackOff);
      ItemStack stackHead = player.getItemBySlot(EquipmentSlot.HEAD);
      int levelHead = getLightLevel(stackHead);
      int levelHandMax = Math.max(levelMain, levelOff);
      return Math.max(levelHandMax, levelHead);
    } 
    if (entity instanceof ItemEntity) {
      ItemEntity entityItem = (ItemEntity)entity;
      ItemStack itemStack = getItemStack(entityItem);
      return getLightLevel(itemStack);
    } 
    return 0;
  }
  
  public static void removeLights(LevelRenderer renderGlobal) {
    synchronized (mapDynamicLights) {
      List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
      for (int i = 0; i < dynamicLights.size(); i++) {
        DynamicLight dynamicLight = dynamicLights.get(i);
        dynamicLight.updateLitChunks(renderGlobal);
      } 
      mapDynamicLights.clear();
    } 
  }
  
  public static void clear() {
    synchronized (mapDynamicLights) {
      mapDynamicLights.clear();
    } 
  }
  
  public static int getCount() {
    synchronized (mapDynamicLights) {
      return mapDynamicLights.size();
    } 
  }
  
  public static ItemStack getItemStack(ItemEntity entityItem) {
    ItemStack itemstack = (ItemStack)entityItem.getEntityData().get(PARAMETER_ITEM_STACK);
    return itemstack;
  }
}
