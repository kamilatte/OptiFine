package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.GiantZombieModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGiant extends ModelAdapterZombie {
  public ModelAdapterGiant() {
    super(EntityType.GIANT, "giant", 3.0F);
  }
  
  public Model makeModel() {
    return (Model)new GiantZombieModel(bakeModelLayer(ModelLayers.GIANT));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    GiantMobRenderer render = new GiantMobRenderer(renderManager.getContext(), 6.0F);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
