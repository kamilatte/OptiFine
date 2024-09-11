package srg.net.optifine.shaders.uniform;

import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.ShaderParameterFloat;

public class ShaderParameterIndexed implements IExpressionFloat {
  private ShaderParameterFloat type;
  
  private int index1;
  
  private int index2;
  
  public ShaderParameterIndexed(ShaderParameterFloat type) {
    this(type, 0, 0);
  }
  
  public ShaderParameterIndexed(ShaderParameterFloat type, int index1) {
    this(type, index1, 0);
  }
  
  public ShaderParameterIndexed(ShaderParameterFloat type, int index1, int index2) {
    this.type = type;
    this.index1 = index1;
    this.index2 = index2;
  }
  
  public float eval() {
    return this.type.eval(this.index1, this.index2);
  }
  
  public String toString() {
    if (this.type.getIndexNames1() == null)
      return String.valueOf(this.type); 
    if (this.type.getIndexNames2() == null)
      return String.valueOf(this.type) + "." + String.valueOf(this.type); 
    return String.valueOf(this.type) + "." + String.valueOf(this.type) + "." + this.type.getIndexNames1()[this.index1];
  }
}
