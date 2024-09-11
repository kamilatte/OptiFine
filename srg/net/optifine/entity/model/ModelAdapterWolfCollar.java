package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
  public ModelAdapterWolfCollar() {
    super(EntityType.WOLF, "wolf_collar", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new WolfModel(bakeModelLayer(ModelLayers.WOLF));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WolfRenderer customRenderer = new WolfRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new WolfModel(bakeModelLayer(ModelLayers.WOLF));
    customRenderer.shadowRadius = 0.5F;
    EntityRenderer render = rendererCache.get(EntityType.WOLF, index, () -> customRenderer);
    if (!(render instanceof WolfRenderer)) {
      Config.warn("Not a RenderWolf: " + String.valueOf(render));
      return null;
    } 
    WolfRenderer renderWolf = (WolfRenderer)render;
    WolfCollarLayer layer = new WolfCollarLayer((RenderLayerParent)renderWolf);
    layer.model = (WolfModel)modelBase;
    renderWolf.removeLayers(WolfCollarLayer.class);
    renderWolf.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderWolf;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    WolfRenderer renderWolf = (WolfRenderer)er;
    List<WolfCollarLayer> layers = renderWolf.getLayers(WolfCollarLayer.class);
    for (WolfCollarLayer layer : layers)
      layer.model.locationTextureCustom = textureLocation; 
    return true;
  }
}
