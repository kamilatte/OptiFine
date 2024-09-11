package srg.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterRaft;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestRaft extends ModelAdapterRaft {
  public ModelAdapterChestRaft() {
    super(EntityType.CHEST_BOAT, "chest_raft", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new ChestRaftModel(bakeModelLayer(ModelLayers.createChestBoatModelName(Boat.Type.BAMBOO)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChestRaftModel))
      return null; 
    ChestRaftModel modelChestRaft = (ChestRaftModel)model;
    ImmutableList<ModelPart> parts = modelChestRaft.parts();
    if (parts != null) {
      if (modelPart.equals("chest_base"))
        return ModelRendererUtils.getModelRenderer(parts, 3); 
      if (modelPart.equals("chest_lid"))
        return ModelRendererUtils.getModelRenderer(parts, 4); 
      if (modelPart.equals("chest_knob"))
        return ModelRendererUtils.getModelRenderer(parts, 5); 
    } 
    return super.getModelRenderer((Model)modelChestRaft, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])ArrayUtils.addObjectsToArray((Object[])names, (Object[])new String[] { "chest_base", "chest_lid", "chest_knob" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BoatRenderer customRenderer = new BoatRenderer(renderManager.getContext(), true);
    EntityRenderer rendererCached = rendererCache.get(EntityType.CHEST_BOAT, index, () -> customRenderer);
    if (!(rendererCached instanceof BoatRenderer)) {
      Config.warn("Not a BoatRender: " + String.valueOf(rendererCached));
      return null;
    } 
    BoatRenderer renderer = (BoatRenderer)rendererCached;
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
