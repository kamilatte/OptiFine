package notch.net.optifine.entity.model;

import aub;
import bsx;
import fgo;
import fvk;
import fwg;
import fxv;
import fyj;
import fyk;
import gkh;
import gne;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterVillager extends ModelAdapter {
  public ModelAdapterVillager() {
    super(bsx.bj, "villager", 0.5F);
  }
  
  protected ModelAdapterVillager(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxv(bakeModelLayer(fyj.bQ));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxv))
      return null; 
    fxv modelVillager = (fxv)model;
    if (modelPart.equals("head"))
      return modelVillager.a().getChildModelDeep("head"); 
    if (modelPart.equals("headwear"))
      return modelVillager.a().getChildModelDeep("hat"); 
    if (modelPart.equals("headwear2"))
      return modelVillager.a().getChildModelDeep("hat_rim"); 
    if (modelPart.equals("body"))
      return modelVillager.a().getChildModelDeep("body"); 
    if (modelPart.equals("bodywear"))
      return modelVillager.a().getChildModelDeep("jacket"); 
    if (modelPart.equals("arms"))
      return modelVillager.a().getChildModelDeep("arms"); 
    if (modelPart.equals("right_leg"))
      return modelVillager.a().getChildModelDeep("right_leg"); 
    if (modelPart.equals("left_leg"))
      return modelVillager.a().getChildModelDeep("left_leg"); 
    if (modelPart.equals("nose"))
      return modelVillager.a().getChildModelDeep("nose"); 
    if (modelPart.equals("root"))
      return modelVillager.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "headwear", "headwear2", "body", "bodywear", "arms", "right_leg", "left_leg", "nose", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    aub resourceManager = (aub)fgo.Q().ab();
    gkh renderManager = fgo.Q().ap();
    gne render = new gne(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
