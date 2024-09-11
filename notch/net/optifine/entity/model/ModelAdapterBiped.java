package notch.net.optifine.entity.model;

import bsx;
import fvx;
import fwg;
import fyk;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterBiped extends ModelAdapter {
  public ModelAdapterBiped(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvx))
      return null; 
    fvx modelBiped = (fvx)model;
    if (modelPart.equals("head"))
      return modelBiped.k; 
    if (modelPart.equals("headwear"))
      return modelBiped.l; 
    if (modelPart.equals("body"))
      return modelBiped.m; 
    if (modelPart.equals("left_arm"))
      return modelBiped.o; 
    if (modelPart.equals("right_arm"))
      return modelBiped.n; 
    if (modelPart.equals("left_leg"))
      return modelBiped.q; 
    if (modelPart.equals("right_leg"))
      return modelBiped.p; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg" };
  }
}
