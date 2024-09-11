package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fve;
import fvk;
import fwg;
import fyj;
import gjx;
import gkh;
import gki;
import gmf;
import gny;
import gov;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCreeperCharge extends ModelAdapterCreeper {
  public ModelAdapterCreeperCharge() {
    super(bsx.x, "creeper_charge", 0.25F);
  }
  
  public fwg makeModel() {
    return (fwg)new fve(bakeModelLayer(fyj.J));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjx customRenderer = new gjx(renderManager.getContext());
    customRenderer.g = (fvk)new fve(bakeModelLayer(fyj.J));
    customRenderer.e = 0.25F;
    gki render = rendererCache.get(bsx.x, index, () -> customRenderer);
    if (!(render instanceof gjx)) {
      Config.warn("Not a CreeperRenderer: " + String.valueOf(render));
      return null;
    } 
    gjx renderCreeper = (gjx)render;
    gny layer = new gny((gmf)renderCreeper, renderManager.getContext().f());
    layer.b = (fve)modelBase;
    renderCreeper.removeLayers(gny.class);
    renderCreeper.a((gov)layer);
    return (IEntityRenderer)renderCreeper;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gjx renderer = (gjx)er;
    List<gny> layers = renderer.getLayers(gny.class);
    for (gny layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
