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
import net.minecraft.client.renderer.entity.layers.BreezeEyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBreeze;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBreezeEyes extends ModelAdapterBreeze {
  public ModelAdapterBreezeEyes() {
    super(EntityType.BREEZE, "breeze_eyes", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new BreezeModel(bakeModelLayer(ModelLayers.BREEZE));
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
    ResourceLocation locTex = (modelBase.locationTextureCustom != null) ? modelBase.locationTextureCustom : new ResourceLocation("textures/entity/breeze/breeze.png");
    BreezeEyesLayer layer = new BreezeEyesLayer((RenderLayerParent)renderBreeze);
    layer.setModel((BreezeModel)modelBase);
    layer.setTextureLocation(locTex);
    renderBreeze.removeLayers(BreezeEyesLayer.class);
    renderBreeze.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderBreeze;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    return true;
  }
}
