package srg.net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IRenderResolver;
import net.optifine.entity.model.anim.RenderEntityParameterBool;
import net.optifine.entity.model.anim.RenderEntityParameterFloat;
import net.optifine.expr.IExpression;

public class RenderResolverEntity implements IRenderResolver {
  public IExpression getParameter(String name) {
    RenderEntityParameterBool parBool = RenderEntityParameterBool.parse(name);
    if (parBool != null)
      return (IExpression)parBool; 
    RenderEntityParameterFloat parFloat = RenderEntityParameterFloat.parse(name);
    if (parFloat != null)
      return (IExpression)parFloat; 
    return null;
  }
  
  public boolean isTileEntity() {
    return false;
  }
}
