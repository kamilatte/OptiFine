package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterStray;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStrayOuter extends ModelAdapterStray {
  public ModelAdapterStrayOuter() {
    super(EntityType.STRAY, "stray_outer", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new SkeletonModel(bakeModelLayer(ModelLayers.STRAY_OUTER_LAYER));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    StrayRenderer customRenderer = new StrayRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new SkeletonModel(bakeModelLayer(ModelLayers.STRAY_OUTER_LAYER));
    customRenderer.shadowRadius = 0.7F;
    EntityRenderer render = rendererCache.get(EntityType.STRAY, index, () -> customRenderer);
    if (!(render instanceof StrayRenderer)) {
      Config.warn("Not a SkeletonModelRenderer: " + String.valueOf(render));
      return null;
    } 
    StrayRenderer renderStray = (StrayRenderer)render;
    ResourceLocation STRAY_CLOTHES_LOCATION = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
    SkeletonClothingLayer layer = new SkeletonClothingLayer((RenderLayerParent)renderStray, renderManager.getContext().getModelSet(), ModelLayers.STRAY_OUTER_LAYER, STRAY_CLOTHES_LOCATION);
    layer.layerModel = (SkeletonModel)modelBase;
    renderStray.removeLayers(SkeletonClothingLayer.class);
    renderStray.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderStray;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    StrayRenderer renderer = (StrayRenderer)er;
    List<SkeletonClothingLayer> layers = renderer.getLayers(SkeletonClothingLayer.class);
    for (SkeletonClothingLayer layer : layers)
      layer.clothesLocation = textureLocation; 
    return true;
  }
}
