package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHoglin;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterZoglin extends ModelAdapterHoglin {
  public ModelAdapterZoglin() {
    super(EntityType.ZOGLIN, "zoglin", 0.7F);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ZoglinRenderer render = new ZoglinRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
