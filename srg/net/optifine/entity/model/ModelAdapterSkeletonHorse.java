package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSkeletonHorse extends ModelAdapterHorse {
  public ModelAdapterSkeletonHorse() {
    super(EntityType.SKELETON_HORSE, "skeleton_horse", 0.75F);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    UndeadHorseRenderer render = new UndeadHorseRenderer(renderManager.getContext(), ModelLayers.SKELETON_HORSE);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
