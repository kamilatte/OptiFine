package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fwt;
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

public class ModelAdapterPufferFishSmall extends ModelAdapter {
  public ModelAdapterPufferFishSmall() {
    super(bsx.aF, "puffer_fish_small", 0.2F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwt(bakeModelLayer(fyj.be));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwt))
      return null; 
    fwt modelPufferFishSmall = (fwt)model;
    if (modelPart.equals("body"))
      return modelPufferFishSmall.a().getChildModelDeep("body"); 
    if (modelPart.equals("eye_right"))
      return modelPufferFishSmall.a().getChildModelDeep("right_eye"); 
    if (modelPart.equals("eye_left"))
      return modelPufferFishSmall.a().getChildModelDeep("left_eye"); 
    if (modelPart.equals("fin_right"))
      return modelPufferFishSmall.a().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelPufferFishSmall.a().getChildModelDeep("left_fin"); 
    if (modelPart.equals("tail"))
      return modelPufferFishSmall.a().getChildModelDeep("back_fin"); 
    if (modelPart.equals("root"))
      return modelPufferFishSmall.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "eye_right", "eye_left", "tail", "fin_right", "fin_left", "root" };
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
    if (!Reflector.RenderPufferfish_modelSmall.exists()) {
      Config.warn("Model field not found: RenderPufferfish.modelSmall");
      return null;
    } 
    Reflector.RenderPufferfish_modelSmall.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
