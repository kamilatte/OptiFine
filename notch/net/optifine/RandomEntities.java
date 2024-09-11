package notch.net.optifine;

import aka;
import akr;
import bsr;
import btn;
import cgb;
import chk;
import dcw;
import ddw;
import dqh;
import fgo;
import fzf;
import gdy;
import geb;
import ggy;
import gkh;
import gql;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntity;
import net.optifine.RandomEntityContext;
import net.optifine.RandomEntityProperties;
import net.optifine.RandomTileEntity;
import net.optifine.reflect.ReflectorRaw;
import net.optifine.util.ArrayUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import ub;

public class RandomEntities {
  private static Map<String, RandomEntityProperties<akr>> mapProperties = new HashMap<>();
  
  private static Map<String, RandomEntityProperties<akr>> mapSpriteProperties = new HashMap<>();
  
  private static boolean active = false;
  
  private static gkh entityRenderDispatcher;
  
  private static RandomEntity randomEntity = new RandomEntity();
  
  private static ggy tileEntityRendererDispatcher;
  
  private static RandomTileEntity randomTileEntity = new RandomTileEntity();
  
  private static boolean working = false;
  
  public static final String SUFFIX_PNG = ".png";
  
  public static final String SUFFIX_PROPERTIES = ".properties";
  
  public static final String SEPARATOR_DIGITS = ".";
  
  public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
  
  public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
  
  public static final String PREFIX_TEXTURES = "textures/";
  
  public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
  
  public static final String PREFIX_OPTIFINE = "optifine/";
  
  public static final String PREFIX_OPTIFINE_MOB = "optifine/mob/";
  
  private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
  
  private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
  
  private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, chk.class, String[].class, 0);
  
  private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, chk.class, String[].class, 1);
  
  public static void entityLoaded(bsr entity, dcw world) {
    if (world == null)
      return; 
    aka edm = entity.ar();
    edm.spawnPosition = entity.do();
    edm.spawnBiome = (ddw)world.t(edm.spawnPosition).a();
    if (entity instanceof cgb) {
      cgb esr = (cgb)entity;
      checkEntityShoulder(esr, false);
    } 
  }
  
  public static void entityUnloaded(bsr entity, dcw world) {
    if (entity instanceof cgb) {
      cgb esr = (cgb)entity;
      checkEntityShoulder(esr, true);
    } 
  }
  
  public static void checkEntityShoulder(cgb entity, boolean attach) {
    geb geb;
    btn owner = entity.T_();
    if (owner == null)
      geb = (Config.getMinecraft()).s; 
    if (!(geb instanceof gdy))
      return; 
    gdy player = (gdy)geb;
    UUID entityUuid = entity.cz();
    if (attach) {
      player.lastAttachedEntity = entity;
      ub nbtLeft = player.gp();
      if (nbtLeft != null && nbtLeft.e("UUID") && Config.equals(nbtLeft.a("UUID"), entityUuid)) {
        player.entityShoulderLeft = entity;
        player.lastAttachedEntity = null;
      } 
      ub nbtRight = player.gq();
      if (nbtRight != null && nbtRight.e("UUID") && Config.equals(nbtRight.a("UUID"), entityUuid)) {
        player.entityShoulderRight = entity;
        player.lastAttachedEntity = null;
      } 
    } else {
      aka edm = entity.ar();
      if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.cz(), entityUuid)) {
        aka edmShoulderLeft = player.entityShoulderLeft.ar();
        edm.spawnPosition = edmShoulderLeft.spawnPosition;
        edm.spawnBiome = edmShoulderLeft.spawnBiome;
        player.entityShoulderLeft = null;
      } 
      if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.cz(), entityUuid)) {
        aka edmShoulderRight = player.entityShoulderRight.ar();
        edm.spawnPosition = edmShoulderRight.spawnPosition;
        edm.spawnBiome = edmShoulderRight.spawnBiome;
        player.entityShoulderRight = null;
      } 
    } 
  }
  
  public static void worldChanged(dcw oldWorld, dcw newWorld) {
    if (newWorld instanceof fzf) {
      fzf newWorldClient = (fzf)newWorld;
      Iterable<bsr> entities = newWorldClient.e();
      for (bsr entity : entities)
        entityLoaded(entity, newWorld); 
    } 
    randomEntity.setEntity(null);
    randomTileEntity.setTileEntity(null);
  }
  
  public static akr getTextureLocation(akr loc) {
    if (!active)
      return loc; 
    IRandomEntity re = getRandomEntityRendered();
    if (re == null)
      return loc; 
    if (working)
      return loc; 
    try {
      working = true;
      String name = loc.a();
      if (name.startsWith("horse/"))
        name = getHorseTexturePath(name, "horse/".length()); 
      if (!name.startsWith("textures/entity/") && !name.startsWith("textures/painting/"))
        return loc; 
      RandomEntityProperties<akr> props = mapProperties.get(name);
      if (props == null)
        return loc; 
      return (akr)props.getResource(re, loc);
    } finally {
      working = false;
    } 
  }
  
  private static String getHorseTexturePath(String path, int pos) {
    if (HORSE_TEXTURES == null || HORSE_TEXTURES_ABBR == null)
      return path; 
    for (int i = 0; i < HORSE_TEXTURES_ABBR.length; i++) {
      String abbr = HORSE_TEXTURES_ABBR[i];
      if (path.startsWith(abbr, pos))
        return HORSE_TEXTURES[i]; 
    } 
    return path;
  }
  
  public static IRandomEntity getRandomEntityRendered() {
    if (entityRenderDispatcher.getRenderedEntity() != null) {
      randomEntity.setEntity(entityRenderDispatcher.getRenderedEntity());
      return (IRandomEntity)randomEntity;
    } 
    if (ggy.tileEntityRendered != null) {
      dqh te = ggy.tileEntityRendered;
      if (te.i() != null) {
        randomTileEntity.setTileEntity(te);
        return (IRandomEntity)randomTileEntity;
      } 
    } 
    return null;
  }
  
  public static IRandomEntity getRandomEntity(bsr entityIn) {
    randomEntity.setEntity(entityIn);
    return (IRandomEntity)randomEntity;
  }
  
  public static IRandomEntity getRandomBlockEntity(dqh tileEntityIn) {
    randomTileEntity.setTileEntity(tileEntityIn);
    return (IRandomEntity)randomTileEntity;
  }
  
  private static RandomEntityProperties<akr> makeProperties(akr loc, RandomEntityContext.Textures context) {
    String path = loc.a();
    akr locProps = getLocationProperties(loc, context.isLegacy());
    if (locProps != null) {
      RandomEntityProperties<akr> props = RandomEntityProperties.parse(locProps, loc, (RandomEntityContext)context);
      if (props != null)
        return props; 
    } 
    int[] variants = getLocationsVariants(loc, context.isLegacy(), (RandomEntityContext)context);
    if (variants == null)
      return null; 
    return new RandomEntityProperties(path, loc, variants, (RandomEntityContext)context);
  }
  
  private static akr getLocationProperties(akr loc, boolean legacy) {
    akr locMcp = getLocationRandom(loc, legacy);
    if (locMcp == null)
      return null; 
    String domain = locMcp.b();
    String path = locMcp.a();
    String pathBase = StrUtils.removeSuffix(path, ".png");
    String pathProps = pathBase + ".properties";
    akr locProps = new akr(domain, pathProps);
    if (Config.hasResource(locProps))
      return locProps; 
    String pathParent = getParentTexturePath(pathBase);
    if (pathParent == null)
      return null; 
    akr locParentProps = new akr(domain, pathParent + ".properties");
    if (Config.hasResource(locParentProps))
      return locParentProps; 
    return null;
  }
  
  protected static akr getLocationRandom(akr loc, boolean legacy) {
    String domain = loc.b();
    String path = loc.a();
    if (path.startsWith("optifine/"))
      return loc; 
    String prefixTextures = "textures/";
    String prefixRandom = "optifine/random/";
    if (legacy) {
      prefixTextures = "textures/entity/";
      prefixRandom = "optifine/mob/";
    } 
    if (!path.startsWith(prefixTextures))
      return null; 
    String pathRandom = StrUtils.replacePrefix(path, prefixTextures, prefixRandom);
    return new akr(domain, pathRandom);
  }
  
  private static String getPathBase(String pathRandom) {
    if (pathRandom.startsWith("optifine/random/"))
      return StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/"); 
    if (pathRandom.startsWith("optifine/mob/"))
      return StrUtils.replacePrefix(pathRandom, "optifine/mob/", "textures/entity/"); 
    return null;
  }
  
  protected static akr getLocationIndexed(akr loc, int index) {
    if (loc == null)
      return null; 
    String path = loc.a();
    int pos = path.lastIndexOf('.');
    if (pos < 0)
      return null; 
    String prefix = path.substring(0, pos);
    String suffix = path.substring(pos);
    String separator = StrUtils.endsWithDigit(prefix) ? "." : "";
    String pathNew = prefix + prefix + separator + index;
    akr locNew = new akr(loc.b(), pathNew);
    return locNew;
  }
  
  private static String getParentTexturePath(String path) {
    for (int i = 0; i < DEPENDANT_SUFFIXES.length; ) {
      String suffix = DEPENDANT_SUFFIXES[i];
      if (!path.endsWith(suffix)) {
        i++;
        continue;
      } 
      String pathParent = StrUtils.removeSuffix(path, suffix);
      return pathParent;
    } 
    return null;
  }
  
  public static int[] getLocationsVariants(akr loc, boolean legacy, RandomEntityContext context) {
    List<Integer> list = new ArrayList<>();
    list.add(Integer.valueOf(1));
    akr locRandom = getLocationRandom(loc, legacy);
    if (locRandom == null)
      return null; 
    for (int i = 1; i < list.size() + 10; i++) {
      int index = i + 1;
      akr locIndex = getLocationIndexed(locRandom, index);
      if (Config.hasResource(locIndex))
        list.add(Integer.valueOf(index)); 
    } 
    if (list.size() <= 1)
      return null; 
    Integer[] arr = list.<Integer>toArray(new Integer[list.size()]);
    int[] intArr = ArrayUtils.toPrimitive(arr);
    Config.dbg(context.getName() + ": " + context.getName() + ", variants: " + loc.a());
    return intArr;
  }
  
  public static void update() {
    entityRenderDispatcher = Config.getEntityRenderDispatcher();
    tileEntityRendererDispatcher = fgo.Q().aq();
    mapProperties.clear();
    mapSpriteProperties.clear();
    active = false;
    if (!Config.isRandomEntities())
      return; 
    initialize();
  }
  
  private static void initialize() {
    String[] prefixes = { "optifine/random/", "optifine/mob/" };
    String[] suffixes = { ".png", ".properties" };
    String[] pathsRandom = ResUtils.collectFiles(prefixes, suffixes);
    Set<String> basePathsChecked = new HashSet();
    for (int i = 0; i < pathsRandom.length; i++) {
      String path = pathsRandom[i];
      path = StrUtils.removeSuffix(path, suffixes);
      path = StrUtils.trimTrailing(path, "0123456789");
      path = StrUtils.removeSuffix(path, ".");
      path = path + ".png";
      String pathBase = getPathBase(path);
      if (!basePathsChecked.contains(pathBase)) {
        basePathsChecked.add(pathBase);
        akr locBase = new akr(pathBase);
        if (Config.hasResource(locBase)) {
          RandomEntityProperties<akr> props = mapProperties.get(pathBase);
          if (props == null) {
            props = makeProperties(locBase, new RandomEntityContext.Textures(false));
            if (props == null)
              props = makeProperties(locBase, new RandomEntityContext.Textures(true)); 
            if (props != null)
              mapProperties.put(pathBase, props); 
          } 
        } 
      } 
    } 
    active = !mapProperties.isEmpty();
  }
  
  public static synchronized void registerSprites(akr atlasLocation, Set<akr> spriteLocations) {
    if (mapProperties.isEmpty())
      return; 
    String prefix = getTexturePrefix(atlasLocation);
    Set<akr> newLocations = new HashSet<>();
    for (akr loc : spriteLocations) {
      String pathFull = "textures/" + prefix + loc.a() + ".png";
      RandomEntityProperties<akr> props = mapProperties.get(pathFull);
      if (props == null)
        continue; 
      mapSpriteProperties.put(loc.a(), props);
      List<akr> locs = props.getAllResources();
      if (locs == null)
        continue; 
      for (int i = 0; i < locs.size(); i++) {
        akr propLoc = locs.get(i);
        akr locSprite = TextureUtils.getSpriteLocation(propLoc);
        newLocations.add(locSprite);
        mapSpriteProperties.put(locSprite.a(), props);
      } 
    } 
    spriteLocations.addAll(newLocations);
  }
  
  private static String getTexturePrefix(akr atlasLocation) {
    if (atlasLocation.a().endsWith("/paintings.png"))
      return "painting/"; 
    return "";
  }
  
  public static gql getRandomSprite(gql spriteIn) {
    if (!active)
      return spriteIn; 
    IRandomEntity re = getRandomEntityRendered();
    if (re == null)
      return spriteIn; 
    if (working)
      return spriteIn; 
    try {
      working = true;
      akr locSpriteIn = spriteIn.getName();
      String name = locSpriteIn.a();
      RandomEntityProperties<akr> props = mapSpriteProperties.get(name);
      if (props == null)
        return spriteIn; 
      akr loc = (akr)props.getResource(re, locSpriteIn);
      if (loc == locSpriteIn)
        return spriteIn; 
      akr locSprite = TextureUtils.getSpriteLocation(loc);
      gql sprite = spriteIn.getTextureAtlas().a(locSprite);
      return sprite;
    } finally {
      working = false;
    } 
  }
  
  public static void dbg(String str) {
    Config.dbg("RandomEntities: " + str);
  }
  
  public static void warn(String str) {
    Config.warn("RandomEntities: " + str);
  }
}
