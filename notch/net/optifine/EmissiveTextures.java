package notch.net.optifine;

import akr;
import fgo;
import gfw;
import gpw;
import gqe;
import gqk;
import gql;
import gqm;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import net.optifine.Config;
import net.optifine.render.RenderUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;

public class EmissiveTextures {
  private static String suffixEmissive = null;
  
  private static String suffixEmissivePng = null;
  
  private static boolean active = false;
  
  private static boolean render = false;
  
  private static boolean hasEmissive = false;
  
  private static boolean renderEmissive = false;
  
  private static final String SUFFIX_PNG = ".png";
  
  private static final akr LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
  
  private static final akr LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
  
  private static gqm textureManager;
  
  private static int countRecursive = 0;
  
  public static boolean isActive() {
    return active;
  }
  
  public static String getSuffixEmissive() {
    return suffixEmissive;
  }
  
  public static void beginRender() {
    if (render) {
      countRecursive++;
      return;
    } 
    render = true;
    hasEmissive = false;
  }
  
  public static akr getEmissiveTexture(akr locationIn) {
    if (!render)
      return locationIn; 
    gpw texture = textureManager.b(locationIn);
    if (texture instanceof gqk)
      return locationIn; 
    akr locationEmissive = null;
    if (texture instanceof gqe)
      locationEmissive = ((gqe)texture).locationEmissive; 
    if (!renderEmissive) {
      if (locationEmissive != null)
        hasEmissive = true; 
      return locationIn;
    } 
    if (locationEmissive == null)
      locationEmissive = LOCATION_TEXTURE_EMPTY; 
    return locationEmissive;
  }
  
  public static gql getEmissiveSprite(gql sprite) {
    if (!render)
      return sprite; 
    gql spriteEmissive = sprite.spriteEmissive;
    if (!renderEmissive) {
      if (spriteEmissive != null)
        hasEmissive = true; 
      return sprite;
    } 
    if (spriteEmissive == null)
      spriteEmissive = sprite.getTextureAtlas().a(LOCATION_SPRITE_EMPTY); 
    return spriteEmissive;
  }
  
  public static gfw getEmissiveQuad(gfw quad) {
    if (!render)
      return quad; 
    gfw quadEmissive = quad.getQuadEmissive();
    if (!renderEmissive) {
      if (quadEmissive != null)
        hasEmissive = true; 
      return quad;
    } 
    return quadEmissive;
  }
  
  public static boolean hasEmissive() {
    if (countRecursive > 0)
      return false; 
    return hasEmissive;
  }
  
  public static void beginRenderEmissive() {
    renderEmissive = true;
  }
  
  public static boolean isRenderEmissive() {
    return renderEmissive;
  }
  
  public static void endRenderEmissive() {
    RenderUtils.flushRenderBuffers();
    renderEmissive = false;
  }
  
  public static void endRender() {
    if (countRecursive > 0) {
      countRecursive--;
      return;
    } 
    render = false;
    hasEmissive = false;
  }
  
  public static void update() {
    textureManager = fgo.Q().aa();
    active = false;
    suffixEmissive = null;
    suffixEmissivePng = null;
    if (!Config.isEmissiveTextures())
      return; 
    try {
      String fileName = "optifine/emissive.properties";
      akr loc = new akr(fileName);
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return; 
      dbg("Loading " + fileName);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      suffixEmissive = propertiesOrdered.getProperty("suffix.emissive");
      if (suffixEmissive != null)
        suffixEmissivePng = suffixEmissive + ".png"; 
      active = (suffixEmissive != null);
    } catch (FileNotFoundException e) {
      return;
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void updateIcons(gqk textureMap, Set<akr> locations) {
    if (!active)
      return; 
    for (akr loc : locations)
      checkEmissive(textureMap, loc); 
  }
  
  private static void checkEmissive(gqk textureMap, akr locSprite) {
    String suffixEm = getSuffixEmissive();
    if (suffixEm == null)
      return; 
    if (locSprite.a().endsWith(suffixEm))
      return; 
    akr locSpriteEm = new akr(locSprite.b(), locSprite.a() + locSprite.a());
    akr locPngEm = textureMap.getSpritePath(locSpriteEm);
    if (!Config.hasResource(locPngEm))
      return; 
    gql sprite = textureMap.registerSprite(locSprite);
    gql spriteEmissive = textureMap.registerSprite(locSpriteEm);
    spriteEmissive.isSpriteEmissive = true;
    sprite.spriteEmissive = spriteEmissive;
    textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
  }
  
  public static void refreshIcons(gqk textureMap) {
    Collection<gql> sprites = textureMap.getRegisteredSprites();
    for (gql sprite : sprites)
      refreshIcon(sprite, textureMap); 
  }
  
  private static void refreshIcon(gql sprite, gqk textureMap) {
    if (sprite.spriteEmissive == null)
      return; 
    gql spriteNew = textureMap.getUploadedSprite(sprite.getName());
    if (spriteNew == null)
      return; 
    gql spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
    if (spriteEmissiveNew == null)
      return; 
    spriteEmissiveNew.isSpriteEmissive = true;
    spriteNew.spriteEmissive = spriteEmissiveNew;
  }
  
  private static void dbg(String str) {
    Config.dbg("EmissiveTextures: " + str);
  }
  
  private static void warn(String str) {
    Config.warn("EmissiveTextures: " + str);
  }
  
  public static boolean isEmissive(akr loc) {
    if (suffixEmissivePng == null)
      return false; 
    return loc.a().endsWith(suffixEmissivePng);
  }
  
  public static void loadTexture(akr loc, gqe tex) {
    if (loc == null || tex == null)
      return; 
    tex.isEmissive = false;
    tex.locationEmissive = null;
    if (suffixEmissivePng == null)
      return; 
    String path = loc.a();
    if (!path.endsWith(".png"))
      return; 
    if (path.endsWith(suffixEmissivePng)) {
      tex.isEmissive = true;
      return;
    } 
    String pathEmPng = path.substring(0, path.length() - ".png".length()) + path.substring(0, path.length() - ".png".length());
    akr locEmPng = new akr(loc.b(), pathEmPng);
    if (!Config.hasResource(locEmPng))
      return; 
    tex.locationEmissive = locEmPng;
  }
}
