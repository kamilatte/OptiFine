package srg.net.optifine.model;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Arrays;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BakedQuadRetextured extends BakedQuad {
  public BakedQuadRetextured(BakedQuad quad, TextureAtlasSprite spriteIn) {
    super(remapVertexData(quad.getVertices(), quad.getSprite(), spriteIn), quad.getTintIndex(), FaceBakery.calculateFacing(quad.getVertices()), spriteIn, quad.isShade());
  }
  
  private static int[] remapVertexData(int[] vertexData, TextureAtlasSprite sprite, TextureAtlasSprite spriteNew) {
    int[] vertexDataNew = Arrays.copyOf(vertexData, vertexData.length);
    for (int i = 0; i < 4; i++) {
      VertexFormat format = DefaultVertexFormat.BLOCK;
      int j = format.getIntegerSize() * i;
      int uvIndex = format.getOffset(2) / 4;
      vertexDataNew[j + uvIndex] = Float.floatToRawIntBits(spriteNew.getInterpolatedU16(sprite.getUnInterpolatedU16(Float.intBitsToFloat(vertexData[j + uvIndex]))));
      vertexDataNew[j + uvIndex + 1] = Float.floatToRawIntBits(spriteNew.getInterpolatedV16(sprite.getUnInterpolatedV16(Float.intBitsToFloat(vertexData[j + uvIndex + 1]))));
    } 
    return vertexDataNew;
  }
}
