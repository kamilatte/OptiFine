package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.HoglinRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHoglin extends ModelAdapter {
  private static Map<String, Integer> mapParts = makeMapParts();
  
  public ModelAdapterHoglin() {
    super(EntityType.HOGLIN, "hoglin", 0.7F);
  }
  
  public ModelAdapterHoglin(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new HoglinModel(bakeModelLayer(ModelLayers.HOGLIN));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof HoglinModel))
      return null; 
    HoglinModel modelBoar = (HoglinModel)model;
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelBoar, Reflector.ModelBoar_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, Integer> makeMapParts() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("head", Integer.valueOf(0));
    map.put("right_ear", Integer.valueOf(1));
    map.put("left_ear", Integer.valueOf(2));
    map.put("body", Integer.valueOf(3));
    map.put("front_right_leg", Integer.valueOf(4));
    map.put("front_left_leg", Integer.valueOf(5));
    map.put("back_right_leg", Integer.valueOf(6));
    map.put("back_left_leg", Integer.valueOf(7));
    map.put("mane", Integer.valueOf(8));
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    HoglinRenderer render = new HoglinRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
