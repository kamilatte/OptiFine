package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvf;
import fvk;
import fwg;
import fyj;
import fyk;
import gjz;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDolphin extends ModelAdapter {
  public ModelAdapterDolphin() {
    super(bsx.y, "dolphin", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvf(bakeModelLayer(fyj.N));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvf))
      return null; 
    fvf modelDolphin = (fvf)model;
    if (modelPart.equals("root"))
      return modelDolphin.a(); 
    fyk modelBody = modelDolphin.a().b("body");
    if (modelBody == null)
      return null; 
    if (modelPart.equals("body"))
      return modelBody; 
    if (modelPart.equals("back_fin"))
      return modelBody.b("back_fin"); 
    if (modelPart.equals("left_fin"))
      return modelBody.b("left_fin"); 
    if (modelPart.equals("right_fin"))
      return modelBody.b("right_fin"); 
    if (modelPart.equals("tail"))
      return modelBody.b("tail"); 
    if (modelPart.equals("tail_fin"))
      return modelBody.b("tail").b("tail_fin"); 
    if (modelPart.equals("head"))
      return modelBody.b("head"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "back_fin", "left_fin", "right_fin", "tail", "tail_fin", "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjz render = new gjz(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
