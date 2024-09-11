package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fxc;
import fyj;
import fyk;
import gkh;
import gmi;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter {
  public ModelAdapterShulkerBullet() {
    super(bsx.aL, "shulker_bullet", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxc(bakeModelLayer(fyj.bm));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxc))
      return null; 
    fxc modelShulkerBullet = (fxc)model;
    if (modelPart.equals("bullet"))
      return modelShulkerBullet.a().getChildModelDeep("main"); 
    if (modelPart.equals("root"))
      return modelShulkerBullet.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bullet", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmi render = new gmi(renderManager.getContext());
    if (!Reflector.RenderShulkerBullet_model.exists()) {
      Config.warn("Field not found: RenderShulkerBullet.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderShulkerBullet_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
