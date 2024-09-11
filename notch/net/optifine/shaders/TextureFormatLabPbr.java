package notch.net.optifine.shaders;

import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.ColorBlenderLabPbrSpecular;
import net.optifine.texture.ColorBlenderLinear;
import net.optifine.texture.IColorBlender;

public class TextureFormatLabPbr implements ITextureFormat {
  private String version;
  
  public TextureFormatLabPbr(String ver) {
    this.version = ver;
  }
  
  public String getMacroName() {
    return "LAB_PBR";
  }
  
  public String getMacroVersion() {
    if (this.version == null)
      return null; 
    return this.version.replace('.', '_');
  }
  
  public IColorBlender getColorBlender(ShadersTextureType typeIn) {
    if (typeIn == ShadersTextureType.SPECULAR)
      return (IColorBlender)new ColorBlenderLabPbrSpecular(); 
    return (IColorBlender)new ColorBlenderLinear();
  }
  
  public boolean isTextureBlend(ShadersTextureType typeIn) {
    if (typeIn == ShadersTextureType.SPECULAR)
      return false; 
    return true;
  }
}
