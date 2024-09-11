package notch.net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionSwitch;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch {
  private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
  
  public ShaderOptionSwitchConst(String name, String description, String value, String path) {
    super(name, description, value, path);
  }
  
  public String getSourceLine() {
    return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
  }
  
  public static ShaderOption parseOption(String line, String path) {
    Matcher m = PATTERN_CONST.matcher(line);
    if (!m.matches())
      return null; 
    String name = m.group(1);
    String value = m.group(2);
    String description = m.group(3);
    if (name == null || name.length() <= 0)
      return null; 
    path = StrUtils.removePrefix(path, "/shaders/");
    net.optifine.shaders.config.ShaderOptionSwitchConst shaderOptionSwitchConst = new net.optifine.shaders.config.ShaderOptionSwitchConst(name, description, value, path);
    shaderOptionSwitchConst.setVisible(false);
    return (ShaderOption)shaderOptionSwitchConst;
  }
  
  public boolean matchesLine(String line) {
    Matcher m = PATTERN_CONST.matcher(line);
    if (!m.matches())
      return false; 
    String defName = m.group(1);
    if (!defName.matches(getName()))
      return false; 
    return true;
  }
  
  public boolean checkUsed() {
    return false;
  }
}
