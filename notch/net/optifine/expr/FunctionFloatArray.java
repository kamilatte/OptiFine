package notch.net.optifine.expr;

import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionFloatArray;

public class FunctionFloatArray implements IExpressionFloatArray {
  private FunctionType type;
  
  private IExpression[] arguments;
  
  public FunctionFloatArray(FunctionType type, IExpression[] arguments) {
    this.type = type;
    this.arguments = arguments;
  }
  
  public float[] eval() {
    return this.type.evalFloatArray(this.arguments);
  }
  
  public String toString() {
    return String.valueOf(this.type) + "()";
  }
}
