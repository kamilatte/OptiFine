package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwe;
import fwg;
import fyj;
import fyk;
import gkh;
import glm;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaSpit extends ModelAdapter {
  public ModelAdapterLlamaSpit() {
    super(bsx.ao, "llama_spit", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwe(bakeModelLayer(fyj.aA));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwe))
      return null; 
    fwe modelLlamaSpit = (fwe)model;
    if (modelPart.equals("body"))
      return modelLlamaSpit.a().getChildModelDeep("main"); 
    if (modelPart.equals("root"))
      return modelLlamaSpit.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glm render = new glm(renderManager.getContext());
    if (!Reflector.RenderLlamaSpit_model.exists()) {
      Config.warn("Field not found: RenderLlamaSpit.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderLlamaSpit_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
