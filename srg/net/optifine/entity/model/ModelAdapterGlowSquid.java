package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGlowSquid extends ModelAdapterSquid {
  public ModelAdapterGlowSquid() {
    super(EntityType.GLOW_SQUID, "glow_squid", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new SquidModel(bakeModelLayer(ModelLayers.GLOW_SQUID));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    GlowSquidRenderer render = new GlowSquidRenderer(renderManager.getContext(), (SquidModel)modelBase);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
