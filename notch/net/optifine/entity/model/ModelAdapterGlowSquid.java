package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxm;
import fyj;
import gkh;
import gkv;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGlowSquid extends ModelAdapterSquid {
  public ModelAdapterGlowSquid() {
    super(bsx.W, "glow_squid", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxm(bakeModelLayer(fyj.al));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkv render = new gkv(renderManager.getContext(), (fxm)modelBase);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
