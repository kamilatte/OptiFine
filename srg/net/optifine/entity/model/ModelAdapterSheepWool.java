package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
  public ModelAdapterSheepWool() {
    super(EntityType.SHEEP, "sheep_wool", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new SheepFurModel(bakeModelLayer(ModelLayers.SHEEP_FUR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SheepRenderer customRenderer = new SheepRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new SheepModel(bakeModelLayer(ModelLayers.SHEEP_FUR));
    customRenderer.shadowRadius = 0.7F;
    EntityRenderer render = rendererCache.get(EntityType.SHEEP, index, () -> customRenderer);
    if (!(render instanceof SheepRenderer)) {
      Config.warn("Not a RenderSheep: " + String.valueOf(render));
      return null;
    } 
    SheepRenderer renderSheep = (SheepRenderer)render;
    SheepFurLayer layer = new SheepFurLayer((RenderLayerParent)renderSheep, renderManager.getContext().getModelSet());
    layer.model = (SheepFurModel)modelBase;
    renderSheep.removeLayers(SheepFurLayer.class);
    renderSheep.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderSheep;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    SheepRenderer renderSheep = (SheepRenderer)er;
    List<SheepFurLayer> layers = renderSheep.getLayers(SheepFurLayer.class);
    for (SheepFurLayer layer : layers)
      layer.model.locationTextureCustom = textureLocation; 
    return true;
  }
}
