package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwo;
import fyj;
import gkh;
import glz;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPiglin;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPiglinBrute extends ModelAdapterPiglin {
  public ModelAdapterPiglinBrute() {
    super(bsx.aB, "piglin_brute", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwo(bakeModelLayer(fyj.aL));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glz render = new glz(renderManager.getContext(), fyj.aL, fyj.aM, fyj.aN, false);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
