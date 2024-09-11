package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TadpoleRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterTadpole extends ModelAdapter {
  public ModelAdapterTadpole() {
    super(EntityType.TADPOLE, "tadpole", 0.14F);
  }
  
  public Model makeModel() {
    return (Model)new TadpoleModel(bakeModelLayer(ModelLayers.TADPOLE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof TadpoleModel))
      return null; 
    TadpoleModel modelTadpole = (TadpoleModel)model;
    if (modelPart.equals("body"))
      return (ModelPart)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 0); 
    if (modelPart.equals("tail"))
      return (ModelPart)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 1); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = { "body", "tail" };
    return names;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TadpoleRenderer render = new TadpoleRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
