package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvi;
import fvk;
import fwg;
import fyj;
import gkf;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterEnderman extends ModelAdapterBiped {
  public ModelAdapterEnderman() {
    super(bsx.H, "enderman", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvi(bakeModelLayer(fyj.Y));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkf render = new gkf(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
