package srg.net.optifine;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public class GlErrors {
  private static boolean frameStarted = false;
  
  private static long timeCheckStartMs = -1L;
  
  private static Int2ObjectOpenHashMap<GlError> glErrors = new Int2ObjectOpenHashMap();
  
  private static final long CHECK_INTERVAL_MS = 3000L;
  
  private static final int CHECK_ERROR_MAX = 10;
  
  public static void frameStart() {
    frameStarted = true;
    if (glErrors.isEmpty())
      return; 
    if (timeCheckStartMs < 0L)
      timeCheckStartMs = System.currentTimeMillis(); 
    if (System.currentTimeMillis() > timeCheckStartMs + 3000L) {
      ObjectCollection<GlError> errors = glErrors.values();
      for (ObjectIterator<GlError> objectIterator = errors.iterator(); objectIterator.hasNext(); ) {
        GlError glError = objectIterator.next();
        glError.onFrameStart();
      } 
      timeCheckStartMs = System.currentTimeMillis();
    } 
  }
  
  public static boolean isEnabled(int error) {
    if (!frameStarted)
      return true; 
    GlError glError = getGlError(error);
    return glError.isEnabled();
  }
  
  private static GlError getGlError(int error) {
    GlError glError = (GlError)glErrors.get(error);
    if (glError == null) {
      glError = new GlError(error);
      glErrors.put(error, glError);
    } 
    return glError;
  }
}
