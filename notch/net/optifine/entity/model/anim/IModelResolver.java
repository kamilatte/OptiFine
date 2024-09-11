package notch.net.optifine.entity.model.anim;

import fyk;
import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.expr.IExpressionResolver;

public interface IModelResolver extends IExpressionResolver {
  fyk getModelRenderer(String paramString);
  
  IModelVariable getModelVariable(String paramString);
}
