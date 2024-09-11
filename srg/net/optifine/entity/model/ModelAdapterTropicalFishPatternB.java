package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterTropicalFishB;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternB extends ModelAdapterTropicalFishB {
  public ModelAdapterTropicalFishPatternB() {
    super(EntityType.TROPICAL_FISH, "tropical_fish_pattern_b", 0.2F);
  }
  
  public Model makeModel() {
    return (Model)new TropicalFishModelB(bakeModelLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new TropicalFishModelB(bakeModelLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
    customRenderer.shadowRadius = 0.2F;
    EntityRenderer render = rendererCache.get(EntityType.TROPICAL_FISH, index, () -> customRenderer);
    if (!(render instanceof TropicalFishRenderer)) {
      Config.warn("Not a RenderTropicalFish: " + String.valueOf(render));
      return null;
    } 
    TropicalFishRenderer renderTropicalFish = (TropicalFishRenderer)render;
    TropicalFishPatternLayer layer = (TropicalFishPatternLayer)renderTropicalFish.getLayer(TropicalFishPatternLayer.class);
    if (layer == null || !layer.custom) {
      layer = new TropicalFishPatternLayer((RenderLayerParent)renderTropicalFish, renderManager.getContext().getModelSet());
      layer.custom = true;
    } 
    if (!Reflector.TropicalFishPatternLayer_modelB.exists()) {
      Config.warn("Field not found: TropicalFishPatternLayer.modelB");
      return null;
    } 
    Reflector.TropicalFishPatternLayer_modelB.setValue(layer, modelBase);
    renderTropicalFish.removeLayers(TropicalFishPatternLayer.class);
    renderTropicalFish.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderTropicalFish;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    TropicalFishRenderer renderTropicalFish = (TropicalFishRenderer)er;
    List<TropicalFishPatternLayer> layers = renderTropicalFish.getLayers(TropicalFishPatternLayer.class);
    for (TropicalFishPatternLayer layer : layers) {
      TropicalFishModelB modelB = (TropicalFishModelB)Reflector.TropicalFishPatternLayer_modelB.getValue(layer);
      if (modelB != null)
        modelB.locationTextureCustom = textureLocation; 
    } 
    return true;
  }
}
