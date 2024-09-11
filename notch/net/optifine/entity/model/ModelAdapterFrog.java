package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvn;
import fwg;
import fyj;
import fyk;
import gkh;
import gks;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterFrog extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterFrog() {
    super(bsx.R, "frog", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvn(bakeModelLayer(fyj.af));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvn))
      return null; 
    fvn modelFrog = (fvn)model;
    if (modelPart.equals("root"))
      return modelFrog.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelFrog.a().getChildModelDeep(name);
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
    map.put("head", "head");
    map.put("eyes", "eyes");
    map.put("tongue", "tongue");
    map.put("left_arm", "left_arm");
    map.put("right_arm", "right_arm");
    map.put("left_leg", "left_leg");
    map.put("right_leg", "right_leg");
    map.put("croaking_body", "croaking_body");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gks render = new gks(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
