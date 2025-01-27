package srg.net.optifine.shaders.config;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.util.StrUtils;

public class ShaderOptionVariable extends ShaderOption {
  private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");
  
  public ShaderOptionVariable(String name, String description, String value, String[] values, String path) {
    super(name, description, value, values, value, path);
    setVisible(((getValues()).length > 1));
  }
  
  public String getSourceLine() {
    return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
  }
  
  public String getValueText(String val) {
    String prefix = Shaders.translate("prefix." + getName(), "");
    String text = super.getValueText(val);
    String suffix = Shaders.translate("suffix." + getName(), "");
    String textFull = prefix + prefix + text;
    return textFull;
  }
  
  public String getValueColor(String val) {
    String valLow = val.toLowerCase();
    if (valLow.equals("false") || valLow.equals("off"))
      return "§c"; 
    return "§a";
  }
  
  public boolean matchesLine(String line) {
    Matcher m = PATTERN_VARIABLE.matcher(line);
    if (!m.matches())
      return false; 
    String defName = m.group(1);
    if (!defName.matches(getName()))
      return false; 
    return true;
  }
  
  public static ShaderOption parseOption(String line, String path) {
    Matcher m = PATTERN_VARIABLE.matcher(line);
    if (!m.matches())
      return null; 
    String name = m.group(1);
    String value = m.group(2);
    String description = m.group(3);
    String vals = StrUtils.getSegment(description, "[", "]");
    if (vals != null && vals.length() > 0)
      description = description.replace(vals, "").trim(); 
    String[] values = parseValues(value, vals);
    if (name == null || name.length() <= 0)
      return null; 
    path = StrUtils.removePrefix(path, "/shaders/");
    ShaderOption so = new net.optifine.shaders.config.ShaderOptionVariable(name, description, value, values, path);
    return so;
  }
  
  public static String[] parseValues(String value, String valuesStr) {
    String[] values = { value };
    if (valuesStr == null)
      return values; 
    valuesStr = valuesStr.trim();
    valuesStr = StrUtils.removePrefix(valuesStr, "[");
    valuesStr = StrUtils.removeSuffix(valuesStr, "]");
    valuesStr = valuesStr.trim();
    if (valuesStr.length() <= 0)
      return values; 
    String[] parts = Config.tokenize(valuesStr, " ");
    if (parts.length <= 0)
      return values; 
    if (!Arrays.<String>asList(parts).contains(value))
      parts = (String[])Config.addObjectToArray((Object[])parts, value, 0); 
    return parts;
  }
}
