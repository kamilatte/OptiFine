package srg.net.optifine.entity.model;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterIllager extends ModelAdapter {
  public ModelAdapterIllager(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public ModelAdapterIllager(EntityType type, String name, float shadowSize, String[] aliases) {
    super(type, name, shadowSize, aliases);
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof IllagerModel))
      return null; 
    IllagerModel modelVillager = (IllagerModel)model;
    if (modelPart.equals("head"))
      return modelVillager.root().getChildModelDeep("head"); 
    if (modelPart.equals("hat"))
      return modelVillager.root().getChildModelDeep("hat"); 
    if (modelPart.equals("body"))
      return modelVillager.root().getChildModelDeep("body"); 
    if (modelPart.equals("arms"))
      return modelVillager.root().getChildModelDeep("arms"); 
    if (modelPart.equals("right_leg"))
      return modelVillager.root().getChildModelDeep("right_leg"); 
    if (modelPart.equals("left_leg"))
      return modelVillager.root().getChildModelDeep("left_leg"); 
    if (modelPart.equals("nose"))
      return modelVillager.root().getChildModelDeep("nose"); 
    if (modelPart.equals("right_arm"))
      return modelVillager.root().getChildModelDeep("right_arm"); 
    if (modelPart.equals("left_arm"))
      return modelVillager.root().getChildModelDeep("left_arm"); 
    if (modelPart.equals("root"))
      return modelVillager.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "hat", "body", "arms", "right_leg", "left_leg", "nose", "right_arm", "left_arm", "root" };
  }
}
