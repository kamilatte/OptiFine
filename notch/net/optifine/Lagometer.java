package notch.net.optifine;

import ayo;
import bnf;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fbd;
import fbg;
import fbi;
import fbk;
import fbn;
import fbq;
import fgo;
import fgs;
import fhz;
import ges;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.MathUtils;
import net.optifine.util.MemoryMonitor;
import org.joml.Matrix4f;

public class Lagometer {
  private static fgo mc;
  
  private static fgs gameSettings;
  
  private static bnf profiler;
  
  public static boolean active = false;
  
  public static TimerNano timerTick = new TimerNano();
  
  public static TimerNano timerScheduledExecutables = new TimerNano();
  
  public static TimerNano timerChunkUpload = new TimerNano();
  
  public static TimerNano timerChunkUpdate = new TimerNano();
  
  public static TimerNano timerVisibility = new TimerNano();
  
  public static TimerNano timerTerrain = new TimerNano();
  
  public static TimerNano timerServer = new TimerNano();
  
  private static long[] timesFrame = new long[512];
  
  private static long[] timesTick = new long[512];
  
  private static long[] timesScheduledExecutables = new long[512];
  
  private static long[] timesChunkUpload = new long[512];
  
  private static long[] timesChunkUpdate = new long[512];
  
  private static long[] timesVisibility = new long[512];
  
  private static long[] timesTerrain = new long[512];
  
  private static long[] timesServer = new long[512];
  
  private static boolean[] gcs = new boolean[512];
  
  private static int numRecordedFrameTimes = 0;
  
  private static long prevFrameTimeNano = -1L;
  
  private static long renderTimeNano = 0L;
  
  public static void updateLagometer() {
    if (mc == null) {
      mc = fgo.Q();
      gameSettings = mc.m;
      profiler = mc.aH();
    } 
    if ((mc.aN()).n && (mc.aN()).p) {
      active = true;
    } else {
      active = false;
      prevFrameTimeNano = -1L;
      return;
    } 
    long timeNowNano = System.nanoTime();
    if (prevFrameTimeNano == -1L) {
      prevFrameTimeNano = timeNowNano;
      return;
    } 
    int frameIndex = numRecordedFrameTimes & timesFrame.length - 1;
    numRecordedFrameTimes++;
    boolean gc = MemoryMonitor.isGcEvent();
    timesFrame[frameIndex] = timeNowNano - prevFrameTimeNano - renderTimeNano;
    timesTick[frameIndex] = timerTick.timeNano;
    timesScheduledExecutables[frameIndex] = timerScheduledExecutables.timeNano;
    timesChunkUpload[frameIndex] = timerChunkUpload.timeNano;
    timesChunkUpdate[frameIndex] = timerChunkUpdate.timeNano;
    timesVisibility[frameIndex] = timerVisibility.timeNano;
    timesTerrain[frameIndex] = timerTerrain.timeNano;
    timesServer[frameIndex] = timerServer.timeNano;
    gcs[frameIndex] = gc;
    timerTick.reset();
    timerScheduledExecutables.reset();
    timerVisibility.reset();
    timerChunkUpdate.reset();
    timerChunkUpload.reset();
    timerTerrain.reset();
    timerServer.reset();
    prevFrameTimeNano = System.nanoTime();
  }
  
  public static void renderLagometer(fhz graphicsIn, int scaleFactor) {
    long timeRenderStartNano = System.nanoTime();
    GlStateManager.clear(256);
    RenderSystem.backupProjectionMatrix();
    int displayWidth = mc.aM().l();
    int displayHeight = mc.aM().m();
    float guiFarPlane = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? Reflector.ForgeHooksClient_getGuiFarPlane.callFloat(new Object[0]) : 21000.0F;
    Matrix4f matrix4f = MathUtils.makeOrtho4f(0.0F, displayWidth, 0.0F, displayHeight, 1000.0F, guiFarPlane);
    RenderSystem.setProjectionMatrix(matrix4f, fbq.b);
    fbi matrixStackIn = graphicsIn.c();
    matrixStackIn.a();
    matrixStackIn.a(0.0F, 0.0F, 10000.0F - guiFarPlane);
    GlStateManager.disableTexture();
    GlStateManager._depthMask(false);
    GlStateManager._disableCull();
    RenderSystem.setShader(ges::an);
    RenderSystem.lineWidth(1.0F);
    fbk tess = fbk.b();
    fbd tessellator = tess.a(fbn.c.a, fbg.g);
    for (int frameNum = 0; frameNum < timesFrame.length; frameNum++) {
      int lum = (frameNum - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
      lum += 155;
      float baseHeight = displayHeight;
      long heightFrame = 0L;
      if (gcs[frameNum]) {
        heightFrame = renderTime(frameNum, timesFrame[frameNum], lum, lum / 2, 0, baseHeight, tessellator);
      } else {
        heightFrame = renderTime(frameNum, timesFrame[frameNum], lum, lum, lum, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesServer[frameNum], lum / 2, lum / 2, lum / 2, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesTerrain[frameNum], 0, lum, 0, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesVisibility[frameNum], lum, lum, 0, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesChunkUpdate[frameNum], lum, 0, 0, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesChunkUpload[frameNum], lum, 0, lum, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesScheduledExecutables[frameNum], 0, 0, lum, baseHeight, tessellator);
        baseHeight -= (float)renderTime(frameNum, timesTick[frameNum], 0, lum, lum, baseHeight, tessellator);
      } 
    } 
    renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, displayHeight, tessellator);
    renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, displayHeight, tessellator);
    tess.draw(tessellator);
    GlStateManager._enableCull();
    GlStateManager._depthMask(true);
    GlStateManager.enableTexture();
    matrixStackIn.b();
    int y60 = displayHeight - 80;
    int y30 = displayHeight - 160;
    String str30 = Config.isShowFrameTime() ? "33" : "30";
    String str60 = Config.isShowFrameTime() ? "17" : "60";
    graphicsIn.a(mc.h, str30, 1, y30, -3881788, false);
    graphicsIn.a(mc.h, str60, 1, y60, -3881788, false);
    RenderSystem.restoreProjectionMatrix();
    float lumMem = 1.0F - (float)((System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0D);
    lumMem = Config.limit(lumMem, 0.0F, 1.0F);
    int memColR = (int)ayo.i(lumMem, 180.0F, 255.0F);
    int memColG = (int)ayo.i(lumMem, 110.0F, 155.0F);
    int memColB = (int)ayo.i(lumMem, 15.0F, 20.0F);
    int colMem = memColR << 16 | memColG << 8 | memColB;
    int posX = 512 / scaleFactor + 2;
    int posY = displayHeight / scaleFactor - 8;
    graphicsIn.a(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
    graphicsIn.b(mc.h, " " + MemoryMonitor.getGcRateMb() + " MB/s", posX, posY, colMem);
    renderTimeNano = System.nanoTime() - timeRenderStartNano;
  }
  
  private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, fbd tessellator) {
    long heightTime = time / 200000L;
    if (heightTime < 3L)
      return 0L; 
    tessellator.a(frameNum + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).a(r, g, b, 255).b(0.0F, 1.0F, 0.0F);
    tessellator.a(frameNum + 0.5F, baseHeight + 0.5F, 0.0F).a(r, g, b, 255).b(0.0F, 1.0F, 0.0F);
    return heightTime;
  }
  
  private static long renderTimeDivider(int frameStart, int frameEnd, long time, int r, int g, int b, float baseHeight, fbd tessellator) {
    long heightTime = time / 200000L;
    if (heightTime < 3L)
      return 0L; 
    tessellator.a(frameStart + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).a(r, g, b, 255).b(1.0F, 0.0F, 0.0F);
    tessellator.a(frameEnd + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).a(r, g, b, 255).b(1.0F, 0.0F, 0.0F);
    return heightTime;
  }
  
  public static boolean isActive() {
    return active;
  }
}
