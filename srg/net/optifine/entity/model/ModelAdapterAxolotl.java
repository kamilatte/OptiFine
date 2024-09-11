package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterAxolotl extends ModelAdapter {
  private static Map<String, Integer> mapPartFields = null;
  
  public ModelAdapterAxolotl() {
    super(EntityType.AXOLOTL, "axolotl", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new AxolotlModel(bakeModelLayer(ModelLayers.AXOLOTL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof AxolotlModel))
      return null; 
    AxolotlModel modelAxolotl = (AxolotlModel)model;
    Map<String, Integer> mapParts = getMapPartFields();
    if (mapParts.containsKey(modelPart)) {
      int index = ((Integer)mapParts.get(modelPart)).intValue();
      return (ModelPart)Reflector.getFieldValue(modelAxolotl, Reflector.ModelAxolotl_ModelRenderers, index);
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
    mapPartFields.put("tail", Integer.valueOf(0));
    mapPartFields.put("leg2", Integer.valueOf(1));
    mapPartFields.put("leg1", Integer.valueOf(2));
    mapPartFields.put("leg4", Integer.valueOf(3));
    mapPartFields.put("leg3", Integer.valueOf(4));
    mapPartFields.put("body", Integer.valueOf(5));
    mapPartFields.put("head", Integer.valueOf(6));
    mapPartFields.put("top_gills", Integer.valueOf(7));
    mapPartFields.put("left_gills", Integer.valueOf(8));
    mapPartFields.put("right_gills", Integer.valueOf(9));
    return mapPartFields;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    AxolotlRenderer render = new AxolotlRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
