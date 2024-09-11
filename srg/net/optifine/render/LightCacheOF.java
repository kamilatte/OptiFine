package srg.net.optifine.render;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
  public static final float getBrightness(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
    float aoLight = getAoLightRaw(blockStateIn, worldIn, blockPosIn);
    aoLight = ModelBlockRenderer.fixAoLightValue(aoLight);
    return aoLight;
  }
  
  private static float getAoLightRaw(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
    if (blockStateIn.getBlock() == Blocks.MOVING_PISTON)
      return 1.0F; 
    if (blockStateIn.getBlock() == Blocks.SCAFFOLDING)
      return 1.0F; 
    return blockStateIn.getShadeBrightness((BlockGetter)worldIn, blockPosIn);
  }
  
  public static final int getPackedLight(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
    if (worldIn instanceof ChunkCacheOF) {
      ChunkCacheOF cc = (ChunkCacheOF)worldIn;
      return cc.getCombinedLight(blockStateIn, worldIn, blockPosIn);
    } 
    return getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
  }
  
  public static int getPackedLightRaw(BlockAndTintGetter worldIn, BlockState blockStateIn, BlockPos blockPosIn) {
    return LevelRenderer.getLightColor(worldIn, blockStateIn, blockPosIn);
  }
}
