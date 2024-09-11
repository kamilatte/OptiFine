package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fwm;
import fyj;
import gkh;
import gki;
import gly;
import gmf;
import gov;
import gow;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPigSaddle extends ModelAdapterQuadruped {
  public ModelAdapterPigSaddle() {
    super(bsx.az, "pig_saddle", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwm(bakeModelLayer(fyj.aR));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gly customRenderer = new gly(renderManager.getContext());
    customRenderer.g = (fvk)new fwm(bakeModelLayer(fyj.aR));
    customRenderer.e = 0.7F;
    gki render = rendererCache.get(bsx.az, index, () -> customRenderer);
    if (!(render instanceof gly)) {
      Config.warn("Not a PigRenderer: " + String.valueOf(render));
      return null;
    } 
    gly renderPig = (gly)render;
    gow layer = new gow((gmf)renderPig, (fvk)modelBase, new akr("textures/entity/pig/pig_saddle.png"));
    renderPig.removeLayers(gow.class);
    renderPig.a((gov)layer);
    return (IEntityRenderer)renderPig;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gly renderer = (gly)er;
    List<gow> layers = renderer.getLayers(gow.class);
    for (gow layer : layers)
      layer.a = textureLocation; 
    return true;
  }
}
