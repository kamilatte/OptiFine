package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlama extends ModelAdapter {
  public ModelAdapterLlama() {
    super(EntityType.LLAMA, "llama", 0.7F);
  }
  
  public ModelAdapterLlama(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new LlamaModel(bakeModelLayer(ModelLayers.LLAMA));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof LlamaModel))
      return null; 
    LlamaModel modelLlama = (LlamaModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 0); 
    if (modelPart.equals("body"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 1); 
    if (modelPart.equals("leg1"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 2); 
    if (modelPart.equals("leg2"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 3); 
    if (modelPart.equals("leg3"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 4); 
    if (modelPart.equals("leg4"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 5); 
    if (modelPart.equals("chest_right"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 6); 
    if (modelPart.equals("chest_left"))
      return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 7); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "chest_right", "chest_left" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    LlamaRenderer render = new LlamaRenderer(renderManager.getContext(), ModelLayers.LLAMA);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
