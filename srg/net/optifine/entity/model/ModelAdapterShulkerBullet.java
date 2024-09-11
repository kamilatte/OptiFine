package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter {
  public ModelAdapterShulkerBullet() {
    super(EntityType.SHULKER_BULLET, "shulker_bullet", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ShulkerBulletModel(bakeModelLayer(ModelLayers.SHULKER_BULLET));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ShulkerBulletModel))
      return null; 
    ShulkerBulletModel modelShulkerBullet = (ShulkerBulletModel)model;
    if (modelPart.equals("bullet"))
      return modelShulkerBullet.root().getChildModelDeep("main"); 
    if (modelPart.equals("root"))
      return modelShulkerBullet.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bullet", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    ShulkerBulletRenderer render = new ShulkerBulletRenderer(renderManager.getContext());
    if (!Reflector.RenderShulkerBullet_model.exists()) {
      Config.warn("Field not found: RenderShulkerBullet.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderShulkerBullet_model, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
