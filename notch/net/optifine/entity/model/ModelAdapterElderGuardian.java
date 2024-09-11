package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import gkc;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterGuardian;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterElderGuardian extends ModelAdapterGuardian {
  public ModelAdapterElderGuardian() {
    super(bsx.D, "elder_guardian", 0.5F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkc render = new gkc(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
