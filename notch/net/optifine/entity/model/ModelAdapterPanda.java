package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwj;
import fyj;
import gkh;
import glv;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPanda extends ModelAdapterQuadruped {
  public ModelAdapterPanda() {
    super(bsx.aw, "panda", 0.9F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwj(bakeModelLayer(fyj.aG));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glv render = new glv(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
