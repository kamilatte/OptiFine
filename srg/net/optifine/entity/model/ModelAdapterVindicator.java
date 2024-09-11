package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterVindicator extends ModelAdapterIllager {
  public ModelAdapterVindicator() {
    super(EntityType.VINDICATOR, "vindicator", 0.5F, new String[] { "vindication_illager" });
  }
  
  public Model makeModel() {
    return (Model)new IllagerModel(bakeModelLayer(ModelLayers.VINDICATOR));
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    VindicatorRenderer render = new VindicatorRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
