package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWitherArmor extends ModelAdapterWither {
  public ModelAdapterWitherArmor() {
    super(EntityType.WITHER, "wither_armor", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new WitherBossModel(bakeModelLayer(ModelLayers.WITHER_ARMOR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WitherBossRenderer customRenderer = new WitherBossRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new WitherBossModel(bakeModelLayer(ModelLayers.WITHER_ARMOR));
    customRenderer.shadowRadius = 0.5F;
    EntityRenderer render = rendererCache.get(EntityType.WITHER, index, () -> customRenderer);
    if (!(render instanceof WitherBossRenderer)) {
      Config.warn("Not a WitherRenderer: " + String.valueOf(render));
      return null;
    } 
    WitherBossRenderer renderWither = (WitherBossRenderer)render;
    WitherArmorLayer layer = new WitherArmorLayer((RenderLayerParent)renderWither, renderManager.getContext().getModelSet());
    layer.model = (WitherBossModel)modelBase;
    renderWither.removeLayers(WitherArmorLayer.class);
    renderWither.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderWither;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    WitherBossRenderer renderer = (WitherBossRenderer)er;
    List<WitherArmorLayer> layers = renderer.getLayers(WitherArmorLayer.class);
    for (WitherArmorLayer layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
