package srg.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBoat extends ModelAdapter {
  public ModelAdapterBoat() {
    super(EntityType.BOAT, "boat", 0.5F);
  }
  
  protected ModelAdapterBoat(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new BoatModel(bakeModelLayer(ModelLayers.createBoatModelName(Boat.Type.OAK)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BoatModel))
      return null; 
    BoatModel modelBoat = (BoatModel)model;
    ImmutableList<ModelPart> parts = modelBoat.parts();
    if (parts != null) {
      if (modelPart.equals("bottom"))
        return ModelRendererUtils.getModelRenderer(parts, 0); 
      if (modelPart.equals("back"))
        return ModelRendererUtils.getModelRenderer(parts, 1); 
      if (modelPart.equals("front"))
        return ModelRendererUtils.getModelRenderer(parts, 2); 
      if (modelPart.equals("right"))
        return ModelRendererUtils.getModelRenderer(parts, 3); 
      if (modelPart.equals("left"))
        return ModelRendererUtils.getModelRenderer(parts, 4); 
      if (modelPart.equals("paddle_left"))
        return ModelRendererUtils.getModelRenderer(parts, 5); 
      if (modelPart.equals("paddle_right"))
        return ModelRendererUtils.getModelRenderer(parts, 6); 
    } 
    if (modelPart.equals("bottom_no_water"))
      return modelBoat.waterPatch(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "back", "front", "right", "left", "paddle_left", "paddle_right", "bottom_no_water" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BoatRenderer renderer = new BoatRenderer(renderManager.getContext(), false);
    rendererCache.put(EntityType.BOAT, index, (EntityRenderer)renderer);
    return makeEntityRender(modelBase, shadowSize, renderer);
  }
  
  protected static IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, BoatRenderer render) {
    if (!Reflector.RenderBoat_boatResources.exists()) {
      Config.warn("Field not found: RenderBoat.boatResources");
      return null;
    } 
    Map<Boat.Type, Pair<ResourceLocation, Model>> resources = (Map<Boat.Type, Pair<ResourceLocation, Model>>)Reflector.RenderBoat_boatResources.getValue(render);
    if (resources instanceof com.google.common.collect.ImmutableMap) {
      resources = new HashMap<>(resources);
      Reflector.RenderBoat_boatResources.setValue(render, resources);
    } 
    Collection<Boat.Type> types = new HashSet<>(resources.keySet());
    for (Boat.Type type : types) {
      Pair<ResourceLocation, Model> pair = resources.get(type);
      if (modelBase.getClass() != ((Model)pair.getSecond()).getClass())
        continue; 
      pair = Pair.of(pair.getFirst(), modelBase);
      resources.put(type, pair);
    } 
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
