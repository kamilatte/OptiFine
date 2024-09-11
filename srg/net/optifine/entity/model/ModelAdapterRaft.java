package srg.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterRaft extends ModelAdapter {
  public ModelAdapterRaft() {
    super(EntityType.BOAT, "raft", 0.5F);
  }
  
  protected ModelAdapterRaft(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new RaftModel(bakeModelLayer(ModelLayers.createBoatModelName(Boat.Type.BAMBOO)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof RaftModel))
      return null; 
    RaftModel modelRaft = (RaftModel)model;
    ImmutableList<ModelPart> parts = modelRaft.parts();
    if (parts != null) {
      if (modelPart.equals("bottom"))
        return ModelRendererUtils.getModelRenderer(parts, 0); 
      if (modelPart.equals("paddle_left"))
        return ModelRendererUtils.getModelRenderer(parts, 1); 
      if (modelPart.equals("paddle_right"))
        return ModelRendererUtils.getModelRenderer(parts, 2); 
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "paddle_left", "paddle_right" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BoatRenderer customRenderer = new BoatRenderer(renderManager.getContext(), false);
    EntityRenderer rendererCached = rendererCache.get(EntityType.BOAT, index, () -> customRenderer);
    if (!(rendererCached instanceof BoatRenderer)) {
      Config.warn("Not a BoatRender: " + String.valueOf(rendererCached));
      return null;
    } 
    BoatRenderer renderer = (BoatRenderer)rendererCached;
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
