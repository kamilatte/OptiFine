package srg.net.optifine.entity.model.anim;

import net.minecraft.client.model.geom.ModelPart;
import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.expr.IExpressionResolver;

public interface IModelResolver extends IExpressionResolver {
  ModelPart getModelRenderer(String paramString);
  
  IModelVariable getModelVariable(String paramString);
}
