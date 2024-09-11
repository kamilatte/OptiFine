package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvm;
import fwg;
import fyj;
import fyk;
import gkh;
import gkr;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterFox() {
    super(bsx.Q, "fox", 0.4F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvm(bakeModelLayer(fyj.ae));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvm))
      return null; 
    fvm modelFox = (fvm)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelFox, Reflector.ModelFox_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return (String[])getMapPartFields().keySet().toArray((Object[])new String[0]);
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("head", Integer.valueOf(0));
    mapPartFields.put("body", Integer.valueOf(1));
    mapPartFields.put("leg1", Integer.valueOf(2));
    mapPartFields.put("leg2", Integer.valueOf(3));
    mapPartFields.put("leg3", Integer.valueOf(4));
    mapPartFields.put("leg4", Integer.valueOf(5));
    mapPartFields.put("tail", Integer.valueOf(6));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkr render = new gkr(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
