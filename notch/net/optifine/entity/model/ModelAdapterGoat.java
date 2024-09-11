package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvq;
import fwg;
import fyj;
import fyk;
import gkh;
import gkw;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGoat extends ModelAdapterQuadruped {
  public ModelAdapterGoat() {
    super(bsx.X, "goat", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvq(bakeModelLayer(fyj.am));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvq))
      return null; 
    fvq modelGoat = (fvq)model;
    fyk head = super.getModelRenderer((fwg)modelGoat, "head");
    if (head != null) {
      if (modelPart.equals("left_horn"))
        return head.b(modelPart); 
      if (modelPart.equals("right_horn"))
        return head.b(modelPart); 
      if (modelPart.equals("nose"))
        return head.b(modelPart); 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectsToArray((Object[])names, (Object[])new String[] { "left_horn", "right_horn", "nose" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkw render = new gkw(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
