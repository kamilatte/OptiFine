package srg.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestBoat extends ModelAdapterBoat {
  public ModelAdapterChestBoat() {
    super(EntityType.CHEST_BOAT, "chest_boat", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new ChestBoatModel(bakeModelLayer(ModelLayers.createChestBoatModelName(Boat.Type.OAK)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChestBoatModel))
      return null; 
    ChestBoatModel modelChestBoat = (ChestBoatModel)model;
    ImmutableList<ModelPart> parts = modelChestBoat.parts();
    if (parts != null) {
      if (modelPart.equals("chest_base"))
        return ModelRendererUtils.getModelRenderer(parts, 7); 
      if (modelPart.equals("chest_lid"))
        return ModelRendererUtils.getModelRenderer(parts, 8); 
      if (modelPart.equals("chest_knob"))
        return ModelRendererUtils.getModelRenderer(parts, 9); 
    } 
    return super.getModelRenderer((Model)modelChestBoat, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])ArrayUtils.addObjectsToArray((Object[])names, (Object[])new String[] { "chest_base", "chest_lid", "chest_knob" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BoatRenderer renderer = new BoatRenderer(renderManager.getContext(), true);
    rendererCache.put(EntityType.CHEST_BOAT, index, (EntityRenderer)renderer);
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
