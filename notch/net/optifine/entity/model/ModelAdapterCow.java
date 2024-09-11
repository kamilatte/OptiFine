package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvd;
import fvk;
import fwg;
import fyj;
import gjw;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCow extends ModelAdapterQuadruped {
  public ModelAdapterCow() {
    super(bsx.w, "cow", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvd(bakeModelLayer(fyj.H));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjw render = new gjw(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
