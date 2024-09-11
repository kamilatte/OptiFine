package notch.net.optifine;

import akq;
import akr;
import ayo;
import azl;
import bsr;
import ddf;
import ddw;
import dtc;
import epd;
import epf;
import ewy;
import exa;
import fgo;
import fgs;
import fhx;
import fhz;
import fzf;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jd;
import ji;
import jm;
import lt;
import net.optifine.Config;
import net.optifine.McDebugInfo;
import net.optifine.config.Option;
import net.optifine.render.RenderCache;
import net.optifine.util.GpuFrameTimer;
import net.optifine.util.GpuMemory;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.NativeMemory;
import net.optifine.util.NumUtils;

public class QuickInfo {
  private static RenderCache renderCache = new RenderCache(100L);
  
  private static fgo minecraft = Config.getMinecraft();
  
  private static fhx font = minecraft.h;
  
  private static double gpuLoadSmooth = 0.0D;
  
  private static McDebugInfo gpuDebugInfo = new McDebugInfo();
  
  private static int gpuPercCached;
  
  public static void render(fhz graphicsIn) {
    if (renderCache.drawCached(graphicsIn))
      return; 
    renderCache.startRender(graphicsIn);
    renderLeft(graphicsIn);
    renderRight(graphicsIn);
    renderCache.stopRender(graphicsIn);
  }
  
  private static void renderLeft(fhz graphicsIn) {
    List<String> lines = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    fgs opts = Config.getGameSettings();
    boolean fullLabel = !Option.isCompact(opts.ofQuickInfoLabels);
    boolean detailedCoords = Option.isDetailed(opts.ofQuickInfoLabels);
    boolean fps = !Option.isOff(opts.ofQuickInfoFps);
    boolean fpsMin = Option.isFull(opts.ofQuickInfoFps);
    boolean chunks = opts.ofQuickInfoChunks;
    boolean entities = opts.ofQuickInfoEntities;
    boolean particles = opts.ofQuickInfoParticles;
    boolean updates = opts.ofQuickInfoUpdates;
    boolean gpu = opts.ofQuickInfoGpu;
    if (fps)
      addFps(next(sb), fullLabel, fpsMin); 
    if (chunks)
      addChunks(next(sb), fullLabel); 
    if (entities)
      addEntities(next(sb), fullLabel); 
    if (fullLabel)
      newLine(lines, sb); 
    if (particles)
      addParticles(next(sb), fullLabel); 
    if (updates)
      addUpdates(next(sb), fullLabel); 
    if (gpu)
      addGpu(next(sb), fullLabel); 
    newLine(lines, sb);
    boolean pos = !Option.isOff(opts.ofQuickInfoPos);
    boolean posRel = Option.isFull(opts.ofQuickInfoPos);
    boolean facing = !Option.isOff(opts.ofQuickInfoFacing);
    boolean facingXyz = facing;
    boolean yawPitch = Option.isFull(opts.ofQuickInfoFacing);
    if (pos)
      addPos(next(sb), fullLabel, detailedCoords, posRel); 
    newLine(lines, sb);
    if (facing)
      addFacing(next(sb), fullLabel, detailedCoords, facingXyz, yawPitch); 
    newLine(lines, sb);
    boolean biome = opts.ofQuickInfoBiome;
    boolean light = opts.ofQuickInfoLight;
    if (biome)
      addBiome(next(sb), fullLabel); 
    if (light)
      addLight(next(sb), fullLabel); 
    newLine(lines, sb);
    render(graphicsIn, lines, false);
  }
  
  private static void renderRight(fhz graphicsIn) {
    List<String> lines = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    fgs opts = Config.getGameSettings();
    boolean fullLabel = !Option.isCompact(opts.ofQuickInfoLabels);
    boolean detailedCoords = Option.isDetailed(opts.ofQuickInfoLabels);
    boolean mem = !Option.isOff(opts.ofQuickInfoMemory);
    boolean memAlloc = Option.isFull(opts.ofQuickInfoMemory);
    boolean memNative = !Option.isOff(opts.ofQuickInfoNativeMemory);
    boolean memGpu = Option.isFull(opts.ofQuickInfoNativeMemory);
    if (mem)
      addMem(next(sb), fullLabel); 
    if (memAlloc)
      addMemAlloc(next(sb), fullLabel); 
    newLine(lines, sb);
    if (memNative)
      addMemNative(next(sb), fullLabel); 
    if (memGpu)
      addMemGpu(next(sb), fullLabel); 
    newLine(lines, sb);
    boolean targetBlock = !Option.isOff(opts.ofQuickInfoTargetBlock);
    boolean targetBlockPos = Option.isFull(opts.ofQuickInfoTargetBlock);
    if (targetBlock)
      addTargetBlock(next(sb), fullLabel, targetBlockPos); 
    newLine(lines, sb);
    boolean targetFluid = !Option.isOff(opts.ofQuickInfoTargetFluid);
    boolean targetFluidPos = Option.isFull(opts.ofQuickInfoTargetFluid);
    if (targetFluid)
      addTargetFluid(next(sb), fullLabel, targetFluidPos); 
    newLine(lines, sb);
    boolean targetEntity = !Option.isOff(opts.ofQuickInfoTargetEntity);
    boolean targetEntityPos = Option.isFull(opts.ofQuickInfoTargetEntity);
    if (targetEntity)
      addTargetEntity(next(sb), fullLabel, detailedCoords, targetEntityPos); 
    newLine(lines, sb);
    render(graphicsIn, lines, true);
  }
  
  private static StringBuilder next(StringBuilder sb) {
    if (!sb.isEmpty())
      sb.append(", "); 
    return sb;
  }
  
  private static void newLine(List<String> lines, StringBuilder sb) {
    if (sb.isEmpty())
      return; 
    lines.add(sb.toString());
    sb.setLength(0);
  }
  
  private static void render(fhz graphicsIn, List<String> linesIn, boolean rightIn) {
    if (linesIn.isEmpty())
      return; 
    fgs opts = Config.getGameSettings();
    boolean background = opts.ofQuickInfoBackground;
    boolean shadow = false;
    if (rightIn) {
      graphicsIn.c().a();
      graphicsIn.c().a(0.0F, 0.0F, -10.0F);
    } 
    int lineHeight = 9;
    if (background) {
      int j = 2;
      for (int k = 0; k < linesIn.size(); k++) {
        String s = linesIn.get(k);
        if (!azl.b(s)) {
          int lineWidth = font.b(s);
          int x = rightIn ? (graphicsIn.a() - 2 - lineWidth) : 2;
          int y = j;
          graphicsIn.a(x - 1, y - 1, x + lineWidth + 1, y + lineHeight - 1, -1873784752);
          j += lineHeight;
        } 
      } 
    } 
    int lineY = 2;
    for (int i = 0; i < linesIn.size(); i++) {
      String s = linesIn.get(i);
      if (!azl.b(s)) {
        int lineWidth = font.b(s);
        int x = rightIn ? (graphicsIn.a() - 2 - lineWidth) : 2;
        int y = lineY;
        graphicsIn.a(font, s, x, y, -2039584, shadow);
        lineY += lineHeight;
      } 
    } 
    if (rightIn)
      graphicsIn.c().b(); 
  }
  
  private static String getName(akr loc) {
    if (loc == null)
      return ""; 
    if (loc.isDefaultNamespace())
      return loc.a(); 
    return loc.toString();
  }
  
  private static void addFps(StringBuilder sb, boolean fullLabel, boolean addFpsMin) {
    if (Config.isShowFrameTime()) {
      int i = Config.getFpsAverage();
      appendDouble1(sb, 1000.0D / Config.limit(i, 1, 2147483647));
      if (addFpsMin) {
        int fpsMin = Config.getFpsMin();
        sb.append('/');
        appendDouble1(sb, 1000.0D / Config.limit(fpsMin, 1, 2147483647));
      } 
      sb.append(" ms");
      return;
    } 
    int fpsAvg = Config.getFpsAverage();
    sb.append(Integer.toString(fpsAvg));
    if (addFpsMin) {
      int fpsMin = Config.getFpsMin();
      sb.append('/');
      sb.append(Integer.toString(fpsMin));
    } 
    sb.append(" fps");
  }
  
  private static void addChunks(StringBuilder sb, boolean fullLabel) {
    int chunks = minecraft.f.k();
    sb.append(fullLabel ? "Chunks: " : "C: ");
    sb.append(Integer.toString(chunks));
  }
  
  private static void addEntities(StringBuilder sb, boolean fullLabel) {
    int entities = minecraft.f.getCountEntitiesRendered();
    sb.append(fullLabel ? "Entities: " : "E: ");
    sb.append(Integer.toString(entities));
    int blockEntities = minecraft.f.getCountTileEntitiesRendered();
    sb.append('+');
    sb.append(Integer.toString(blockEntities));
  }
  
  private static void addParticles(StringBuilder sb, boolean fullLabel) {
    int particles = minecraft.g.getCountParticles();
    sb.append(fullLabel ? "Particles: " : "P: ");
    sb.append(Integer.toString(particles));
  }
  
  private static void addUpdates(StringBuilder sb, boolean fullLabel) {
    int updates = Config.getChunkUpdates();
    sb.append(fullLabel ? "Updates: " : "U: ");
    sb.append(Integer.toString(updates));
  }
  
  private static void addGpu(StringBuilder sb, boolean fullLabel) {
    double gpuLoad = GpuFrameTimer.getGpuLoad();
    gpuLoadSmooth = (gpuLoadSmooth * 4.0D + gpuLoad) / 5.0D;
    int gpuPerc = (int)Math.round(gpuLoadSmooth * 100.0D);
    gpuPerc = NumUtils.limit(gpuPerc, 0, 100);
    if (gpuPercCached <= 0 || gpuDebugInfo.isChanged())
      gpuPercCached = gpuPerc; 
    sb.append(fullLabel ? "GPU: " : "G: ");
    sb.append(Integer.toString(gpuPercCached));
    sb.append("%");
  }
  
  private static void addPos(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean posRel) {
    bsr entity = minecraft.an();
    jd pos = entity.do();
    sb.append(fullLabel ? "Position: " : "Pos: ");
    if (detailedCoords) {
      sb.append(" (");
      appendDouble3(sb, entity.dt());
      sb.append(", ");
      appendDouble3(sb, entity.dv());
      sb.append(", ");
      appendDouble3(sb, entity.dz());
      sb.append(")");
    } else {
      sb.append(Integer.toString(pos.u()));
      sb.append(", ");
      sb.append(Integer.toString(pos.v()));
      sb.append(", ");
      sb.append(Integer.toString(pos.w()));
    } 
    if (posRel) {
      sb.append(" [");
      sb.append(Integer.toString(pos.u() & 0xF));
      sb.append(", ");
      sb.append(Integer.toString(pos.v() & 0xF));
      sb.append(", ");
      sb.append(Integer.toString(pos.w() & 0xF));
      sb.append("]");
    } 
  }
  
  private static void addFacing(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean facingXyz, boolean yawPitch) {
    bsr entity = minecraft.an();
    ji dir = entity.cH();
    sb.append(fullLabel ? "Facing: " : "F: ");
    sb.append(dir.toString());
    if (facingXyz) {
      String dirXyz = getXyz(dir);
      sb.append(" [");
      sb.append(dirXyz);
      sb.append("]");
    } 
    if (yawPitch) {
      float yaw = ayo.g(entity.dE());
      float pitch = ayo.g(entity.dG());
      if (detailedCoords) {
        sb.append(" (");
        appendDouble1(sb, yaw);
        sb.append('/');
        appendDouble1(sb, pitch);
        sb.append(')');
      } else {
        sb.append(" (");
        sb.append(Integer.toString(Math.round(yaw)));
        sb.append('/');
        sb.append(Integer.toString(Math.round(pitch)));
        sb.append(')');
      } 
    } 
  }
  
  private static String getXyz(ji dir) {
    switch (null.$SwitchMap$net$minecraft$core$Direction[dir.ordinal()]) {
      case 1:
        return "Z-";
      case 2:
        return "Z+";
      case 3:
        return "X-";
      case 4:
        return "X+";
      case 5:
        return "Y+";
      case 6:
        return "Y-";
    } 
    return "?";
  }
  
  private static void addBiome(StringBuilder sb, boolean fullLabel) {
    bsr entity = minecraft.an();
    jd pos = entity.do();
    jm<ddw> biome = minecraft.r.t(pos);
    Optional<akq<ddw>> key = biome.e();
    String name = getBiomeName(key);
    sb.append(fullLabel ? "Biome: " : "B: ");
    sb.append(name);
  }
  
  private static String getBiomeName(Optional<akq<ddw>> key) {
    if (!key.isPresent())
      return "[unregistered]"; 
    akr loc = ((akq)key.get()).a();
    if (loc.isDefaultNamespace())
      return loc.a(); 
    return loc.toString();
  }
  
  private static void addLight(StringBuilder sb, boolean fullLabel) {
    bsr entity = minecraft.an();
    jd pos = entity.do();
    fzf world = minecraft.r;
    int lightSky = world.a(ddf.a, pos);
    int lightBlock = world.a(ddf.b, pos);
    if (fullLabel) {
      sb.append("Light: ");
      sb.append(Integer.toString(lightSky));
      sb.append(" sky, ");
      sb.append(Integer.toString(lightBlock));
      sb.append(" block");
    } else {
      sb.append("L: ");
      sb.append(Integer.toString(lightSky));
      sb.append("/");
      sb.append(Integer.toString(lightBlock));
    } 
  }
  
  private static void addMem(StringBuilder sb, boolean fullLabel) {
    long max = Runtime.getRuntime().maxMemory();
    long total = Runtime.getRuntime().totalMemory();
    long free = Runtime.getRuntime().freeMemory();
    long used = total - free;
    sb.append(fullLabel ? "Memory: " : "M: ");
    sb.append(Integer.toString(bytesToMb(used)));
    sb.append("/");
    sb.append(Integer.toString(bytesToMb(max)));
    if (fullLabel)
      sb.append(" MB"); 
  }
  
  private static void addMemAlloc(StringBuilder sb, boolean fullLabel) {
    int allocMb = (int)MemoryMonitor.getAllocationRateAvgMb();
    sb.append(fullLabel ? "Allocation: " : "A: ");
    sb.append(Integer.toString(allocMb));
    if (fullLabel)
      sb.append(" MB/s"); 
  }
  
  private static void addMemNative(StringBuilder sb, boolean fullLabel) {
    long bufferAllocated = NativeMemory.getBufferAllocated();
    long bufferMaximum = NativeMemory.getBufferMaximum();
    long imageAllocated = NativeMemory.getImageAllocated();
    sb.append(fullLabel ? "Native: " : "N: ");
    sb.append(Integer.toString(bytesToMb(bufferAllocated)));
    sb.append("/");
    sb.append(Integer.toString(bytesToMb(bufferMaximum)));
    sb.append("+");
    sb.append(Integer.toString(bytesToMb(imageAllocated)));
    if (fullLabel)
      sb.append(" MB"); 
  }
  
  private static void addMemGpu(StringBuilder sb, boolean fullLabel) {
    long gpuBufferAllocated = GpuMemory.getBufferAllocated();
    long gpuTextureAllocated = GpuMemory.getTextureAllocated();
    sb.append(fullLabel ? "GPU: " : "G: ");
    sb.append(Integer.toString(bytesToMb(gpuBufferAllocated)));
    sb.append("+");
    sb.append(Integer.toString(bytesToMb(gpuTextureAllocated)));
    if (fullLabel)
      sb.append(" MB"); 
  }
  
  private static int bytesToMb(long bytes) {
    return (int)(bytes / 1024L / 1024L);
  }
  
  private static void addTargetBlock(StringBuilder sb, boolean fullLabel, boolean showPos) {
    bsr entity = minecraft.an();
    double reachDist = minecraft.s.gy();
    exa rayTrace = entity.a(reachDist, 0.0F, false);
    if (rayTrace.c() == exa.a.b) {
      jd pos = ((ewy)rayTrace).a();
      dtc state = minecraft.r.a_(pos);
      sb.append(fullLabel ? "Target Block: " : "TB: ");
      sb.append(getName(state.getBlockLocation()));
      if (showPos) {
        sb.append(" (");
        sb.append(Integer.toString(pos.u()));
        sb.append(", ");
        sb.append(Integer.toString(pos.v()));
        sb.append(", ");
        sb.append(Integer.toString(pos.w()));
        sb.append(")");
      } 
    } 
  }
  
  private static void addTargetFluid(StringBuilder sb, boolean fullLabel, boolean showPos) {
    bsr entity = minecraft.an();
    double reachDist = minecraft.s.gy();
    exa rayTrace = entity.a(reachDist, 0.0F, true);
    if (rayTrace.c() == exa.a.b) {
      jd pos = ((ewy)rayTrace).a();
      dtc state = minecraft.r.a_(pos);
      epd fluid = state.u().a();
      if (fluid == epf.a)
        return; 
      akr loc = lt.c.b(fluid);
      String name = getName(loc);
      sb.append(fullLabel ? "Target Fluid: " : "TF: ");
      sb.append(name);
      if (showPos) {
        sb.append(" (");
        sb.append(Integer.toString(pos.u()));
        sb.append(", ");
        sb.append(Integer.toString(pos.v()));
        sb.append(", ");
        sb.append(Integer.toString(pos.w()));
        sb.append(")");
      } 
    } 
  }
  
  private static void addTargetEntity(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean showPos) {
    bsr entity = minecraft.u;
    if (entity == null)
      return; 
    jd pos = entity.do();
    akr loc = lt.f.b(entity.am());
    String name = getName(loc);
    if (loc == null)
      return; 
    sb.append(fullLabel ? "Target Entity: " : "TE: ");
    sb.append(name);
    if (showPos)
      if (detailedCoords) {
        sb.append(" (");
        appendDouble3(sb, entity.dt());
        sb.append(", ");
        appendDouble3(sb, entity.dv());
        sb.append(", ");
        appendDouble3(sb, entity.dz());
        sb.append(")");
      } else {
        sb.append(" (");
        sb.append(Integer.toString(pos.u()));
        sb.append(", ");
        sb.append(Integer.toString(pos.v()));
        sb.append(", ");
        sb.append(Integer.toString(pos.w()));
        sb.append(")");
      }  
  }
  
  private static void appendDouble1(StringBuilder sb, double num) {
    num = Math.round(num * 10.0D) / 10.0D;
    sb.append(num);
  }
  
  private static void appendDouble3(StringBuilder sb, double num) {
    num = Math.round(num * 1000.0D) / 1000.0D;
    sb.append(num);
    if (sb.charAt(sb.length() - 2) == '.')
      sb.append('0'); 
    if (sb.charAt(sb.length() - 3) == '.')
      sb.append('0'); 
  }
}
