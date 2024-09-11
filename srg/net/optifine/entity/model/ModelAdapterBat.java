package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBat extends ModelAdapter {
  public ModelAdapterBat() {
    super(EntityType.BAT, "bat", 0.25F);
  }
  
  public Model makeModel() {
    return (Model)new BatModel(bakeModelLayer(ModelLayers.BAT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BatModel))
      return null; 
    BatModel modelBat = (BatModel)model;
    if (modelPart.equals("head"))
      return modelBat.root().getChildModelDeep("head"); 
    if (modelPart.equals("body"))
      return modelBat.root().getChildModelDeep("body"); 
    if (modelPart.equals("right_wing"))
      return modelBat.root().getChildModelDeep("right_wing"); 
    if (modelPart.equals("left_wing"))
      return modelBat.root().getChildModelDeep("left_wing"); 
    if (modelPart.equals("outer_right_wing"))
      return modelBat.root().getChildModelDeep("right_wing_tip"); 
    if (modelPart.equals("outer_left_wing"))
      return modelBat.root().getChildModelDeep("left_wing_tip"); 
    if (modelPart.equals("feet"))
      return modelBat.root().getChildModelDeep("feet"); 
    if (modelPart.equals("root"))
      return modelBat.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing", "feet", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BatRenderer render = new BatRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
