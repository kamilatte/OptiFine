package srg.net.optifine.render;

import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.vertex.BufferBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.optifine.render.IBufferSourceListener;

public class RenderCache implements IBufferSourceListener {
  private long cacheTimeMs;
  
  private long updateTimeMs;
  
  private Map<RenderType, ByteBuffer> renderTypeBuffers = new LinkedHashMap<>();
  
  private Deque<ByteBuffer> freeBuffers = new ArrayDeque<>();
  
  public RenderCache(long cacheTimeMs) {
    this.cacheTimeMs = cacheTimeMs;
  }
  
  public boolean drawCached(GuiGraphics graphicsIn) {
    if (System.currentTimeMillis() > this.updateTimeMs) {
      graphicsIn.flush();
      for (ByteBuffer bb : this.renderTypeBuffers.values())
        this.freeBuffers.add(bb); 
      this.renderTypeBuffers.clear();
      this.updateTimeMs = System.currentTimeMillis() + this.cacheTimeMs;
      return false;
    } 
    for (RenderType rt : this.renderTypeBuffers.keySet()) {
      ByteBuffer bb = this.renderTypeBuffers.get(rt);
      graphicsIn.putBulkData(rt, bb);
      bb.rewind();
    } 
    graphicsIn.flush();
    return true;
  }
  
  public void startRender(GuiGraphics graphicsIn) {
    graphicsIn.bufferSource().addListener(this);
  }
  
  public void stopRender(GuiGraphics graphicsIn) {
    graphicsIn.flush();
    graphicsIn.bufferSource().removeListener(this);
  }
  
  public void finish(RenderType renderTypeIn, BufferBuilder bufferIn) {
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
      bb = GlUtil.allocateMemory(size); 
    bb.position(0);
    bb.limit(0);
    return bb;
  }
}
