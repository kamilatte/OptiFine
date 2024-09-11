package srg.net.optifine;

import java.util.BitSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class SmartAnimations {
  private static boolean active;
  
  private static BitSet spritesRendered = new BitSet();
  
  private static BitSet texturesRendered = new BitSet();
  
  public static boolean isActive() {
    return (active && !Shaders.isShadowPass);
  }
  
  public static void update() {
    active = (Config.getGameSettings()).ofSmartAnimations;
  }
  
  public static void spriteRendered(TextureAtlasSprite sprite) {
    if (!sprite.isTerrain())
      return; 
    int animationIndex = sprite.getAnimationIndex();
    if (animationIndex < 0)
      return; 
    spritesRendered.set(animationIndex);
  }
  
  public static void spritesRendered(BitSet animationIndexes) {
    if (animationIndexes == null)
      return; 
    spritesRendered.or(animationIndexes);
  }
  
  public static boolean isSpriteRendered(TextureAtlasSprite sprite) {
    if (!sprite.isTerrain())
      return true; 
    int animationIndex = sprite.getAnimationIndex();
    if (animationIndex < 0)
      return false; 
    return spritesRendered.get(animationIndex);
  }
  
  public static void resetSpritesRendered(TextureAtlas atlasTexture) {
    if (!atlasTexture.isTerrain())
      return; 
    spritesRendered.clear();
  }
  
  public static void textureRendered(int textureId) {
    if (textureId < 0)
      return; 
    texturesRendered.set(textureId);
  }
  
  public static boolean isTextureRendered(int texId) {
    if (texId < 0)
      return false; 
    return texturesRendered.get(texId);
  }
  
  public static void resetTexturesRendered() {
    texturesRendered.clear();
  }
}
