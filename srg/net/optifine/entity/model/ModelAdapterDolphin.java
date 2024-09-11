package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDolphin extends ModelAdapter {
  public ModelAdapterDolphin() {
    super(EntityType.DOLPHIN, "dolphin", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new DolphinModel(bakeModelLayer(ModelLayers.DOLPHIN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof DolphinModel))
      return null; 
    DolphinModel modelDolphin = (DolphinModel)model;
    if (modelPart.equals("root"))
      return modelDolphin.root(); 
    ModelPart modelBody = modelDolphin.root().getChild("body");
    if (modelBody == null)
      return null; 
    if (modelPart.equals("body"))
      return modelBody; 
    if (modelPart.equals("back_fin"))
      return modelBody.getChild("back_fin"); 
    if (modelPart.equals("left_fin"))
      return modelBody.getChild("left_fin"); 
    if (modelPart.equals("right_fin"))
      return modelBody.getChild("right_fin"); 
    if (modelPart.equals("tail"))
      return modelBody.getChild("tail"); 
    if (modelPart.equals("tail_fin"))
      return modelBody.getChild("tail").getChild("tail_fin"); 
    if (modelPart.equals("head"))
      return modelBody.getChild("head"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "back_fin", "left_fin", "right_fin", "tail", "tail_fin", "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    DolphinRenderer render = new DolphinRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
