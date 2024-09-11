package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fyj;
import fyk;
import gke;
import gkh;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterDragon extends ModelAdapter {
  public ModelAdapterDragon() {
    super(bsx.F, "dragon", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new gke.a(bakeModelLayer(fyj.aa));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof gke.a))
      return null; 
    gke.a modelDragon = (gke.a)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 0); 
    if (modelPart.equals("spine"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 1); 
    if (modelPart.equals("jaw"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 2); 
    if (modelPart.equals("body"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 3); 
    if (modelPart.equals("left_wing"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 4); 
    if (modelPart.equals("left_wing_tip"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 5); 
    if (modelPart.equals("front_left_leg"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 6); 
    if (modelPart.equals("front_left_shin"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 7); 
    if (modelPart.equals("front_left_foot"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 8); 
    if (modelPart.equals("back_left_leg"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 9); 
    if (modelPart.equals("back_left_shin"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 10); 
    if (modelPart.equals("back_left_foot"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 11); 
    if (modelPart.equals("right_wing"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 12); 
    if (modelPart.equals("right_wing_tip"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 13); 
    if (modelPart.equals("front_right_leg"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 14); 
    if (modelPart.equals("front_right_shin"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 15); 
    if (modelPart.equals("front_right_foot"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 16); 
    if (modelPart.equals("back_right_leg"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 17); 
    if (modelPart.equals("back_right_shin"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 18); 
    if (modelPart.equals("back_right_foot"))
      return (fyk)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 19); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "head", "spine", "jaw", "body", "left_wing", "left_wing_tip", "front_left_leg", "front_left_shin", "front_left_foot", "back_left_leg", 
        "back_left_shin", "back_left_foot", "right_wing", "right_wing_tip", "front_right_leg", "front_right_shin", "front_right_foot", "back_right_leg", "back_right_shin", "back_right_foot" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gke render = new gke(renderManager.getContext());
    if (!Reflector.EnderDragonRenderer_model.exists()) {
      Config.warn("Field not found: EnderDragonRenderer.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.EnderDragonRenderer_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
