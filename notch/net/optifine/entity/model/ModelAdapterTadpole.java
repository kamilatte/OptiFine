package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxo;
import fyj;
import fyk;
import gkh;
import gmu;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTadpole extends ModelAdapter {
  public ModelAdapterTadpole() {
    super(bsx.ba, "tadpole", 0.14F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxo(bakeModelLayer(fyj.bG));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxo))
      return null; 
    fxo modelTadpole = (fxo)model;
    if (modelPart.equals("body"))
      return (fyk)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 0); 
    if (modelPart.equals("tail"))
      return (fyk)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 1); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = { "body", "tail" };
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmu render = new gmu(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
