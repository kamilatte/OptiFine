package notch.net.optifine.shaders;

import com.mojang.blaze3d.platform.TextureUtil;
import gpw;
import net.optifine.shaders.ICustomTexture;

public class CustomTexture implements ICustomTexture {
  private int textureUnit = -1;
  
  private String path = null;
  
  private gpw texture = null;
  
  public CustomTexture(int textureUnit, String path, gpw texture) {
    this.textureUnit = textureUnit;
    this.path = path;
    this.texture = texture;
  }
  
  public int getTextureUnit() {
    return this.textureUnit;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public gpw getTexture() {
    return this.texture;
  }
  
  public int getTextureId() {
    return this.texture.a();
  }
  
  public void deleteTexture() {
    TextureUtil.releaseTextureId(this.texture.a());
  }
  
  public String toString() {
    return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + getTextureId();
  }
}
