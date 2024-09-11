package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fyc;
import fyj;
import gkh;
import glb;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterHusk extends ModelAdapterBiped {
  public ModelAdapterHusk() {
    super(bsx.ac, "husk", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fyc(bakeModelLayer(fyj.as));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glb render = new glb(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
