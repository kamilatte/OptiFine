package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart extends ModelAdapter {
  public ModelAdapterMinecart() {
    super(EntityType.MINECART, "minecart", 0.5F);
  }
  
  protected ModelAdapterMinecart(EntityType type, String name, float shadow) {
    super(type, name, shadow);
  }
  
  public Model makeModel() {
    return (Model)new MinecartModel(bakeModelLayer(ModelLayers.MINECART));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof MinecartModel))
      return null; 
    MinecartModel modelMinecart = (MinecartModel)model;
    if (modelPart.equals("bottom"))
      return modelMinecart.root().getChildModelDeep("bottom"); 
    if (modelPart.equals("back"))
      return modelMinecart.root().getChildModelDeep("back"); 
    if (modelPart.equals("front"))
      return modelMinecart.root().getChildModelDeep("front"); 
    if (modelPart.equals("right"))
      return modelMinecart.root().getChildModelDeep("right"); 
    if (modelPart.equals("left"))
      return modelMinecart.root().getChildModelDeep("left"); 
    if (modelPart.equals("root"))
      return modelMinecart.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "back", "front", "right", "left", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    MinecartRenderer render = new MinecartRenderer(renderManager.getContext(), ModelLayers.MINECART);
    if (!Reflector.RenderMinecart_modelMinecart.exists()) {
      Config.warn("Field not found: RenderMinecart.modelMinecart");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
