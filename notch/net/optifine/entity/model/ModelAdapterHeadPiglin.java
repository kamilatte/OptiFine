package notch.net.optifine.entity.model;

import dnb;
import fwg;
import fwn;
import fyj;
import fyk;
import net.optifine.entity.model.ModelAdapterHead;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadPiglin extends ModelAdapterHead {
  public ModelAdapterHeadPiglin() {
    super("head_piglin", null, dnb.b.h);
  }
  
  public fwg makeModel() {
    return (fwg)new fwn(bakeModelLayer(fyj.aO));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwn))
      return null; 
    fwn modelPiglinHead = (fwn)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 0); 
    if (modelPart.equals("left_ear"))
      return (fyk)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 1); 
    if (modelPart.equals("right_ear"))
      return (fyk)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "left_ear", "right_ear" };
  }
}
