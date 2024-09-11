package srg.net.optifine.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;

public class RenderUtils {
  private static boolean flushRenderBuffers = true;
  
  private static Minecraft mc = Minecraft.getInstance();
  
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
    RenderBuffers rtb = mc.renderBuffers();
    rtb.bufferSource().flushRenderBuffers();
    rtb.crumblingBufferSource().flushRenderBuffers();
  }
  
  public static void finishRenderBuffers() {
    RenderBuffers rtb = mc.renderBuffers();
    rtb.bufferSource().endBatch();
    rtb.crumblingBufferSource().endBatch();
  }
}
