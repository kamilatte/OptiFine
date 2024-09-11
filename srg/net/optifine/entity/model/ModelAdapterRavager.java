package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.RavagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterRavager extends ModelAdapter {
  private static Map<String, String> mapPartFields = null;
  
  public ModelAdapterRavager() {
    super(EntityType.RAVAGER, "ravager", 1.1F);
  }
  
  public Model makeModel() {
    return (Model)new RavagerModel(bakeModelLayer(ModelLayers.RAVAGER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof RavagerModel))
      return null; 
    RavagerModel modelRavager = (RavagerModel)model;
    if (modelPart.equals("root"))
      return modelRavager.root(); 
    Map<String, String> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelRavager.root().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return (String[])getMapPartFields().keySet().toArray((Object[])new String[0]);
  }
  
  private static Map<String, String> getMapPartFields() {
    if (mapPartFields != null)
      return mapPartFields; 
    mapPartFields = new LinkedHashMap<>();
    mapPartFields.put("head", "head");
    mapPartFields.put("jaw", "mouth");
    mapPartFields.put("body", "body");
    mapPartFields.put("leg1", "right_hind_leg");
    mapPartFields.put("leg2", "left_hind_leg");
    mapPartFields.put("leg3", "right_front_leg");
    mapPartFields.put("leg4", "left_front_leg");
    mapPartFields.put("neck", "neck");
    mapPartFields.put("root", "root");
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    RavagerRenderer render = new RavagerRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
