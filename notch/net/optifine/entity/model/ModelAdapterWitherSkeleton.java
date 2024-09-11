package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxf;
import fyj;
import gkh;
import gnl;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWitherSkeleton extends ModelAdapterBiped {
  public ModelAdapterWitherSkeleton() {
    super(bsx.bq, "wither_skeleton", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxf(bakeModelLayer(fyj.bY));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnl render = new gnl(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
