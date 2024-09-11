package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxl;
import fyj;
import fyk;
import gkh;
import gmq;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSpider extends ModelAdapter {
  public ModelAdapterSpider() {
    super(bsx.aW, "spider", 1.0F);
  }
  
  protected ModelAdapterSpider(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxl(bakeModelLayer(fyj.by));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxl))
      return null; 
    fxl modelSpider = (fxl)model;
    if (modelPart.equals("head"))
      return modelSpider.a().getChildModelDeep("head"); 
    if (modelPart.equals("neck"))
      return modelSpider.a().getChildModelDeep("body0"); 
    if (modelPart.equals("body"))
      return modelSpider.a().getChildModelDeep("body1"); 
    if (modelPart.equals("leg1"))
      return modelSpider.a().getChildModelDeep("right_hind_leg"); 
    if (modelPart.equals("leg2"))
      return modelSpider.a().getChildModelDeep("left_hind_leg"); 
    if (modelPart.equals("leg3"))
      return modelSpider.a().getChildModelDeep("right_middle_hind_leg"); 
    if (modelPart.equals("leg4"))
      return modelSpider.a().getChildModelDeep("left_middle_hind_leg"); 
    if (modelPart.equals("leg5"))
      return modelSpider.a().getChildModelDeep("right_middle_front_leg"); 
    if (modelPart.equals("leg6"))
      return modelSpider.a().getChildModelDeep("left_middle_front_leg"); 
    if (modelPart.equals("leg7"))
      return modelSpider.a().getChildModelDeep("right_front_leg"); 
    if (modelPart.equals("leg8"))
      return modelSpider.a().getChildModelDeep("left_front_leg"); 
    if (modelPart.equals("root"))
      return modelSpider.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", 
        "leg8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmq render = new gmq(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
