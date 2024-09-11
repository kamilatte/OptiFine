package srg.net.optifine.util;

import com.mojang.blaze3d.platform.NativeImage;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
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
    TextureManager textureManager = Minecraft.getInstance().getTextureManager();
    long sum = 0L;
    Collection<AbstractTexture> textures = textureManager.getTextures();
    for (AbstractTexture texture : textures) {
      long size = getTextureSize(texture);
      if (Config.isShaders())
        size *= 3L; 
      sum += size;
    } 
    return sum;
  }
  
  public static long getTextureSize(AbstractTexture texture) {
    if (texture instanceof DynamicTexture) {
      DynamicTexture dt = (DynamicTexture)texture;
      NativeImage img = dt.getPixels();
      if (img != null)
        return img.getSize(); 
    } 
    if (texture instanceof FontTexture) {
      FontTexture ft = (FontTexture)texture;
      return 262144L;
    } 
    if (texture instanceof SimpleShaderTexture) {
      SimpleShaderTexture sst = (SimpleShaderTexture)texture;
      return sst.getSize();
    } 
    if (texture instanceof SimpleTexture) {
      SimpleTexture st = (SimpleTexture)texture;
      return st.size;
    } 
    if (texture instanceof TextureAtlas) {
      TextureAtlas ta = (TextureAtlas)texture;
      return (ta.getWidth() * ta.getHeight() * 4);
    } 
    return 0L;
  }
}
