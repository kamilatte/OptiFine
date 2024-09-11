package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaSpit extends ModelAdapter {
  public ModelAdapterLlamaSpit() {
    super(EntityType.LLAMA_SPIT, "llama_spit", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new LlamaSpitModel(bakeModelLayer(ModelLayers.LLAMA_SPIT));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof LlamaSpitModel))
      return null; 
    LlamaSpitModel modelLlamaSpit = (LlamaSpitModel)model;
    if (modelPart.equals("body"))
      return modelLlamaSpit.root().getChildModelDeep("main"); 
    if (modelPart.equals("root"))
      return modelLlamaSpit.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    LlamaSpitRenderer render = new LlamaSpitRenderer(renderManager.getContext());
    if (!Reflector.RenderLlamaSpit_model.exists()) {
      Config.warn("Field not found: RenderLlamaSpit.model");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderLlamaSpit_model, modelBase);
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
