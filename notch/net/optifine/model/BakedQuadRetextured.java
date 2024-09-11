package notch.net.optifine.model;

import fbg;
import fbn;
import gfw;
import ggd;
import gql;
import java.util.Arrays;

public class BakedQuadRetextured extends gfw {
  public BakedQuadRetextured(gfw quad, gql spriteIn) {
    super(remapVertexData(quad.b(), quad.a(), spriteIn), quad.d(), ggd.a(quad.b()), spriteIn, quad.f());
  }
  
  private static int[] remapVertexData(int[] vertexData, gql sprite, gql spriteNew) {
    int[] vertexDataNew = Arrays.copyOf(vertexData, vertexData.length);
    for (int i = 0; i < 4; i++) {
      fbn format = fbg.b;
      int j = format.getIntegerSize() * i;
      int uvIndex = format.getOffset(2) / 4;
      vertexDataNew[j + uvIndex] = Float.floatToRawIntBits(spriteNew.getInterpolatedU16(sprite.getUnInterpolatedU16(Float.intBitsToFloat(vertexData[j + uvIndex]))));
      vertexDataNew[j + uvIndex + 1] = Float.floatToRawIntBits(spriteNew.getInterpolatedV16(sprite.getUnInterpolatedV16(Float.intBitsToFloat(vertexData[j + uvIndex + 1]))));
    } 
    return vertexDataNew;
  }
}
