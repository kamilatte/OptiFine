package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fyb;
import fyj;
import gkh;
import gki;
import gmf;
import gnn;
import gov;
import gpl;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
  public ModelAdapterWolfCollar() {
    super(bsx.bs, "wolf_collar", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fyb(bakeModelLayer(fyj.cd));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnn customRenderer = new gnn(renderManager.getContext());
    customRenderer.g = (fvk)new fyb(bakeModelLayer(fyj.cd));
    customRenderer.e = 0.5F;
    gki render = rendererCache.get(bsx.bs, index, () -> customRenderer);
    if (!(render instanceof gnn)) {
      Config.warn("Not a RenderWolf: " + String.valueOf(render));
      return null;
    } 
    gnn renderWolf = (gnn)render;
    gpl layer = new gpl((gmf)renderWolf);
    layer.model = (fyb)modelBase;
    renderWolf.removeLayers(gpl.class);
    renderWolf.a((gov)layer);
    return (IEntityRenderer)renderWolf;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gnn renderWolf = (gnn)er;
    List<gpl> layers = renderWolf.getLayers(gpl.class);
    for (gpl layer : layers)
      layer.model.locationTextureCustom = textureLocation; 
    return true;
  }
}
