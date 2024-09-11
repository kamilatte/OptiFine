package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fyc;
import fyj;
import gkh;
import gnp;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZombie extends ModelAdapterBiped {
  public ModelAdapterZombie() {
    super(bsx.bu, "zombie", 0.5F);
  }
  
  protected ModelAdapterZombie(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fyc(bakeModelLayer(fyj.cg));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnp render = new gnp(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
