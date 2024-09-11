package srg.net.optifine.shaders;

import java.nio.IntBuffer;
import java.util.Arrays;
import net.optifine.util.ArrayUtils;
import net.optifine.util.BufferUtil;
import org.lwjgl.BufferUtils;

public class DrawBuffers {
  private String name;
  
  private final int maxColorBuffers;
  
  private final int maxDrawBuffers;
  
  private final IntBuffer drawBuffers;
  
  private int[] attachmentMappings;
  
  private IntBuffer glDrawBuffers;
  
  public DrawBuffers(String name, int maxColorBuffers, int maxDrawBuffers) {
    this.name = name;
    this.maxColorBuffers = maxColorBuffers;
    this.maxDrawBuffers = maxDrawBuffers;
    this.drawBuffers = IntBuffer.wrap(new int[maxDrawBuffers]);
  }
  
  public int get(int index) {
    return this.drawBuffers.get(index);
  }
  
  public net.optifine.shaders.DrawBuffers put(int attachment) {
    resetMappings();
    this.drawBuffers.put(attachment);
    return this;
  }
  
  public net.optifine.shaders.DrawBuffers put(int index, int attachment) {
    resetMappings();
    this.drawBuffers.put(index, attachment);
    return this;
  }
  
  public int position() {
    return this.drawBuffers.position();
  }
  
  public net.optifine.shaders.DrawBuffers position(int newPosition) {
    resetMappings();
    this.drawBuffers.position(newPosition);
    return this;
  }
  
  public int limit() {
    return this.drawBuffers.limit();
  }
  
  public net.optifine.shaders.DrawBuffers limit(int newLimit) {
    resetMappings();
    this.drawBuffers.limit(newLimit);
    return this;
  }
  
  public int capacity() {
    return this.drawBuffers.capacity();
  }
  
  public net.optifine.shaders.DrawBuffers fill(int val) {
    for (int i = 0; i < this.drawBuffers.limit(); i++)
      this.drawBuffers.put(i, val); 
    resetMappings();
    return this;
  }
  
  private void resetMappings() {
    this.attachmentMappings = null;
    this.glDrawBuffers = null;
  }
  
  public int[] getAttachmentMappings() {
    if (this.attachmentMappings == null)
      this.attachmentMappings = makeAttachmentMappings(this.drawBuffers, this.maxColorBuffers, this.maxDrawBuffers); 
    return this.attachmentMappings;
  }
  
  private static int[] makeAttachmentMappings(IntBuffer drawBuffers, int maxColorBuffers, int maxDrawBuffers) {
    int[] ams = new int[maxColorBuffers];
    Arrays.fill(ams, -1);
    int i;
    for (i = 0; i < drawBuffers.limit(); i++) {
      int att = drawBuffers.get(i);
      int ai = att - 36064;
      if (ai >= 0 && ai < maxDrawBuffers)
        ams[ai] = ai; 
    } 
    for (i = 0; i < drawBuffers.limit(); i++) {
      int att = drawBuffers.get(i);
      int ai = att - 36064;
      if (ai >= maxDrawBuffers && ai < maxColorBuffers) {
        int mi = getMappingIndex(ai, maxDrawBuffers, ams);
        if (mi < 0)
          throw new RuntimeException("Too many draw buffers, mapping: " + ArrayUtils.arrayToString(ams)); 
        ams[ai] = mi;
      } 
    } 
    return ams;
  }
  
  private static int getMappingIndex(int ai, int maxDrawBuffers, int[] attachmentMappings) {
    if (ai < maxDrawBuffers)
      return ai; 
    if (attachmentMappings[ai] >= 0)
      return attachmentMappings[ai]; 
    for (int i = 0; i < maxDrawBuffers; ) {
      if (ArrayUtils.contains(attachmentMappings, i)) {
        i++;
        continue;
      } 
      return i;
    } 
    return -1;
  }
  
  public IntBuffer getGlDrawBuffers() {
    if (this.glDrawBuffers == null)
      this.glDrawBuffers = makeGlDrawBuffers(this.drawBuffers, getAttachmentMappings()); 
    return this.glDrawBuffers;
  }
  
  private static IntBuffer makeGlDrawBuffers(IntBuffer drawBuffers, int[] attachmentMappings) {
    IntBuffer glDrawBuffers = BufferUtils.createIntBuffer(drawBuffers.capacity());
    for (int i = 0; i < drawBuffers.limit(); i++) {
      int att = drawBuffers.get(i);
      int ai = att - 36064;
      int attFixed = 0;
      if (ai >= 0 && ai < attachmentMappings.length)
        attFixed = 36064 + attachmentMappings[ai]; 
      glDrawBuffers.put(i, attFixed);
    } 
    glDrawBuffers.limit(drawBuffers.limit());
    glDrawBuffers.position(drawBuffers.position());
    return glDrawBuffers;
  }
  
  public String getInfo(boolean glBuffers) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < this.drawBuffers.limit(); i++) {
      int att = this.drawBuffers.get(i);
      int ai = att - 36064;
      if (glBuffers) {
        int[] ams = getAttachmentMappings();
        if (ai >= 0 && ai < ams.length)
          ai = ams[ai]; 
      } 
      String attName = getIndexName(ai);
      sb.append(attName);
    } 
    return sb.toString();
  }
  
  private String getIndexName(int ai) {
    if (ai >= 0 && ai < this.maxColorBuffers)
      return "" + ai; 
    return "N";
  }
  
  public int indexOf(int att) {
    for (int i = 0; i < limit(); i++) {
      if (get(i) == att)
        return i; 
    } 
    return -1;
  }
  
  public String toString() {
    return this.name + ": " + this.name + ", mapping: " + BufferUtil.getBufferString(this.drawBuffers) + ", glDrawBuffers: " + 
      ArrayUtils.arrayToString(this.attachmentMappings);
  }
}
