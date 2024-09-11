package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fya;
import fyj;
import gkh;
import gki;
import gmf;
import gnk;
import gov;
import gpj;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWitherArmor extends ModelAdapterWither {
  public ModelAdapterWitherArmor() {
    super(bsx.bp, "wither_armor", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fya(bakeModelLayer(fyj.bX));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnk customRenderer = new gnk(renderManager.getContext());
    customRenderer.g = (fvk)new fya(bakeModelLayer(fyj.bX));
    customRenderer.e = 0.5F;
    gki render = rendererCache.get(bsx.bp, index, () -> customRenderer);
    if (!(render instanceof gnk)) {
      Config.warn("Not a WitherRenderer: " + String.valueOf(render));
      return null;
    } 
    gnk renderWither = (gnk)render;
    gpj layer = new gpj((gmf)renderWither, renderManager.getContext().f());
    layer.b = (fya)modelBase;
    renderWither.removeLayers(gpj.class);
    renderWither.a((gov)layer);
    return (IEntityRenderer)renderWither;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gnk renderer = (gnk)er;
    List<gpj> layers = renderer.getLayers(gpj.class);
    for (gpj layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
