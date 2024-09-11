package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fut;
import fvk;
import fwg;
import fyj;
import gjp;
import gkh;
import gki;
import gmf;
import gnu;
import gov;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBreeze;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBreezeWind extends ModelAdapterBreeze {
  public ModelAdapterBreezeWind() {
    super(bsx.m, "breeze_wind", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fut(fut.a(128, 128).a());
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjp customRenderer = new gjp(renderManager.getContext());
    customRenderer.g = (fvk)new fut(bakeModelLayer(fyj.s));
    customRenderer.e = 0.0F;
    gki render = rendererCache.get(bsx.m, index, () -> customRenderer);
    if (!(render instanceof gjp)) {
      Config.warn("Not a RenderBreeze: " + String.valueOf(render));
      return null;
    } 
    gjp renderBreeze = (gjp)render;
    akr locTex = (modelBase.locationTextureCustom != null) ? modelBase.locationTextureCustom : new akr("textures/entity/breeze/breeze_wind.png");
    gnu layer = new gnu(renderManager.getContext(), (gmf)renderBreeze);
    layer.setModel((fut)modelBase);
    layer.setTextureLocation(locTex);
    renderBreeze.removeLayers(gnu.class);
    renderBreeze.a((gov)layer);
    return (IEntityRenderer)renderBreeze;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    return true;
  }
}
