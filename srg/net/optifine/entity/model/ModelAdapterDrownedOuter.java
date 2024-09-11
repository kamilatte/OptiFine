package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterDrowned;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
  public ModelAdapterDrownedOuter() {
    super(EntityType.DROWNED, "drowned_outer", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new DrownedModel(bakeModelLayer(ModelLayers.DROWNED_OUTER_LAYER));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    DrownedRenderer customRenderer = new DrownedRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new DrownedModel(bakeModelLayer(ModelLayers.DROWNED_OUTER_LAYER));
    customRenderer.shadowRadius = 0.75F;
    EntityRenderer render = rendererCache.get(EntityType.DROWNED, index, () -> customRenderer);
    if (!(render instanceof DrownedRenderer)) {
      Config.warn("Not a DrownedRenderer: " + String.valueOf(render));
      return null;
    } 
    DrownedRenderer renderDrowned = (DrownedRenderer)render;
    DrownedOuterLayer layer = new DrownedOuterLayer((RenderLayerParent)renderDrowned, renderManager.getContext().getModelSet());
    layer.model = (DrownedModel)modelBase;
    renderDrowned.removeLayers(DrownedOuterLayer.class);
    renderDrowned.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderDrowned;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    DrownedRenderer renderer = (DrownedRenderer)er;
    List<DrownedOuterLayer> layers = renderer.getLayers(DrownedOuterLayer.class);
    for (DrownedOuterLayer layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
