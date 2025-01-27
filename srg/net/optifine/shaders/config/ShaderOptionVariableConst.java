package srg.net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionVariable;
import net.optifine.util.StrUtils;

public class ShaderOptionVariableConst extends ShaderOptionVariable {
  private String type = null;
  
  private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
  
  public ShaderOptionVariableConst(String name, String type, String description, String value, String[] values, String path) {
    super(name, description, value, values, path);
    this.type = type;
  }
  
  public String getSourceLine() {
    return "const " + this.type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
  }
  
  public boolean matchesLine(String line) {
    Matcher m = PATTERN_CONST.matcher(line);
    if (!m.matches())
      return false; 
    String defName = m.group(2);
    if (!defName.matches(getName()))
      return false; 
    return true;
  }
  
  public static ShaderOption parseOption(String line, String path) {
    Matcher m = PATTERN_CONST.matcher(line);
    if (!m.matches())
      return null; 
    String type = m.group(1);
    String name = m.group(2);
    String value = m.group(3);
    String description = m.group(4);
    String vals = StrUtils.getSegment(description, "[", "]");
    if (vals != null && vals.length() > 0)
      description = description.replace(vals, "").trim(); 
    String[] values = parseValues(value, vals);
    if (name == null || name.length() <= 0)
      return null; 
    path = StrUtils.removePrefix(path, "/shaders/");
    return (ShaderOption)new net.optifine.shaders.config.ShaderOptionVariableConst(name, type, description, value, values, path);
  }
}
