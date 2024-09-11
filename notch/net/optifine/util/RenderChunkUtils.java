package notch.net.optifine.util;

import ayo;
import dvj;
import gia;

public class RenderChunkUtils {
  public static int getCountBlocks(gia.b renderChunk) {
    dvj[] ebss = renderChunk.getChunk().d();
    if (ebss == null)
      return 0; 
    int indexEbs = renderChunk.f().v() - renderChunk.getWorld().I_() >> 4;
    dvj ebs = ebss[indexEbs];
    if (ebs == null)
      return 0; 
    return ebs.getBlockRefCount();
  }
  
  public static double getRelativeBufferSize(gia.b renderChunk) {
    int blockCount = getCountBlocks(renderChunk);
    double vertexCountRel = getRelativeBufferSize(blockCount);
    return vertexCountRel;
  }
  
  public static double getRelativeBufferSize(int blockCount) {
    double countRel = blockCount / 4096.0D;
    countRel *= 0.995D;
    double weight = countRel * 2.0D - 1.0D;
    weight = ayo.a(weight, -1.0D, 1.0D);
    return Math.sqrt(1.0D - weight * weight);
  }
}
