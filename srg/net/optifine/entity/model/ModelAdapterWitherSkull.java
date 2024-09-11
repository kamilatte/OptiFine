package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull extends ModelAdapter {
  public ModelAdapterWitherSkull() {
    super(EntityType.WITHER_SKULL, "wither_skull", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new SkullModel(bakeModelLayer(ModelLayers.WITHER_SKULL));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SkullModel))
      return null; 
    SkullModel modelSkeletonHead = (SkullModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 1); 
    if (modelPart.equals("root"))
      return (ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WitherSkullRenderer render = new WitherSkullRenderer(renderManager.getContext());
    if (!Reflector.RenderWitherSkull_model.exists()) {
      Config.warn("Field not found: RenderWitherSkull_model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderWitherSkull_model, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
