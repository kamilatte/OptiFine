package srg.net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.IntBuffer;
import java.util.Arrays;
import net.optifine.util.ArrayUtils;
import net.optifine.util.BufferUtil;
import org.lwjgl.BufferUtils;

public class FlipTextures {
  private String name;
  
  private IntBuffer texturesA;
  
  private IntBuffer texturesB;
  
  private boolean[] flips;
  
  private boolean[] changed;
  
  public FlipTextures(String name, int capacity) {
    this.name = name;
    this.texturesA = BufferUtils.createIntBuffer(capacity);
    this.texturesB = BufferUtils.createIntBuffer(capacity);
    this.flips = new boolean[capacity];
    this.changed = new boolean[capacity];
  }
  
  public int capacity() {
    return this.texturesA.capacity();
  }
  
  public int position() {
    return this.texturesA.position();
  }
  
  public int limit() {
    return this.texturesA.limit();
  }
  
  public net.optifine.shaders.FlipTextures position(int position) {
    this.texturesA.position(position);
    this.texturesB.position(position);
    return this;
  }
  
  public net.optifine.shaders.FlipTextures limit(int limit) {
    this.texturesA.limit(limit);
    this.texturesB.limit(limit);
    return this;
  }
  
  public int get(boolean main, int index) {
    if (main)
      return getA(index); 
    return getB(index);
  }
  
  public int getA(int index) {
    return get(index, this.flips[index]);
  }
  
  public int getB(int index) {
    return get(index, !this.flips[index]);
  }
  
  private int get(int index, boolean flipped) {
    IntBuffer textures = flipped ? this.texturesB : this.texturesA;
    return textures.get(index);
  }
  
  public void flip(int index) {
    this.flips[index] = !this.flips[index];
    this.changed[index] = true;
  }
  
  public boolean isChanged(int index) {
    return this.changed[index];
  }
  
  public void reset() {
    Arrays.fill(this.flips, false);
    Arrays.fill(this.changed, false);
  }
  
  public void genTextures() {
    GlStateManager.genTextures(this.texturesA);
    GlStateManager.genTextures(this.texturesB);
  }
  
  public void deleteTextures() {
    GlStateManager.deleteTextures(this.texturesA);
    GlStateManager.deleteTextures(this.texturesB);
    reset();
  }
  
  public void fill(int val) {
    int limit = limit();
    for (int i = 0; i < limit; i++) {
      this.texturesA.put(i, val);
      this.texturesB.put(i, val);
    } 
  }
  
  public net.optifine.shaders.FlipTextures clear() {
    position(0);
    limit(capacity());
    return this;
  }
  
  public String toString() {
    return this.name + ", A: " + this.name + ", B: " + BufferUtil.getBufferString(this.texturesA) + ", flips: [" + BufferUtil.getBufferString(this.texturesB) + "], changed: [" + 
      ArrayUtils.arrayToString(this.flips, limit()) + "]";
  }
}
