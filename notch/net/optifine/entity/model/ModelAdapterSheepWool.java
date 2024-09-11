package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fwz;
import fxa;
import fyj;
import gkh;
import gki;
import gmf;
import gmh;
import gov;
import gox;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
  public ModelAdapterSheepWool() {
    super(bsx.aJ, "sheep_wool", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwz(bakeModelLayer(fyj.bj));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmh customRenderer = new gmh(renderManager.getContext());
    customRenderer.g = (fvk)new fxa(bakeModelLayer(fyj.bj));
    customRenderer.e = 0.7F;
    gki render = rendererCache.get(bsx.aJ, index, () -> customRenderer);
    if (!(render instanceof gmh)) {
      Config.warn("Not a RenderSheep: " + String.valueOf(render));
      return null;
    } 
    gmh renderSheep = (gmh)render;
    gox layer = new gox((gmf)renderSheep, renderManager.getContext().f());
    layer.b = (fwz)modelBase;
    renderSheep.removeLayers(gox.class);
    renderSheep.a((gov)layer);
    return (IEntityRenderer)renderSheep;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gmh renderSheep = (gmh)er;
    List<gox> layers = renderSheep.getLayers(gox.class);
    for (gox layer : layers)
      layer.b.locationTextureCustom = textureLocation; 
    return true;
  }
}
