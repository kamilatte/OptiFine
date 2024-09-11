package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
  public ModelAdapterQuadruped(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof QuadrupedModel))
      return null; 
    QuadrupedModel modelQuadruped = (QuadrupedModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 0); 
    if (modelPart.equals("body"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 1); 
    if (modelPart.equals("leg1"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 2); 
    if (modelPart.equals("leg2"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 3); 
    if (modelPart.equals("leg3"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 4); 
    if (modelPart.equals("leg4"))
      return (ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 5); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4" };
  }
}
