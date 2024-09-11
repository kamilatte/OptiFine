package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwk;
import fyj;
import fyk;
import gkh;
import glw;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterParrot extends ModelAdapter {
  public ModelAdapterParrot() {
    super(bsx.ax, "parrot", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwk(bakeModelLayer(fyj.aH));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwk))
      return null; 
    fwk modelParrot = (fwk)model;
    if (modelPart.equals("body"))
      return modelParrot.a().b("body"); 
    if (modelPart.equals("tail"))
      return modelParrot.a().b("tail"); 
    if (modelPart.equals("left_wing"))
      return modelParrot.a().b("left_wing"); 
    if (modelPart.equals("right_wing"))
      return modelParrot.a().b("right_wing"); 
    if (modelPart.equals("head"))
      return modelParrot.a().b("head"); 
    if (modelPart.equals("left_leg"))
      return modelParrot.a().b("left_leg"); 
    if (modelPart.equals("right_leg"))
      return modelParrot.a().b("right_leg"); 
    if (modelPart.equals("root"))
      return modelParrot.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tail", "left_wing", "right_wing", "head", "left_leg", "right_leg", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glw render = new glw(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
