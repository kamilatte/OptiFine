package notch.net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;

public interface IExpressionFloatArray extends IExpression {
  float[] eval();
  
  default ExpressionType getExpressionType() {
    return ExpressionType.FLOAT_ARRAY;
  }
}
