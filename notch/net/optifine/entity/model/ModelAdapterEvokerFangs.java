package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvl;
import fwg;
import fyj;
import fyk;
import gkh;
import gkl;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs extends ModelAdapter {
  public ModelAdapterEvokerFangs() {
    super(bsx.K, "evoker_fangs", 0.0F, new String[] { "evocation_fangs" });
  }
  
  public fwg makeModel() {
    return (fwg)new fvl(bakeModelLayer(fyj.ad));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvl))
      return null; 
    fvl modelEvokerFangs = (fvl)model;
    if (modelPart.equals("base"))
      return modelEvokerFangs.a().getChildModelDeep("base"); 
    if (modelPart.equals("upper_jaw"))
      return modelEvokerFangs.a().getChildModelDeep("upper_jaw"); 
    if (modelPart.equals("lower_jaw"))
      return modelEvokerFangs.a().getChildModelDeep("lower_jaw"); 
    if (modelPart.equals("root"))
      return modelEvokerFangs.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "upper_jaw", "lower_jaw", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkl render = new gkl(renderManager.getContext());
    if (!Reflector.RenderEvokerFangs_model.exists()) {
      Config.warn("Field not found: RenderEvokerFangs.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderEvokerFangs_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
