package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fwl;
import fyj;
import fyk;
import gkh;
import glx;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPhantom extends ModelAdapter {
  private static Map<String, String> mapPartFields = null;
  
  public ModelAdapterPhantom() {
    super(bsx.ay, "phantom", 0.75F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwl(bakeModelLayer(fyj.aI));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwl))
      return null; 
    fwl modelPhantom = (fwl)model;
    if (modelPart.equals("root"))
      return modelPhantom.a(); 
    Map<String, String> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelPhantom.a().getChildModelDeep(name);
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
    mapPartFields.put("body", "body");
    mapPartFields.put("head", "head");
    mapPartFields.put("left_wing", "left_wing_base");
    mapPartFields.put("left_wing_tip", "left_wing_tip");
    mapPartFields.put("right_wing", "right_wing_base");
    mapPartFields.put("right_wing_tip", "right_wing_tip");
    mapPartFields.put("tail", "tail_base");
    mapPartFields.put("tail2", "tail_tip");
    mapPartFields.put("root", "root");
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    glx render = new glx(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
