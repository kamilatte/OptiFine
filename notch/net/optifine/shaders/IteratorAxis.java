package notch.net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import jd;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator<jd> {
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
  
  public IteratorAxis(jd posStart, jd posEnd, double yDelta, double zDelta) {
    this.yDelta = yDelta;
    this.zDelta = zDelta;
    this.xStart = posStart.u();
    this.xEnd = posEnd.u();
    this.yStart = posStart.v();
    this.yEnd = posEnd.v() - 0.5D;
    this.zStart = posStart.w();
    this.zEnd = posEnd.w() - 0.5D;
    this.xNext = this.xStart;
    this.yNext = this.yStart;
    this.zNext = this.zStart;
    this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
  }
  
  public boolean hasNext() {
    return this.hasNext;
  }
  
  public jd next() {
    if (!this.hasNext)
      throw new NoSuchElementException(); 
    this.pos.setXyz(this.xNext, this.yNext, this.zNext);
    nextPos();
    this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
    return (jd)this.pos;
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
    jd posStart = new jd(-2, 10, 20);
    jd posEnd = new jd(2, 12, 22);
    double yDelta = -0.5D;
    double zDelta = 0.5D;
    net.optifine.shaders.IteratorAxis it = new net.optifine.shaders.IteratorAxis(posStart, posEnd, yDelta, zDelta);
    System.out.println("Start: " + String.valueOf(posStart) + ", end: " + String.valueOf(posEnd) + ", yDelta: " + yDelta + ", zDelta: " + zDelta);
    while (it.hasNext()) {
      jd blockPos = it.next();
      System.out.println(String.valueOf(blockPos));
    } 
  }
}
