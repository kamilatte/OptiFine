package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fyb;
import fyj;
import fyk;
import gkh;
import gnn;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWolf extends ModelAdapter {
  public ModelAdapterWolf() {
    super(bsx.bs, "wolf", 0.5F);
  }
  
  protected ModelAdapterWolf(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fyb(bakeModelLayer(fyj.cd));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fyb))
      return null; 
    fyb modelWolf = (fyb)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 0); 
    if (modelPart.equals("body"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 2); 
    if (modelPart.equals("leg1"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 3); 
    if (modelPart.equals("leg2"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 4); 
    if (modelPart.equals("leg3"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 5); 
    if (modelPart.equals("leg4"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 6); 
    if (modelPart.equals("tail"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 7); 
    if (modelPart.equals("mane"))
      return (fyk)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 9); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnn render = new gnn(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
