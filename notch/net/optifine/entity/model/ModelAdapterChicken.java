package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fuz;
import fvk;
import fwg;
import fyj;
import fyk;
import gju;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken extends ModelAdapter {
  public ModelAdapterChicken() {
    super(bsx.t, "chicken", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuz(bakeModelLayer(fyj.A));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuz))
      return null; 
    fuz modelChicken = (fuz)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 0); 
    if (modelPart.equals("body"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 1); 
    if (modelPart.equals("right_leg"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 2); 
    if (modelPart.equals("left_leg"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 3); 
    if (modelPart.equals("right_wing"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 4); 
    if (modelPart.equals("left_wing"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 5); 
    if (modelPart.equals("bill"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 6); 
    if (modelPart.equals("chin"))
      return (fyk)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 7); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gju render = new gju(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
