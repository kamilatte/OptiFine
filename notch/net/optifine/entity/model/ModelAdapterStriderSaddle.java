package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fxn;
import fyj;
import gkh;
import gki;
import gmf;
import gmt;
import gov;
import gow;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterStrider;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStriderSaddle extends ModelAdapterStrider {
  public ModelAdapterStriderSaddle() {
    super(bsx.aZ, "strider_saddle", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxn(bakeModelLayer(fyj.bF));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmt customRenderer = new gmt(renderManager.getContext());
    customRenderer.g = (fvk)new fxn(bakeModelLayer(fyj.bF));
    customRenderer.e = 0.5F;
    gki render = rendererCache.get(bsx.aZ, index, () -> customRenderer);
    if (!(render instanceof gmt)) {
      Config.warn("Not a StriderRenderer: " + String.valueOf(render));
      return null;
    } 
    gmt renderStrider = (gmt)render;
    gow layer = new gow((gmf)renderStrider, (fvk)modelBase, new akr("textures/entity/strider/strider_saddle.png"));
    renderStrider.removeLayers(gow.class);
    renderStrider.a((gov)layer);
    return (IEntityRenderer)renderStrider;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gmt renderer = (gmt)er;
    List<gow> layers = renderer.getLayers(gow.class);
    for (gow layer : layers)
      layer.a = textureLocation; 
    return true;
  }
}
