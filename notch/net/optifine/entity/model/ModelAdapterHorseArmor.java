package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fvv;
import fwg;
import fyj;
import gkh;
import gki;
import gkz;
import gmf;
import goj;
import gov;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
  public ModelAdapterHorseArmor() {
    super(bsx.ab, "horse_armor", 0.75F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvv(bakeModelLayer(fyj.ar));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkz customRenderer = new gkz(renderManager.getContext());
    customRenderer.g = (fvk)new fvv(bakeModelLayer(fyj.ar));
    customRenderer.e = 0.75F;
    gki render = rendererCache.get(bsx.ab, index, () -> customRenderer);
    if (!(render instanceof gkz)) {
      Config.warn("Not a HorseRenderer: " + String.valueOf(render));
      return null;
    } 
    gkz renderHorse = (gkz)render;
    goj layer = new goj((gmf)renderHorse, renderManager.getContext().f());
    layer.a = (fvv)modelBase;
    renderHorse.removeLayers(goj.class);
    renderHorse.a((gov)layer);
    return (IEntityRenderer)renderHorse;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gkz renderer = (gkz)er;
    List<goj> layers = renderer.getLayers(goj.class);
    for (goj layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
