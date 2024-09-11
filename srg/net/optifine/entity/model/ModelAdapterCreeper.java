package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCreeper extends ModelAdapter {
  public ModelAdapterCreeper() {
    super(EntityType.CREEPER, "creeper", 0.5F);
  }
  
  public ModelAdapterCreeper(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new CreeperModel(bakeModelLayer(ModelLayers.CREEPER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof CreeperModel))
      return null; 
    CreeperModel modelCreeper = (CreeperModel)model;
    if (modelPart.equals("head"))
      return modelCreeper.root().getChildModelDeep("head"); 
    if (modelPart.equals("body"))
      return modelCreeper.root().getChildModelDeep("body"); 
    if (modelPart.equals("leg1"))
      return modelCreeper.root().getChildModelDeep("right_hind_leg"); 
    if (modelPart.equals("leg2"))
      return modelCreeper.root().getChildModelDeep("left_hind_leg"); 
    if (modelPart.equals("leg3"))
      return modelCreeper.root().getChildModelDeep("right_front_leg"); 
    if (modelPart.equals("leg4"))
      return modelCreeper.root().getChildModelDeep("left_front_leg"); 
    if (modelPart.equals("root"))
      return modelCreeper.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    CreeperRenderer render = new CreeperRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
