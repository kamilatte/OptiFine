package srg.net.optifine.util;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class RenderChunkUtils {
  public static int getCountBlocks(SectionRenderDispatcher.RenderSection renderChunk) {
    LevelChunkSection[] ebss = renderChunk.getChunk().getSections();
    if (ebss == null)
      return 0; 
    int indexEbs = renderChunk.getOrigin().getY() - renderChunk.getWorld().getMinBuildHeight() >> 4;
    LevelChunkSection ebs = ebss[indexEbs];
    if (ebs == null)
      return 0; 
    return ebs.getBlockRefCount();
  }
  
  public static double getRelativeBufferSize(SectionRenderDispatcher.RenderSection renderChunk) {
    int blockCount = getCountBlocks(renderChunk);
    double vertexCountRel = getRelativeBufferSize(blockCount);
    return vertexCountRel;
  }
  
  public static double getRelativeBufferSize(int blockCount) {
    double countRel = blockCount / 4096.0D;
    countRel *= 0.995D;
    double weight = countRel * 2.0D - 1.0D;
    weight = Mth.clamp(weight, -1.0D, 1.0D);
    return Math.sqrt(1.0D - weight * weight);
  }
}
