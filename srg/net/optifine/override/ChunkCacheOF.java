package srg.net.optifine.override;

import java.util.Arrays;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.optifine.BlockPosM;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements BlockAndTintGetter {
  private final RenderChunkRegion chunkCache;
  
  private final int posX;
  
  private final int posY;
  
  private final int posZ;
  
  private final int sizeX;
  
  private final int sizeY;
  
  private final int sizeZ;
  
  private final int sizeXZ;
  
  private int[] combinedLights;
  
  private BlockState[] blockStates;
  
  private Biome[] biomes;
  
  private final int arraySize;
  
  private RenderEnv renderEnv;
  
  private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
  
  private static final ArrayCache cacheBlockStates = new ArrayCache(BlockState.class, 16);
  
  private static final ArrayCache cacheBiomes = new ArrayCache(Biome.class, 16);
  
  public ChunkCacheOF(RenderChunkRegion chunkCache, SectionPos sectionPos) {
    this.chunkCache = chunkCache;
    int minChunkX = sectionPos.getX() - 1;
    int minChunkY = sectionPos.getY() - 1;
    int minChunkZ = sectionPos.getZ() - 1;
    int maxChunkX = sectionPos.getX() + 1;
    int maxChunkY = sectionPos.getY() + 1;
    int maxChunkZ = sectionPos.getZ() + 1;
    this.sizeX = maxChunkX - minChunkX + 1 << 4;
    this.sizeY = maxChunkY - minChunkY + 1 << 4;
    this.sizeZ = maxChunkZ - minChunkZ + 1 << 4;
    this.sizeXZ = this.sizeX * this.sizeZ;
    this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
    this.posX = minChunkX << 4;
    this.posY = minChunkY << 4;
    this.posZ = minChunkZ << 4;
  }
  
  public int getPositionIndex(BlockPos pos) {
    int dx = pos.getX() - this.posX;
    if (dx < 0 || dx >= this.sizeX)
      return -1; 
    int dy = pos.getY() - this.posY;
    if (dy < 0 || dy >= this.sizeY)
      return -1; 
    int dz = pos.getZ() - this.posZ;
    if (dz < 0 || dz >= this.sizeZ)
      return -1; 
    return dy * this.sizeXZ + dz * this.sizeX + dx;
  }
  
  public int getBrightness(LightLayer type, BlockPos pos) {
    return this.chunkCache.getBrightness(type, pos);
  }
  
  public BlockState getBlockState(BlockPos pos) {
    int index = getPositionIndex(pos);
    if (index < 0 || index >= this.arraySize || this.blockStates == null)
      return this.chunkCache.getBlockState(pos); 
    BlockState iblockstate = this.blockStates[index];
    if (iblockstate == null) {
      iblockstate = this.chunkCache.getBlockState(pos);
      this.blockStates[index] = iblockstate;
    } 
    return iblockstate;
  }
  
  public void renderStart() {
    if (this.combinedLights == null)
      this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize); 
    if (this.blockStates == null)
      this.blockStates = (BlockState[])cacheBlockStates.allocate(this.arraySize); 
    if (this.biomes == null)
      this.biomes = (Biome[])cacheBiomes.allocate(this.arraySize); 
    Arrays.fill(this.combinedLights, -1);
    Arrays.fill((Object[])this.blockStates, (Object)null);
    Arrays.fill((Object[])this.biomes, (Object)null);
    loadBlockStates();
  }
  
  private void loadBlockStates() {
    if (this.sizeX != 48 || this.sizeY != 48 || this.sizeZ != 48)
      return; 
    LevelChunk chunk = this.chunkCache.getLevelChunk(SectionPos.blockToSectionCoord(this.posX) + 1, SectionPos.blockToSectionCoord(this.posZ) + 1);
    BlockPosM pos = new BlockPosM();
    for (int y = 16; y < 32; y++) {
      int dy = y * this.sizeXZ;
      for (int z = 16; z < 32; z++) {
        int dz = z * this.sizeX;
        for (int x = 16; x < 32; x++) {
          pos.setXyz(this.posX + x, this.posY + y, this.posZ + z);
          int index = dy + dz + x;
          BlockState bs = chunk.getBlockState((BlockPos)pos);
          this.blockStates[index] = bs;
        } 
      } 
    } 
  }
  
  public void renderFinish() {
    cacheCombinedLights.free(this.combinedLights);
    this.combinedLights = null;
    cacheBlockStates.free(this.blockStates);
    this.blockStates = null;
    cacheBiomes.free(this.biomes);
    this.biomes = null;
    this.chunkCache.finish();
  }
  
  public int getCombinedLight(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
    int index = getPositionIndex(blockPosIn);
    if (index < 0 || index >= this.combinedLights.length || this.combinedLights == null)
      return LightCacheOF.getPackedLightRaw(worldIn, blockStateIn, blockPosIn); 
    int light = this.combinedLights[index];
    if (light == -1) {
      light = LightCacheOF.getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
      this.combinedLights[index] = light;
    } 
    return light;
  }
  
  public Biome getBiome(BlockPos pos) {
    int index = getPositionIndex(pos);
    if (index < 0 || index >= this.arraySize || this.biomes == null)
      return this.chunkCache.getBiome(pos); 
    Biome biome = this.biomes[index];
    if (biome == null) {
      biome = this.chunkCache.getBiome(pos);
      this.biomes[index] = biome;
    } 
    return biome;
  }
  
  public BlockEntity getBlockEntity(BlockPos pos) {
    return this.chunkCache.getBlockEntity(pos);
  }
  
  public boolean canSeeSky(BlockPos pos) {
    return this.chunkCache.canSeeSky(pos);
  }
  
  public FluidState getFluidState(BlockPos pos) {
    return getBlockState(pos).getFluidState();
  }
  
  public int getBlockTint(BlockPos blockPosIn, ColorResolver colorResolverIn) {
    return this.chunkCache.getBlockTint(blockPosIn, colorResolverIn);
  }
  
  public LevelLightEngine getLightEngine() {
    return this.chunkCache.getLightEngine();
  }
  
  public RenderEnv getRenderEnv() {
    return this.renderEnv;
  }
  
  public void setRenderEnv(RenderEnv renderEnv) {
    this.renderEnv = renderEnv;
  }
  
  public float getShade(Direction directionIn, boolean shadeIn) {
    return this.chunkCache.getShade(directionIn, shadeIn);
  }
  
  public int getHeight() {
    return this.chunkCache.getHeight();
  }
  
  public int getMinBuildHeight() {
    return this.chunkCache.getMinBuildHeight();
  }
}
