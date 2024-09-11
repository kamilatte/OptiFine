package notch.net.optifine;

import ajw;
import akr;
import ayo;
import bsr;
import bsy;
import bte;
import btn;
import cjh;
import cjm;
import cjp;
import ckc;
import cmx;
import cso;
import cul;
import cuq;
import cut;
import dfy;
import dga;
import fzf;
import gex;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jd;
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
  
  private static Map<cul, Integer> mapItemLightLevels = new HashMap<>();
  
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
  
  private static final ajw<cuq> PARAMETER_ITEM_STACK = (ajw<cuq>)Reflector.EntityItem_ITEM.getValue();
  
  private static boolean initialized;
  
  public static void entityAdded(bsr entityIn, gex renderGlobal) {}
  
  public static void entityRemoved(bsr entityIn, gex renderGlobal) {
    synchronized (mapDynamicLights) {
      DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.an());
      if (dynamicLight != null)
        dynamicLight.updateLitChunks(renderGlobal); 
    } 
  }
  
  public static void update(gex renderGlobal) {
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
        akr loc = new akr(modId, "optifine/dynamic_lights.properties");
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
      loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, (IObjectLocator<cul>)new ItemLocator(), cp, path, modId);
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
        akr loc = new akr(nameFull);
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
  
  private static void updateMapDynamicLights(gex renderGlobal) {
    fzf world = renderGlobal.getWorld();
    if (world == null)
      return; 
    Iterable<bsr> entities = world.e();
    for (bsr entity : entities) {
      int lightLevel = getLightLevel(entity);
      if (lightLevel > 0) {
        int i = entity.an();
        DynamicLight dynamicLight1 = mapDynamicLights.get(i);
        if (dynamicLight1 == null) {
          dynamicLight1 = new DynamicLight(entity);
          mapDynamicLights.put(i, dynamicLight1);
        } 
        continue;
      } 
      int key = entity.an();
      DynamicLight dynamicLight = mapDynamicLights.remove(key);
      if (dynamicLight != null)
        dynamicLight.updateLitChunks(renderGlobal); 
    } 
  }
  
  public static int getCombinedLight(jd pos, int combinedLight) {
    double lightPos = getLightLevel(pos);
    combinedLight = getCombinedLight(lightPos, combinedLight);
    return combinedLight;
  }
  
  public static int getCombinedLight(bsr entity, int combinedLight) {
    double lightPos = getLightLevel(entity.do());
    if (entity == (Config.getMinecraft()).s) {
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
  
  public static double getLightLevel(jd pos) {
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
          double dx = pos.u() - px;
          double dy = pos.v() - py;
          double dz = pos.w() - pz;
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
  
  public static int getLightLevel(cuq itemStack) {
    if (itemStack == null)
      return 0; 
    cul item = itemStack.g();
    if (item instanceof cso) {
      cso itemBlock = (cso)item;
      dfy block = itemBlock.d();
      if (block != null) {
        if (block == dga.hX)
          return 0; 
        if (block == dga.fg)
          return 6; 
        if (block == dga.sv)
          return 12; 
        return block.o().h();
      } 
    } 
    if (item == cut.qA)
      return dga.H.o().h(); 
    if (item == cut.sg || item == cut.so)
      return 10; 
    if (item == cut.qY)
      return 8; 
    if (item == cut.uz)
      return 8; 
    if (item == cut.sp)
      return 8; 
    if (item == cut.us)
      return dga.fO.o().h() / 2; 
    if (item == cut.rg)
      return 8; 
    if (item == cut.ud)
      return 8; 
    if (!mapItemLightLevels.isEmpty()) {
      Integer level = mapItemLightLevels.get(item);
      if (level != null)
        return level.intValue(); 
    } 
    return 0;
  }
  
  public static int getLightLevel(bsr entity) {
    if (entity == Config.getMinecraft().an())
      if (!Config.isDynamicHandLight())
        return 0;  
    if (entity instanceof cmx) {
      cmx player = (cmx)entity;
      if (player.R_())
        return 0; 
    } 
    if (entity.bR())
      return 15; 
    if (!mapEntityLightLevels.isEmpty()) {
      String typeName = EntityTypeNameLocator.getEntityTypeName(entity);
      Integer level = mapEntityLightLevels.get(typeName);
      if (level != null)
        return level.intValue(); 
    } 
    if (entity instanceof cne)
      return 15; 
    if (entity instanceof cji)
      return 15; 
    if (entity instanceof cjm) {
      cjm entityBlaze = (cjm)entity;
      if (entityBlaze.bR())
        return 15; 
      return 10;
    } 
    if (entity instanceof ckc) {
      ckc emc = (ckc)entity;
      if (emc.cb > 0.6D)
        return 13; 
      return 8;
    } 
    if (entity instanceof cjp) {
      cjp entityCreeper = (cjp)entity;
      if (entityCreeper.H(0.0F) > 0.001D)
        return 15; 
    } 
    if (entity instanceof bte) {
      bte glowSquid = (bte)entity;
      int squidLight = (int)ayo.b(0.0F, 11.0F, 1.0F - glowSquid.x() / 10.0F);
      return squidLight;
    } 
    if (entity instanceof ciy)
      return 8; 
    if (entity instanceof btn) {
      btn player = (btn)entity;
      cuq stackMain = player.eT();
      int levelMain = getLightLevel(stackMain);
      cuq stackOff = player.eU();
      int levelOff = getLightLevel(stackOff);
      cuq stackHead = player.a(bsy.f);
      int levelHead = getLightLevel(stackHead);
      int levelHandMax = Math.max(levelMain, levelOff);
      return Math.max(levelHandMax, levelHead);
    } 
    if (entity instanceof cjh) {
      cjh entityItem = (cjh)entity;
      cuq itemStack = getItemStack(entityItem);
      return getLightLevel(itemStack);
    } 
    return 0;
  }
  
  public static void removeLights(gex renderGlobal) {
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
  
  public static cuq getItemStack(cjh entityItem) {
    cuq itemstack = (cuq)entityItem.ar().a(PARAMETER_ITEM_STACK);
    return itemstack;
  }
}
