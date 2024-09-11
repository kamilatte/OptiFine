package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fuu;
import fvk;
import fwg;
import fyj;
import fyk;
import gjq;
import gkh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCamel extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterCamel() {
    super(bsx.o, "camel", 0.7F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuu(bakeModelLayer(fyj.w));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuu))
      return null; 
    fuu modelCamel = (fuu)model;
    if (modelPart.equals("root"))
      return modelCamel.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelCamel.a().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("body", "body");
    map.put("hump", "hump");
    map.put("tail", "tail");
    map.put("head", "head");
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    map.put("back_left_leg", "left_hind_leg");
    map.put("back_right_leg", "right_hind_leg");
    map.put("front_left_leg", "left_front_leg");
    map.put("front_right_leg", "right_front_leg");
    map.put("saddle", "saddle");
    map.put("reins", "reins");
    map.put("bridle", "bridle");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjq render = new gjq(renderManager.getContext(), fyj.w);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
