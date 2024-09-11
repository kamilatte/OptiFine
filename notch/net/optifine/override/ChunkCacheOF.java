package notch.net.optifine.override;

import dbz;
import dch;
import ddf;
import ddw;
import dqh;
import dtc;
import dvi;
import eot;
import epe;
import ghx;
import java.util.Arrays;
import jd;
import ji;
import kf;
import net.optifine.BlockPosM;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements dbz {
  private final ghx chunkCache;
  
  private final int posX;
  
  private final int posY;
  
  private final int posZ;
  
  private final int sizeX;
  
  private final int sizeY;
  
  private final int sizeZ;
  
  private final int sizeXZ;
  
  private int[] combinedLights;
  
  private dtc[] blockStates;
  
  private ddw[] biomes;
  
  private final int arraySize;
  
  private RenderEnv renderEnv;
  
  private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
  
  private static final ArrayCache cacheBlockStates = new ArrayCache(dtc.class, 16);
  
  private static final ArrayCache cacheBiomes = new ArrayCache(ddw.class, 16);
  
  public ChunkCacheOF(ghx chunkCache, kf sectionPos) {
    this.chunkCache = chunkCache;
    int minChunkX = sectionPos.u() - 1;
    int minChunkY = sectionPos.v() - 1;
    int minChunkZ = sectionPos.w() - 1;
    int maxChunkX = sectionPos.u() + 1;
    int maxChunkY = sectionPos.v() + 1;
    int maxChunkZ = sectionPos.w() + 1;
    this.sizeX = maxChunkX - minChunkX + 1 << 4;
    this.sizeY = maxChunkY - minChunkY + 1 << 4;
    this.sizeZ = maxChunkZ - minChunkZ + 1 << 4;
    this.sizeXZ = this.sizeX * this.sizeZ;
    this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
    this.posX = minChunkX << 4;
    this.posY = minChunkY << 4;
    this.posZ = minChunkZ << 4;
  }
  
  public int getPositionIndex(jd pos) {
    int dx = pos.u() - this.posX;
    if (dx < 0 || dx >= this.sizeX)
      return -1; 
    int dy = pos.v() - this.posY;
    if (dy < 0 || dy >= this.sizeY)
      return -1; 
    int dz = pos.w() - this.posZ;
    if (dz < 0 || dz >= this.sizeZ)
      return -1; 
    return dy * this.sizeXZ + dz * this.sizeX + dx;
  }
  
  public int a(ddf type, jd pos) {
    return this.chunkCache.a(type, pos);
  }
  
  public dtc a_(jd pos) {
    int index = getPositionIndex(pos);
    if (index < 0 || index >= this.arraySize || this.blockStates == null)
      return this.chunkCache.a_(pos); 
    dtc iblockstate = this.blockStates[index];
    if (iblockstate == null) {
      iblockstate = this.chunkCache.a_(pos);
      this.blockStates[index] = iblockstate;
    } 
    return iblockstate;
  }
  
  public void renderStart() {
    if (this.combinedLights == null)
      this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize); 
    if (this.blockStates == null)
      this.blockStates = (dtc[])cacheBlockStates.allocate(this.arraySize); 
    if (this.biomes == null)
      this.biomes = (ddw[])cacheBiomes.allocate(this.arraySize); 
    Arrays.fill(this.combinedLights, -1);
    Arrays.fill((Object[])this.blockStates, (Object)null);
    Arrays.fill((Object[])this.biomes, (Object)null);
    loadBlockStates();
  }
  
  private void loadBlockStates() {
    if (this.sizeX != 48 || this.sizeY != 48 || this.sizeZ != 48)
      return; 
    dvi chunk = this.chunkCache.getLevelChunk(kf.a(this.posX) + 1, kf.a(this.posZ) + 1);
    BlockPosM pos = new BlockPosM();
    for (int y = 16; y < 32; y++) {
      int dy = y * this.sizeXZ;
      for (int z = 16; z < 32; z++) {
        int dz = z * this.sizeX;
        for (int x = 16; x < 32; x++) {
          pos.setXyz(this.posX + x, this.posY + y, this.posZ + z);
          int index = dy + dz + x;
          dtc bs = chunk.a_((jd)pos);
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
  
  public int getCombinedLight(dtc blockStateIn, dbz worldIn, jd blockPosIn) {
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
  
  public ddw getBiome(jd pos) {
    int index = getPositionIndex(pos);
    if (index < 0 || index >= this.arraySize || this.biomes == null)
      return this.chunkCache.getBiome(pos); 
    ddw biome = this.biomes[index];
    if (biome == null) {
      biome = this.chunkCache.getBiome(pos);
      this.biomes[index] = biome;
    } 
    return biome;
  }
  
  public dqh c_(jd pos) {
    return this.chunkCache.c_(pos);
  }
  
  public boolean h(jd pos) {
    return this.chunkCache.h(pos);
  }
  
  public epe b_(jd pos) {
    return a_(pos).u();
  }
  
  public int a(jd blockPosIn, dch colorResolverIn) {
    return this.chunkCache.a(blockPosIn, colorResolverIn);
  }
  
  public eot y_() {
    return this.chunkCache.y_();
  }
  
  public RenderEnv getRenderEnv() {
    return this.renderEnv;
  }
  
  public void setRenderEnv(RenderEnv renderEnv) {
    this.renderEnv = renderEnv;
  }
  
  public float a(ji directionIn, boolean shadeIn) {
    return this.chunkCache.a(directionIn, shadeIn);
  }
  
  public int J_() {
    return this.chunkCache.J_();
  }
  
  public int I_() {
    return this.chunkCache.I_();
  }
}
