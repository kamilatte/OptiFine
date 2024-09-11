package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterPhantom extends ModelAdapter {
  private static Map<String, String> mapPartFields = null;
  
  public ModelAdapterPhantom() {
    super(EntityType.PHANTOM, "phantom", 0.75F);
  }
  
  public Model makeModel() {
    return (Model)new PhantomModel(bakeModelLayer(ModelLayers.PHANTOM));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof PhantomModel))
      return null; 
    PhantomModel modelPhantom = (PhantomModel)model;
    if (modelPart.equals("root"))
      return modelPhantom.root(); 
    Map<String, String> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelPhantom.root().getChildModelDeep(name);
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
    mapPartFields.put("body", "body");
    mapPartFields.put("head", "head");
    mapPartFields.put("left_wing", "left_wing_base");
    mapPartFields.put("left_wing_tip", "left_wing_tip");
    mapPartFields.put("right_wing", "right_wing_base");
    mapPartFields.put("right_wing_tip", "right_wing_tip");
    mapPartFields.put("tail", "tail_base");
    mapPartFields.put("tail2", "tail_tip");
    mapPartFields.put("root", "root");
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    PhantomRenderer render = new PhantomRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
