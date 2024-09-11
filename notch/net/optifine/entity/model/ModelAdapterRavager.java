package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwx;
import fyj;
import fyk;
import gkh;
import gme;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterRavager extends ModelAdapter {
  private static Map<String, String> mapPartFields = null;
  
  public ModelAdapterRavager() {
    super(bsx.aH, "ravager", 1.1F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwx(bakeModelLayer(fyj.bg));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwx))
      return null; 
    fwx modelRavager = (fwx)model;
    if (modelPart.equals("root"))
      return modelRavager.a(); 
    Map<String, String> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelRavager.a().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return (String[])getMapPartFields().keySet().toArray((Object[])new String[0]);
  }
  
  private static Map<String, String> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("head", "head");
    mapPartFields.put("jaw", "mouth");
    mapPartFields.put("body", "body");
    mapPartFields.put("leg1", "right_hind_leg");
    mapPartFields.put("leg2", "left_hind_leg");
    mapPartFields.put("leg3", "right_front_leg");
    mapPartFields.put("leg4", "left_front_leg");
    mapPartFields.put("neck", "neck");
    mapPartFields.put("root", "root");
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gme render = new gme(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
