package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SnifferRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSniffer extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterSniffer() {
    super(EntityType.SNIFFER, "sniffer", 1.1F);
  }
  
  public Model makeModel() {
    return (Model)new SnifferModel(bakeModelLayer(ModelLayers.SNIFFER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SnifferModel))
      return null; 
    SnifferModel modelSniffer = (SnifferModel)model;
    if (modelPart.equals("root"))
      return modelSniffer.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelSniffer.root().getChildModelDeep(name);
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
    map.put("back_left_leg", "left_hind_leg");
    map.put("back_right_leg", "right_hind_leg");
    map.put("middle_left_leg", "left_mid_leg");
    map.put("middle_right_leg", "right_mid_leg");
    map.put("front_left_leg", "left_front_leg");
    map.put("front_right_leg", "right_front_leg");
    map.put("head", "head");
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    map.put("nose", "nose");
    map.put("lower_beak", "lower_beak");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SnifferRenderer render = new SnifferRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
