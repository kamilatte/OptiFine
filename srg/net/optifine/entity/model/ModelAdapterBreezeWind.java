package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.BreezeWindLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBreeze;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBreezeWind extends ModelAdapterBreeze {
  public ModelAdapterBreezeWind() {
    super(EntityType.BREEZE, "breeze_wind", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new BreezeModel(BreezeModel.createBodyLayer(128, 128).bakeRoot());
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BreezeRenderer customRenderer = new BreezeRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new BreezeModel(bakeModelLayer(ModelLayers.BREEZE));
    customRenderer.shadowRadius = 0.0F;
    EntityRenderer render = rendererCache.get(EntityType.BREEZE, index, () -> customRenderer);
    if (!(render instanceof BreezeRenderer)) {
      Config.warn("Not a RenderBreeze: " + String.valueOf(render));
      return null;
    } 
    BreezeRenderer renderBreeze = (BreezeRenderer)render;
    ResourceLocation locTex = (modelBase.locationTextureCustom != null) ? modelBase.locationTextureCustom : new ResourceLocation("textures/entity/breeze/breeze_wind.png");
    BreezeWindLayer layer = new BreezeWindLayer(renderManager.getContext(), (RenderLayerParent)renderBreeze);
    layer.setModel((BreezeModel)modelBase);
    layer.setTextureLocation(locTex);
    renderBreeze.removeLayers(BreezeWindLayer.class);
    renderBreeze.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderBreeze;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    return true;
  }
}
