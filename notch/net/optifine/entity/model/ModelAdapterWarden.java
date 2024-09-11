package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxw;
import fyj;
import fyk;
import gkh;
import gnh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWarden extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterWarden() {
    super(bsx.bm, "warden", 0.9F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxw(bakeModelLayer(fyj.bS));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxw))
      return null; 
    fxw modelWarden = (fxw)model;
    if (modelPart.equals("root"))
      return modelWarden.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelWarden.a().getChildModelDeep(name);
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
    map.put("head", "head");
    map.put("right_leg", "right_leg");
    map.put("left_leg", "left_leg");
    map.put("right_arm", "right_arm");
    map.put("left_arm", "left_arm");
    map.put("right_tendril", "right_tendril");
    map.put("left_tendril", "left_tendril");
    map.put("right_ribcage", "right_ribcage");
    map.put("left_ribcage", "left_ribcage");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnh render = new gnh(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
