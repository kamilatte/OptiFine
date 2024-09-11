package notch.net.optifine.entity.model;

import bsx;
import fvy;
import fwg;
import fyk;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterIllager extends ModelAdapter {
  public ModelAdapterIllager(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public ModelAdapterIllager(bsx type, String name, float shadowSize, String[] aliases) {
    super(type, name, shadowSize, aliases);
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvy))
      return null; 
    fvy modelVillager = (fvy)model;
    if (modelPart.equals("head"))
      return modelVillager.a().getChildModelDeep("head"); 
    if (modelPart.equals("hat"))
      return modelVillager.a().getChildModelDeep("hat"); 
    if (modelPart.equals("body"))
      return modelVillager.a().getChildModelDeep("body"); 
    if (modelPart.equals("arms"))
      return modelVillager.a().getChildModelDeep("arms"); 
    if (modelPart.equals("right_leg"))
      return modelVillager.a().getChildModelDeep("right_leg"); 
    if (modelPart.equals("left_leg"))
      return modelVillager.a().getChildModelDeep("left_leg"); 
    if (modelPart.equals("nose"))
      return modelVillager.a().getChildModelDeep("nose"); 
    if (modelPart.equals("right_arm"))
      return modelVillager.a().getChildModelDeep("right_arm"); 
    if (modelPart.equals("left_arm"))
      return modelVillager.a().getChildModelDeep("left_arm"); 
    if (modelPart.equals("root"))
      return modelVillager.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "hat", "body", "arms", "right_leg", "left_leg", "nose", "right_arm", "left_arm", "root" };
  }
}
