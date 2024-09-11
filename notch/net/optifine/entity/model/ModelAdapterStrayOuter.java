package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fxf;
import fyj;
import gkh;
import gki;
import gmf;
import gms;
import gov;
import goz;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterStray;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStrayOuter extends ModelAdapterStray {
  public ModelAdapterStrayOuter() {
    super(bsx.aY, "stray_outer", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxf(bakeModelLayer(fyj.bD));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gms customRenderer = new gms(renderManager.getContext());
    customRenderer.g = (fvk)new fxf(bakeModelLayer(fyj.bD));
    customRenderer.e = 0.7F;
    gki render = rendererCache.get(bsx.aY, index, () -> customRenderer);
    if (!(render instanceof gms)) {
      Config.warn("Not a SkeletonModelRenderer: " + String.valueOf(render));
      return null;
    } 
    gms renderStray = (gms)render;
    akr STRAY_CLOTHES_LOCATION = new akr("textures/entity/skeleton/stray_overlay.png");
    goz layer = new goz((gmf)renderStray, renderManager.getContext().f(), fyj.bD, STRAY_CLOTHES_LOCATION);
    layer.a = (fxf)modelBase;
    renderStray.removeLayers(goz.class);
    renderStray.a((gov)layer);
    return (IEntityRenderer)renderStray;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gms renderer = (gms)er;
    List<goz> layers = renderer.getLayers(goz.class);
    for (goz layer : layers)
      layer.b = textureLocation; 
    return true;
  }
}
