package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxs;
import fyj;
import fyk;
import gkh;
import gnb;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTurtle extends ModelAdapterQuadruped {
  public ModelAdapterTurtle() {
    super(bsx.bh, "turtle", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxs(bakeModelLayer(fyj.bO));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwu))
      return null; 
    fxs modelQuadruped = (fxs)model;
    if (modelPart.equals("body2"))
      return (fyk)Reflector.ModelTurtle_body2.getValue(modelQuadruped); 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectToArray((Object[])names, "body2");
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnb render = new gnb(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
