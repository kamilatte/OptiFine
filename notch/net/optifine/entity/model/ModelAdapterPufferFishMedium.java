package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fws;
import fyj;
import fyk;
import gkh;
import gki;
import gmc;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishMedium extends ModelAdapter {
  public ModelAdapterPufferFishMedium() {
    super(bsx.aF, "puffer_fish_medium", 0.2F);
  }
  
  public fwg makeModel() {
    return (fwg)new fws(bakeModelLayer(fyj.bd));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fws))
      return null; 
    fws modelPufferFishMedium = (fws)model;
    if (modelPart.equals("body"))
      return modelPufferFishMedium.a().getChildModelDeep("body"); 
    if (modelPart.equals("fin_right"))
      return modelPufferFishMedium.a().getChildModelDeep("right_blue_fin"); 
    if (modelPart.equals("fin_left"))
      return modelPufferFishMedium.a().getChildModelDeep("left_blue_fin"); 
    if (modelPart.equals("spikes_front_top"))
      return modelPufferFishMedium.a().getChildModelDeep("top_front_fin"); 
    if (modelPart.equals("spikes_back_top"))
      return modelPufferFishMedium.a().getChildModelDeep("top_back_fin"); 
    if (modelPart.equals("spikes_front_right"))
      return modelPufferFishMedium.a().getChildModelDeep("right_front_fin"); 
    if (modelPart.equals("spikes_back_right"))
      return modelPufferFishMedium.a().getChildModelDeep("right_back_fin"); 
    if (modelPart.equals("spikes_back_left"))
      return modelPufferFishMedium.a().getChildModelDeep("left_back_fin"); 
    if (modelPart.equals("spikes_front_left"))
      return modelPufferFishMedium.a().getChildModelDeep("left_front_fin"); 
    if (modelPart.equals("spikes_back_bottom"))
      return modelPufferFishMedium.a().getChildModelDeep("bottom_back_fin"); 
    if (modelPart.equals("spikes_front_bottom"))
      return modelPufferFishMedium.a().getChildModelDeep("bottom_front_fin"); 
    if (modelPart.equals("root"))
      return modelPufferFishMedium.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "fin_right", "fin_left", "spikes_front_top", "spikes_back_top", "spikes_front_right", "spikes_back_right", "spikes_back_left", "spikes_front_left", "spikes_back_bottom", 
        "spikes_front_bottom", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmc customRenderer = new gmc(renderManager.getContext());
    customRenderer.e = shadowSize;
    gki render = rendererCache.get(bsx.aF, index, () -> customRenderer);
    if (!(render instanceof gmc)) {
      Config.warn("Not a PufferfishRenderer: " + String.valueOf(render));
      return null;
    } 
    gmc renderFish = (gmc)render;
    if (!Reflector.RenderPufferfish_modelMedium.exists()) {
      Config.warn("Model field not found: RenderPufferfish.modelMedium");
      return null;
    } 
    Reflector.RenderPufferfish_modelMedium.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
