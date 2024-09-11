package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import gkh;
import gmp;
import net.optifine.entity.model.ArrowModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterArrow;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSpectralArrow extends ModelAdapterArrow {
  public ModelAdapterSpectralArrow() {
    super(bsx.aV, "spectral_arrow", 0.0F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmp render = new gmp(renderManager.getContext());
    render.model = (ArrowModel)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
