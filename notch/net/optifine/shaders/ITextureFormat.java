package notch.net.optifine.shaders;

import net.optifine.Config;
import net.optifine.config.ConfigUtils;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.shaders.TextureFormatLabPbr;
import net.optifine.texture.IColorBlender;

public interface ITextureFormat {
  IColorBlender getColorBlender(ShadersTextureType paramShadersTextureType);
  
  boolean isTextureBlend(ShadersTextureType paramShadersTextureType);
  
  String getMacroName();
  
  String getMacroVersion();
  
  static net.optifine.shaders.ITextureFormat readConfiguration() {
    if (!Config.isShaders())
      return null; 
    String formatStr = ConfigUtils.readString("optifine/texture.properties", "format");
    if (formatStr != null) {
      String[] parts = Config.tokenize(formatStr, "/");
      String name = parts[0];
      String ver = (parts.length > 1) ? parts[1] : null;
      if (name.equals("lab-pbr"))
        return (net.optifine.shaders.ITextureFormat)new TextureFormatLabPbr(ver); 
      Config.warn("Unknown texture format: " + formatStr);
      return null;
    } 
    return null;
  }
}
