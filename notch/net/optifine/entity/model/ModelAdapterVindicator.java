package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvy;
import fwg;
import fyj;
import gkh;
import gnf;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterVindicator extends ModelAdapterIllager {
  public ModelAdapterVindicator() {
    super(bsx.bk, "vindicator", 0.5F, new String[] { "vindication_illager" });
  }
  
  public fwg makeModel() {
    return (fwg)new fvy(bakeModelLayer(fyj.bR));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnf render = new gnf(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
