package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PufferfishSmallModel;
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

public class ModelAdapterPufferFishSmall extends ModelAdapter {
  public ModelAdapterPufferFishSmall() {
    super(EntityType.PUFFERFISH, "puffer_fish_small", 0.2F);
  }
  
  public Model makeModel() {
    return (Model)new PufferfishSmallModel(bakeModelLayer(ModelLayers.PUFFERFISH_SMALL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof PufferfishSmallModel))
      return null; 
    PufferfishSmallModel modelPufferFishSmall = (PufferfishSmallModel)model;
    if (modelPart.equals("body"))
      return modelPufferFishSmall.root().getChildModelDeep("body"); 
    if (modelPart.equals("eye_right"))
      return modelPufferFishSmall.root().getChildModelDeep("right_eye"); 
    if (modelPart.equals("eye_left"))
      return modelPufferFishSmall.root().getChildModelDeep("left_eye"); 
    if (modelPart.equals("fin_right"))
      return modelPufferFishSmall.root().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelPufferFishSmall.root().getChildModelDeep("left_fin"); 
    if (modelPart.equals("tail"))
      return modelPufferFishSmall.root().getChildModelDeep("back_fin"); 
    if (modelPart.equals("root"))
      return modelPufferFishSmall.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "eye_right", "eye_left", "tail", "fin_right", "fin_left", "root" };
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
    if (!Reflector.RenderPufferfish_modelSmall.exists()) {
      Config.warn("Model field not found: RenderPufferfish.modelSmall");
      return null;
    } 
    Reflector.RenderPufferfish_modelSmall.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
