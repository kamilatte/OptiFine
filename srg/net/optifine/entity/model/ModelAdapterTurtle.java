package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTurtle extends ModelAdapterQuadruped {
  public ModelAdapterTurtle() {
    super(EntityType.TURTLE, "turtle", 0.7F);
  }
  
  public Model makeModel() {
    return (Model)new TurtleModel(bakeModelLayer(ModelLayers.TURTLE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof net.minecraft.client.model.QuadrupedModel))
      return null; 
    TurtleModel modelQuadruped = (TurtleModel)model;
    if (modelPart.equals("body2"))
      return (ModelPart)Reflector.ModelTurtle_body2.getValue(modelQuadruped); 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectToArray((Object[])names, "body2");
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TurtleRenderer render = new TurtleRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
