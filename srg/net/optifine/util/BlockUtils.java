package srg.net.optifine.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.optifine.Config;
import net.optifine.render.RenderEnv;

public class BlockUtils {
  private static final ThreadLocal<RenderSideCacheKey> threadLocalKey = ThreadLocal.withInitial(() -> new RenderSideCacheKey(null, null, null));
  
  private static final ThreadLocal<Object2ByteLinkedOpenHashMap<RenderSideCacheKey>> threadLocalMap;
  
  static {
    threadLocalMap = ThreadLocal.withInitial(() -> {
          Object object = new Object(200);
          object.defaultReturnValue(127);
          return (Object2ByteLinkedOpenHashMap)object;
        });
  }
  
  public static boolean shouldSideBeRendered(BlockState blockStateIn, BlockGetter blockReaderIn, BlockPos blockPosIn, Direction facingIn, RenderEnv renderEnv) {
    BlockPos posNeighbour = blockPosIn.relative(facingIn);
    BlockState stateNeighbour = blockReaderIn.getBlockState(posNeighbour);
    if (stateNeighbour.isCacheOpaqueCube() && !(blockStateIn.getBlock() instanceof net.minecraft.world.level.block.PowderSnowBlock))
      return false; 
    if (blockStateIn.skipRendering(stateNeighbour, facingIn))
      return false; 
    if (stateNeighbour.canOcclude())
      return shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour); 
    return true;
  }
  
  public static boolean shouldSideBeRenderedCached(BlockState blockStateIn, BlockGetter blockReaderIn, BlockPos blockPosIn, Direction facingIn, RenderEnv renderEnv, BlockState stateNeighbourIn, BlockPos posNeighbourIn) {
    long key = blockStateIn.getBlockStateId() << 36L | stateNeighbourIn.getBlockStateId() << 4L | facingIn.ordinal();
    Long2ByteLinkedOpenHashMap map = renderEnv.getRenderSideMap();
    byte b0 = map.getAndMoveToFirst(key);
    if (b0 != 0)
      return (b0 > 0); 
    VoxelShape voxelshape = blockStateIn.getFaceOcclusionShape(blockReaderIn, blockPosIn, facingIn);
    if (voxelshape.isEmpty())
      return true; 
    VoxelShape voxelshape1 = stateNeighbourIn.getFaceOcclusionShape(blockReaderIn, posNeighbourIn, facingIn.getOpposite());
    boolean flag = Shapes.joinIsNotEmpty(voxelshape, voxelshape1, BooleanOp.ONLY_FIRST);
    if (map.size() > 400)
      map.removeLastByte(); 
    map.putAndMoveToFirst(key, (byte)(flag ? 1 : -1));
    return flag;
  }
  
  public static int getBlockId(Block block) {
    return BuiltInRegistries.BLOCK.getId(block);
  }
  
  public static Block getBlock(ResourceLocation loc) {
    if (!BuiltInRegistries.BLOCK.containsKey(loc))
      return null; 
    return (Block)BuiltInRegistries.BLOCK.get(loc);
  }
  
  public static int getMetadata(BlockState blockState) {
    Block block = blockState.getBlock();
    StateDefinition<Block, BlockState> stateContainer = block.getStateDefinition();
    ImmutableList immutableList = stateContainer.getPossibleStates();
    int metadata = immutableList.indexOf(blockState);
    return metadata;
  }
  
  public static int getMetadataCount(Block block) {
    StateDefinition<Block, BlockState> stateContainer = block.getStateDefinition();
    ImmutableList immutableList = stateContainer.getPossibleStates();
    return immutableList.size();
  }
  
  public static BlockState getBlockState(Block block, int metadata) {
    StateDefinition<Block, BlockState> stateContainer = block.getStateDefinition();
    ImmutableList<BlockState> immutableList = stateContainer.getPossibleStates();
    if (metadata < 0 || metadata >= immutableList.size())
      return null; 
    BlockState blockState = immutableList.get(metadata);
    return blockState;
  }
  
  public static List<BlockState> getBlockStates(Block block) {
    StateDefinition<Block, BlockState> stateContainer = block.getStateDefinition();
    return (List<BlockState>)stateContainer.getPossibleStates();
  }
  
  public static boolean isFullCube(BlockState stateIn, BlockGetter blockReaderIn, BlockPos posIn) {
    return stateIn.isCacheOpaqueCollisionShape();
  }
  
  public static Collection<Property> getProperties(BlockState blockState) {
    return blockState.getProperties();
  }
  
  public static boolean isPropertyTrue(BlockState blockState, BooleanProperty prop) {
    Boolean value = (Boolean)blockState.getValues().get(prop);
    return Config.isTrue(value);
  }
  
  public static boolean isPropertyFalse(BlockState blockState, BooleanProperty prop) {
    Boolean value = (Boolean)blockState.getValues().get(prop);
    return Config.isFalse(value);
  }
}
