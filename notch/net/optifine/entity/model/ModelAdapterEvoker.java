package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvy;
import fwg;
import fyj;
import gkh;
import gkm;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterEvoker extends ModelAdapterIllager {
  public ModelAdapterEvoker() {
    super(bsx.J, "evoker", 0.5F, new String[] { "evocation_illager" });
  }
  
  public fwg makeModel() {
    return (fwg)new fvy(bakeModelLayer(fyj.ac));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkm render = new gkm(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
