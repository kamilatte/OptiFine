package notch.net.optifine.entity.model.anim;

import net.optifine.expr.IExpression;

public interface IRenderResolver {
  IExpression getParameter(String paramString);
  
  boolean isTileEntity();
}