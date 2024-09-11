package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvp;
import fwg;
import fyj;
import gkh;
import gku;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGiant extends ModelAdapterZombie {
  public ModelAdapterGiant() {
    super(bsx.U, "giant", 3.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvp(bakeModelLayer(fyj.ai));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gku render = new gku(renderManager.getContext(), 6.0F);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
