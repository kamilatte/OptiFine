package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken extends ModelAdapter {
  public ModelAdapterChicken() {
    super(EntityType.CHICKEN, "chicken", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new ChickenModel(bakeModelLayer(ModelLayers.CHICKEN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChickenModel))
      return null; 
    ChickenModel modelChicken = (ChickenModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 0); 
    if (modelPart.equals("body"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 1); 
    if (modelPart.equals("right_leg"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 2); 
    if (modelPart.equals("left_leg"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 3); 
    if (modelPart.equals("right_wing"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 4); 
    if (modelPart.equals("left_wing"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 5); 
    if (modelPart.equals("bill"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 6); 
    if (modelPart.equals("chin"))
      return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 7); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ChickenRenderer render = new ChickenRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
