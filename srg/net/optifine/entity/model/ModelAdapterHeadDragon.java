package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.SkullBlock;
import net.optifine.entity.model.ModelAdapterHead;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapterHead {
  public ModelAdapterHeadDragon() {
    super("head_dragon", null, SkullBlock.Types.DRAGON);
  }
  
  public Model makeModel() {
    return (Model)new DragonHeadModel(bakeModelLayer(ModelLayers.DRAGON_SKULL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof DragonHeadModel))
      return null; 
    DragonHeadModel modelDragonHead = (DragonHeadModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head); 
    if (modelPart.equals("jaw"))
      return (ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "jaw" };
  }
}
