package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import gkh;
import gno;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHoglin;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZoglin extends ModelAdapterHoglin {
  public ModelAdapterZoglin() {
    super(bsx.bt, "zoglin", 0.7F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gno render = new gno(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
