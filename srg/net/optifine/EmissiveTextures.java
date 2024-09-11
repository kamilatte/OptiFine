package srg.net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
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
  
  private static final ResourceLocation LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
  
  private static final ResourceLocation LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
  
  private static TextureManager textureManager;
  
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
  
  public static ResourceLocation getEmissiveTexture(ResourceLocation locationIn) {
    if (!render)
      return locationIn; 
    AbstractTexture texture = textureManager.getTexture(locationIn);
    if (texture instanceof TextureAtlas)
      return locationIn; 
    ResourceLocation locationEmissive = null;
    if (texture instanceof SimpleTexture)
      locationEmissive = ((SimpleTexture)texture).locationEmissive; 
    if (!renderEmissive) {
      if (locationEmissive != null)
        hasEmissive = true; 
      return locationIn;
    } 
    if (locationEmissive == null)
      locationEmissive = LOCATION_TEXTURE_EMPTY; 
    return locationEmissive;
  }
  
  public static TextureAtlasSprite getEmissiveSprite(TextureAtlasSprite sprite) {
    if (!render)
      return sprite; 
    TextureAtlasSprite spriteEmissive = sprite.spriteEmissive;
    if (!renderEmissive) {
      if (spriteEmissive != null)
        hasEmissive = true; 
      return sprite;
    } 
    if (spriteEmissive == null)
      spriteEmissive = sprite.getTextureAtlas().getSprite(LOCATION_SPRITE_EMPTY); 
    return spriteEmissive;
  }
  
  public static BakedQuad getEmissiveQuad(BakedQuad quad) {
    if (!render)
      return quad; 
    BakedQuad quadEmissive = quad.getQuadEmissive();
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
    textureManager = Minecraft.getInstance().getTextureManager();
    active = false;
    suffixEmissive = null;
    suffixEmissivePng = null;
    if (!Config.isEmissiveTextures())
      return; 
    try {
      String fileName = "optifine/emissive.properties";
      ResourceLocation loc = new ResourceLocation(fileName);
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
  
  public static void updateIcons(TextureAtlas textureMap, Set<ResourceLocation> locations) {
    if (!active)
      return; 
    for (ResourceLocation loc : locations)
      checkEmissive(textureMap, loc); 
  }
  
  private static void checkEmissive(TextureAtlas textureMap, ResourceLocation locSprite) {
    String suffixEm = getSuffixEmissive();
    if (suffixEm == null)
      return; 
    if (locSprite.getPath().endsWith(suffixEm))
      return; 
    ResourceLocation locSpriteEm = new ResourceLocation(locSprite.getNamespace(), locSprite.getPath() + locSprite.getPath());
    ResourceLocation locPngEm = textureMap.getSpritePath(locSpriteEm);
    if (!Config.hasResource(locPngEm))
      return; 
    TextureAtlasSprite sprite = textureMap.registerSprite(locSprite);
    TextureAtlasSprite spriteEmissive = textureMap.registerSprite(locSpriteEm);
    spriteEmissive.isSpriteEmissive = true;
    sprite.spriteEmissive = spriteEmissive;
    textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
  }
  
  public static void refreshIcons(TextureAtlas textureMap) {
    Collection<TextureAtlasSprite> sprites = textureMap.getRegisteredSprites();
    for (TextureAtlasSprite sprite : sprites)
      refreshIcon(sprite, textureMap); 
  }
  
  private static void refreshIcon(TextureAtlasSprite sprite, TextureAtlas textureMap) {
    if (sprite.spriteEmissive == null)
      return; 
    TextureAtlasSprite spriteNew = textureMap.getUploadedSprite(sprite.getName());
    if (spriteNew == null)
      return; 
    TextureAtlasSprite spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
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
  
  public static boolean isEmissive(ResourceLocation loc) {
    if (suffixEmissivePng == null)
      return false; 
    return loc.getPath().endsWith(suffixEmissivePng);
  }
  
  public static void loadTexture(ResourceLocation loc, SimpleTexture tex) {
    if (loc == null || tex == null)
      return; 
    tex.isEmissive = false;
    tex.locationEmissive = null;
    if (suffixEmissivePng == null)
      return; 
    String path = loc.getPath();
    if (!path.endsWith(".png"))
      return; 
    if (path.endsWith(suffixEmissivePng)) {
      tex.isEmissive = true;
      return;
    } 
    String pathEmPng = path.substring(0, path.length() - ".png".length()) + path.substring(0, path.length() - ".png".length());
    ResourceLocation locEmPng = new ResourceLocation(loc.getNamespace(), pathEmPng);
    if (!Config.hasResource(locEmPng))
      return; 
    tex.locationEmissive = locEmPng;
  }
}
