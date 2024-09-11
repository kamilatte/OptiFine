package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fyj;
import gkh;
import glz;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPiglin;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZombifiedPiglin extends ModelAdapterPiglin {
  public ModelAdapterZombifiedPiglin() {
    super(bsx.bx, "zombified_piglin", 0.5F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glz render = new glz(renderManager.getContext(), fyj.co, fyj.cp, fyj.cq, true);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
