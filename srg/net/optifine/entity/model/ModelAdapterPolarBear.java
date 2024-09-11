package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPolarBear extends ModelAdapterQuadruped {
  public ModelAdapterPolarBear() {
    super(EntityType.POLAR_BEAR, "polar_bear", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new PolarBearModel(bakeModelLayer(ModelLayers.POLAR_BEAR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PolarBearRenderer render = new PolarBearRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
