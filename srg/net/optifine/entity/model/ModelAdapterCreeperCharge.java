package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCreeperCharge extends ModelAdapterCreeper {
  public ModelAdapterCreeperCharge() {
    super(EntityType.CREEPER, "creeper_charge", 0.25F);
  }
  
  public Model makeModel() {
    return (Model)new CreeperModel(bakeModelLayer(ModelLayers.CREEPER_ARMOR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    CreeperRenderer customRenderer = new CreeperRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new CreeperModel(bakeModelLayer(ModelLayers.CREEPER_ARMOR));
    customRenderer.shadowRadius = 0.25F;
    EntityRenderer render = rendererCache.get(EntityType.CREEPER, index, () -> customRenderer);
    if (!(render instanceof CreeperRenderer)) {
      Config.warn("Not a CreeperRenderer: " + String.valueOf(render));
      return null;
    } 
    CreeperRenderer renderCreeper = (CreeperRenderer)render;
    CreeperPowerLayer layer = new CreeperPowerLayer((RenderLayerParent)renderCreeper, renderManager.getContext().getModelSet());
    layer.model = (CreeperModel)modelBase;
    renderCreeper.removeLayers(CreeperPowerLayer.class);
    renderCreeper.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderCreeper;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    CreeperRenderer renderer = (CreeperRenderer)er;
    List<CreeperPowerLayer> layers = renderer.getLayers(CreeperPowerLayer.class);
    for (CreeperPowerLayer layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
