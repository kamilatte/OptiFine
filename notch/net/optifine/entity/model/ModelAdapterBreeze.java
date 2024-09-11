package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fut;
import fvk;
import fwg;
import fyj;
import fyk;
import gjp;
import gkh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBreeze extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterBreeze() {
    super(bsx.m, "breeze", 0.8F);
  }
  
  protected ModelAdapterBreeze(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fut(bakeModelLayer(fyj.s));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fut))
      return null; 
    fut modelBreeze = (fut)model;
    if (modelPart.equals("root"))
      return modelBreeze.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelBreeze.a().getChildModelDeep(name);
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
    map.put("rods", "rods");
    map.put("head", "head");
    map.put("wind_body", "wind_body");
    map.put("wind_middle", "wind_mid");
    map.put("wind_bottom", "wind_bottom");
    map.put("wind_top", "wind_top");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjp render = new gjp(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
