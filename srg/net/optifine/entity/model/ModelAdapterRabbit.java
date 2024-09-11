package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterRabbit extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterRabbit() {
    super(EntityType.RABBIT, "rabbit", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new RabbitModel(bakeModelLayer(ModelLayers.RABBIT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof RabbitModel))
      return null; 
    RabbitModel modelRabbit = (RabbitModel)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelRabbit, Reflector.ModelRabbit_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", 
        "tail", "nose" };
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("left_foot", Integer.valueOf(0));
    mapPartFields.put("right_foot", Integer.valueOf(1));
    mapPartFields.put("left_thigh", Integer.valueOf(2));
    mapPartFields.put("right_thigh", Integer.valueOf(3));
    mapPartFields.put("body", Integer.valueOf(4));
    mapPartFields.put("left_arm", Integer.valueOf(5));
    mapPartFields.put("right_arm", Integer.valueOf(6));
    mapPartFields.put("head", Integer.valueOf(7));
    mapPartFields.put("right_ear", Integer.valueOf(8));
    mapPartFields.put("left_ear", Integer.valueOf(9));
    mapPartFields.put("tail", Integer.valueOf(10));
    mapPartFields.put("nose", Integer.valueOf(11));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    RabbitRenderer render = new RabbitRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
