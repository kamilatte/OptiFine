package srg.net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.RenderType;
import net.optifine.Config;
import net.optifine.render.VboRange;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.LinkedList;
import org.lwjgl.PointerBuffer;

public class VboRegion {
  private RenderType layer = null;
  
  private int glArrayObjectId = GlStateManager._glGenVertexArrays();
  
  private int glBufferId = GlStateManager._glGenBuffers();
  
  private int capacity = 4096;
  
  private int positionTop = 0;
  
  private int sizeUsed;
  
  private LinkedList<VboRange> rangeList = new LinkedList();
  
  private VboRange compactRangeLast = null;
  
  private PointerBuffer bufferIndexVertex = Config.createDirectPointerBuffer(this.capacity);
  
  private IntBuffer bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
  
  private final int vertexBytes = DefaultVertexFormat.BLOCK.getVertexSize();
  
  private VertexFormat.Mode drawMode = VertexFormat.Mode.QUADS;
  
  private boolean isShaders = Config.isShaders();
  
  public VboRegion(RenderType layer) {
    this.layer = layer;
    bindBuffer();
    long capacityBytes = toBytes(this.capacity);
    GlStateManager._glBufferData(GlStateManager.GL_ARRAY_BUFFER, capacityBytes, GlStateManager.GL_STATIC_DRAW);
    unbindBuffer();
  }
  
  public void bufferData(ByteBuffer data, VboRange range) {
    if (this.glBufferId < 0)
      return; 
    int posOld = range.getPosition();
    int sizeOld = range.getSize();
    int sizeNew = toVertex(data.limit());
    if (sizeNew <= 0) {
      if (posOld >= 0) {
        range.setPosition(-1);
        range.setSize(0);
        this.rangeList.remove(range.getNode());
        this.sizeUsed -= sizeOld;
      } 
      return;
    } 
    if (sizeNew > sizeOld) {
      range.setPosition(this.positionTop);
      range.setSize(sizeNew);
      this.positionTop += sizeNew;
      if (posOld >= 0)
        this.rangeList.remove(range.getNode()); 
      this.rangeList.addLast(range.getNode());
    } 
    range.setSize(sizeNew);
    this.sizeUsed += sizeNew - sizeOld;
    checkVboSize(range.getPositionNext());
    long positionBytes = toBytes(range.getPosition());
    bindVertexArray();
    bindBuffer();
    GlStateManager.bufferSubData(GlStateManager.GL_ARRAY_BUFFER, positionBytes, data);
    unbindBuffer();
    unbindVertexArray();
    if (this.positionTop > this.sizeUsed * 11 / 10)
      compactRanges(1); 
  }
  
  private void compactRanges(int countMax) {
    if (this.rangeList.isEmpty())
      return; 
    VboRange range = this.compactRangeLast;
    if (range == null || !this.rangeList.contains(range.getNode()))
      range = (VboRange)this.rangeList.getFirst().getItem(); 
    int posCompact = range.getPosition();
    VboRange rangePrev = range.getPrev();
    if (rangePrev == null) {
      posCompact = 0;
    } else {
      posCompact = rangePrev.getPositionNext();
    } 
    int count = 0;
    while (range != null && count < countMax) {
      count++;
      if (range.getPosition() == posCompact) {
        posCompact += range.getSize();
        range = range.getNext();
        continue;
      } 
      int sizeFree = range.getPosition() - posCompact;
      if (range.getSize() <= sizeFree) {
        copyVboData(range.getPosition(), posCompact, range.getSize());
        range.setPosition(posCompact);
        posCompact += range.getSize();
        range = range.getNext();
        continue;
      } 
      checkVboSize(this.positionTop + range.getSize());
      copyVboData(range.getPosition(), this.positionTop, range.getSize());
      range.setPosition(this.positionTop);
      this.positionTop += range.getSize();
      VboRange rangeNext = range.getNext();
      this.rangeList.remove(range.getNode());
      this.rangeList.addLast(range.getNode());
      range = rangeNext;
    } 
    if (range == null)
      this.positionTop = ((VboRange)this.rangeList.getLast().getItem()).getPositionNext(); 
    this.compactRangeLast = range;
  }
  
  private void checkRanges() {
    int count = 0;
    int size = 0;
    VboRange range = (VboRange)this.rangeList.getFirst().getItem();
    while (range != null) {
      count++;
      size += range.getSize();
      if (range.getPosition() < 0 || range.getSize() <= 0 || range.getPositionNext() > this.positionTop)
        throw new RuntimeException("Invalid range: " + String.valueOf(range)); 
      VboRange rangePrev = range.getPrev();
      if (rangePrev != null)
        if (range.getPosition() < rangePrev.getPositionNext())
          throw new RuntimeException("Invalid range: " + String.valueOf(range));  
      VboRange rangeNext = range.getNext();
      if (rangeNext != null)
        if (range.getPositionNext() > rangeNext.getPosition())
          throw new RuntimeException("Invalid range: " + String.valueOf(range));  
      range = range.getNext();
    } 
    if (count != this.rangeList.getSize())
      throw new RuntimeException("Invalid count: " + count + " <> " + this.rangeList.getSize()); 
    if (size != this.sizeUsed)
      throw new RuntimeException("Invalid size: " + size + " <> " + this.sizeUsed); 
  }
  
  private void checkVboSize(int sizeMin) {
    if (this.capacity < sizeMin)
      expandVbo(sizeMin); 
  }
  
  private void copyVboData(int posFrom, int posTo, int size) {
    long posFromBytes = toBytes(posFrom);
    long posToBytes = toBytes(posTo);
    long sizeBytes = toBytes(size);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, this.glBufferId);
    GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, posFromBytes, posToBytes, sizeBytes);
    Config.checkGlError("Copy VBO range");
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
  }
  
  private void expandVbo(int sizeMin) {
    int capacityNew = this.capacity * 6 / 4;
    while (capacityNew < sizeMin)
      capacityNew = capacityNew * 6 / 4; 
    long capacityBytes = toBytes(this.capacity);
    long capacityNewBytes = toBytes(capacityNew);
    int glBufferIdNew = GlStateManager._glGenBuffers();
    GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, glBufferIdNew);
    GlStateManager._glBufferData(GlStateManager.GL_ARRAY_BUFFER, capacityNewBytes, GlStateManager.GL_STATIC_DRAW);
    Config.checkGlError("Expand VBO");
    GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, glBufferIdNew);
    GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, 0L, 0L, capacityBytes);
    Config.checkGlError("Copy VBO: " + capacityNewBytes);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
    GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
    GlStateManager._glDeleteBuffers(this.glBufferId);
    this.bufferIndexVertex = Config.createDirectPointerBuffer(capacityNew);
    this.bufferCountVertex = Config.createDirectIntBuffer(capacityNew);
    this.glBufferId = glBufferIdNew;
    this.capacity = capacityNew;
  }
  
  public void bindVertexArray() {
    GlStateManager._glBindVertexArray(this.glArrayObjectId);
  }
  
  public void bindBuffer() {
    GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, this.glBufferId);
  }
  
  public void drawArrays(VertexFormat.Mode drawMode, VboRange range) {
    if (this.drawMode != drawMode) {
      if (this.bufferIndexVertex.position() > 0)
        throw new IllegalArgumentException("Mixed region draw modes: " + String.valueOf(this.drawMode) + " != " + String.valueOf(drawMode)); 
      this.drawMode = drawMode;
    } 
    int indexSize = 4;
    int pos = drawMode.indexCount(range.getPosition()) * indexSize;
    this.bufferIndexVertex.put(pos);
    int count = drawMode.indexCount(range.getSize());
    this.bufferCountVertex.put(count);
  }
  
  public void finishDraw() {
    bindVertexArray();
    bindBuffer();
    this.layer.format().setupBufferState();
    if (this.isShaders)
      ShadersRender.setupArrayPointersVbo(); 
    int indexCount = this.drawMode.indexCount(this.positionTop);
    RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.getSequentialBuffer(this.drawMode);
    VertexFormat.IndexType indexType = rendersystem$autostorageindexbuffer.type();
    rendersystem$autostorageindexbuffer.bind(indexCount);
    this.bufferIndexVertex.flip();
    this.bufferCountVertex.flip();
    GlStateManager.glMultiDrawElements(this.drawMode.asGLMode, this.bufferCountVertex, indexType.asGLType, this.bufferIndexVertex);
    this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
    this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
    if (this.positionTop > this.sizeUsed * 11 / 10)
      compactRanges(1); 
  }
  
  public void unbindBuffer() {
    GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
  }
  
  public static void unbindVertexArray() {
    GlStateManager._glBindVertexArray(0);
  }
  
  public void deleteGlBuffers() {
    if (this.glArrayObjectId >= 0) {
      GlStateManager._glDeleteVertexArrays(this.glArrayObjectId);
      this.glArrayObjectId = -1;
    } 
    if (this.glBufferId >= 0) {
      GlStateManager._glDeleteBuffers(this.glBufferId);
      this.glBufferId = -1;
    } 
  }
  
  private long toBytes(int vertex) {
    return vertex * this.vertexBytes;
  }
  
  private int toVertex(long bytes) {
    return (int)(bytes / this.vertexBytes);
  }
  
  public int getPositionTop() {
    return this.positionTop;
  }
}
