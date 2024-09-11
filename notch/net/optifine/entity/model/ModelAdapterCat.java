package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fuv;
import fvk;
import fwg;
import fyj;
import gjr;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCat extends ModelAdapterOcelot {
  public ModelAdapterCat() {
    super(bsx.p, "cat", 0.4F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuv(bakeModelLayer(fyj.u));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjr render = new gjr(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
