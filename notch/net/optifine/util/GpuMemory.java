package notch.net.optifine.util;

import faj;
import fgo;
import fle;
import gpw;
import gpy;
import gqe;
import gqk;
import gqm;
import java.util.Collection;
import net.optifine.Config;
import net.optifine.shaders.SimpleShaderTexture;

public class GpuMemory {
  private static long bufferAllocated = 0L;
  
  private static long textureAllocated = 0L;
  
  private static long textureAllocatedUpdateTime = 0L;
  
  public static synchronized void bufferAllocated(long size) {
    bufferAllocated += size;
  }
  
  public static synchronized void bufferFreed(long size) {
    bufferAllocated -= size;
  }
  
  public static long getBufferAllocated() {
    return bufferAllocated;
  }
  
  public static long getTextureAllocated() {
    if (System.currentTimeMillis() > textureAllocatedUpdateTime) {
      textureAllocated = calculateTextureAllocated();
      textureAllocatedUpdateTime = System.currentTimeMillis() + 1000L;
    } 
    return textureAllocated;
  }
  
  private static long calculateTextureAllocated() {
    gqm textureManager = fgo.Q().aa();
    long sum = 0L;
    Collection<gpw> textures = textureManager.getTextures();
    for (gpw texture : textures) {
      long size = getTextureSize(texture);
      if (Config.isShaders())
        size *= 3L; 
      sum += size;
    } 
    return sum;
  }
  
  public static long getTextureSize(gpw texture) {
    if (texture instanceof gpy) {
      gpy dt = (gpy)texture;
      faj img = dt.e();
      if (img != null)
        return img.getSize(); 
    } 
    if (texture instanceof fle) {
      fle ft = (fle)texture;
      return 262144L;
    } 
    if (texture instanceof SimpleShaderTexture) {
      SimpleShaderTexture sst = (SimpleShaderTexture)texture;
      return sst.getSize();
    } 
    if (texture instanceof gqe) {
      gqe st = (gqe)texture;
      return st.size;
    } 
    if (texture instanceof gqk) {
      gqk ta = (gqk)texture;
      return (ta.i() * ta.j() * 4);
    } 
    return 0L;
  }
}
