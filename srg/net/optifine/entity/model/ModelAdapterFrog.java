package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterFrog extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterFrog() {
    super(EntityType.FROG, "frog", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new FrogModel(bakeModelLayer(ModelLayers.FROG));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof FrogModel))
      return null; 
    FrogModel modelFrog = (FrogModel)model;
    if (modelPart.equals("root"))
      return modelFrog.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelFrog.root().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("body", "body");
    map.put("head", "head");
    map.put("eyes", "eyes");
    map.put("tongue", "tongue");
    map.put("left_arm", "left_arm");
    map.put("right_arm", "right_arm");
    map.put("left_leg", "left_leg");
    map.put("right_leg", "right_leg");
    map.put("croaking_body", "croaking_body");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    FrogRenderer render = new FrogRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
