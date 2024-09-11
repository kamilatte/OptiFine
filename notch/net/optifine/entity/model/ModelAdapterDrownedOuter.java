package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvg;
import fvk;
import fwg;
import fyj;
import gkb;
import gkh;
import gki;
import gmf;
import god;
import gov;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterDrowned;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
  public ModelAdapterDrownedOuter() {
    super(bsx.B, "drowned_outer", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvg(bakeModelLayer(fyj.V));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkb customRenderer = new gkb(renderManager.getContext());
    customRenderer.g = (fvk)new fvg(bakeModelLayer(fyj.V));
    customRenderer.e = 0.75F;
    gki render = rendererCache.get(bsx.B, index, () -> customRenderer);
    if (!(render instanceof gkb)) {
      Config.warn("Not a DrownedRenderer: " + String.valueOf(render));
      return null;
    } 
    gkb renderDrowned = (gkb)render;
    god layer = new god((gmf)renderDrowned, renderManager.getContext().f());
    layer.b = (fvg)modelBase;
    renderDrowned.removeLayers(god.class);
    renderDrowned.a((gov)layer);
    return (IEntityRenderer)renderDrowned;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gkb renderer = (gkb)er;
    List<god> layers = renderer.getLayers(god.class);
    for (god layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
