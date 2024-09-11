package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fxg;
import fyj;
import fyk;
import gkh;
import gnm;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull extends ModelAdapter {
  public ModelAdapterWitherSkull() {
    super(bsx.br, "wither_skull", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxg(bakeModelLayer(fyj.cc));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxg))
      return null; 
    fxg modelSkeletonHead = (fxg)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 1); 
    if (modelPart.equals("root"))
      return (fyk)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnm render = new gnm(renderManager.getContext());
    if (!Reflector.RenderWitherSkull_model.exists()) {
      Config.warn("Field not found: RenderWitherSkull_model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderWitherSkull_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
