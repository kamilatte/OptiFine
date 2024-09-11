package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishMedium extends ModelAdapter {
  public ModelAdapterPufferFishMedium() {
    super(EntityType.PUFFERFISH, "puffer_fish_medium", 0.2F);
  }
  
  public Model makeModel() {
    return (Model)new PufferfishMidModel(bakeModelLayer(ModelLayers.PUFFERFISH_MEDIUM));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof PufferfishMidModel))
      return null; 
    PufferfishMidModel modelPufferFishMedium = (PufferfishMidModel)model;
    if (modelPart.equals("body"))
      return modelPufferFishMedium.root().getChildModelDeep("body"); 
    if (modelPart.equals("fin_right"))
      return modelPufferFishMedium.root().getChildModelDeep("right_blue_fin"); 
    if (modelPart.equals("fin_left"))
      return modelPufferFishMedium.root().getChildModelDeep("left_blue_fin"); 
    if (modelPart.equals("spikes_front_top"))
      return modelPufferFishMedium.root().getChildModelDeep("top_front_fin"); 
    if (modelPart.equals("spikes_back_top"))
      return modelPufferFishMedium.root().getChildModelDeep("top_back_fin"); 
    if (modelPart.equals("spikes_front_right"))
      return modelPufferFishMedium.root().getChildModelDeep("right_front_fin"); 
    if (modelPart.equals("spikes_back_right"))
      return modelPufferFishMedium.root().getChildModelDeep("right_back_fin"); 
    if (modelPart.equals("spikes_back_left"))
      return modelPufferFishMedium.root().getChildModelDeep("left_back_fin"); 
    if (modelPart.equals("spikes_front_left"))
      return modelPufferFishMedium.root().getChildModelDeep("left_front_fin"); 
    if (modelPart.equals("spikes_back_bottom"))
      return modelPufferFishMedium.root().getChildModelDeep("bottom_back_fin"); 
    if (modelPart.equals("spikes_front_bottom"))
      return modelPufferFishMedium.root().getChildModelDeep("bottom_front_fin"); 
    if (modelPart.equals("root"))
      return modelPufferFishMedium.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "fin_right", "fin_left", "spikes_front_top", "spikes_back_top", "spikes_front_right", "spikes_back_right", "spikes_back_left", "spikes_front_left", "spikes_back_bottom", 
        "spikes_front_bottom", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PufferfishRenderer customRenderer = new PufferfishRenderer(renderManager.getContext());
    customRenderer.shadowRadius = shadowSize;
    EntityRenderer render = rendererCache.get(EntityType.PUFFERFISH, index, () -> customRenderer);
    if (!(render instanceof PufferfishRenderer)) {
      Config.warn("Not a PufferfishRenderer: " + String.valueOf(render));
      return null;
    } 
    PufferfishRenderer renderFish = (PufferfishRenderer)render;
    if (!Reflector.RenderPufferfish_modelMedium.exists()) {
      Config.warn("Model field not found: RenderPufferfish.modelMedium");
      return null;
    } 
    Reflector.RenderPufferfish_modelMedium.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
