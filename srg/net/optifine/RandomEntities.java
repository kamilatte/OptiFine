package srg.net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
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

public class RandomEntities {
  private static Map<String, RandomEntityProperties<ResourceLocation>> mapProperties = new HashMap<>();
  
  private static Map<String, RandomEntityProperties<ResourceLocation>> mapSpriteProperties = new HashMap<>();
  
  private static boolean active = false;
  
  private static EntityRenderDispatcher entityRenderDispatcher;
  
  private static RandomEntity randomEntity = new RandomEntity();
  
  private static BlockEntityRenderDispatcher tileEntityRendererDispatcher;
  
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
  
  private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, Horse.class, String[].class, 0);
  
  private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, Horse.class, String[].class, 1);
  
  public static void entityLoaded(Entity entity, Level world) {
    if (world == null)
      return; 
    SynchedEntityData edm = entity.getEntityData();
    edm.spawnPosition = entity.blockPosition();
    edm.spawnBiome = (Biome)world.getBiome(edm.spawnPosition).value();
    if (entity instanceof ShoulderRidingEntity) {
      ShoulderRidingEntity esr = (ShoulderRidingEntity)entity;
      checkEntityShoulder(esr, false);
    } 
  }
  
  public static void entityUnloaded(Entity entity, Level world) {
    if (entity instanceof ShoulderRidingEntity) {
      ShoulderRidingEntity esr = (ShoulderRidingEntity)entity;
      checkEntityShoulder(esr, true);
    } 
  }
  
  public static void checkEntityShoulder(ShoulderRidingEntity entity, boolean attach) {
    LocalPlayer localPlayer;
    LivingEntity owner = entity.getOwner();
    if (owner == null)
      localPlayer = (Config.getMinecraft()).player; 
    if (!(localPlayer instanceof AbstractClientPlayer))
      return; 
    AbstractClientPlayer player = (AbstractClientPlayer)localPlayer;
    UUID entityUuid = entity.getUUID();
    if (attach) {
      player.lastAttachedEntity = entity;
      CompoundTag nbtLeft = player.getShoulderEntityLeft();
      if (nbtLeft != null && nbtLeft.contains("UUID") && Config.equals(nbtLeft.getUUID("UUID"), entityUuid)) {
        player.entityShoulderLeft = entity;
        player.lastAttachedEntity = null;
      } 
      CompoundTag nbtRight = player.getShoulderEntityRight();
      if (nbtRight != null && nbtRight.contains("UUID") && Config.equals(nbtRight.getUUID("UUID"), entityUuid)) {
        player.entityShoulderRight = entity;
        player.lastAttachedEntity = null;
      } 
    } else {
      SynchedEntityData edm = entity.getEntityData();
      if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.getUUID(), entityUuid)) {
        SynchedEntityData edmShoulderLeft = player.entityShoulderLeft.getEntityData();
        edm.spawnPosition = edmShoulderLeft.spawnPosition;
        edm.spawnBiome = edmShoulderLeft.spawnBiome;
        player.entityShoulderLeft = null;
      } 
      if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.getUUID(), entityUuid)) {
        SynchedEntityData edmShoulderRight = player.entityShoulderRight.getEntityData();
        edm.spawnPosition = edmShoulderRight.spawnPosition;
        edm.spawnBiome = edmShoulderRight.spawnBiome;
        player.entityShoulderRight = null;
      } 
    } 
  }
  
  public static void worldChanged(Level oldWorld, Level newWorld) {
    if (newWorld instanceof ClientLevel) {
      ClientLevel newWorldClient = (ClientLevel)newWorld;
      Iterable<Entity> entities = newWorldClient.entitiesForRendering();
      for (Entity entity : entities)
        entityLoaded(entity, newWorld); 
    } 
    randomEntity.setEntity(null);
    randomTileEntity.setTileEntity(null);
  }
  
  public static ResourceLocation getTextureLocation(ResourceLocation loc) {
    if (!active)
      return loc; 
    IRandomEntity re = getRandomEntityRendered();
    if (re == null)
      return loc; 
    if (working)
      return loc; 
    try {
      working = true;
      String name = loc.getPath();
      if (name.startsWith("horse/"))
        name = getHorseTexturePath(name, "horse/".length()); 
      if (!name.startsWith("textures/entity/") && !name.startsWith("textures/painting/"))
        return loc; 
      RandomEntityProperties<ResourceLocation> props = mapProperties.get(name);
      if (props == null)
        return loc; 
      return (ResourceLocation)props.getResource(re, loc);
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
    if (BlockEntityRenderDispatcher.tileEntityRendered != null) {
      BlockEntity te = BlockEntityRenderDispatcher.tileEntityRendered;
      if (te.getLevel() != null) {
        randomTileEntity.setTileEntity(te);
        return (IRandomEntity)randomTileEntity;
      } 
    } 
    return null;
  }
  
  public static IRandomEntity getRandomEntity(Entity entityIn) {
    randomEntity.setEntity(entityIn);
    return (IRandomEntity)randomEntity;
  }
  
  public static IRandomEntity getRandomBlockEntity(BlockEntity tileEntityIn) {
    randomTileEntity.setTileEntity(tileEntityIn);
    return (IRandomEntity)randomTileEntity;
  }
  
  private static RandomEntityProperties<ResourceLocation> makeProperties(ResourceLocation loc, RandomEntityContext.Textures context) {
    String path = loc.getPath();
    ResourceLocation locProps = getLocationProperties(loc, context.isLegacy());
    if (locProps != null) {
      RandomEntityProperties<ResourceLocation> props = RandomEntityProperties.parse(locProps, loc, (RandomEntityContext)context);
      if (props != null)
        return props; 
    } 
    int[] variants = getLocationsVariants(loc, context.isLegacy(), (RandomEntityContext)context);
    if (variants == null)
      return null; 
    return new RandomEntityProperties(path, loc, variants, (RandomEntityContext)context);
  }
  
  private static ResourceLocation getLocationProperties(ResourceLocation loc, boolean legacy) {
    ResourceLocation locMcp = getLocationRandom(loc, legacy);
    if (locMcp == null)
      return null; 
    String domain = locMcp.getNamespace();
    String path = locMcp.getPath();
    String pathBase = StrUtils.removeSuffix(path, ".png");
    String pathProps = pathBase + ".properties";
    ResourceLocation locProps = new ResourceLocation(domain, pathProps);
    if (Config.hasResource(locProps))
      return locProps; 
    String pathParent = getParentTexturePath(pathBase);
    if (pathParent == null)
      return null; 
    ResourceLocation locParentProps = new ResourceLocation(domain, pathParent + ".properties");
    if (Config.hasResource(locParentProps))
      return locParentProps; 
    return null;
  }
  
  protected static ResourceLocation getLocationRandom(ResourceLocation loc, boolean legacy) {
    String domain = loc.getNamespace();
    String path = loc.getPath();
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
    return new ResourceLocation(domain, pathRandom);
  }
  
  private static String getPathBase(String pathRandom) {
    if (pathRandom.startsWith("optifine/random/"))
      return StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/"); 
    if (pathRandom.startsWith("optifine/mob/"))
      return StrUtils.replacePrefix(pathRandom, "optifine/mob/", "textures/entity/"); 
    return null;
  }
  
  protected static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
    if (loc == null)
      return null; 
    String path = loc.getPath();
    int pos = path.lastIndexOf('.');
    if (pos < 0)
      return null; 
    String prefix = path.substring(0, pos);
    String suffix = path.substring(pos);
    String separator = StrUtils.endsWithDigit(prefix) ? "." : "";
    String pathNew = prefix + prefix + separator + index;
    ResourceLocation locNew = new ResourceLocation(loc.getNamespace(), pathNew);
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
  
  public static int[] getLocationsVariants(ResourceLocation loc, boolean legacy, RandomEntityContext context) {
    List<Integer> list = new ArrayList<>();
    list.add(Integer.valueOf(1));
    ResourceLocation locRandom = getLocationRandom(loc, legacy);
    if (locRandom == null)
      return null; 
    for (int i = 1; i < list.size() + 10; i++) {
      int index = i + 1;
      ResourceLocation locIndex = getLocationIndexed(locRandom, index);
      if (Config.hasResource(locIndex))
        list.add(Integer.valueOf(index)); 
    } 
    if (list.size() <= 1)
      return null; 
    Integer[] arr = list.<Integer>toArray(new Integer[list.size()]);
    int[] intArr = ArrayUtils.toPrimitive(arr);
    Config.dbg(context.getName() + ": " + context.getName() + ", variants: " + loc.getPath());
    return intArr;
  }
  
  public static void update() {
    entityRenderDispatcher = Config.getEntityRenderDispatcher();
    tileEntityRendererDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
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
        ResourceLocation locBase = new ResourceLocation(pathBase);
        if (Config.hasResource(locBase)) {
          RandomEntityProperties<ResourceLocation> props = mapProperties.get(pathBase);
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
  
  public static synchronized void registerSprites(ResourceLocation atlasLocation, Set<ResourceLocation> spriteLocations) {
    if (mapProperties.isEmpty())
      return; 
    String prefix = getTexturePrefix(atlasLocation);
    Set<ResourceLocation> newLocations = new HashSet<>();
    for (ResourceLocation loc : spriteLocations) {
      String pathFull = "textures/" + prefix + loc.getPath() + ".png";
      RandomEntityProperties<ResourceLocation> props = mapProperties.get(pathFull);
      if (props == null)
        continue; 
      mapSpriteProperties.put(loc.getPath(), props);
      List<ResourceLocation> locs = props.getAllResources();
      if (locs == null)
        continue; 
      for (int i = 0; i < locs.size(); i++) {
        ResourceLocation propLoc = locs.get(i);
        ResourceLocation locSprite = TextureUtils.getSpriteLocation(propLoc);
        newLocations.add(locSprite);
        mapSpriteProperties.put(locSprite.getPath(), props);
      } 
    } 
    spriteLocations.addAll(newLocations);
  }
  
  private static String getTexturePrefix(ResourceLocation atlasLocation) {
    if (atlasLocation.getPath().endsWith("/paintings.png"))
      return "painting/"; 
    return "";
  }
  
  public static TextureAtlasSprite getRandomSprite(TextureAtlasSprite spriteIn) {
    if (!active)
      return spriteIn; 
    IRandomEntity re = getRandomEntityRendered();
    if (re == null)
      return spriteIn; 
    if (working)
      return spriteIn; 
    try {
      working = true;
      ResourceLocation locSpriteIn = spriteIn.getName();
      String name = locSpriteIn.getPath();
      RandomEntityProperties<ResourceLocation> props = mapSpriteProperties.get(name);
      if (props == null)
        return spriteIn; 
      ResourceLocation loc = (ResourceLocation)props.getResource(re, locSpriteIn);
      if (loc == locSpriteIn)
        return spriteIn; 
      ResourceLocation locSprite = TextureUtils.getSpriteLocation(loc);
      TextureAtlasSprite sprite = spriteIn.getTextureAtlas().getSprite(locSprite);
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
