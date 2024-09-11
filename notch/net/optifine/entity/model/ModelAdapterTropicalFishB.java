package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fxr;
import fyj;
import fyk;
import gkh;
import gki;
import gna;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishB extends ModelAdapter {
  public ModelAdapterTropicalFishB() {
    super(bsx.bg, "tropical_fish_b", 0.2F);
  }
  
  public ModelAdapterTropicalFishB(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxr(bakeModelLayer(fyj.bK));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxr))
      return null; 
    fxr modelTropicalFish = (fxr)model;
    if (modelPart.equals("body"))
      return modelTropicalFish.a().getChildModelDeep("body"); 
    if (modelPart.equals("tail"))
      return modelTropicalFish.a().getChildModelDeep("tail"); 
    if (modelPart.equals("fin_right"))
      return modelTropicalFish.a().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelTropicalFish.a().getChildModelDeep("left_fin"); 
    if (modelPart.equals("fin_top"))
      return modelTropicalFish.a().getChildModelDeep("top_fin"); 
    if (modelPart.equals("fin_bottom"))
      return modelTropicalFish.a().getChildModelDeep("bottom_fin"); 
    if (modelPart.equals("root"))
      return modelTropicalFish.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tail", "fin_right", "fin_left", "fin_top", "fin_bottom", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gna customRenderer = new gna(renderManager.getContext());
    customRenderer.e = shadowSize;
    gki render = rendererCache.get(bsx.bg, index, () -> customRenderer);
    if (!(render instanceof gna)) {
      Config.warn("Not a TropicalFishRenderer: " + String.valueOf(render));
      return null;
    } 
    gna renderFish = (gna)render;
    if (!Reflector.RenderTropicalFish_modelB.exists()) {
      Config.warn("Model field not found: RenderTropicalFish.modelB");
      return null;
    } 
    Reflector.RenderTropicalFish_modelB.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
