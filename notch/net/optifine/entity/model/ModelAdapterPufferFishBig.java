package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fwr;
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

public class ModelAdapterPufferFishBig extends ModelAdapter {
  public ModelAdapterPufferFishBig() {
    super(bsx.aF, "puffer_fish_big", 0.2F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwr(bakeModelLayer(fyj.bc));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwr))
      return null; 
    fwr modelPufferFishBig = (fwr)model;
    if (modelPart.equals("body"))
      return modelPufferFishBig.a().getChildModelDeep("body"); 
    if (modelPart.equals("fin_right"))
      return modelPufferFishBig.a().getChildModelDeep("right_blue_fin"); 
    if (modelPart.equals("fin_left"))
      return modelPufferFishBig.a().getChildModelDeep("left_blue_fin"); 
    if (modelPart.equals("spikes_front_top"))
      return modelPufferFishBig.a().getChildModelDeep("top_front_fin"); 
    if (modelPart.equals("spikes_middle_top"))
      return modelPufferFishBig.a().getChildModelDeep("top_middle_fin"); 
    if (modelPart.equals("spikes_back_top"))
      return modelPufferFishBig.a().getChildModelDeep("top_back_fin"); 
    if (modelPart.equals("spikes_front_right"))
      return modelPufferFishBig.a().getChildModelDeep("right_front_fin"); 
    if (modelPart.equals("spikes_front_left"))
      return modelPufferFishBig.a().getChildModelDeep("left_front_fin"); 
    if (modelPart.equals("spikes_front_bottom"))
      return modelPufferFishBig.a().getChildModelDeep("bottom_front_fin"); 
    if (modelPart.equals("spikes_middle_bottom"))
      return modelPufferFishBig.a().getChildModelDeep("bottom_middle_fin"); 
    if (modelPart.equals("spikes_back_bottom"))
      return modelPufferFishBig.a().getChildModelDeep("bottom_back_fin"); 
    if (modelPart.equals("spikes_back_right"))
      return modelPufferFishBig.a().getChildModelDeep("right_back_fin"); 
    if (modelPart.equals("spikes_back_left"))
      return modelPufferFishBig.a().getChildModelDeep("left_back_fin"); 
    if (modelPart.equals("root"))
      return modelPufferFishBig.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "fin_right", "fin_left", "spikes_front_top", "spikes_middle_top", "spikes_back_top", "spikes_front_right", "spikes_front_left", "spikes_front_bottom", "spikes_middle_bottom", 
        "spikes_back_bottom", "spikes_back_right", "spikes_back_left", "root" };
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
    if (!Reflector.RenderPufferfish_modelBig.exists()) {
      Config.warn("Model field not found: RenderPufferfish.modelBig");
      return null;
    } 
    Reflector.RenderPufferfish_modelBig.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
