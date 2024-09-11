package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwd;
import fwg;
import fyj;
import fyk;
import gkh;
import gll;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlama extends ModelAdapter {
  public ModelAdapterLlama() {
    super(bsx.an, "llama", 0.7F);
  }
  
  public ModelAdapterLlama(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fwd(bakeModelLayer(fyj.ay));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwd))
      return null; 
    fwd modelLlama = (fwd)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 0); 
    if (modelPart.equals("body"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 1); 
    if (modelPart.equals("leg1"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 2); 
    if (modelPart.equals("leg2"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 3); 
    if (modelPart.equals("leg3"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 4); 
    if (modelPart.equals("leg4"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 5); 
    if (modelPart.equals("chest_right"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 6); 
    if (modelPart.equals("chest_left"))
      return (fyk)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 7); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "chest_right", "chest_left" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gll render = new gll(renderManager.getContext(), fyj.ay);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
