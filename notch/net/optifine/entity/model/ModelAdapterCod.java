package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fva;
import fvk;
import fwg;
import fyj;
import fyk;
import gjv;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCod extends ModelAdapter {
  public ModelAdapterCod() {
    super(bsx.u, "cod", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fva(bakeModelLayer(fyj.B));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fva))
      return null; 
    fva modelCod = (fva)model;
    if (modelPart.equals("body"))
      return modelCod.a().getChildModelDeep("body"); 
    if (modelPart.equals("fin_back"))
      return modelCod.a().getChildModelDeep("top_fin"); 
    if (modelPart.equals("head"))
      return modelCod.a().getChildModelDeep("head"); 
    if (modelPart.equals("nose"))
      return modelCod.a().getChildModelDeep("nose"); 
    if (modelPart.equals("fin_right"))
      return modelCod.a().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelCod.a().getChildModelDeep("left_fin"); 
    if (modelPart.equals("tail"))
      return modelCod.a().getChildModelDeep("tail_fin"); 
    if (modelPart.equals("root"))
      return modelCod.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "fin_back", "head", "nose", "fin_right", "fin_left", "tail", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjv render = new gjv(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
