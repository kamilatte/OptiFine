package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxt;
import fyj;
import fyk;
import gkh;
import gnd;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterVex extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterVex() {
    super(bsx.bi, "vex", 0.3F);
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxt))
      return null; 
    fxt modelVex = (fxt)model;
    if (modelPart.equals("root"))
      return modelVex.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelVex.a().getChildModelDeep(name);
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
  
  public fwg makeModel() {
    return (fwg)new fxt(bakeModelLayer(fyj.bP));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnd render = new gnd(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
