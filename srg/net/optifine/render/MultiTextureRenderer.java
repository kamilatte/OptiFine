package srg.net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
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
  
  public static void draw(VertexFormat.Mode drawMode, int indexTypeIn, MultiTextureData multiTextureData) {
    shaders = Config.isShaders();
    SpriteRenderData[] srds = multiTextureData.getSpriteRenderDatas();
    for (int i = 0; i < srds.length; i++) {
      SpriteRenderData srd = srds[i];
      draw(drawMode, indexTypeIn, srd);
    } 
  }
  
  private static void draw(VertexFormat.Mode drawMode, int indexTypeIn, SpriteRenderData srd) {
    TextureAtlasSprite sprite = srd.getSprite();
    int[] positions = srd.getPositions();
    int[] counts = srd.getCounts();
    sprite.bindSpriteTexture();
    if (shaders) {
      int normalTex = (sprite.spriteNormal != null) ? sprite.spriteNormal.glSpriteTextureId : 0;
      int specularTex = (sprite.spriteSpecular != null) ? sprite.spriteSpecular.glSpriteTextureId : 0;
      TextureAtlas at = sprite.getTextureAtlas();
      ShadersTex.bindNSTextures(normalTex, specularTex, at.isNormalBlend(), at.isSpecularBlend(), at.isMipmaps());
      if (Shaders.uniform_spriteBounds.isDefined())
        Shaders.uniform_spriteBounds.setValue(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1()); 
    } 
    if (bufferPositions.capacity() < positions.length) {
      int size = Mth.smallestEncompassingPowerOfTwo(positions.length);
      bufferPositions = Config.createDirectPointerBuffer(size);
      bufferCounts = Config.createDirectIntBuffer(size);
    } 
    bufferPositions.clear();
    bufferCounts.clear();
    int indexSize = getIndexSize(indexTypeIn);
    int i;
    for (i = 0; i < positions.length; i++)
      bufferPositions.put((drawMode.indexCount(positions[i]) * indexSize)); 
    for (i = 0; i < counts.length; i++)
      bufferCounts.put(drawMode.indexCount(counts[i])); 
    bufferPositions.flip();
    bufferCounts.flip();
    GlStateManager.glMultiDrawElements(drawMode.asGLMode, bufferCounts, indexTypeIn, bufferPositions);
  }
  
  private static int getIndexSize(int indexTypeIn) {
    if (indexTypeIn == 5125)
      return 4; 
    if (indexTypeIn == 5123)
      return 2; 
    return 1;
  }
}
