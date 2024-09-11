package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fun;
import fvk;
import fwg;
import fyj;
import fyk;
import gjk;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBat extends ModelAdapter {
  public ModelAdapterBat() {
    super(bsx.g, "bat", 0.25F);
  }
  
  public fwg makeModel() {
    return (fwg)new fun(bakeModelLayer(fyj.h));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fun))
      return null; 
    fun modelBat = (fun)model;
    if (modelPart.equals("head"))
      return modelBat.a().getChildModelDeep("head"); 
    if (modelPart.equals("body"))
      return modelBat.a().getChildModelDeep("body"); 
    if (modelPart.equals("right_wing"))
      return modelBat.a().getChildModelDeep("right_wing"); 
    if (modelPart.equals("left_wing"))
      return modelBat.a().getChildModelDeep("left_wing"); 
    if (modelPart.equals("outer_right_wing"))
      return modelBat.a().getChildModelDeep("right_wing_tip"); 
    if (modelPart.equals("outer_left_wing"))
      return modelBat.a().getChildModelDeep("left_wing_tip"); 
    if (modelPart.equals("feet"))
      return modelBat.a().getChildModelDeep("feet"); 
    if (modelPart.equals("root"))
      return modelBat.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing", "feet", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjk render = new gjk(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
