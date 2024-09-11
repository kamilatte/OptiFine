package srg.net.optifine.util;

import net.minecraft.world.level.chunk.LevelChunk;
import net.optifine.ChunkOF;

public class ChunkUtils {
  public static boolean hasEntities(LevelChunk chunk) {
    if (chunk instanceof ChunkOF) {
      ChunkOF chunkOF = (ChunkOF)chunk;
      return chunkOF.hasEntities();
    } 
    return true;
  }
  
  public static boolean isLoaded(LevelChunk chunk) {
    if (chunk instanceof ChunkOF) {
      ChunkOF chunkOF = (ChunkOF)chunk;
      return chunkOF.isLoaded();
    } 
    return false;
  }
}
