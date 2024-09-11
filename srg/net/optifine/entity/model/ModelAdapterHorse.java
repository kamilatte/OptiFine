package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse extends ModelAdapter {
  private static Map<String, Integer> mapParts = makeMapParts();
  
  private static Map<String, String> mapPartsNeck = makeMapPartsNeck();
  
  private static Map<String, String> mapPartsHead = makeMapPartsHead();
  
  private static Map<String, String> mapPartsBody = makeMapPartsBody();
  
  public ModelAdapterHorse() {
    super(EntityType.HORSE, "horse", 0.75F);
  }
  
  protected ModelAdapterHorse(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new HorseModel(bakeModelLayer(ModelLayers.HORSE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof HorseModel))
      return null; 
    HorseModel modelHorse = (HorseModel)model;
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelHorse, Reflector.ModelHorse_ModelRenderers, index);
    } 
    if (mapPartsNeck.containsKey(modelPart)) {
      ModelPart modelNeck = getModelRenderer((Model)modelHorse, "neck");
      String name = mapPartsNeck.get(modelPart);
      return modelNeck.getChild(name);
    } 
    if (mapPartsHead.containsKey(modelPart)) {
      ModelPart modelHead = getModelRenderer((Model)modelHorse, "head");
      String name = mapPartsHead.get(modelPart);
      return modelHead.getChild(name);
    } 
    if (mapPartsBody.containsKey(modelPart)) {
      ModelPart modelBody = getModelRenderer((Model)modelHorse, "body");
      String name = mapPartsBody.get(modelPart);
      return modelBody.getChild(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "neck", "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "child_back_left_leg", "child_back_right_leg", "child_front_left_leg", "child_front_right_leg", 
        "tail", "saddle", "head", "mane", "mouth", "left_ear", "right_ear", "left_bit", "right_bit", "left_rein", 
        "right_rein", "headpiece", "noseband" };
  }
  
  private static Map<String, Integer> makeMapParts() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("body", Integer.valueOf(0));
    map.put("neck", Integer.valueOf(1));
    map.put("back_right_leg", Integer.valueOf(2));
    map.put("back_left_leg", Integer.valueOf(3));
    map.put("front_right_leg", Integer.valueOf(4));
    map.put("front_left_leg", Integer.valueOf(5));
    map.put("child_back_right_leg", Integer.valueOf(6));
    map.put("child_back_left_leg", Integer.valueOf(7));
    map.put("child_front_right_leg", Integer.valueOf(8));
    map.put("child_front_left_leg", Integer.valueOf(9));
    return map;
  }
  
  private static Map<String, String> makeMapPartsNeck() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("head", "head");
    map.put("mane", "mane");
    map.put("mouth", "upper_mouth");
    map.put("left_bit", "left_saddle_mouth");
    map.put("right_bit", "right_saddle_mouth");
    map.put("left_rein", "left_saddle_line");
    map.put("right_rein", "right_saddle_line");
    map.put("headpiece", "head_saddle");
    map.put("noseband", "mouth_saddle_wrap");
    return map;
  }
  
  private static Map<String, String> makeMapPartsBody() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("tail", "tail");
    map.put("saddle", "saddle");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    HorseRenderer render = new HorseRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
  
  private static Map<String, String> makeMapPartsHead() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("left_ear", "left_ear");
    map.put("right_ear", "right_ear");
    return map;
  }
}
