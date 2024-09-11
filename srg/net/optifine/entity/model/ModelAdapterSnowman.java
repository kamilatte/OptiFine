package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSnowman extends ModelAdapter {
  public ModelAdapterSnowman() {
    super(EntityType.SNOW_GOLEM, "snow_golem", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new SnowGolemModel(bakeModelLayer(ModelLayers.SNOW_GOLEM));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SnowGolemModel))
      return null; 
    SnowGolemModel modelSnowman = (SnowGolemModel)model;
    if (modelPart.equals("body"))
      return modelSnowman.root().getChildModelDeep("upper_body"); 
    if (modelPart.equals("body_bottom"))
      return modelSnowman.root().getChildModelDeep("lower_body"); 
    if (modelPart.equals("head"))
      return modelSnowman.root().getChildModelDeep("head"); 
    if (modelPart.equals("right_hand"))
      return modelSnowman.root().getChildModelDeep("right_arm"); 
    if (modelPart.equals("left_hand"))
      return modelSnowman.root().getChildModelDeep("left_arm"); 
    if (modelPart.equals("root"))
      return modelSnowman.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "body_bottom", "head", "right_hand", "left_hand", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SnowGolemRenderer render = new SnowGolemRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
