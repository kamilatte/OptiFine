package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fxq;
import fyj;
import gkh;
import gki;
import gmf;
import gna;
import gov;
import gpf;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterTropicalFishA;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternA extends ModelAdapterTropicalFishA {
  public ModelAdapterTropicalFishPatternA() {
    super(bsx.bg, "tropical_fish_pattern_a", 0.2F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxq(bakeModelLayer(fyj.bN));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gna customRenderer = new gna(renderManager.getContext());
    customRenderer.g = (fvk)new fxq(bakeModelLayer(fyj.bN));
    customRenderer.e = 0.2F;
    gki render = rendererCache.get(bsx.bg, index, () -> customRenderer);
    if (!(render instanceof gna)) {
      Config.warn("Not a RenderTropicalFish: " + String.valueOf(render));
      return null;
    } 
    gna renderTropicalFish = (gna)render;
    gpf layer = (gpf)renderTropicalFish.getLayer(gpf.class);
    if (layer == null || !layer.custom) {
      layer = new gpf((gmf)renderTropicalFish, renderManager.getContext().f());
      layer.custom = true;
    } 
    if (!Reflector.TropicalFishPatternLayer_modelA.exists()) {
      Config.warn("Field not found: TropicalFishPatternLayer.modelA");
      return null;
    } 
    Reflector.TropicalFishPatternLayer_modelA.setValue(layer, modelBase);
    renderTropicalFish.removeLayers(gpf.class);
    renderTropicalFish.a((gov)layer);
    return (IEntityRenderer)renderTropicalFish;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gna renderTropicalFish = (gna)er;
    List<gpf> layers = renderTropicalFish.getLayers(gpf.class);
    for (gpf layer : layers) {
      fxq modelA = (fxq)Reflector.TropicalFishPatternLayer_modelA.getValue(layer);
      if (modelA != null)
        modelA.locationTextureCustom = textureLocation; 
    } 
    return true;
  }
}
