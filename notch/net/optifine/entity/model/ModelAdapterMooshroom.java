package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvd;
import fvk;
import fwg;
import fyj;
import gkh;
import glq;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterMooshroom extends ModelAdapterQuadruped {
  public ModelAdapterMooshroom() {
    super(bsx.as, "mooshroom", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvd(bakeModelLayer(fyj.aD));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glq render = new glq(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
