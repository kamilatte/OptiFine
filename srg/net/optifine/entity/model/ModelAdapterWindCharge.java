package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WindChargeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WindChargeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWindCharge extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterWindCharge() {
    super(EntityType.WIND_CHARGE, "wind_charge", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new WindChargeModel(bakeModelLayer(ModelLayers.WIND_CHARGE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof WindChargeModel))
      return null; 
    WindChargeModel modelWindCharge = (WindChargeModel)model;
    if (modelPart.equals("root"))
      return modelWindCharge.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelWindCharge.root().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("core", "projectile");
    map.put("wind", "wind");
    map.put("cube1", "cube_r1");
    map.put("cube2", "cube_r2");
    map.put("charge", "wind_charge");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WindChargeRenderer render = new WindChargeRenderer(renderManager.getContext());
    if (!Reflector.RenderWindCharge_model.exists()) {
      Config.warn("Field not found: RenderWindCharge.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderWindCharge_model, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
