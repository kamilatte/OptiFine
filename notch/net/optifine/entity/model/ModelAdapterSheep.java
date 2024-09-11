package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxa;
import fyj;
import gkh;
import gmh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSheep extends ModelAdapterQuadruped {
  public ModelAdapterSheep() {
    super(bsx.aJ, "sheep", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxa(bakeModelLayer(fyj.bi));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmh render = new gmh(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
