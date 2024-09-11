package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDrowned extends ModelAdapterZombie {
  public ModelAdapterDrowned() {
    super(EntityType.DROWNED, "drowned", 0.5F);
  }
  
  public ModelAdapterDrowned(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new DrownedModel(bakeModelLayer(ModelLayers.DROWNED));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    DrownedRenderer render = new DrownedRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
