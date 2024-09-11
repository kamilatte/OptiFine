package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fug;
import fvk;
import fwg;
import fyj;
import fyk;
import gjf;
import gkh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterAllay extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterAllay() {
    super(bsx.a, "allay", 0.4F);
  }
  
  public fwg makeModel() {
    return (fwg)new fug(bakeModelLayer(fyj.a));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fug))
      return null; 
    fug modelAllay = (fug)model;
    if (modelPart.equals("root"))
      return modelAllay.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelAllay.a().getChildModelDeep(name);
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
    map.put("right_arm", "right_arm");
    map.put("left_arm", "left_arm");
    map.put("right_wing", "right_wing");
    map.put("left_wing", "left_wing");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjf render = new gjf(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
