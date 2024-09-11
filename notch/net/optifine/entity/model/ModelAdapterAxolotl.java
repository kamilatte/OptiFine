package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fum;
import fvk;
import fwg;
import fyj;
import fyk;
import gjj;
import gkh;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterAxolotl extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterAxolotl() {
    super(bsx.f, "axolotl", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fum(bakeModelLayer(fyj.f));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fum))
      return null; 
    fum modelAxolotl = (fum)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelAxolotl, Reflector.ModelAxolotl_ModelRenderers, index);
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
    mapPartFields.put("tail", Integer.valueOf(0));
    mapPartFields.put("leg2", Integer.valueOf(1));
    mapPartFields.put("leg1", Integer.valueOf(2));
    mapPartFields.put("leg4", Integer.valueOf(3));
    mapPartFields.put("leg3", Integer.valueOf(4));
    mapPartFields.put("body", Integer.valueOf(5));
    mapPartFields.put("head", Integer.valueOf(6));
    mapPartFields.put("top_gills", Integer.valueOf(7));
    mapPartFields.put("left_gills", Integer.valueOf(8));
    mapPartFields.put("right_gills", Integer.valueOf(9));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjj render = new gjj(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
