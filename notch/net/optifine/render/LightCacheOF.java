package notch.net.optifine.render;

import dbz;
import dcc;
import dga;
import dtc;
import gex;
import gfv;
import jd;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
  public static final float getBrightness(dtc blockStateIn, dbz worldIn, jd blockPosIn) {
    float aoLight = getAoLightRaw(blockStateIn, worldIn, blockPosIn);
    aoLight = gfv.fixAoLightValue(aoLight);
    return aoLight;
  }
  
  private static float getAoLightRaw(dtc blockStateIn, dbz worldIn, jd blockPosIn) {
    if (blockStateIn.b() == dga.bQ)
      return 1.0F; 
    if (blockStateIn.b() == dga.nS)
      return 1.0F; 
    return blockStateIn.f((dcc)worldIn, blockPosIn);
  }
  
  public static final int getPackedLight(dtc blockStateIn, dbz worldIn, jd blockPosIn) {
    if (worldIn instanceof ChunkCacheOF) {
      ChunkCacheOF cc = (ChunkCacheOF)worldIn;
      return cc.getCombinedLight(blockStateIn, worldIn, blockPosIn);
    } 
    return getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
  }
  
  public static int getPackedLightRaw(dbz worldIn, dtc blockStateIn, jd blockPosIn) {
    return gex.a(worldIn, blockStateIn, blockPosIn);
  }
}
