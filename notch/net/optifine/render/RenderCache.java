package notch.net.optifine.render;

import fac;
import fbd;
import fhz;
import gfh;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.render.IBufferSourceListener;

public class RenderCache implements IBufferSourceListener {
  private long cacheTimeMs;
  
  private long updateTimeMs;
  
  private Map<gfh, ByteBuffer> renderTypeBuffers = new LinkedHashMap<>();
  
  private Deque<ByteBuffer> freeBuffers = new ArrayDeque<>();
  
  public RenderCache(long cacheTimeMs) {
    this.cacheTimeMs = cacheTimeMs;
  }
  
  public boolean drawCached(fhz graphicsIn) {
    if (System.currentTimeMillis() > this.updateTimeMs) {
      graphicsIn.e();
      for (ByteBuffer bb : this.renderTypeBuffers.values())
        this.freeBuffers.add(bb); 
      this.renderTypeBuffers.clear();
      this.updateTimeMs = System.currentTimeMillis() + this.cacheTimeMs;
      return false;
    } 
    for (gfh rt : this.renderTypeBuffers.keySet()) {
      ByteBuffer bb = this.renderTypeBuffers.get(rt);
      graphicsIn.putBulkData(rt, bb);
      bb.rewind();
    } 
    graphicsIn.e();
    return true;
  }
  
  public void startRender(fhz graphicsIn) {
    graphicsIn.d().addListener(this);
  }
  
  public void stopRender(fhz graphicsIn) {
    graphicsIn.e();
    graphicsIn.d().removeListener(this);
  }
  
  public void finish(gfh renderTypeIn, fbd bufferIn) {
    ByteBuffer bb = this.renderTypeBuffers.get(renderTypeIn);
    if (bb == null) {
      bb = allocateByteBuffer(524288);
      this.renderTypeBuffers.put(renderTypeIn, bb);
    } 
    bb.position(bb.limit());
    bb.limit(bb.capacity());
    bufferIn.getBulkData(bb);
    bb.flip();
  }
  
  private ByteBuffer allocateByteBuffer(int size) {
    ByteBuffer bb = this.freeBuffers.pollLast();
    if (bb == null)
      bb = fac.a(size); 
    bb.position(0);
    bb.limit(0);
    return bb;
  }
}
