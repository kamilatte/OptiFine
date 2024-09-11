package srg.net.optifine.entity.model;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSlimeOuter extends ModelAdapter {
  public ModelAdapterSlimeOuter() {
    super(EntityType.SLIME, "slime_outer", 0.25F);
  }
  
  public Model makeModel() {
    return (Model)new SlimeModel(bakeModelLayer(ModelLayers.SLIME_OUTER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SlimeModel))
      return null; 
    SlimeModel modelSlime = (SlimeModel)model;
    if (modelPart.equals("body"))
      return modelSlime.root().getChildModelDeep("cube"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SlimeRenderer customRenderer = new SlimeRenderer(renderManager.getContext());
    customRenderer.model = (EntityModel)new SlimeModel(bakeModelLayer(ModelLayers.SLIME_OUTER));
    customRenderer.shadowRadius = 0.25F;
    EntityRenderer render = rendererCache.get(EntityType.SLIME, index, () -> customRenderer);
    if (!(render instanceof SlimeRenderer)) {
      Config.warn("Not a SlimeRenderer: " + String.valueOf(render));
      return null;
    } 
    SlimeRenderer renderSlime = (SlimeRenderer)render;
    SlimeOuterLayer layer = new SlimeOuterLayer((RenderLayerParent)renderSlime, renderManager.getContext().getModelSet());
    layer.model = (EntityModel)modelBase;
    renderSlime.removeLayers(SlimeOuterLayer.class);
    renderSlime.addLayer((RenderLayer)layer);
    return (IEntityRenderer)renderSlime;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
    SlimeRenderer renderer = (SlimeRenderer)er;
    List<SlimeOuterLayer> layers = renderer.getLayers(SlimeOuterLayer.class);
    for (SlimeOuterLayer layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
