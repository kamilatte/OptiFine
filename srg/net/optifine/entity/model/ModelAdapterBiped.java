package srg.net.optifine.entity.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterBiped extends ModelAdapter {
  public ModelAdapterBiped(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof HumanoidModel))
      return null; 
    HumanoidModel modelBiped = (HumanoidModel)model;
    if (modelPart.equals("head"))
      return modelBiped.head; 
    if (modelPart.equals("headwear"))
      return modelBiped.hat; 
    if (modelPart.equals("body"))
      return modelBiped.body; 
    if (modelPart.equals("left_arm"))
      return modelBiped.leftArm; 
    if (modelPart.equals("right_arm"))
      return modelBiped.rightArm; 
    if (modelPart.equals("left_leg"))
      return modelBiped.leftLeg; 
    if (modelPart.equals("right_leg"))
      return modelBiped.rightLeg; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg" };
  }
}
