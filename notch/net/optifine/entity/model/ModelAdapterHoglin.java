package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvu;
import fwg;
import fyj;
import fyk;
import gkh;
import gky;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHoglin extends ModelAdapter {
  private static Map<String, Integer> mapParts = makeMapParts();
  
  public ModelAdapterHoglin() {
    super(bsx.Z, "hoglin", 0.7F);
  }
  
  public ModelAdapterHoglin(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fvu(bakeModelLayer(fyj.ao));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvu))
      return null; 
    fvu modelBoar = (fvu)model;
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (fyk)Reflector.getFieldValue(modelBoar, Reflector.ModelBoar_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, Integer> makeMapParts() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("head", Integer.valueOf(0));
    map.put("right_ear", Integer.valueOf(1));
    map.put("left_ear", Integer.valueOf(2));
    map.put("body", Integer.valueOf(3));
    map.put("front_right_leg", Integer.valueOf(4));
    map.put("front_left_leg", Integer.valueOf(5));
    map.put("back_right_leg", Integer.valueOf(6));
    map.put("back_left_leg", Integer.valueOf(7));
    map.put("mane", Integer.valueOf(8));
    return map;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gky render = new gky(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
