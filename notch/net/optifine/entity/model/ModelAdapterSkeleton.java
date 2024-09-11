package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxf;
import fyj;
import gkh;
import gml;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSkeleton extends ModelAdapterBiped {
  public ModelAdapterSkeleton() {
    super(bsx.aN, "skeleton", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxf(bakeModelLayer(fyj.bo));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gml render = new gml(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
