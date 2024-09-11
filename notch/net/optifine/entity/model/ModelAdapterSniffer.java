package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxj;
import fyj;
import fyk;
import gkh;
import gmn;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSniffer extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterSniffer() {
    super(bsx.aR, "sniffer", 1.1F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxj(bakeModelLayer(fyj.bv));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxj))
      return null; 
    fxj modelSniffer = (fxj)model;
    if (modelPart.equals("root"))
      return modelSniffer.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelSniffer.a().getChildModelDeep(name);
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
    map.put("back_left_leg", "left_hind_leg");
    map.put("back_right_leg", "right_hind_leg");
    map.put("middle_left_leg", "left_mid_leg");
    map.put("middle_right_leg", "right_mid_leg");
    map.put("front_left_leg", "left_front_leg");
    map.put("front_right_leg", "right_front_leg");
    map.put("head", "head");
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    map.put("nose", "nose");
    map.put("lower_beak", "lower_beak");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmn render = new gmn(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
