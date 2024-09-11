package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxf;
import fyj;
import gkh;
import gms;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStray extends ModelAdapterBiped {
  public ModelAdapterStray() {
    super(bsx.aY, "stray", 0.7F);
  }
  
  public ModelAdapterStray(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxf(bakeModelLayer(fyj.bA));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gms render = new gms(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
