package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwf;
import fwg;
import fyj;
import fyk;
import gkh;
import glo;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart extends ModelAdapter {
  public ModelAdapterMinecart() {
    super(bsx.ar, "minecart", 0.5F);
  }
  
  protected ModelAdapterMinecart(bsx type, String name, float shadow) {
    super(type, name, shadow);
  }
  
  public fwg makeModel() {
    return (fwg)new fwf(bakeModelLayer(fyj.aC));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwf))
      return null; 
    fwf modelMinecart = (fwf)model;
    if (modelPart.equals("bottom"))
      return modelMinecart.a().getChildModelDeep("bottom"); 
    if (modelPart.equals("back"))
      return modelMinecart.a().getChildModelDeep("back"); 
    if (modelPart.equals("front"))
      return modelMinecart.a().getChildModelDeep("front"); 
    if (modelPart.equals("right"))
      return modelMinecart.a().getChildModelDeep("right"); 
    if (modelPart.equals("left"))
      return modelMinecart.a().getChildModelDeep("left"); 
    if (modelPart.equals("root"))
      return modelMinecart.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "back", "front", "right", "left", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glo render = new glo(renderManager.getContext(), fyj.aC);
    if (!Reflector.RenderMinecart_modelMinecart.exists()) {
      Config.warn("Field not found: RenderMinecart.modelMinecart");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
