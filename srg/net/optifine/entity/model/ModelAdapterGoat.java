package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterGoat extends ModelAdapterQuadruped {
  public ModelAdapterGoat() {
    super(EntityType.GOAT, "goat", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new GoatModel(bakeModelLayer(ModelLayers.GOAT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof GoatModel))
      return null; 
    GoatModel modelGoat = (GoatModel)model;
    ModelPart head = super.getModelRenderer((Model)modelGoat, "head");
    if (head != null) {
      if (modelPart.equals("left_horn"))
        return head.getChild(modelPart); 
      if (modelPart.equals("right_horn"))
        return head.getChild(modelPart); 
      if (modelPart.equals("nose"))
        return head.getChild(modelPart); 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectsToArray((Object[])names, (Object[])new String[] { "left_horn", "right_horn", "nose" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    GoatRenderer render = new GoatRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
