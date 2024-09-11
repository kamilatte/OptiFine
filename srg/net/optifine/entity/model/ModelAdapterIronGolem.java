package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterIronGolem extends ModelAdapter {
  public ModelAdapterIronGolem() {
    super(EntityType.IRON_GOLEM, "iron_golem", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new IronGolemModel(bakeModelLayer(ModelLayers.IRON_GOLEM));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof IronGolemModel))
      return null; 
    IronGolemModel modelIronGolem = (IronGolemModel)model;
    if (modelPart.equals("head"))
      return modelIronGolem.root().getChild("head"); 
    if (modelPart.equals("body"))
      return modelIronGolem.root().getChild("body"); 
    if (modelPart.equals("right_arm"))
      return modelIronGolem.root().getChild("right_arm"); 
    if (modelPart.equals("left_arm"))
      return modelIronGolem.root().getChild("left_arm"); 
    if (modelPart.equals("left_leg"))
      return modelIronGolem.root().getChild("left_leg"); 
    if (modelPart.equals("right_leg"))
      return modelIronGolem.root().getChild("right_leg"); 
    if (modelPart.equals("root"))
      return modelIronGolem.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_arm", "left_arm", "left_leg", "right_leg", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    IronGolemRenderer render = new IronGolemRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
