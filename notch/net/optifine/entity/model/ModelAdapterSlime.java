package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxi;
import fyj;
import fyk;
import gkh;
import gmm;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSlime extends ModelAdapter {
  public ModelAdapterSlime() {
    super(bsx.aP, "slime", 0.25F);
  }
  
  public ModelAdapterSlime(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxi(bakeModelLayer(fyj.bt));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxi))
      return null; 
    fxi modelSlime = (fxi)model;
    if (modelPart.equals("body"))
      return modelSlime.a().getChildModelDeep("cube"); 
    if (modelPart.equals("left_eye"))
      return modelSlime.a().getChildModelDeep("left_eye"); 
    if (modelPart.equals("right_eye"))
      return modelSlime.a().getChildModelDeep("right_eye"); 
    if (modelPart.equals("mouth"))
      return modelSlime.a().getChildModelDeep("mouth"); 
    if (modelPart.equals("root"))
      return modelSlime.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "left_eye", "right_eye", "mouth", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmm render = new gmm(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
