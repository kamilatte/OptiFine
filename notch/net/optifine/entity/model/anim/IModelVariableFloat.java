package notch.net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionFloat;

public interface IModelVariableFloat extends IExpressionFloat, IModelVariable {
  float getValue();
  
  void setValue(float paramFloat);
  
  default void setValue(IExpression expr) {
    float val = ((IExpressionFloat)expr).eval();
    setValue(val);
  }
}
