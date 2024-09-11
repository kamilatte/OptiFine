package srg.net.optifine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.optifine.Config;
import net.optifine.DynamicLights;

public class DynamicLight {
  private Entity entity = null;
  
  private double offsetY = 0.0D;
  
  private double lastPosX = -2.147483648E9D;
  
  private double lastPosY = -2.147483648E9D;
  
  private double lastPosZ = -2.147483648E9D;
  
  private int lastLightLevel = 0;
  
  private long timeCheckMs = 0L;
  
  private Set<BlockPos> setLitChunkPos = new HashSet<>();
  
  private BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();
  
  public DynamicLight(Entity entity) {
    this.entity = entity;
    this.offsetY = entity.getEyeHeight();
  }
  
  public void update(LevelRenderer renderGlobal) {
    if (Config.isDynamicLightsFast()) {
      long timeNowMs = System.currentTimeMillis();
      if (timeNowMs < this.timeCheckMs + 500L)
        return; 
      this.timeCheckMs = timeNowMs;
    } 
    double posX = this.entity.getX() - 0.5D;
    double posY = this.entity.getY() - 0.5D + this.offsetY;
    double posZ = this.entity.getZ() - 0.5D;
    int lightLevel = DynamicLights.getLightLevel(this.entity);
    double dx = posX - this.lastPosX;
    double dy = posY - this.lastPosY;
    double dz = posZ - this.lastPosZ;
    double delta = 0.1D;
    if (Math.abs(dx) <= delta && Math.abs(dy) <= delta && Math.abs(dz) <= delta && this.lastLightLevel == lightLevel)
      return; 
    this.lastPosX = posX;
    this.lastPosY = posY;
    this.lastPosZ = posZ;
    this.lastLightLevel = lightLevel;
    Set<BlockPos> setNewPos = new HashSet<>();
    if (lightLevel > 0) {
      Direction dirX = ((Mth.floor(posX) & 0xF) >= 8) ? Direction.EAST : Direction.WEST;
      Direction dirY = ((Mth.floor(posY) & 0xF) >= 8) ? Direction.UP : Direction.DOWN;
      Direction dirZ = ((Mth.floor(posZ) & 0xF) >= 8) ? Direction.SOUTH : Direction.NORTH;
      BlockPos chunkPos = BlockPos.containing(posX, posY, posZ);
      SectionRenderDispatcher.RenderSection chunk = renderGlobal.getRenderChunk(chunkPos);
      BlockPos chunkPosX = getChunkPos(chunk, chunkPos, dirX);
      SectionRenderDispatcher.RenderSection chunkX = renderGlobal.getRenderChunk(chunkPosX);
      BlockPos chunkPosZ = getChunkPos(chunk, chunkPos, dirZ);
      SectionRenderDispatcher.RenderSection chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
      BlockPos chunkPosXZ = getChunkPos(chunkX, chunkPosX, dirZ);
      SectionRenderDispatcher.RenderSection chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
      BlockPos chunkPosY = getChunkPos(chunk, chunkPos, dirY);
      SectionRenderDispatcher.RenderSection chunkY = renderGlobal.getRenderChunk(chunkPosY);
      BlockPos chunkPosYX = getChunkPos(chunkY, chunkPosY, dirX);
      SectionRenderDispatcher.RenderSection chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
      BlockPos chunkPosYZ = getChunkPos(chunkY, chunkPosY, dirZ);
      SectionRenderDispatcher.RenderSection chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
      BlockPos chunkPosYXZ = getChunkPos(chunkYX, chunkPosYX, dirZ);
      SectionRenderDispatcher.RenderSection chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
      updateChunkLight(chunk, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkX, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkXZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkY, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYX, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYZ, this.setLitChunkPos, setNewPos);
      updateChunkLight(chunkYXZ, this.setLitChunkPos, setNewPos);
    } 
    updateLitChunks(renderGlobal);
    this.setLitChunkPos = setNewPos;
  }
  
  private BlockPos getChunkPos(SectionRenderDispatcher.RenderSection renderChunk, BlockPos pos, Direction facing) {
    if (renderChunk != null)
      return renderChunk.getRelativeOrigin(facing); 
    return pos.relative(facing, 16);
  }
  
  private void updateChunkLight(SectionRenderDispatcher.RenderSection renderChunk, Set<BlockPos> setPrevPos, Set<BlockPos> setNewPos) {
    if (renderChunk == null)
      return; 
    SectionRenderDispatcher.CompiledSection compiledChunk = renderChunk.getCompiled();
    if (compiledChunk != null && !compiledChunk.hasNoRenderableLayers()) {
      renderChunk.setDirty(false);
      renderChunk.setNeedsBackgroundPriorityUpdate(true);
    } 
    BlockPos pos = renderChunk.getOrigin().immutable();
    if (setPrevPos != null)
      setPrevPos.remove(pos); 
    if (setNewPos != null)
      setNewPos.add(pos); 
  }
  
  public void updateLitChunks(LevelRenderer renderGlobal) {
    for (Iterator<BlockPos> it = this.setLitChunkPos.iterator(); it.hasNext(); ) {
      BlockPos posOld = it.next();
      SectionRenderDispatcher.RenderSection chunkOld = renderGlobal.getRenderChunk(posOld);
      updateChunkLight(chunkOld, null, null);
    } 
  }
  
  public Entity getEntity() {
    return this.entity;
  }
  
  public double getLastPosX() {
    return this.lastPosX;
  }
  
  public double getLastPosY() {
    return this.lastPosY;
  }
  
  public double getLastPosZ() {
    return this.lastPosZ;
  }
  
  public int getLastLightLevel() {
    return this.lastLightLevel;
  }
  
  public double getOffsetY() {
    return this.offsetY;
  }
  
  public String toString() {
    return "Entity: " + String.valueOf(this.entity) + ", offsetY: " + this.offsetY;
  }
}
