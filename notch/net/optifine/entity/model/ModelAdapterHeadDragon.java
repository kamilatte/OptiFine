package notch.net.optifine.entity.model;

import dnb;
import fwg;
import fye;
import fyj;
import fyk;
import net.optifine.entity.model.ModelAdapterHead;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapterHead {
  public ModelAdapterHeadDragon() {
    super("head_dragon", null, dnb.b.i);
  }
  
  public fwg makeModel() {
    return (fwg)new fye(bakeModelLayer(fyj.R));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fye))
      return null; 
    fye modelDragonHead = (fye)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head); 
    if (modelPart.equals("jaw"))
      return (fyk)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "jaw" };
  }
}
