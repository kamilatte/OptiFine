package srg.net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;

public interface IModelVariableBool extends IExpressionBool, IModelVariable {
  boolean getValue();
  
  void setValue(boolean paramBoolean);
  
  default void setValue(IExpression expr) {
    boolean val = ((IExpressionBool)expr).eval();
    setValue(val);
  }
}
