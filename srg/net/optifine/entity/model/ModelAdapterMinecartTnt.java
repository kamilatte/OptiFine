package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecartTnt extends ModelAdapterMinecart {
  public ModelAdapterMinecartTnt() {
    super(EntityType.TNT_MINECART, "tnt_minecart", 0.5F);
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TntMinecartRenderer render = new TntMinecartRenderer(renderManager.getContext());
    if (!Reflector.RenderMinecart_modelMinecart.exists()) {
      Config.warn("Field not found: RenderMinecart.modelMinecart");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
