package srg.net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterStrider extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterStrider() {
    super(EntityType.STRIDER, "strider", 0.5F);
  }
  
  protected ModelAdapterStrider(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new StriderModel(bakeModelLayer(ModelLayers.STRIDER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof StriderModel))
      return null; 
    StriderModel modelStrider = (StriderModel)model;
    if (modelPart.equals("root"))
      return modelStrider.root(); 
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelStrider.root().getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("right_leg", "right_leg");
    map.put("left_leg", "left_leg");
    map.put("body", "body");
    map.put("hair_right_bottom", "right_bottom_bristle");
    map.put("hair_right_middle", "right_middle_bristle");
    map.put("hair_right_top", "right_top_bristle");
    map.put("hair_left_top", "left_top_bristle");
    map.put("hair_left_middle", "left_middle_bristle");
    map.put("hair_left_bottom", "left_bottom_bristle");
    map.put("root", "root");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    StriderRenderer render = new StriderRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
