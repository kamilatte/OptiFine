package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishA extends ModelAdapter {
  public ModelAdapterTropicalFishA() {
    super(EntityType.TROPICAL_FISH, "tropical_fish_a", 0.2F);
  }
  
  public ModelAdapterTropicalFishA(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new TropicalFishModelA(bakeModelLayer(ModelLayers.TROPICAL_FISH_SMALL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof TropicalFishModelA))
      return null; 
    TropicalFishModelA modelTropicalFish = (TropicalFishModelA)model;
    if (modelPart.equals("body"))
      return modelTropicalFish.root().getChildModelDeep("body"); 
    if (modelPart.equals("tail"))
      return modelTropicalFish.root().getChildModelDeep("tail"); 
    if (modelPart.equals("fin_right"))
      return modelTropicalFish.root().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelTropicalFish.root().getChildModelDeep("left_fin"); 
    if (modelPart.equals("fin_top"))
      return modelTropicalFish.root().getChildModelDeep("top_fin"); 
    if (modelPart.equals("root"))
      return modelTropicalFish.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tail", "fin_right", "fin_left", "fin_top", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
    customRenderer.shadowRadius = shadowSize;
    EntityRenderer render = rendererCache.get(EntityType.TROPICAL_FISH, index, () -> customRenderer);
    if (!(render instanceof TropicalFishRenderer)) {
      Config.warn("Not a TropicalFishRenderer: " + String.valueOf(render));
      return null;
    } 
    TropicalFishRenderer renderFish = (TropicalFishRenderer)render;
    if (!Reflector.RenderTropicalFish_modelA.exists()) {
      Config.warn("Model field not found: RenderTropicalFish.modelA");
      return null;
    } 
    Reflector.RenderTropicalFish_modelA.setValue(renderFish, modelBase);
    return (IEntityRenderer)renderFish;
  }
}
