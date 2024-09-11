package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwo;
import fyj;
import fyk;
import gkh;
import glz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPlayer;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterPiglin extends ModelAdapterPlayer {
  public ModelAdapterPiglin() {
    super(bsx.aA, "piglin", 0.5F);
  }
  
  protected ModelAdapterPiglin(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fwo(bakeModelLayer(fyj.aK));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (model instanceof fwo) {
      fwo piglinModel = (fwo)model;
      if (Reflector.ModelPiglin_ModelRenderers.exists()) {
        if (modelPart.equals("left_ear"))
          return piglinModel.k.b("left_ear"); 
        if (modelPart.equals("right_ear"))
          return piglinModel.k.b("right_ear"); 
      } 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> names = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    names.add("left_ear");
    names.add("right_ear");
    return names.<String>toArray(new String[names.size()]);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glz render = new glz(renderManager.getContext(), fyj.aK, fyj.aP, fyj.aQ, false);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
