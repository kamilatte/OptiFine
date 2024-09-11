package notch.net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;

public interface IParameters {
  ExpressionType[] getParameterTypes(IExpression[] paramArrayOfIExpression);
}
