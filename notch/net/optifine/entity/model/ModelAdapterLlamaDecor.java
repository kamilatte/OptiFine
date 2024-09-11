package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwd;
import fwg;
import fyj;
import gkh;
import gki;
import gll;
import gmf;
import gop;
import gov;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterLlama;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaDecor extends ModelAdapterLlama {
  public ModelAdapterLlamaDecor() {
    super(bsx.an, "llama_decor", 0.7F);
  }
  
  protected ModelAdapterLlamaDecor(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fwd(bakeModelLayer(fyj.az));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gll customRenderer = new gll(renderManager.getContext(), fyj.az);
    customRenderer.g = (fvk)new fwd(bakeModelLayer(fyj.az));
    customRenderer.e = 0.7F;
    bsx entityType = getType().getLeft().get();
    gki render = rendererCache.get(entityType, index, () -> customRenderer);
    if (!(render instanceof gll)) {
      Config.warn("Not a RenderLlama: " + String.valueOf(render));
      return null;
    } 
    gll renderLlama = (gll)render;
    gop layer = new gop((gmf)renderLlama, renderManager.getContext().f());
    if (!Reflector.LayerLlamaDecor_model.exists()) {
      Config.warn("Field not found: LayerLlamaDecor.model");
      return null;
    } 
    Reflector.LayerLlamaDecor_model.setValue(layer, modelBase);
    renderLlama.removeLayers(gop.class);
    renderLlama.a((gov)layer);
    return (IEntityRenderer)renderLlama;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gll llamaRenderer = (gll)er;
    List<gop> layers = llamaRenderer.getLayers(gop.class);
    for (gov layer : layers) {
      fwg model = (fwg)Reflector.LayerLlamaDecor_model.getValue(layer);
      if (model != null)
        model.locationTextureCustom = textureLocation; 
    } 
    return true;
  }
}
