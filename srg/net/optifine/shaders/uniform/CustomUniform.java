package srg.net.optifine.shaders.uniform;

import net.optifine.expr.IExpression;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.uniform.ShaderUniformBase;
import net.optifine.shaders.uniform.UniformType;

public class CustomUniform {
  private String name;
  
  private UniformType type;
  
  private IExpression expression;
  
  private ShaderUniformBase shaderUniform;
  
  public CustomUniform(String name, UniformType type, IExpression expression) {
    this.name = name;
    this.type = type;
    this.expression = expression;
    this.shaderUniform = type.makeShaderUniform(name);
  }
  
  public void setProgram(int program) {
    this.shaderUniform.setProgram(program);
  }
  
  public void update() {
    if (!this.shaderUniform.isDefined())
      return; 
    try {
      this.type.updateUniform(this.expression, this.shaderUniform);
    } catch (RuntimeException e) {
      SMCLog.severe("Error updating custom uniform: " + this.shaderUniform.getName());
      SMCLog.severe(e.getClass().getName() + ": " + e.getClass().getName());
      this.shaderUniform.disable();
      SMCLog.severe("Custom uniform disabled: " + this.shaderUniform.getName());
    } 
  }
  
  public void reset() {
    this.shaderUniform.reset();
  }
  
  public String getName() {
    return this.name;
  }
  
  public UniformType getType() {
    return this.type;
  }
  
  public IExpression getExpression() {
    return this.expression;
  }
  
  public ShaderUniformBase getShaderUniform() {
    return this.shaderUniform;
  }
  
  public String toString() {
    return this.type.name().toLowerCase() + " " + this.type.name().toLowerCase();
  }
}
