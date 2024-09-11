package notch.net.optifine.util;

import akr;
import aty;
import aub;
import aue;
import ayo;
import com.mojang.blaze3d.platform.GlStateManager;
import faj;
import goq;
import gpw;
import gqb;
import gqe;
import gqk;
import gql;
import gss;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomItems;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomPanorama;
import net.optifine.CustomSky;
import net.optifine.EmissiveTextures;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartLeaves;
import net.optifine.TextureAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TextureUtils {
  private static final String texGrassTop = "grass_block_top";
  
  private static final String texGrassSide = "grass_block_side";
  
  private static final String texGrassSideOverlay = "grass_block_side_overlay";
  
  private static final String texSnow = "snow";
  
  private static final String texGrassSideSnowed = "grass_block_snow";
  
  private static final String texMyceliumSide = "mycelium_side";
  
  private static final String texMyceliumTop = "mycelium_top";
  
  private static final String texWaterStill = "water_still";
  
  private static final String texWaterFlow = "water_flow";
  
  private static final String texLavaStill = "lava_still";
  
  private static final String texLavaFlow = "lava_flow";
  
  private static final String texFireLayer0 = "fire_0";
  
  private static final String texFireLayer1 = "fire_1";
  
  private static final String texSoulFireLayer0 = "soul_fire_0";
  
  private static final String texSoulFireLayer1 = "soul_fire_1";
  
  private static final String texCampFire = "campfire_fire";
  
  private static final String texCampFireLogLit = "campfire_log_lit";
  
  private static final String texSoulCampFire = "soul_campfire_fire";
  
  private static final String texSoulCampFireLogLit = "soul_campfire_log_lit";
  
  private static final String texPortal = "nether_portal";
  
  private static final String texGlass = "glass";
  
  private static final String texGlassPaneTop = "glass_pane_top";
  
  public static gql iconGrassTop;
  
  public static gql iconGrassSide;
  
  public static gql iconGrassSideOverlay;
  
  public static gql iconSnow;
  
  public static gql iconGrassSideSnowed;
  
  public static gql iconMyceliumSide;
  
  public static gql iconMyceliumTop;
  
  public static gql iconWaterStill;
  
  public static gql iconWaterFlow;
  
  public static gql iconLavaStill;
  
  public static gql iconLavaFlow;
  
  public static gql iconFireLayer0;
  
  public static gql iconFireLayer1;
  
  public static gql iconSoulFireLayer0;
  
  public static gql iconSoulFireLayer1;
  
  public static gql iconCampFire;
  
  public static gql iconCampFireLogLit;
  
  public static gql iconSoulCampFire;
  
  public static gql iconSoulCampFireLogLit;
  
  public static gql iconPortal;
  
  public static gql iconGlass;
  
  public static gql iconGlassPaneTop;
  
  public static final String SPRITE_PREFIX_BLOCKS = "minecraft:block/";
  
  public static final String SPRITE_PREFIX_ITEMS = "minecraft:item/";
  
  public static final akr LOCATION_SPRITE_EMPTY = new akr("optifine/ctm/default/empty");
  
  public static final akr LOCATION_TEXTURE_EMPTY = new akr("optifine/ctm/default/empty.png");
  
  public static final akr WHITE_TEXTURE_LOCATION = new akr("textures/misc/white.png");
  
  private static IntBuffer staticBuffer = Config.createDirectIntBuffer(256);
  
  private static int glMaximumTextureSize = -1;
  
  private static Map<Integer, String> mapTextureAllocations = new HashMap<>();
  
  private static Map<akr, akr> mapSpriteLocations = new HashMap<>();
  
  private static akr LOCATION_ATLAS_PAINTINGS = new akr("textures/atlas/paintings.png");
  
  public static void update() {
    gqk mapBlocks = getTextureMapBlocks();
    if (mapBlocks == null)
      return; 
    String prefix = "minecraft:block/";
    iconGrassTop = getSpriteCheck(mapBlocks, prefix + "grass_block_top");
    iconGrassSide = getSpriteCheck(mapBlocks, prefix + "grass_block_side");
    iconGrassSideOverlay = getSpriteCheck(mapBlocks, prefix + "grass_block_side_overlay");
    iconSnow = getSpriteCheck(mapBlocks, prefix + "snow");
    iconGrassSideSnowed = getSpriteCheck(mapBlocks, prefix + "grass_block_snow");
    iconMyceliumSide = getSpriteCheck(mapBlocks, prefix + "mycelium_side");
    iconMyceliumTop = getSpriteCheck(mapBlocks, prefix + "mycelium_top");
    iconWaterStill = getSpriteCheck(mapBlocks, prefix + "water_still");
    iconWaterFlow = getSpriteCheck(mapBlocks, prefix + "water_flow");
    iconLavaStill = getSpriteCheck(mapBlocks, prefix + "lava_still");
    iconLavaFlow = getSpriteCheck(mapBlocks, prefix + "lava_flow");
    iconFireLayer0 = getSpriteCheck(mapBlocks, prefix + "fire_0");
    iconFireLayer1 = getSpriteCheck(mapBlocks, prefix + "fire_1");
    iconSoulFireLayer0 = getSpriteCheck(mapBlocks, prefix + "soul_fire_0");
    iconSoulFireLayer1 = getSpriteCheck(mapBlocks, prefix + "soul_fire_1");
    iconCampFire = getSpriteCheck(mapBlocks, prefix + "campfire_fire");
    iconCampFireLogLit = getSpriteCheck(mapBlocks, prefix + "campfire_log_lit");
    iconSoulCampFire = getSpriteCheck(mapBlocks, prefix + "soul_campfire_fire");
    iconSoulCampFireLogLit = getSpriteCheck(mapBlocks, prefix + "soul_campfire_log_lit");
    iconPortal = getSpriteCheck(mapBlocks, prefix + "nether_portal");
    iconGlass = getSpriteCheck(mapBlocks, prefix + "glass");
    iconGlassPaneTop = getSpriteCheck(mapBlocks, prefix + "glass_pane_top");
    String prefixItems = "minecraft:item/";
    mapSpriteLocations.clear();
  }
  
  public static gql getSpriteCheck(gqk textureMap, String name) {
    gql sprite = textureMap.getUploadedSprite(name);
    if (sprite == null || gqb.isMisingSprite(sprite))
      Config.warn("Sprite not found: " + name); 
    return sprite;
  }
  
  public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
    if (name.startsWith("/mob/zombie") || name
      .startsWith("/mob/pigzombie")) {
      int width = bi.getWidth();
      int height = bi.getHeight();
      if (width == height * 2) {
        BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
        Graphics2D gr = scaledImage.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr.drawImage(bi, 0, 0, width, height, null);
        return scaledImage;
      } 
    } 
    return bi;
  }
  
  public static int ceilPowerOfTwo(int val) {
    int i = 1;
    while (i < val)
      i *= 2; 
    return i;
  }
  
  public static int getPowerOfTwo(int val) {
    int i = 1;
    int po2 = 0;
    while (i < val) {
      i *= 2;
      po2++;
    } 
    return po2;
  }
  
  public static int twoToPower(int power) {
    int val = 1;
    for (int i = 0; i < power; i++)
      val *= 2; 
    return val;
  }
  
  public static gpw getTexture(akr loc) {
    gpw tex = Config.getTextureManager().b(loc);
    if (tex != null)
      return tex; 
    if (!Config.hasResource(loc))
      return null; 
    gqe gqe = new gqe(loc);
    Config.getTextureManager().a(loc, (gpw)gqe);
    return (gpw)gqe;
  }
  
  public static void resourcesPreReload(aue rm) {
    CustomItems.update();
  }
  
  public static void resourcesReloaded(aue rm) {
    if (getTextureMapBlocks() == null)
      return; 
    Config.dbg("*** Reloading custom textures ***");
    CustomSky.reset();
    TextureAnimations.reset();
    update();
    NaturalTextures.update();
    BetterGrass.update();
    BetterSnow.update();
    TextureAnimations.update();
    CustomColors.update();
    CustomSky.update();
    CustomItems.updateModels();
    CustomEntityModels.update();
    Shaders.resourcesReloaded();
    Lang.resourcesReloaded();
    Config.updateTexturePackClouds();
    SmartLeaves.updateLeavesModels();
    CustomPanorama.update();
    CustomGuis.update();
    goq.update();
    CustomLoadingScreens.update();
    CustomBlockLayers.update();
    Config.getTextureManager().e();
    Config.dbg("Disable Forge light pipeline");
    ReflectorForge.setForgeLightPipelineEnabled(false);
  }
  
  public static gqk getTextureMapBlocks() {
    return Config.getTextureMap();
  }
  
  public static void registerResourceListener() {
    aue rm = Config.getResourceManager();
    if (rm instanceof aub) {
      aub rrm = (aub)rm;
      Object object1 = new Object();
      rrm.a((aty)object1);
      Object object2 = new Object();
      rrm.a((aty)object2);
    } 
  }
  
  public static void registerTickableTextures() {
    Object object = new Object();
    akr ttl = new akr("optifine/tickable_textures");
    Config.getTextureManager().a(ttl, (gpw)object);
  }
  
  public static void registerCustomModels(gss modelBakery) {
    CustomItems.loadModels(modelBakery);
  }
  
  public static void registerCustomSprites(gqk textureMap) {
    if (textureMap.g().equals(gqk.e)) {
      ConnectedTextures.updateIcons(textureMap);
      CustomItems.updateIcons(textureMap);
      BetterGrass.updateIcons(textureMap);
    } 
    textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
  }
  
  public static void registerCustomSpriteLocations(akr atlasLocation, Set<akr> spriteLocations) {
    RandomEntities.registerSprites(atlasLocation, spriteLocations);
  }
  
  public static void refreshCustomSprites(gqk textureMap) {
    if (textureMap.g().equals(gqk.e)) {
      ConnectedTextures.refreshIcons(textureMap);
      CustomItems.refreshIcons(textureMap);
      BetterGrass.refreshIcons(textureMap);
    } 
    EmissiveTextures.refreshIcons(textureMap);
  }
  
  public static akr fixResourceLocation(akr loc, String basePath) {
    if (!loc.b().equals("minecraft"))
      return loc; 
    String path = loc.a();
    String pathFixed = fixResourcePath(path, basePath);
    if (pathFixed != path)
      loc = new akr(loc.b(), pathFixed); 
    return loc;
  }
  
  public static String fixResourcePath(String path, String basePath) {
    String strAssMc = "assets/minecraft/";
    if (path.startsWith(strAssMc)) {
      path = path.substring(strAssMc.length());
      return path;
    } 
    if (path.startsWith("./")) {
      path = path.substring(2);
      if (!basePath.endsWith("/"))
        basePath = basePath + "/"; 
      path = basePath + basePath;
      return path;
    } 
    if (path.startsWith("/~"))
      path = path.substring(1); 
    String strOptifine = "optifine/";
    if (path.startsWith("~/")) {
      path = path.substring(2);
      path = strOptifine + strOptifine;
      return path;
    } 
    if (path.startsWith("/")) {
      path = strOptifine + strOptifine;
      return path;
    } 
    return path;
  }
  
  public static String getBasePath(String path) {
    int pos = path.lastIndexOf('/');
    if (pos < 0)
      return ""; 
    return path.substring(0, pos);
  }
  
  public static void applyAnisotropicLevel() {
    if ((GL.getCapabilities()).GL_EXT_texture_filter_anisotropic) {
      float maxLevel = GL11.glGetFloat(34047);
      float level = Config.getAnisotropicFilterLevel();
      level = Math.min(level, maxLevel);
      GL11.glTexParameterf(3553, 34046, level);
    } 
  }
  
  public static void bindTexture(int glTexId) {
    GlStateManager._bindTexture(glTexId);
  }
  
  public static boolean isPowerOfTwo(int x) {
    int x2 = ayo.c(x);
    return (x2 == x);
  }
  
  public static faj scaleImage(faj ni, int w2) {
    BufferedImage bi = toBufferedImage(ni);
    BufferedImage bi2 = scaleImage(bi, w2);
    faj ni2 = toNativeImage(bi2);
    return ni2;
  }
  
  public static BufferedImage toBufferedImage(faj ni) {
    int width = ni.a();
    int height = ni.b();
    int[] data = new int[width * height];
    ni.getBufferRGBA().get(data);
    BufferedImage bi = new BufferedImage(width, height, 2);
    bi.setRGB(0, 0, width, height, data, 0, width);
    return bi;
  }
  
  private static faj toNativeImage(BufferedImage bi) {
    int width = bi.getWidth();
    int height = bi.getHeight();
    int[] data = new int[width * height];
    bi.getRGB(0, 0, width, height, data, 0, width);
    faj ni = new faj(width, height, false);
    ni.getBufferRGBA().put(data);
    return ni;
  }
  
  public static BufferedImage scaleImage(BufferedImage bi, int w2) {
    int w = bi.getWidth();
    int h = bi.getHeight();
    int h2 = h * w2 / w;
    BufferedImage bi2 = new BufferedImage(w2, h2, 2);
    Graphics2D g2 = bi2.createGraphics();
    Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
    if (w2 < w || w2 % w != 0)
      method = RenderingHints.VALUE_INTERPOLATION_BILINEAR; 
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
    g2.drawImage(bi, 0, 0, w2, h2, null);
    return bi2;
  }
  
  public static int scaleToGrid(int size, int sizeGrid) {
    if (size == sizeGrid)
      return size; 
    int sizeNew = size / sizeGrid * sizeGrid;
    while (sizeNew < size)
      sizeNew += sizeGrid; 
    return sizeNew;
  }
  
  public static int scaleToMin(int size, int sizeMin) {
    if (size >= sizeMin)
      return size; 
    int sizeNew = sizeMin / size * size;
    while (sizeNew < sizeMin)
      sizeNew += size; 
    return sizeNew;
  }
  
  public static Dimension getImageSize(InputStream in, String suffix) {
    Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
    while (iter.hasNext()) {
      ImageReader reader = iter.next();
      try {
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis);
        int width = reader.getWidth(reader.getMinIndex());
        int height = reader.getHeight(reader.getMinIndex());
        return new Dimension(width, height);
      } catch (IOException e) {
      
      } finally {
        reader.dispose();
      } 
    } 
    return null;
  }
  
  public static void dbgMipmaps(gql textureatlassprite) {
    faj[] mipmapImages = textureatlassprite.getMipmapImages();
    for (int l = 0; l < mipmapImages.length; l++) {
      faj image = mipmapImages[l];
      if (image == null) {
        Config.dbg("" + l + ": " + l);
      } else {
        Config.dbg("" + l + ": " + l);
      } 
    } 
  }
  
  public static void saveGlTexture(String name, int textureId, int mipmapLevels, int width, int height) {
    bindTexture(textureId);
    GL11.glPixelStorei(3333, 1);
    GL11.glPixelStorei(3317, 1);
    name = StrUtils.removeSuffix(name, ".png");
    File fileBase = new File(name);
    File dir = fileBase.getParentFile();
    if (dir != null)
      dir.mkdirs(); 
    for (int i = 0; i < 16; i++) {
      String namePng = name + "_" + name + ".png";
      File filePng = new File(namePng);
      filePng.delete();
    } 
    for (int level = 0; level <= mipmapLevels; level++) {
      File filePng = new File(name + "_" + name + ".png");
      int widthLevel = width >> level;
      int heightLevel = height >> level;
      int sizeLevel = widthLevel * heightLevel;
      IntBuffer buf = BufferUtils.createIntBuffer(sizeLevel);
      int[] data = new int[sizeLevel];
      GL11.glGetTexImage(3553, level, 32993, 33639, buf);
      buf.get(data);
      BufferedImage image = new BufferedImage(widthLevel, heightLevel, 2);
      image.setRGB(0, 0, widthLevel, heightLevel, data, 0, widthLevel);
      try {
        ImageIO.write(image, "png", filePng);
        Config.dbg("Exported: " + String.valueOf(filePng));
      } catch (Exception e) {
        Config.warn("Error writing: " + String.valueOf(filePng));
        Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
      } 
    } 
  }
  
  public static int getGLMaximumTextureSize() {
    if (glMaximumTextureSize < 0)
      glMaximumTextureSize = detectGLMaximumTextureSize(); 
    return glMaximumTextureSize;
  }
  
  private static int detectGLMaximumTextureSize() {
    for (int i = 65536; i > 0; i >>= 1) {
      GlStateManager._texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
      int err = GL11.glGetError();
      int width = GlStateManager._getTexLevelParameter(32868, 0, 4096);
      if (width != 0)
        return i; 
    } 
    return 0;
  }
  
  public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
    if (imageStream == null)
      return null; 
    try {
      BufferedImage bufferedimage = ImageIO.read(imageStream);
      return bufferedimage;
    } finally {
      IOUtils.closeQuietly(imageStream);
    } 
  }
  
  public static int toAbgr(int argb) {
    int a = argb >> 24 & 0xFF;
    int r = argb >> 16 & 0xFF;
    int g = argb >> 8 & 0xFF;
    int b = argb >> 0 & 0xFF;
    int abgr = a << 24 | b << 16 | g << 8 | r;
    return abgr;
  }
  
  public static void resetDataUnpacking() {
    GlStateManager._pixelStore(3314, 0);
    GlStateManager._pixelStore(3316, 0);
    GlStateManager._pixelStore(3315, 0);
    GlStateManager._pixelStore(3317, 4);
  }
  
  public static String getStackTrace(Throwable t) {
    CharArrayWriter caw = new CharArrayWriter();
    t.printStackTrace(new PrintWriter(caw));
    return caw.toString();
  }
  
  public static void debugTextureGenerated(int id) {
    mapTextureAllocations.put(Integer.valueOf(id), getStackTrace(new Throwable("StackTrace")));
    Config.dbg("Textures: " + mapTextureAllocations.size());
  }
  
  public static void debugTextureDeleted(int id) {
    mapTextureAllocations.remove(Integer.valueOf(id));
    Config.dbg("Textures: " + mapTextureAllocations.size());
  }
  
  public static gql getCustomSprite(gql sprite) {
    if (Config.isRandomEntities())
      sprite = RandomEntities.getRandomSprite(sprite); 
    if (EmissiveTextures.isActive())
      sprite = EmissiveTextures.getEmissiveSprite(sprite); 
    return sprite;
  }
  
  public static akr getSpriteLocation(akr loc) {
    akr locSprite = mapSpriteLocations.get(loc);
    if (locSprite == null) {
      String pathSprite = loc.a();
      pathSprite = StrUtils.removePrefix(pathSprite, "textures/");
      pathSprite = StrUtils.removeSuffix(pathSprite, ".png");
      locSprite = new akr(loc.b(), pathSprite);
      mapSpriteLocations.put(loc, locSprite);
    } 
    return locSprite;
  }
}
