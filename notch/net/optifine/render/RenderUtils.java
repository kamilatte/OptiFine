package notch.net.optifine.render;

import fgo;
import gff;

public class RenderUtils {
  private static boolean flushRenderBuffers = true;
  
  private static fgo mc = fgo.Q();
  
  public static boolean setFlushRenderBuffers(boolean flushRenderBuffers) {
    boolean prev = net.optifine.render.RenderUtils.flushRenderBuffers;
    net.optifine.render.RenderUtils.flushRenderBuffers = flushRenderBuffers;
    return prev;
  }
  
  public static boolean isFlushRenderBuffers() {
    return flushRenderBuffers;
  }
  
  public static void flushRenderBuffers() {
    if (!flushRenderBuffers)
      return; 
    gff rtb = mc.aO();
    rtb.c().flushRenderBuffers();
    rtb.d().flushRenderBuffers();
  }
  
  public static void finishRenderBuffers() {
    gff rtb = mc.aO();
    rtb.c().b();
    rtb.d().b();
  }
}
