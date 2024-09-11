package srg.net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ListQuadsOverlay {
  private List<BakedQuad> listQuads = new ArrayList<>();
  
  private List<BlockState> listBlockStates = new ArrayList<>();
  
  private List<BakedQuad> listQuadsSingle = Arrays.asList(new BakedQuad[1]);
  
  public void addQuad(BakedQuad quad, BlockState blockState) {
    if (quad == null)
      return; 
    this.listQuads.add(quad);
    this.listBlockStates.add(blockState);
  }
  
  public int size() {
    return this.listQuads.size();
  }
  
  public BakedQuad getQuad(int index) {
    return this.listQuads.get(index);
  }
  
  public BlockState getBlockState(int index) {
    if (index < 0 || index >= this.listBlockStates.size())
      return Blocks.AIR.defaultBlockState(); 
    return this.listBlockStates.get(index);
  }
  
  public List<BakedQuad> getListQuadsSingle(BakedQuad quad) {
    this.listQuadsSingle.set(0, quad);
    return this.listQuadsSingle;
  }
  
  public void clear() {
    this.listQuads.clear();
    this.listBlockStates.clear();
  }
}
