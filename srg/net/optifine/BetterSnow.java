package srg.net.optifine;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.optifine.Config;

public class BetterSnow {
  private static BakedModel modelSnowLayer = null;
  
  public static void update() {
    modelSnowLayer = Config.getMinecraft().getBlockRenderer().getBlockModelShaper().getBlockModel(Blocks.SNOW.defaultBlockState());
  }
  
  public static BakedModel getModelSnowLayer() {
    return modelSnowLayer;
  }
  
  public static BlockState getStateSnowLayer() {
    return Blocks.SNOW.defaultBlockState();
  }
  
  public static boolean shouldRender(BlockAndTintGetter lightReader, BlockState blockState, BlockPos blockPos) {
    if (!(lightReader instanceof BlockGetter))
      return false; 
    BlockAndTintGetter blockAndTintGetter = lightReader;
    if (!checkBlock((BlockGetter)blockAndTintGetter, blockState, blockPos))
      return false; 
    return hasSnowNeighbours((BlockGetter)blockAndTintGetter, blockPos);
  }
  
  private static boolean hasSnowNeighbours(BlockGetter blockAccess, BlockPos pos) {
    Block blockSnow = Blocks.SNOW;
    if (blockAccess.getBlockState(pos.north()).getBlock() == blockSnow || blockAccess
      .getBlockState(pos.south()).getBlock() == blockSnow || blockAccess
      .getBlockState(pos.west()).getBlock() == blockSnow || blockAccess
      .getBlockState(pos.east()).getBlock() == blockSnow) {
      BlockState bsDown = blockAccess.getBlockState(pos.below());
      if (bsDown.isSolidRender(blockAccess, pos))
        return true; 
      Block blockDown = bsDown.getBlock();
      if (blockDown instanceof StairBlock)
        return (bsDown.getValue((Property)StairBlock.HALF) == Half.TOP); 
      if (blockDown instanceof SlabBlock)
        return (bsDown.getValue((Property)SlabBlock.TYPE) == SlabType.TOP); 
    } 
    return false;
  }
  
  private static boolean checkBlock(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos) {
    if (blockState.isSolidRender(blockAccess, blockPos))
      return false; 
    Block block = blockState.getBlock();
    if (block == Blocks.SNOW_BLOCK)
      return false; 
    if (block instanceof net.minecraft.world.level.block.BushBlock)
      if (block instanceof net.minecraft.world.level.block.DoublePlantBlock || block instanceof net.minecraft.world.level.block.FlowerBlock || block instanceof net.minecraft.world.level.block.MushroomBlock || block instanceof net.minecraft.world.level.block.SaplingBlock || block instanceof net.minecraft.world.level.block.TallGrassBlock)
        return true;  
    if (block instanceof net.minecraft.world.level.block.FenceBlock || block instanceof net.minecraft.world.level.block.FenceGateBlock || block instanceof net.minecraft.world.level.block.FlowerPotBlock || block instanceof net.minecraft.world.level.block.CrossCollisionBlock || block instanceof net.minecraft.world.level.block.SugarCaneBlock || block instanceof net.minecraft.world.level.block.WallBlock)
      return true; 
    if (block instanceof net.minecraft.world.level.block.RedstoneTorchBlock)
      return true; 
    if (block instanceof StairBlock)
      return (blockState.getValue((Property)StairBlock.HALF) == Half.TOP); 
    if (block instanceof SlabBlock)
      return (blockState.getValue((Property)SlabBlock.TYPE) == SlabType.TOP); 
    if (block instanceof ButtonBlock)
      return (blockState.getValue((Property)ButtonBlock.FACE) != AttachFace.FLOOR); 
    if (block instanceof net.minecraft.world.level.block.HopperBlock)
      return true; 
    if (block instanceof net.minecraft.world.level.block.LadderBlock)
      return true; 
    if (block instanceof net.minecraft.world.level.block.LeverBlock)
      return true; 
    if (block instanceof net.minecraft.world.level.block.TurtleEggBlock)
      return true; 
    if (block instanceof net.minecraft.world.level.block.VineBlock)
      return true; 
    return false;
  }
}
