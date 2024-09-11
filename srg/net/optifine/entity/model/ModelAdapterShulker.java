package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulker extends ModelAdapter {
  public ModelAdapterShulker() {
    super(EntityType.SHULKER, "shulker", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ShulkerModel(bakeModelLayer(ModelLayers.SHULKER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ShulkerModel))
      return null; 
    ShulkerModel modelShulker = (ShulkerModel)model;
    if (modelPart.equals("base"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0); 
    if (modelPart.equals("lid"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1); 
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "lid", "head" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ShulkerRenderer render = new ShulkerRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
