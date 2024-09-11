package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwq;
import fyj;
import gkh;
import gmb;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPolarBear extends ModelAdapterQuadruped {
  public ModelAdapterPolarBear() {
    super(bsx.aD, "polar_bear", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwq(bakeModelLayer(fyj.bb));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmb render = new gmb(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
