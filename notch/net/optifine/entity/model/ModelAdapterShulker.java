package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxd;
import fyj;
import fyk;
import gkh;
import gmj;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulker extends ModelAdapter {
  public ModelAdapterShulker() {
    super(bsx.aK, "shulker", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxd(bakeModelLayer(fyj.bl));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxd))
      return null; 
    fxd modelShulker = (fxd)model;
    if (modelPart.equals("base"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0); 
    if (modelPart.equals("lid"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1); 
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "lid", "head" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmj render = new gmj(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
