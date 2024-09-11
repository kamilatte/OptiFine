package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvy;
import fwg;
import fyj;
import gkh;
import gld;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterIllusioner extends ModelAdapterIllager {
  public ModelAdapterIllusioner() {
    super(bsx.ad, "illusioner", 0.5F, new String[] { "illusion_illager" });
  }
  
  public fwg makeModel() {
    fvy model = new fvy(bakeModelLayer(fyj.av));
    (model.c()).k = true;
    return (fwg)model;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gld render = new gld(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
