package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxk;
import fyj;
import fyk;
import gkh;
import gmo;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSnowman extends ModelAdapter {
  public ModelAdapterSnowman() {
    super(bsx.aS, "snow_golem", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxk(bakeModelLayer(fyj.bw));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxk))
      return null; 
    fxk modelSnowman = (fxk)model;
    if (modelPart.equals("body"))
      return modelSnowman.a().getChildModelDeep("upper_body"); 
    if (modelPart.equals("body_bottom"))
      return modelSnowman.a().getChildModelDeep("lower_body"); 
    if (modelPart.equals("head"))
      return modelSnowman.a().getChildModelDeep("head"); 
    if (modelPart.equals("right_hand"))
      return modelSnowman.a().getChildModelDeep("right_arm"); 
    if (modelPart.equals("left_hand"))
      return modelSnowman.a().getChildModelDeep("left_arm"); 
    if (modelPart.equals("root"))
      return modelSnowman.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "body_bottom", "head", "right_hand", "left_hand", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmo render = new gmo(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
