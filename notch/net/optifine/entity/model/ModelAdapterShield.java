package notch.net.optifine.entity.model;

import fwg;
import fxb;
import fyj;
import fyk;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.entity.model.VirtualEntityRenderer;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class ModelAdapterShield extends ModelAdapter {
  public ModelAdapterShield() {
    super((Either)null, "shield", 0.0F, null);
  }
  
  public fwg makeModel() {
    return (fwg)new fxb(bakeModelLayer(fyj.bk));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxb))
      return null; 
    fxb modelShield = (fxb)model;
    if (modelPart.equals("plate"))
      return (fyk)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 1); 
    if (modelPart.equals("handle"))
      return (fyk)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 2); 
    if (modelPart.equals("root"))
      return (fyk)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "plate", "handle", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    return (IEntityRenderer)new VirtualEntityRenderer(modelBase);
  }
}
