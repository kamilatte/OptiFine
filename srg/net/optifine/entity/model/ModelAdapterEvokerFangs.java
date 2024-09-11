package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs extends ModelAdapter {
  public ModelAdapterEvokerFangs() {
    super(EntityType.EVOKER_FANGS, "evoker_fangs", 0.0F, new String[] { "evocation_fangs" });
  }
  
  public Model makeModel() {
    return (Model)new EvokerFangsModel(bakeModelLayer(ModelLayers.EVOKER_FANGS));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof EvokerFangsModel))
      return null; 
    EvokerFangsModel modelEvokerFangs = (EvokerFangsModel)model;
    if (modelPart.equals("base"))
      return modelEvokerFangs.root().getChildModelDeep("base"); 
    if (modelPart.equals("upper_jaw"))
      return modelEvokerFangs.root().getChildModelDeep("upper_jaw"); 
    if (modelPart.equals("lower_jaw"))
      return modelEvokerFangs.root().getChildModelDeep("lower_jaw"); 
    if (modelPart.equals("root"))
      return modelEvokerFangs.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "upper_jaw", "lower_jaw", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    EvokerFangsRenderer render = new EvokerFangsRenderer(renderManager.getContext());
    if (!Reflector.RenderEvokerFangs_model.exists()) {
      Config.warn("Field not found: RenderEvokerFangs.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderEvokerFangs_model, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
