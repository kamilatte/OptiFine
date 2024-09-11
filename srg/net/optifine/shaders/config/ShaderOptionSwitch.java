package srg.net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitch extends ShaderOption {
  private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
  
  private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
  
  public ShaderOptionSwitch(String name, String description, String value, String path) {
    super(name, description, value, new String[] { "false", "true" }, value, path);
  }
  
  public String getSourceLine() {
    return isTrue(getValue()) ? ("#define " + 
      getName() + " // Shader option ON") : ("//#define " + 
      
      getName() + " // Shader option OFF");
  }
  
  public String getValueText(String val) {
    String valTextRes = super.getValueText(val);
    if (valTextRes != val)
      return valTextRes; 
    if (isTrue(val))
      return Lang.getOn(); 
    return Lang.getOff();
  }
  
  public String getValueColor(String val) {
    if (isTrue(val))
      return "§a"; 
    return "§c";
  }
  
  public static ShaderOption parseOption(String line, String path) {
    Matcher m = PATTERN_DEFINE.matcher(line);
    if (!m.matches())
      return null; 
    String comment = m.group(1);
    String name = m.group(2);
    String description = m.group(3);
    if (name == null || name.length() <= 0)
      return null; 
    boolean commented = Config.equals(comment, "//");
    boolean enabled = !commented;
    path = StrUtils.removePrefix(path, "/shaders/");
    ShaderOption so = new net.optifine.shaders.config.ShaderOptionSwitch(name, description, String.valueOf(enabled), path);
    return so;
  }
  
  public boolean matchesLine(String line) {
    Matcher m = PATTERN_DEFINE.matcher(line);
    if (!m.matches())
      return false; 
    String defName = m.group(2);
    if (!defName.matches(getName()))
      return false; 
    return true;
  }
  
  public boolean checkUsed() {
    return true;
  }
  
  public boolean isUsedInLine(String line) {
    Matcher mif = PATTERN_IFDEF.matcher(line);
    if (mif.matches()) {
      String name = mif.group(2);
      if (name.equals(getName()))
        return true; 
    } 
    return false;
  }
  
  public static boolean isTrue(String val) {
    return Boolean.valueOf(val).booleanValue();
  }
}
