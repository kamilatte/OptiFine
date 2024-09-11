package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinHeadModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.SkullBlock;
import net.optifine.entity.model.ModelAdapterHead;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadPiglin extends ModelAdapterHead {
  public ModelAdapterHeadPiglin() {
    super("head_piglin", null, SkullBlock.Types.PIGLIN);
  }
  
  public Model makeModel() {
    return (Model)new PiglinHeadModel(bakeModelLayer(ModelLayers.PIGLIN_HEAD));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof PiglinHeadModel))
      return null; 
    PiglinHeadModel modelPiglinHead = (PiglinHeadModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 0); 
    if (modelPart.equals("left_ear"))
      return (ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 1); 
    if (modelPart.equals("right_ear"))
      return (ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "left_ear", "right_ear" };
  }
}
