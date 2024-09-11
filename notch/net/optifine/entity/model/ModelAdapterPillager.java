package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvy;
import fwg;
import fyj;
import gkh;
import gma;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPillager extends ModelAdapterIllager {
  public ModelAdapterPillager() {
    super(bsx.aC, "pillager", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvy(bakeModelLayer(fyj.aS));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gma render = new gma(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
