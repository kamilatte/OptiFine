package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
  public ModelAdapterCatCollar() {
    super(EntityType.CAT, "cat_collar", 0.4F);
  }
  
  public Model makeModel() {
    return (Model)new CatModel(bakeModelLayer(ModelLayers.CAT_COLLAR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    CatRenderer customRenderer = new CatRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new CatModel(bakeModelLayer(ModelLayers.CAT_COLLAR));
    customRenderer.shadowRadius = 0.4F;
    EntityRenderer render = rendererCache.get(EntityType.CAT, index, () -> customRenderer);
    if (!(render instanceof CatRenderer)) {
      Config.warn("Not a RenderCat: " + String.valueOf(render));
      return null;
    } 
    CatRenderer renderCat = (CatRenderer)render;
    CatCollarLayer layer = new CatCollarLayer((RenderLayerParent)renderCat, renderManager.getContext().getModelSet());
    layer.catModel = (CatModel)modelBase;
    renderCat.removeLayers(CatCollarLayer.class);
    renderCat.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderCat;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    CatRenderer renderCat = (CatRenderer)er;
    List<CatCollarLayer> layers = renderCat.getLayers(CatCollarLayer.class);
    for (CatCollarLayer layer : layers)
      layer.catModel.locationTextureCustom = textureLocation; 
    return true;
  }
}
