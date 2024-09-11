package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import gjs;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterSpider;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCaveSpider extends ModelAdapterSpider {
  public ModelAdapterCaveSpider() {
    super(bsx.q, "cave_spider", 0.7F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjs render = new gjs(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
