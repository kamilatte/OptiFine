package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvv;
import fwg;
import fyj;
import fyk;
import gkh;
import gkz;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse extends ModelAdapter {
  private static Map<String, Integer> mapParts = makeMapParts();
  
  private static Map<String, String> mapPartsNeck = makeMapPartsNeck();
  
  private static Map<String, String> mapPartsHead = makeMapPartsHead();
  
  private static Map<String, String> mapPartsBody = makeMapPartsBody();
  
  public ModelAdapterHorse() {
    super(bsx.ab, "horse", 0.75F);
  }
  
  protected ModelAdapterHorse(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fvv(bakeModelLayer(fyj.aq));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvv))
      return null; 
    fvv modelHorse = (fvv)model;
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelHorse, Reflector.ModelHorse_ModelRenderers, index);
    } 
    if (mapPartsNeck.containsKey(modelPart)) {
      fyk modelNeck = getModelRenderer((fwg)modelHorse, "neck");
      String name = mapPartsNeck.get(modelPart);
      return modelNeck.b(name);
    } 
    if (mapPartsHead.containsKey(modelPart)) {
      fyk modelHead = getModelRenderer((fwg)modelHorse, "head");
      String name = mapPartsHead.get(modelPart);
      return modelHead.b(name);
    } 
    if (mapPartsBody.containsKey(modelPart)) {
      fyk modelBody = getModelRenderer((fwg)modelHorse, "body");
      String name = mapPartsBody.get(modelPart);
      return modelBody.b(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "neck", "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "child_back_left_leg", "child_back_right_leg", "child_front_left_leg", "child_front_right_leg", 
        "tail", "saddle", "head", "mane", "mouth", "left_ear", "right_ear", "left_bit", "right_bit", "left_rein", 
        "right_rein", "headpiece", "noseband" };
  }
  
  private static Map<String, Integer> makeMapParts() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("body", Integer.valueOf(0));
    map.put("neck", Integer.valueOf(1));
    map.put("back_right_leg", Integer.valueOf(2));
    map.put("back_left_leg", Integer.valueOf(3));
    map.put("front_right_leg", Integer.valueOf(4));
    map.put("front_left_leg", Integer.valueOf(5));
    map.put("child_back_right_leg", Integer.valueOf(6));
    map.put("child_back_left_leg", Integer.valueOf(7));
    map.put("child_front_right_leg", Integer.valueOf(8));
    map.put("child_front_left_leg", Integer.valueOf(9));
    return map;
  }
  
  private static Map<String, String> makeMapPartsNeck() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("head", "head");
    map.put("mane", "mane");
    map.put("mouth", "upper_mouth");
    map.put("left_bit", "left_saddle_mouth");
    map.put("right_bit", "right_saddle_mouth");
    map.put("left_rein", "left_saddle_line");
    map.put("right_rein", "right_saddle_line");
    map.put("headpiece", "head_saddle");
    map.put("noseband", "mouth_saddle_wrap");
    return map;
  }
  
  private static Map<String, String> makeMapPartsBody() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("tail", "tail");
    map.put("saddle", "saddle");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkz render = new gkz(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
  
  private static Map<String, String> makeMapPartsHead() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    return map;
  }
}
