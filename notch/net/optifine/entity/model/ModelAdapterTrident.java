package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fxp;
import fyj;
import fyk;
import gkh;
import gmw;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTrident extends ModelAdapter {
  public ModelAdapterTrident() {
    super(bsx.bf, "trident", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxp(bakeModelLayer(fyj.bJ));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxp))
      return null; 
    fxp modelTrident = (fxp)model;
    fyk root = (fyk)Reflector.ModelTrident_root.getValue(modelTrident);
    if (root != null) {
      if (modelPart.equals("body"))
        return root.getChildModelDeep("pole"); 
      if (modelPart.equals("base"))
        return root.getChildModelDeep("base"); 
      if (modelPart.equals("left_spike"))
        return root.getChildModelDeep("left_spike"); 
      if (modelPart.equals("middle_spike"))
        return root.getChildModelDeep("middle_spike"); 
      if (modelPart.equals("right_spike"))
        return root.getChildModelDeep("right_spike"); 
    } 
    if (modelPart.equals("root"))
      return root; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "base", "left_spike", "middle_spike", "right_spike", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmw render = new gmw(renderManager.getContext());
    if (!Reflector.RenderTrident_modelTrident.exists()) {
      Config.warn("Field not found: RenderTrident.modelTrident");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderTrident_modelTrident, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
