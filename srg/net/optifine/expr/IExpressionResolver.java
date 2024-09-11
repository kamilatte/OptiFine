package srg.net.optifine.expr;

import net.optifine.expr.IExpression;

public interface IExpressionResolver {
  IExpression getExpression(String paramString);
}
