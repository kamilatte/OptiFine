package notch.net.minecraftforge.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import gfh;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ChunkRenderTypeSet implements Iterable<gfh> {
  private static final List<gfh> CHUNK_RENDER_TYPES_LIST = gfh.I();
  
  private static final gfh[] CHUNK_RENDER_TYPES = CHUNK_RENDER_TYPES_LIST.<gfh>toArray(new gfh[0]);
  
  private static final net.minecraftforge.client.ChunkRenderTypeSet NONE = (net.minecraftforge.client.ChunkRenderTypeSet)new None();
  
  private static final net.minecraftforge.client.ChunkRenderTypeSet ALL = (net.minecraftforge.client.ChunkRenderTypeSet)new All();
  
  private final BitSet bits;
  
  public static net.minecraftforge.client.ChunkRenderTypeSet none() {
    return NONE;
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet all() {
    return ALL;
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet of(gfh... renderTypes) {
    return of(Arrays.asList(renderTypes));
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet of(Collection<gfh> renderTypes) {
    if (renderTypes.isEmpty())
      return none(); 
    return of(renderTypes);
  }
  
  private static net.minecraftforge.client.ChunkRenderTypeSet of(Iterable<gfh> renderTypes) {
    BitSet bits = new BitSet();
    for (gfh renderType : renderTypes) {
      int index = renderType.getChunkLayerId();
      Preconditions.checkArgument((index >= 0), "Attempted to create chunk render type set with a non-chunk render type: " + String.valueOf(renderType));
      bits.set(index);
    } 
    return new net.minecraftforge.client.ChunkRenderTypeSet(bits);
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet union(net.minecraftforge.client.ChunkRenderTypeSet... sets) {
    return union(Arrays.asList(sets));
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet union(Collection<net.minecraftforge.client.ChunkRenderTypeSet> sets) {
    if (sets.isEmpty())
      return none(); 
    return union(sets);
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet union(Iterable<net.minecraftforge.client.ChunkRenderTypeSet> sets) {
    BitSet bits = new BitSet();
    for (net.minecraftforge.client.ChunkRenderTypeSet set : sets)
      bits.or(set.bits); 
    return new net.minecraftforge.client.ChunkRenderTypeSet(bits);
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet intersection(net.minecraftforge.client.ChunkRenderTypeSet... sets) {
    return intersection(Arrays.asList(sets));
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet intersection(Collection<net.minecraftforge.client.ChunkRenderTypeSet> sets) {
    if (sets.isEmpty())
      return all(); 
    return intersection(sets);
  }
  
  public static net.minecraftforge.client.ChunkRenderTypeSet intersection(Iterable<net.minecraftforge.client.ChunkRenderTypeSet> sets) {
    BitSet bits = new BitSet();
    bits.set(0, CHUNK_RENDER_TYPES.length);
    for (net.minecraftforge.client.ChunkRenderTypeSet set : sets)
      bits.and(set.bits); 
    return new net.minecraftforge.client.ChunkRenderTypeSet(bits);
  }
  
  private ChunkRenderTypeSet(BitSet bits) {
    this.bits = bits;
  }
  
  public boolean isEmpty() {
    return this.bits.isEmpty();
  }
  
  public boolean contains(gfh renderType) {
    int id = renderType.getChunkLayerId();
    return (id >= 0 && this.bits.get(id));
  }
  
  public Iterator<gfh> iterator() {
    return (Iterator<gfh>)new IteratorImpl(this);
  }
  
  public List<gfh> asList() {
    return (List<gfh>)ImmutableList.copyOf(this);
  }
}
