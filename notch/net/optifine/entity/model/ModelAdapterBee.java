package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fuo;
import fvk;
import fwg;
import fyj;
import fyk;
import gjl;
import gkh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBee extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterBee() {
    super(bsx.h, "bee", 0.4F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuo(bakeModelLayer(fyj.k));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuo))
      return null; 
    fuo modelBee = (fuo)model;
    if (modelPart.equals("body"))
      return (fyk)Reflector.getFieldValue(modelBee, Reflector.ModelBee_ModelRenderers, 0); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      fyk body = getModelRenderer((fwg)modelBee, "body");
      return body.getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("body", "bone");
    map.put("torso", "body");
    map.put("right_wing", "right_wing");
    map.put("left_wing", "left_wing");
    map.put("front_legs", "front_legs");
    map.put("middle_legs", "middle_legs");
    map.put("back_legs", "back_legs");
    map.put("stinger", "stinger");
    map.put("left_antenna", "left_antenna");
    map.put("right_antenna", "right_antenna");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjl render = new gjl(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
