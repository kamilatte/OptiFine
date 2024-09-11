package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterOcelot() {
    super(EntityType.OCELOT, "ocelot", 0.4F);
  }
  
  protected ModelAdapterOcelot(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new OcelotModel(bakeModelLayer(ModelLayers.OCELOT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof OcelotModel))
      return null; 
    OcelotModel modelOcelot = (OcelotModel)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelOcelot, Reflector.ModelOcelot_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body" };
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("back_left_leg", Integer.valueOf(0));
    mapPartFields.put("back_right_leg", Integer.valueOf(1));
    mapPartFields.put("front_left_leg", Integer.valueOf(2));
    mapPartFields.put("front_right_leg", Integer.valueOf(3));
    mapPartFields.put("tail", Integer.valueOf(4));
    mapPartFields.put("tail2", Integer.valueOf(5));
    mapPartFields.put("head", Integer.valueOf(6));
    mapPartFields.put("body", Integer.valueOf(7));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    OcelotRenderer render = new OcelotRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
