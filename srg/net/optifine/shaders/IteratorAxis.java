package srg.net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.core.BlockPos;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator<BlockPos> {
  private double yDelta;
  
  private double zDelta;
  
  private int xStart;
  
  private int xEnd;
  
  private double yStart;
  
  private double yEnd;
  
  private double zStart;
  
  private double zEnd;
  
  private int xNext;
  
  private double yNext;
  
  private double zNext;
  
  private BlockPosM pos = new BlockPosM(0, 0, 0);
  
  private boolean hasNext = false;
  
  public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
    this.yDelta = yDelta;
    this.zDelta = zDelta;
    this.xStart = posStart.getX();
    this.xEnd = posEnd.getX();
    this.yStart = posStart.getY();
    this.yEnd = posEnd.getY() - 0.5D;
    this.zStart = posStart.getZ();
    this.zEnd = posEnd.getZ() - 0.5D;
    this.xNext = this.xStart;
    this.yNext = this.yStart;
    this.zNext = this.zStart;
    this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
  }
  
  public boolean hasNext() {
    return this.hasNext;
  }
  
  public BlockPos next() {
    if (!this.hasNext)
      throw new NoSuchElementException(); 
    this.pos.setXyz(this.xNext, this.yNext, this.zNext);
    nextPos();
    this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
    return (BlockPos)this.pos;
  }
  
  private void nextPos() {
    this.zNext++;
    if (this.zNext < this.zEnd)
      return; 
    this.zNext = this.zStart;
    this.yNext++;
    if (this.yNext < this.yEnd)
      return; 
    this.yNext = this.yStart;
    this.yStart += this.yDelta;
    this.yEnd += this.yDelta;
    this.yNext = this.yStart;
    this.zStart += this.zDelta;
    this.zEnd += this.zDelta;
    this.zNext = this.zStart;
    this.xNext++;
    if (this.xNext < this.xEnd)
      return; 
  }
  
  public void remove() {
    throw new RuntimeException("Not implemented");
  }
  
  public static void main(String[] args) throws Exception {
    BlockPos posStart = new BlockPos(-2, 10, 20);
    BlockPos posEnd = new BlockPos(2, 12, 22);
    double yDelta = -0.5D;
    double zDelta = 0.5D;
    net.optifine.shaders.IteratorAxis it = new net.optifine.shaders.IteratorAxis(posStart, posEnd, yDelta, zDelta);
    System.out.println("Start: " + String.valueOf(posStart) + ", end: " + String.valueOf(posEnd) + ", yDelta: " + yDelta + ", zDelta: " + zDelta);
    while (it.hasNext()) {
      BlockPos blockPos = it.next();
      System.out.println(String.valueOf(blockPos));
    } 
  }
}
