package notch.net.optifine.entity.model;

import aub;
import bsx;
import fgo;
import fvk;
import fwg;
import fyd;
import fyj;
import gkh;
import gnq;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
  public ModelAdapterZombieVillager() {
    super(bsx.bw, "zombie_villager", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fyd(bakeModelLayer(fyj.cl));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    aub resourceManager = (aub)fgo.Q().ab();
    gkh renderManager = fgo.Q().ap();
    gnq render = new gnq(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
