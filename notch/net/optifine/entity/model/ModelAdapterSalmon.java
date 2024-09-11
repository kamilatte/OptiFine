package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwy;
import fyj;
import fyk;
import gkh;
import gmg;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSalmon extends ModelAdapter {
  public ModelAdapterSalmon() {
    super(bsx.aI, "salmon", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwy(bakeModelLayer(fyj.bh));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwy))
      return null; 
    fwy modelSalmon = (fwy)model;
    if (modelPart.equals("body_front"))
      return modelSalmon.a().getChildModelDeep("body_front"); 
    if (modelPart.equals("body_back"))
      return modelSalmon.a().getChildModelDeep("body_back"); 
    if (modelPart.equals("head"))
      return modelSalmon.a().getChildModelDeep("head"); 
    if (modelPart.equals("fin_back_1"))
      return modelSalmon.a().getChildModelDeep("top_front_fin"); 
    if (modelPart.equals("fin_back_2"))
      return modelSalmon.a().getChildModelDeep("top_back_fin"); 
    if (modelPart.equals("tail"))
      return modelSalmon.a().getChildModelDeep("back_fin"); 
    if (modelPart.equals("fin_right"))
      return modelSalmon.a().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelSalmon.a().getChildModelDeep("left_fin"); 
    if (modelPart.equals("root"))
      return modelSalmon.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body_front", "body_back", "head", "fin_back_1", "fin_back_2", "tail", "fin_right", "fin_left", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmg render = new gmg(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
