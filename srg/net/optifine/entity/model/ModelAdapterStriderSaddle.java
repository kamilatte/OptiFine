package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterStrider;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStriderSaddle extends ModelAdapterStrider {
  public ModelAdapterStriderSaddle() {
    super(EntityType.STRIDER, "strider_saddle", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new StriderModel(bakeModelLayer(ModelLayers.STRIDER_SADDLE));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    StriderRenderer customRenderer = new StriderRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new StriderModel(bakeModelLayer(ModelLayers.STRIDER_SADDLE));
    customRenderer.shadowRadius = 0.5F;
    EntityRenderer render = rendererCache.get(EntityType.STRIDER, index, () -> customRenderer);
    if (!(render instanceof StriderRenderer)) {
      Config.warn("Not a StriderRenderer: " + String.valueOf(render));
      return null;
    } 
    StriderRenderer renderStrider = (StriderRenderer)render;
    SaddleLayer layer = new SaddleLayer((RenderLayerParent)renderStrider, (EntityModel)modelBase, new ResourceLocation("textures/entity/strider/strider_saddle.png"));
    renderStrider.removeLayers(SaddleLayer.class);
    renderStrider.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderStrider;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    StriderRenderer renderer = (StriderRenderer)er;
    List<SaddleLayer> layers = renderer.getLayers(SaddleLayer.class);
    for (SaddleLayer layer : layers)
      layer.textureLocation = textureLocation; 
    return true;
  }
}
