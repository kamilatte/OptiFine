package srg.net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapterBiped;

public abstract class ModelAdapterPlayer extends ModelAdapterBiped {
  protected ModelAdapterPlayer(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (model instanceof PlayerModel) {
      PlayerModel playerModel = (PlayerModel)model;
      if (modelPart.equals("left_sleeve"))
        return playerModel.leftSleeve; 
      if (modelPart.equals("right_sleeve"))
        return playerModel.rightSleeve; 
      if (modelPart.equals("left_pants"))
        return playerModel.leftPants; 
      if (modelPart.equals("right_pants"))
        return playerModel.rightPants; 
      if (modelPart.equals("jacket"))
        return playerModel.jacket; 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> names = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    names.add("left_sleeve");
    names.add("right_sleeve");
    names.add("left_pants");
    names.add("right_pants");
    names.add("jacket");
    return names.<String>toArray(new String[names.size()]);
  }
}
