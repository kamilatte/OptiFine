package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterLlama;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaDecor extends ModelAdapterLlama {
  public ModelAdapterLlamaDecor() {
    super(EntityType.LLAMA, "llama_decor", 0.7F);
  }
  
  protected ModelAdapterLlamaDecor(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new LlamaModel(bakeModelLayer(ModelLayers.LLAMA_DECOR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    LlamaRenderer customRenderer = new LlamaRenderer(renderManager.getContext(), ModelLayers.LLAMA_DECOR);
    customRenderer.model = (EntityModel)new LlamaModel(bakeModelLayer(ModelLayers.LLAMA_DECOR));
    customRenderer.shadowRadius = 0.7F;
    EntityType entityType = getType().getLeft().get();
    EntityRenderer render = rendererCache.get(entityType, index, () -> customRenderer);
    if (!(render instanceof LlamaRenderer)) {
      Config.warn("Not a RenderLlama: " + String.valueOf(render));
      return null;
    } 
    LlamaRenderer renderLlama = (LlamaRenderer)render;
    LlamaDecorLayer layer = new LlamaDecorLayer((RenderLayerParent)renderLlama, renderManager.getContext().getModelSet());
    if (!Reflector.LayerLlamaDecor_model.exists()) {
      Config.warn("Field not found: LayerLlamaDecor.model");
      return null;
    } 
    Reflector.LayerLlamaDecor_model.setValue(layer, modelBase);
    renderLlama.removeLayers(LlamaDecorLayer.class);
    renderLlama.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderLlama;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    LlamaRenderer llamaRenderer = (LlamaRenderer)er;
    List<LlamaDecorLayer> layers = llamaRenderer.getLayers(LlamaDecorLayer.class);
    for (RenderLayer layer : layers) {
      Model model = (Model)Reflector.LayerLlamaDecor_model.getValue(layer);
      if (model != null)
        model.locationTextureCustom = textureLocation; 
    } 
    return true;
  }
}
