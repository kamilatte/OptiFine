package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import gkh;
import gng;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWanderingTrader extends ModelAdapterVillager {
  public ModelAdapterWanderingTrader() {
    super(bsx.bl, "wandering_trader", 0.5F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gng render = new gng(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
