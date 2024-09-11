package notch.net.optifine.render;

import ayo;
import com.mojang.blaze3d.platform.GlStateManager;
import fbn;
import gqk;
import gql;
import java.nio.IntBuffer;
import net.optifine.Config;
import net.optifine.render.MultiTextureData;
import net.optifine.render.SpriteRenderData;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import org.lwjgl.PointerBuffer;

public class MultiTextureRenderer {
  private static PointerBuffer bufferPositions = Config.createDirectPointerBuffer(1024);
  
  private static IntBuffer bufferCounts = Config.createDirectIntBuffer(1024);
  
  private static boolean shaders;
  
  public static void draw(fbn.c drawMode, int indexTypeIn, MultiTextureData multiTextureData) {
    shaders = Config.isShaders();
    SpriteRenderData[] srds = multiTextureData.getSpriteRenderDatas();
    for (int i = 0; i < srds.length; i++) {
      SpriteRenderData srd = srds[i];
      draw(drawMode, indexTypeIn, srd);
    } 
  }
  
  private static void draw(fbn.c drawMode, int indexTypeIn, SpriteRenderData srd) {
    gql sprite = srd.getSprite();
    int[] positions = srd.getPositions();
    int[] counts = srd.getCounts();
    sprite.bindSpriteTexture();
    if (shaders) {
      int normalTex = (sprite.spriteNormal != null) ? sprite.spriteNormal.glSpriteTextureId : 0;
      int specularTex = (sprite.spriteSpecular != null) ? sprite.spriteSpecular.glSpriteTextureId : 0;
      gqk at = sprite.getTextureAtlas();
      ShadersTex.bindNSTextures(normalTex, specularTex, at.isNormalBlend(), at.isSpecularBlend(), at.isMipmaps());
      if (Shaders.uniform_spriteBounds.isDefined())
        Shaders.uniform_spriteBounds.setValue(sprite.c(), sprite.g(), sprite.d(), sprite.h()); 
    } 
    if (bufferPositions.capacity() < positions.length) {
      int size = ayo.c(positions.length);
      bufferPositions = Config.createDirectPointerBuffer(size);
      bufferCounts = Config.createDirectIntBuffer(size);
    } 
    bufferPositions.clear();
    bufferCounts.clear();
    int indexSize = getIndexSize(indexTypeIn);
    int i;
    for (i = 0; i < positions.length; i++)
      bufferPositions.put((drawMode.a(positions[i]) * indexSize)); 
    for (i = 0; i < counts.length; i++)
      bufferCounts.put(drawMode.a(counts[i])); 
    bufferPositions.flip();
    bufferCounts.flip();
    GlStateManager.glMultiDrawElements(drawMode.i, bufferCounts, indexTypeIn, bufferPositions);
  }
  
  private static int getIndexSize(int indexTypeIn) {
    if (indexTypeIn == 5125)
      return 4; 
    if (indexTypeIn == 5123)
      return 2; 
    return 1;
  }
}
