package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWarden extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterWarden() {
    super(EntityType.WARDEN, "warden", 0.9F);
  }
  
  public Model makeModel() {
    return (Model)new WardenModel(bakeModelLayer(ModelLayers.WARDEN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof WardenModel))
      return null; 
    WardenModel modelWarden = (WardenModel)model;
    if (modelPart.equals("root"))
      return modelWarden.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelWarden.root().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("body", "bone");
    map.put("torso", "body");
    map.put("head", "head");
    map.put("right_leg", "right_leg");
    map.put("left_leg", "left_leg");
    map.put("right_arm", "right_arm");
    map.put("left_arm", "left_arm");
    map.put("right_tendril", "right_tendril");
    map.put("left_tendril", "left_tendril");
    map.put("right_ribcage", "right_ribcage");
    map.put("left_ribcage", "left_ribcage");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WardenRenderer render = new WardenRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
