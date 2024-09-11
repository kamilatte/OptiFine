package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.CamelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCamel extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterCamel() {
    super(EntityType.CAMEL, "camel", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new CamelModel(bakeModelLayer(ModelLayers.CAMEL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof CamelModel))
      return null; 
    CamelModel modelCamel = (CamelModel)model;
    if (modelPart.equals("root"))
      return modelCamel.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelCamel.root().getChildModelDeep(name);
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
    map.put("hump", "hump");
    map.put("tail", "tail");
    map.put("head", "head");
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    map.put("back_left_leg", "left_hind_leg");
    map.put("back_right_leg", "right_hind_leg");
    map.put("front_left_leg", "left_front_leg");
    map.put("front_right_leg", "right_front_leg");
    map.put("saddle", "saddle");
    map.put("reins", "reins");
    map.put("bridle", "bridle");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    CamelRenderer render = new CamelRenderer(renderManager.getContext(), ModelLayers.CAMEL);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
