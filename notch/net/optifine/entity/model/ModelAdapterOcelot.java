package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwi;
import fyj;
import fyk;
import gkh;
import gls;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterOcelot() {
    super(bsx.au, "ocelot", 0.4F);
  }
  
  protected ModelAdapterOcelot(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fwi(bakeModelLayer(fyj.aF));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwi))
      return null; 
    fwi modelOcelot = (fwi)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelOcelot, Reflector.ModelOcelot_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body" };
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("back_left_leg", Integer.valueOf(0));
    mapPartFields.put("back_right_leg", Integer.valueOf(1));
    mapPartFields.put("front_left_leg", Integer.valueOf(2));
    mapPartFields.put("front_right_leg", Integer.valueOf(3));
    mapPartFields.put("tail", Integer.valueOf(4));
    mapPartFields.put("tail2", Integer.valueOf(5));
    mapPartFields.put("head", Integer.valueOf(6));
    mapPartFields.put("body", Integer.valueOf(7));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gls render = new gls(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
