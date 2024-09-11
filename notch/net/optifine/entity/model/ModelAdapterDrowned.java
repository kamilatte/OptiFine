package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvg;
import fvk;
import fwg;
import fyj;
import gkb;
import gkh;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDrowned extends ModelAdapterZombie {
  public ModelAdapterDrowned() {
    super(bsx.B, "drowned", 0.5F);
  }
  
  public ModelAdapterDrowned(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fvg(bakeModelLayer(fyj.S));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkb render = new gkb(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
