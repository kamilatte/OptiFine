package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterParrot extends ModelAdapter {
  public ModelAdapterParrot() {
    super(EntityType.PARROT, "parrot", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new ParrotModel(bakeModelLayer(ModelLayers.PARROT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ParrotModel))
      return null; 
    ParrotModel modelParrot = (ParrotModel)model;
    if (modelPart.equals("body"))
      return modelParrot.root().getChild("body"); 
    if (modelPart.equals("tail"))
      return modelParrot.root().getChild("tail"); 
    if (modelPart.equals("left_wing"))
      return modelParrot.root().getChild("left_wing"); 
    if (modelPart.equals("right_wing"))
      return modelParrot.root().getChild("right_wing"); 
    if (modelPart.equals("head"))
      return modelParrot.root().getChild("head"); 
    if (modelPart.equals("left_leg"))
      return modelParrot.root().getChild("left_leg"); 
    if (modelPart.equals("right_leg"))
      return modelParrot.root().getChild("right_leg"); 
    if (modelPart.equals("root"))
      return modelParrot.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tail", "left_wing", "right_wing", "head", "left_leg", "right_leg", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ParrotRenderer render = new ParrotRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
