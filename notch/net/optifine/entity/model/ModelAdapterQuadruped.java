package notch.net.optifine.entity.model;

import bsx;
import fwg;
import fwu;
import fyk;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
  public ModelAdapterQuadruped(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwu))
      return null; 
    fwu modelQuadruped = (fwu)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 0); 
    if (modelPart.equals("body"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 1); 
    if (modelPart.equals("leg1"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 2); 
    if (modelPart.equals("leg2"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 3); 
    if (modelPart.equals("leg3"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 4); 
    if (modelPart.equals("leg4"))
      return (fyk)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 5); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4" };
  }
}
