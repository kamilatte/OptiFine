package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fyj;
import gkh;
import gnc;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZombieHorse extends ModelAdapterHorse {
  public ModelAdapterZombieHorse() {
    super(bsx.bv, "zombie_horse", 0.75F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnc render = new gnc(renderManager.getContext(), fyj.ci);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
