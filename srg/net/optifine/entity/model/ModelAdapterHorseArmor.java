package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
  public ModelAdapterHorseArmor() {
    super(EntityType.HORSE, "horse_armor", 0.75F);
  }
  
  public Model makeModel() {
    return (Model)new HorseModel(bakeModelLayer(ModelLayers.HORSE_ARMOR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    HorseRenderer customRenderer = new HorseRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new HorseModel(bakeModelLayer(ModelLayers.HORSE_ARMOR));
    customRenderer.shadowRadius = 0.75F;
    EntityRenderer render = rendererCache.get(EntityType.HORSE, index, () -> customRenderer);
    if (!(render instanceof HorseRenderer)) {
      Config.warn("Not a HorseRenderer: " + String.valueOf(render));
      return null;
    } 
    HorseRenderer renderHorse = (HorseRenderer)render;
    HorseArmorLayer layer = new HorseArmorLayer((RenderLayerParent)renderHorse, renderManager.getContext().getModelSet());
    layer.model = (HorseModel)modelBase;
    renderHorse.removeLayers(HorseArmorLayer.class);
    renderHorse.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderHorse;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    HorseRenderer renderer = (HorseRenderer)er;
    List<HorseArmorLayer> layers = renderer.getLayers(HorseArmorLayer.class);
    for (HorseArmorLayer layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
