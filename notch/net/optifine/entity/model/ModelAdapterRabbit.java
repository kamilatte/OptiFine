package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwv;
import fyj;
import fyk;
import gkh;
import gmd;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterRabbit extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterRabbit() {
    super(bsx.aG, "rabbit", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwv(bakeModelLayer(fyj.bf));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwv))
      return null; 
    fwv modelRabbit = (fwv)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelRabbit, Reflector.ModelRabbit_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", 
        "tail", "nose" };
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("left_foot", Integer.valueOf(0));
    mapPartFields.put("right_foot", Integer.valueOf(1));
    mapPartFields.put("left_thigh", Integer.valueOf(2));
    mapPartFields.put("right_thigh", Integer.valueOf(3));
    mapPartFields.put("body", Integer.valueOf(4));
    mapPartFields.put("left_arm", Integer.valueOf(5));
    mapPartFields.put("right_arm", Integer.valueOf(6));
    mapPartFields.put("head", Integer.valueOf(7));
    mapPartFields.put("right_ear", Integer.valueOf(8));
    mapPartFields.put("left_ear", Integer.valueOf(9));
    mapPartFields.put("tail", Integer.valueOf(10));
    mapPartFields.put("nose", Integer.valueOf(11));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmd render = new gmd(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
