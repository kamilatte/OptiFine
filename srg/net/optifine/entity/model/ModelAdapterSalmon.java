package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSalmon extends ModelAdapter {
  public ModelAdapterSalmon() {
    super(EntityType.SALMON, "salmon", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new SalmonModel(bakeModelLayer(ModelLayers.SALMON));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SalmonModel))
      return null; 
    SalmonModel modelSalmon = (SalmonModel)model;
    if (modelPart.equals("body_front"))
      return modelSalmon.root().getChildModelDeep("body_front"); 
    if (modelPart.equals("body_back"))
      return modelSalmon.root().getChildModelDeep("body_back"); 
    if (modelPart.equals("head"))
      return modelSalmon.root().getChildModelDeep("head"); 
    if (modelPart.equals("fin_back_1"))
      return modelSalmon.root().getChildModelDeep("top_front_fin"); 
    if (modelPart.equals("fin_back_2"))
      return modelSalmon.root().getChildModelDeep("top_back_fin"); 
    if (modelPart.equals("tail"))
      return modelSalmon.root().getChildModelDeep("back_fin"); 
    if (modelPart.equals("fin_right"))
      return modelSalmon.root().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelSalmon.root().getChildModelDeep("left_fin"); 
    if (modelPart.equals("root"))
      return modelSalmon.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body_front", "body_back", "head", "fin_back_1", "fin_back_2", "tail", "fin_right", "fin_left", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SalmonRenderer render = new SalmonRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
