package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPigSaddle extends ModelAdapterQuadruped {
  public ModelAdapterPigSaddle() {
    super(EntityType.PIG, "pig_saddle", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new PigModel(bakeModelLayer(ModelLayers.PIG_SADDLE));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PigRenderer customRenderer = new PigRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new PigModel(bakeModelLayer(ModelLayers.PIG_SADDLE));
    customRenderer.shadowRadius = 0.7F;
    EntityRenderer render = rendererCache.get(EntityType.PIG, index, () -> customRenderer);
    if (!(render instanceof PigRenderer)) {
      Config.warn("Not a PigRenderer: " + String.valueOf(render));
      return null;
    } 
    PigRenderer renderPig = (PigRenderer)render;
    SaddleLayer layer = new SaddleLayer((RenderLayerParent)renderPig, (EntityModel)modelBase, new ResourceLocation("textures/entity/pig/pig_saddle.png"));
    renderPig.removeLayers(SaddleLayer.class);
    renderPig.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderPig;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    PigRenderer renderer = (PigRenderer)er;
    List<SaddleLayer> layers = renderer.getLayers(SaddleLayer.class);
    for (SaddleLayer layer : layers)
      layer.textureLocation = textureLocation; 
    return true;
  }
}
