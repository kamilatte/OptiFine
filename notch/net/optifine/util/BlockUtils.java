package notch.net.optifine.util;

import akr;
import com.google.common.collect.ImmutableList;
import dcc;
import dfy;
import dtc;
import dtd;
import dtt;
import duf;
import exg;
import exs;
import exv;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import jd;
import ji;
import lt;
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
  
  public static boolean shouldSideBeRendered(dtc blockStateIn, dcc blockReaderIn, jd blockPosIn, ji facingIn, RenderEnv renderEnv) {
    jd posNeighbour = blockPosIn.a(facingIn);
    dtc stateNeighbour = blockReaderIn.a_(posNeighbour);
    if (stateNeighbour.isCacheOpaqueCube() && !(blockStateIn.b() instanceof dlt))
      return false; 
    if (blockStateIn.a(stateNeighbour, facingIn))
      return false; 
    if (stateNeighbour.p())
      return shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour); 
    return true;
  }
  
  public static boolean shouldSideBeRenderedCached(dtc blockStateIn, dcc blockReaderIn, jd blockPosIn, ji facingIn, RenderEnv renderEnv, dtc stateNeighbourIn, jd posNeighbourIn) {
    long key = blockStateIn.getBlockStateId() << 36L | stateNeighbourIn.getBlockStateId() << 4L | facingIn.ordinal();
    Long2ByteLinkedOpenHashMap map = renderEnv.getRenderSideMap();
    byte b0 = map.getAndMoveToFirst(key);
    if (b0 != 0)
      return (b0 > 0); 
    exv voxelshape = blockStateIn.a(blockReaderIn, blockPosIn, facingIn);
    if (voxelshape.c())
      return true; 
    exv voxelshape1 = stateNeighbourIn.a(blockReaderIn, posNeighbourIn, facingIn.g());
    boolean flag = exs.c(voxelshape, voxelshape1, exg.e);
    if (map.size() > 400)
      map.removeLastByte(); 
    map.putAndMoveToFirst(key, (byte)(flag ? 1 : -1));
    return flag;
  }
  
  public static int getBlockId(dfy block) {
    return lt.e.a(block);
  }
  
  public static dfy getBlock(akr loc) {
    if (!lt.e.d(loc))
      return null; 
    return (dfy)lt.e.a(loc);
  }
  
  public static int getMetadata(dtc blockState) {
    dfy block = blockState.b();
    dtd<dfy, dtc> stateContainer = block.l();
    ImmutableList immutableList = stateContainer.a();
    int metadata = immutableList.indexOf(blockState);
    return metadata;
  }
  
  public static int getMetadataCount(dfy block) {
    dtd<dfy, dtc> stateContainer = block.l();
    ImmutableList immutableList = stateContainer.a();
    return immutableList.size();
  }
  
  public static dtc getBlockState(dfy block, int metadata) {
    dtd<dfy, dtc> stateContainer = block.l();
    ImmutableList<dtc> immutableList = stateContainer.a();
    if (metadata < 0 || metadata >= immutableList.size())
      return null; 
    dtc blockState = immutableList.get(metadata);
    return blockState;
  }
  
  public static List<dtc> getBlockStates(dfy block) {
    dtd<dfy, dtc> stateContainer = block.l();
    return (List<dtc>)stateContainer.a();
  }
  
  public static boolean isFullCube(dtc stateIn, dcc blockReaderIn, jd posIn) {
    return stateIn.isCacheOpaqueCollisionShape();
  }
  
  public static Collection<duf> getProperties(dtc blockState) {
    return blockState.B();
  }
  
  public static boolean isPropertyTrue(dtc blockState, dtt prop) {
    Boolean value = (Boolean)blockState.C().get(prop);
    return Config.isTrue(value);
  }
  
  public static boolean isPropertyFalse(dtc blockState, dtt prop) {
    Boolean value = (Boolean)blockState.C().get(prop);
    return Config.isFalse(value);
  }
}
