package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwm;
import fyj;
import gkh;
import gly;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPig extends ModelAdapterQuadruped {
  public ModelAdapterPig() {
    super(bsx.az, "pig", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwm(bakeModelLayer(fyj.aJ));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gly render = new gly(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
