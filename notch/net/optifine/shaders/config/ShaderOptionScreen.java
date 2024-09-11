package notch.net.optifine.shaders.config;

import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;

public class ShaderOptionScreen extends ShaderOption {
  public ShaderOptionScreen(String name) {
    super(name, null, null, new String[0], null, null);
  }
  
  public String getNameText() {
    return Shaders.translate("screen." + getName(), getName());
  }
  
  public String getDescriptionText() {
    return Shaders.translate("screen." + getName() + ".comment", null);
  }
}
