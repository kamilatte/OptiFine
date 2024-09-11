package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fxy;
import fyj;
import fyk;
import gkh;
import gni;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWindCharge extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterWindCharge() {
    super(bsx.bn, "wind_charge", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxy(bakeModelLayer(fyj.bU));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxy))
      return null; 
    fxy modelWindCharge = (fxy)model;
    if (modelPart.equals("root"))
      return modelWindCharge.a(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelWindCharge.a().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("core", "projectile");
    map.put("wind", "wind");
    map.put("cube1", "cube_r1");
    map.put("cube2", "cube_r2");
    map.put("charge", "wind_charge");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gni render = new gni(renderManager.getContext());
    if (!Reflector.RenderWindCharge_model.exists()) {
      Config.warn("Field not found: RenderWindCharge.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderWindCharge_model, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
