package notch.net.optifine.util;

import dvi;
import net.optifine.ChunkOF;

public class ChunkUtils {
  public static boolean hasEntities(dvi chunk) {
    if (chunk instanceof ChunkOF) {
      ChunkOF chunkOF = (ChunkOF)chunk;
      return chunkOF.hasEntities();
    } 
    return true;
  }
  
  public static boolean isLoaded(dvi chunk) {
    if (chunk instanceof ChunkOF) {
      ChunkOF chunkOF = (ChunkOF)chunk;
      return chunkOF.isLoaded();
    } 
    return false;
  }
}
