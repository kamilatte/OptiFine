package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ArrowModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterArrow;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSpectralArrow extends ModelAdapterArrow {
  public ModelAdapterSpectralArrow() {
    super(EntityType.SPECTRAL_ARROW, "spectral_arrow", 0.0F);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SpectralArrowRenderer render = new SpectralArrowRenderer(renderManager.getContext());
    render.model = (ArrowModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
