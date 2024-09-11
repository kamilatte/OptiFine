package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fuv;
import fvk;
import fwg;
import fyj;
import gjr;
import gkh;
import gki;
import gmf;
import gnx;
import gov;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
  public ModelAdapterCatCollar() {
    super(bsx.p, "cat_collar", 0.4F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuv(bakeModelLayer(fyj.v));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjr customRenderer = new gjr(renderManager.getContext());
    customRenderer.g = (fvk)new fuv(bakeModelLayer(fyj.v));
    customRenderer.e = 0.4F;
    gki render = rendererCache.get(bsx.p, index, () -> customRenderer);
    if (!(render instanceof gjr)) {
      Config.warn("Not a RenderCat: " + String.valueOf(render));
      return null;
    } 
    gjr renderCat = (gjr)render;
    gnx layer = new gnx((gmf)renderCat, renderManager.getContext().f());
    layer.b = (fuv)modelBase;
    renderCat.removeLayers(gnx.class);
    renderCat.a((gov)layer);
    return (IEntityRenderer)renderCat;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gjr renderCat = (gjr)er;
    List<gnx> layers = renderCat.getLayers(gnx.class);
    for (gnx layer : layers)
      layer.b.locationTextureCustom = textureLocation; 
    return true;
  }
}
