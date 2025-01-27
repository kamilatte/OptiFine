package srg.net.optifine.shaders.config;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;
import net.optifine.Config;
import net.optifine.util.StrUtils;
import org.joml.Vector4f;

public class ShaderLine {
  private Type type;
  
  private String name;
  
  private String value;
  
  private String line;
  
  public ShaderLine(Type type, String name, String value, String line) {
    this.type = type;
    this.name = name;
    this.value = value;
    this.line = line;
  }
  
  public Type getType() {
    return this.type;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public boolean isUniform() {
    return (this.type == Type.UNIFORM);
  }
  
  public boolean isUniform(String name) {
    return (isUniform() && name.equals(this.name));
  }
  
  public boolean isAttribute() {
    return (this.type == Type.ATTRIBUTE);
  }
  
  public boolean isAttribute(String name) {
    return (isAttribute() && name.equals(this.name));
  }
  
  public boolean isProperty() {
    return (this.type == Type.PROPERTY);
  }
  
  public boolean isConstInt() {
    return (this.type == Type.CONST_INT);
  }
  
  public boolean isConstFloat() {
    return (this.type == Type.CONST_FLOAT);
  }
  
  public boolean isConstBool() {
    return (this.type == Type.CONST_BOOL);
  }
  
  public boolean isExtension() {
    return (this.type == Type.EXTENSION);
  }
  
  public boolean isConstVec2() {
    return (this.type == Type.CONST_VEC2);
  }
  
  public boolean isConstVec4() {
    return (this.type == Type.CONST_VEC4);
  }
  
  public boolean isConstIVec3() {
    return (this.type == Type.CONST_IVEC3);
  }
  
  public boolean isLayout() {
    return (this.type == Type.LAYOUT);
  }
  
  public boolean isLayout(String name) {
    return (isLayout() && name.equals(this.name));
  }
  
  public boolean isProperty(String name) {
    return (isProperty() && name.equals(this.name));
  }
  
  public boolean isProperty(String name, String value) {
    return (isProperty(name) && value.equals(this.value));
  }
  
  public boolean isConstInt(String name) {
    return (isConstInt() && name.equals(this.name));
  }
  
  public boolean isConstIntSuffix(String suffix) {
    return (isConstInt() && this.name.endsWith(suffix));
  }
  
  public boolean isConstIVec3(String name) {
    return (isConstIVec3() && name.equals(this.name));
  }
  
  public boolean isConstFloat(String name) {
    return (isConstFloat() && name.equals(this.name));
  }
  
  public boolean isConstBool(String name) {
    return (isConstBool() && name.equals(this.name));
  }
  
  public boolean isExtension(String name) {
    return (isExtension() && name.equals(this.name));
  }
  
  public boolean isConstBoolSuffix(String suffix) {
    return (isConstBool() && this.name.endsWith(suffix));
  }
  
  public boolean isConstBoolSuffix(String suffix, boolean val) {
    return (isConstBoolSuffix(suffix) && getValueBool() == val);
  }
  
  public boolean isConstBool(String name1, String name2) {
    return (isConstBool(name1) || isConstBool(name2));
  }
  
  public boolean isConstBool(String name1, String name2, String name3) {
    return (isConstBool(name1) || isConstBool(name2) || isConstBool(name3));
  }
  
  public boolean isConstBool(String name, boolean val) {
    return (isConstBool(name) && getValueBool() == val);
  }
  
  public boolean isConstBool(String name1, String name2, boolean val) {
    return (isConstBool(name1, name2) && getValueBool() == val);
  }
  
  public boolean isConstBool(String name1, String name2, String name3, boolean val) {
    return (isConstBool(name1, name2, name3) && getValueBool() == val);
  }
  
  public boolean isConstVec2(String name) {
    return (isConstVec2() && name.equals(this.name));
  }
  
  public boolean isConstVec4Suffix(String suffix) {
    return (isConstVec4() && this.name.endsWith(suffix));
  }
  
  public int getValueInt() {
    try {
      return Integer.parseInt(this.value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
    } 
  }
  
  public float getValueFloat() {
    try {
      return Float.parseFloat(this.value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
    } 
  }
  
  public Vec3i getValueIVec3() {
    if (this.value == null)
      return null; 
    String str = this.value.trim();
    str = StrUtils.removePrefix(str, "ivec3");
    str = StrUtils.trim(str, " ()");
    String[] parts = Config.tokenize(str, ", ");
    if (parts.length != 3)
      return null; 
    int[] vals = new int[3];
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      int val = Config.parseInt(part, -2147483648);
      if (val == Integer.MIN_VALUE)
        return null; 
      vals[i] = val;
    } 
    return new Vec3i(vals[0], vals[1], vals[2]);
  }
  
  public Vec2 getValueVec2() {
    if (this.value == null)
      return null; 
    String str = this.value.trim();
    str = StrUtils.removePrefix(str, "vec2");
    str = StrUtils.trim(str, " ()");
    String[] parts = Config.tokenize(str, ", ");
    if (parts.length != 2)
      return null; 
    float[] fs = new float[2];
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      part = StrUtils.removeSuffix(part, new String[] { "F", "f" });
      float f = Config.parseFloat(part, Float.MAX_VALUE);
      if (f == Float.MAX_VALUE)
        return null; 
      fs[i] = f;
    } 
    return new Vec2(fs[0], fs[1]);
  }
  
  public Vector4f getValueVec4() {
    if (this.value == null)
      return null; 
    String str = this.value.trim();
    str = StrUtils.removePrefix(str, "vec4");
    str = StrUtils.trim(str, " ()");
    String[] parts = Config.tokenize(str, ", ");
    if (parts.length != 4)
      return null; 
    float[] fs = new float[4];
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      part = StrUtils.removeSuffix(part, new String[] { "F", "f" });
      float f = Config.parseFloat(part, Float.MAX_VALUE);
      if (f == Float.MAX_VALUE)
        return null; 
      fs[i] = f;
    } 
    return new Vector4f(fs[0], fs[1], fs[2], fs[3]);
  }
  
  public boolean getValueBool() {
    String valLow = this.value.toLowerCase();
    if (!valLow.equals("true") && !valLow.equals("false"))
      throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line); 
    return Boolean.valueOf(this.value).booleanValue();
  }
  
  public String toString() {
    return this.line;
  }
}
