package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterWolf extends ModelAdapter {
  public ModelAdapterWolf() {
    super(EntityType.WOLF, "wolf", 0.5F);
  }
  
  protected ModelAdapterWolf(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new WolfModel(bakeModelLayer(ModelLayers.WOLF));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof WolfModel))
      return null; 
    WolfModel modelWolf = (WolfModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 0); 
    if (modelPart.equals("body"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 2); 
    if (modelPart.equals("leg1"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 3); 
    if (modelPart.equals("leg2"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 4); 
    if (modelPart.equals("leg3"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 5); 
    if (modelPart.equals("leg4"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 6); 
    if (modelPart.equals("tail"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 7); 
    if (modelPart.equals("mane"))
      return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 9); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WolfRenderer render = new WolfRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
