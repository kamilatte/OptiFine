package notch.net.optifine.util;

import org.lwjgl.opengl.GL;

public class GpuFrameTimer {
  private static boolean timerQuerySupported = (GL.getCapabilities()).GL_ARB_timer_query;
  
  private static long lastTimeCpuNs = 0L;
  
  private static long frameTimeCpuNs = 0L;
  
  private static TimerQuery timerQuery;
  
  private static long frameTimeGpuNs = 0L;
  
  private static long lastTimeActiveMs = 0L;
  
  public static void startRender() {
    if (timerQuery != null && timerQuery.hasResult()) {
      long frameTimeNs = timerQuery.getResult();
      frameTimeGpuNs = (frameTimeGpuNs + frameTimeNs) / 2L;
      timerQuery = null;
    } 
    if (System.currentTimeMillis() > lastTimeActiveMs + 1000L)
      return; 
    long timeCpuNs = System.nanoTime();
    if (lastTimeCpuNs != 0L) {
      long frameTimeNs = timeCpuNs - lastTimeCpuNs;
      frameTimeCpuNs = (frameTimeCpuNs + frameTimeNs) / 2L;
    } 
    lastTimeCpuNs = timeCpuNs;
    if (timerQuery == null && timerQuerySupported) {
      timerQuery = new TimerQuery();
      timerQuery.start();
    } 
  }
  
  public static void finishRender() {
    if (timerQuery != null)
      timerQuery.finish(); 
  }
  
  public static double getGpuLoad() {
    lastTimeActiveMs = System.currentTimeMillis();
    return Math.max(frameTimeGpuNs, 0L) / Math.max(frameTimeCpuNs, 1.0D);
  }
}
