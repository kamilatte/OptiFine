package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPiglin;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPiglinBrute extends ModelAdapterPiglin {
  public ModelAdapterPiglinBrute() {
    super(EntityType.PIGLIN_BRUTE, "piglin_brute", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new PiglinModel(bakeModelLayer(ModelLayers.PIGLIN_BRUTE));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PiglinRenderer render = new PiglinRenderer(renderManager.getContext(), ModelLayers.PIGLIN_BRUTE, ModelLayers.PIGLIN_BRUTE_INNER_ARMOR, ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR, false);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
