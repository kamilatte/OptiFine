package srg.net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;

public interface IExpressionFloat extends IExpression {
  float eval();
  
  default ExpressionType getExpressionType() {
    return ExpressionType.FLOAT;
  }
}
