package notch.net.optifine.entity.model;

import bsx;
import fgo;
import ful;
import fvk;
import fwg;
import fyj;
import fyk;
import gjh;
import gkh;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
  public ModelAdapterArmorStand() {
    super(bsx.d, "armor_stand", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ful(bakeModelLayer(fyj.c));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ful))
      return null; 
    ful modelArmorStand = (ful)model;
    if (modelPart.equals("right"))
      return (fyk)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 0); 
    if (modelPart.equals("left"))
      return (fyk)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 1); 
    if (modelPart.equals("waist"))
      return (fyk)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 2); 
    if (modelPart.equals("base"))
      return (fyk)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 3); 
    return super.getModelRenderer((fwg)modelArmorStand, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectsToArray((Object[])names, (Object[])new String[] { "right", "left", "waist", "base" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjh render = new gjh(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
