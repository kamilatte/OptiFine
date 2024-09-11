package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxn;
import fyj;
import fyk;
import gkh;
import gmt;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStrider extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterStrider() {
    super(bsx.aZ, "strider", 0.5F);
  }
  
  protected ModelAdapterStrider(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxn(bakeModelLayer(fyj.bE));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxn))
      return null; 
    fxn modelStrider = (fxn)model;
    if (modelPart.equals("root"))
      return modelStrider.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelStrider.a().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("right_leg", "right_leg");
    map.put("left_leg", "left_leg");
    map.put("body", "body");
    map.put("hair_right_bottom", "right_bottom_bristle");
    map.put("hair_right_middle", "right_middle_bristle");
    map.put("hair_right_top", "right_top_bristle");
    map.put("hair_left_top", "left_top_bristle");
    map.put("hair_left_middle", "left_middle_bristle");
    map.put("hair_left_bottom", "left_bottom_bristle");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmt render = new gmt(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
