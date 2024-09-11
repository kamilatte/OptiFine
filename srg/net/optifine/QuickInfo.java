package srg.net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
  
  private static Minecraft minecraft = Config.getMinecraft();
  
  private static Font font = minecraft.font;
  
  private static double gpuLoadSmooth = 0.0D;
  
  private static McDebugInfo gpuDebugInfo = new McDebugInfo();
  
  private static int gpuPercCached;
  
  public static void render(GuiGraphics graphicsIn) {
    if (renderCache.drawCached(graphicsIn))
      return; 
    renderCache.startRender(graphicsIn);
    renderLeft(graphicsIn);
    renderRight(graphicsIn);
    renderCache.stopRender(graphicsIn);
  }
  
  private static void renderLeft(GuiGraphics graphicsIn) {
    List<String> lines = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Options opts = Config.getGameSettings();
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
  
  private static void renderRight(GuiGraphics graphicsIn) {
    List<String> lines = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Options opts = Config.getGameSettings();
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
  
  private static void render(GuiGraphics graphicsIn, List<String> linesIn, boolean rightIn) {
    if (linesIn.isEmpty())
      return; 
    Options opts = Config.getGameSettings();
    boolean background = opts.ofQuickInfoBackground;
    boolean shadow = false;
    if (rightIn) {
      graphicsIn.pose().pushPose();
      graphicsIn.pose().translate(0.0F, 0.0F, -10.0F);
    } 
    int lineHeight = 9;
    if (background) {
      int j = 2;
      for (int k = 0; k < linesIn.size(); k++) {
        String s = linesIn.get(k);
        if (!StringUtil.isNullOrEmpty(s)) {
          int lineWidth = font.width(s);
          int x = rightIn ? (graphicsIn.guiWidth() - 2 - lineWidth) : 2;
          int y = j;
          graphicsIn.fill(x - 1, y - 1, x + lineWidth + 1, y + lineHeight - 1, -1873784752);
          j += lineHeight;
        } 
      } 
    } 
    int lineY = 2;
    for (int i = 0; i < linesIn.size(); i++) {
      String s = linesIn.get(i);
      if (!StringUtil.isNullOrEmpty(s)) {
        int lineWidth = font.width(s);
        int x = rightIn ? (graphicsIn.guiWidth() - 2 - lineWidth) : 2;
        int y = lineY;
        graphicsIn.drawString(font, s, x, y, -2039584, shadow);
        lineY += lineHeight;
      } 
    } 
    if (rightIn)
      graphicsIn.pose().popPose(); 
  }
  
  private static String getName(ResourceLocation loc) {
    if (loc == null)
      return ""; 
    if (loc.isDefaultNamespace())
      return loc.getPath(); 
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
    int chunks = minecraft.levelRenderer.countRenderedSections();
    sb.append(fullLabel ? "Chunks: " : "C: ");
    sb.append(Integer.toString(chunks));
  }
  
  private static void addEntities(StringBuilder sb, boolean fullLabel) {
    int entities = minecraft.levelRenderer.getCountEntitiesRendered();
    sb.append(fullLabel ? "Entities: " : "E: ");
    sb.append(Integer.toString(entities));
    int blockEntities = minecraft.levelRenderer.getCountTileEntitiesRendered();
    sb.append('+');
    sb.append(Integer.toString(blockEntities));
  }
  
  private static void addParticles(StringBuilder sb, boolean fullLabel) {
    int particles = minecraft.particleEngine.getCountParticles();
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
    Entity entity = minecraft.getCameraEntity();
    BlockPos pos = entity.blockPosition();
    sb.append(fullLabel ? "Position: " : "Pos: ");
    if (detailedCoords) {
      sb.append(" (");
      appendDouble3(sb, entity.getX());
      sb.append(", ");
      appendDouble3(sb, entity.getY());
      sb.append(", ");
      appendDouble3(sb, entity.getZ());
      sb.append(")");
    } else {
      sb.append(Integer.toString(pos.getX()));
      sb.append(", ");
      sb.append(Integer.toString(pos.getY()));
      sb.append(", ");
      sb.append(Integer.toString(pos.getZ()));
    } 
    if (posRel) {
      sb.append(" [");
      sb.append(Integer.toString(pos.getX() & 0xF));
      sb.append(", ");
      sb.append(Integer.toString(pos.getY() & 0xF));
      sb.append(", ");
      sb.append(Integer.toString(pos.getZ() & 0xF));
      sb.append("]");
    } 
  }
  
  private static void addFacing(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean facingXyz, boolean yawPitch) {
    Entity entity = minecraft.getCameraEntity();
    Direction dir = entity.getDirection();
    sb.append(fullLabel ? "Facing: " : "F: ");
    sb.append(dir.toString());
    if (facingXyz) {
      String dirXyz = getXyz(dir);
      sb.append(" [");
      sb.append(dirXyz);
      sb.append("]");
    } 
    if (yawPitch) {
      float yaw = Mth.wrapDegrees(entity.getYRot());
      float pitch = Mth.wrapDegrees(entity.getXRot());
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
  
  private static String getXyz(Direction dir) {
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
    Entity entity = minecraft.getCameraEntity();
    BlockPos pos = entity.blockPosition();
    Holder<Biome> biome = minecraft.level.getBiome(pos);
    Optional<ResourceKey<Biome>> key = biome.unwrapKey();
    String name = getBiomeName(key);
    sb.append(fullLabel ? "Biome: " : "B: ");
    sb.append(name);
  }
  
  private static String getBiomeName(Optional<ResourceKey<Biome>> key) {
    if (!key.isPresent())
      return "[unregistered]"; 
    ResourceLocation loc = ((ResourceKey)key.get()).location();
    if (loc.isDefaultNamespace())
      return loc.getPath(); 
    return loc.toString();
  }
  
  private static void addLight(StringBuilder sb, boolean fullLabel) {
    Entity entity = minecraft.getCameraEntity();
    BlockPos pos = entity.blockPosition();
    ClientLevel world = minecraft.level;
    int lightSky = world.getBrightness(LightLayer.SKY, pos);
    int lightBlock = world.getBrightness(LightLayer.BLOCK, pos);
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
    Entity entity = minecraft.getCameraEntity();
    double reachDist = minecraft.player.blockInteractionRange();
    HitResult rayTrace = entity.pick(reachDist, 0.0F, false);
    if (rayTrace.getType() == HitResult.Type.BLOCK) {
      BlockPos pos = ((BlockHitResult)rayTrace).getBlockPos();
      BlockState state = minecraft.level.getBlockState(pos);
      sb.append(fullLabel ? "Target Block: " : "TB: ");
      sb.append(getName(state.getBlockLocation()));
      if (showPos) {
        sb.append(" (");
        sb.append(Integer.toString(pos.getX()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getY()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getZ()));
        sb.append(")");
      } 
    } 
  }
  
  private static void addTargetFluid(StringBuilder sb, boolean fullLabel, boolean showPos) {
    Entity entity = minecraft.getCameraEntity();
    double reachDist = minecraft.player.blockInteractionRange();
    HitResult rayTrace = entity.pick(reachDist, 0.0F, true);
    if (rayTrace.getType() == HitResult.Type.BLOCK) {
      BlockPos pos = ((BlockHitResult)rayTrace).getBlockPos();
      BlockState state = minecraft.level.getBlockState(pos);
      Fluid fluid = state.getFluidState().getType();
      if (fluid == Fluids.EMPTY)
        return; 
      ResourceLocation loc = BuiltInRegistries.FLUID.getKey(fluid);
      String name = getName(loc);
      sb.append(fullLabel ? "Target Fluid: " : "TF: ");
      sb.append(name);
      if (showPos) {
        sb.append(" (");
        sb.append(Integer.toString(pos.getX()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getY()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getZ()));
        sb.append(")");
      } 
    } 
  }
  
  private static void addTargetEntity(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean showPos) {
    Entity entity = minecraft.crosshairPickEntity;
    if (entity == null)
      return; 
    BlockPos pos = entity.blockPosition();
    ResourceLocation loc = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
    String name = getName(loc);
    if (loc == null)
      return; 
    sb.append(fullLabel ? "Target Entity: " : "TE: ");
    sb.append(name);
    if (showPos)
      if (detailedCoords) {
        sb.append(" (");
        appendDouble3(sb, entity.getX());
        sb.append(", ");
        appendDouble3(sb, entity.getY());
        sb.append(", ");
        appendDouble3(sb, entity.getZ());
        sb.append(")");
      } else {
        sb.append(" (");
        sb.append(Integer.toString(pos.getX()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getY()));
        sb.append(", ");
        sb.append(Integer.toString(pos.getZ()));
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
