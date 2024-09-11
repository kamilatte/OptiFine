package srg.net.optifine.shaders;

import java.nio.ByteBuffer;
import net.optifine.shaders.ICustomTexture;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.PixelType;
import net.optifine.texture.TextureType;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class CustomTextureRaw implements ICustomTexture {
  private TextureType type;
  
  private int textureUnit;
  
  private int textureId;
  
  public CustomTextureRaw(TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType, ByteBuffer data, int textureUnit, boolean blur, boolean clamp) {
    this.type = type;
    this.textureUnit = textureUnit;
    this.textureId = GL11.glGenTextures();
    GL11.glBindTexture(getTarget(), this.textureId);
    TextureUtils.resetDataUnpacking();
    int wrapMode = clamp ? 33071 : 10497;
    int filterMode = blur ? 9729 : 9728;
    switch (null.$SwitchMap$net$optifine$texture$TextureType[type.ordinal()]) {
      case 1:
        GL11.glTexImage1D(3552, 0, internalFormat.getId(), width, 0, pixelFormat.getId(), pixelType.getId(), data);
        GL11.glTexParameteri(3552, 10242, wrapMode);
        GL11.glTexParameteri(3552, 10240, filterMode);
        GL11.glTexParameteri(3552, 10241, filterMode);
        break;
      case 2:
        GL11.glTexImage2D(3553, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
        GL11.glTexParameteri(3553, 10242, wrapMode);
        GL11.glTexParameteri(3553, 10243, wrapMode);
        GL11.glTexParameteri(3553, 10240, filterMode);
        GL11.glTexParameteri(3553, 10241, filterMode);
        break;
      case 3:
        GL20.glTexImage3D(32879, 0, internalFormat.getId(), width, height, depth, 0, pixelFormat.getId(), pixelType.getId(), data);
        GL11.glTexParameteri(32879, 10242, wrapMode);
        GL11.glTexParameteri(32879, 10243, wrapMode);
        GL11.glTexParameteri(32879, 32882, wrapMode);
        GL11.glTexParameteri(32879, 10240, filterMode);
        GL11.glTexParameteri(32879, 10241, filterMode);
        break;
      case 4:
        GL11.glTexImage2D(34037, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
        GL11.glTexParameteri(34037, 10242, wrapMode);
        GL11.glTexParameteri(34037, 10243, wrapMode);
        GL11.glTexParameteri(34037, 10240, filterMode);
        GL11.glTexParameteri(34037, 10241, filterMode);
        break;
    } 
    GL11.glBindTexture(getTarget(), 0);
  }
  
  public int getTarget() {
    return this.type.getId();
  }
  
  public int getTextureId() {
    return this.textureId;
  }
  
  public int getTextureUnit() {
    return this.textureUnit;
  }
  
  public void deleteTexture() {
    if (this.textureId > 0) {
      GL11.glDeleteTextures(this.textureId);
      this.textureId = 0;
    } 
  }
}
