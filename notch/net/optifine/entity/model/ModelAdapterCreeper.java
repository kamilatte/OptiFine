package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fve;
import fvk;
import fwg;
import fyj;
import fyk;
import gjx;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCreeper extends ModelAdapter {
  public ModelAdapterCreeper() {
    super(bsx.x, "creeper", 0.5F);
  }
  
  public ModelAdapterCreeper(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fve(bakeModelLayer(fyj.I));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fve))
      return null; 
    fve modelCreeper = (fve)model;
    if (modelPart.equals("head"))
      return modelCreeper.a().getChildModelDeep("head"); 
    if (modelPart.equals("body"))
      return modelCreeper.a().getChildModelDeep("body"); 
    if (modelPart.equals("leg1"))
      return modelCreeper.a().getChildModelDeep("right_hind_leg"); 
    if (modelPart.equals("leg2"))
      return modelCreeper.a().getChildModelDeep("left_hind_leg"); 
    if (modelPart.equals("leg3"))
      return modelCreeper.a().getChildModelDeep("right_front_leg"); 
    if (modelPart.equals("leg4"))
      return modelCreeper.a().getChildModelDeep("left_front_leg"); 
    if (modelPart.equals("root"))
      return modelCreeper.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjx render = new gjx(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
