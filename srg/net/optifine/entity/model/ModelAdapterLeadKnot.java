package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot extends ModelAdapter {
  public ModelAdapterLeadKnot() {
    super(EntityType.LEASH_KNOT, "lead_knot", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new LeashKnotModel(bakeModelLayer(ModelLayers.LEASH_KNOT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof LeashKnotModel))
      return null; 
    LeashKnotModel modelLeashKnot = (LeashKnotModel)model;
    if (modelPart.equals("knot"))
      return modelLeashKnot.root().getChildModelDeep("knot"); 
    if (modelPart.equals("root"))
      return modelLeashKnot.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "knot", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    LeashKnotRenderer render = new LeashKnotRenderer(renderManager.getContext());
    if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
      Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
