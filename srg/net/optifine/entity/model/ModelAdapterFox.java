package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterFox() {
    super(EntityType.FOX, "fox", 0.4F);
  }
  
  public Model makeModel() {
    return (Model)new FoxModel(bakeModelLayer(ModelLayers.FOX));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof FoxModel))
      return null; 
    FoxModel modelFox = (FoxModel)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelFox, Reflector.ModelFox_ModelRenderers, index);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return (String[])getMapPartFields().keySet().toArray((Object[])new String[0]);
  }
  
  private static Map<String, Integer> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("head", Integer.valueOf(0));
    mapPartFields.put("body", Integer.valueOf(1));
    mapPartFields.put("leg1", Integer.valueOf(2));
    mapPartFields.put("leg2", Integer.valueOf(3));
    mapPartFields.put("leg3", Integer.valueOf(4));
    mapPartFields.put("leg4", Integer.valueOf(5));
    mapPartFields.put("tail", Integer.valueOf(6));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    FoxRenderer render = new FoxRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
