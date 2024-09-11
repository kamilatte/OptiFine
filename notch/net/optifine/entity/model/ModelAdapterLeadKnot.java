package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwb;
import fwg;
import fyj;
import fyk;
import gkh;
import gli;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot extends ModelAdapter {
  public ModelAdapterLeadKnot() {
    super(bsx.al, "lead_knot", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwb(bakeModelLayer(fyj.ax));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwb))
      return null; 
    fwb modelLeashKnot = (fwb)model;
    if (modelPart.equals("knot"))
      return modelLeashKnot.a().getChildModelDeep("knot"); 
    if (modelPart.equals("root"))
      return modelLeashKnot.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "knot", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gli render = new gli(renderManager.getContext());
    if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
      Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
