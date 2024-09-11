package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSpider extends ModelAdapter {
  public ModelAdapterSpider() {
    super(EntityType.SPIDER, "spider", 1.0F);
  }
  
  protected ModelAdapterSpider(EntityType type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new SpiderModel(bakeModelLayer(ModelLayers.SPIDER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SpiderModel))
      return null; 
    SpiderModel modelSpider = (SpiderModel)model;
    if (modelPart.equals("head"))
      return modelSpider.root().getChildModelDeep("head"); 
    if (modelPart.equals("neck"))
      return modelSpider.root().getChildModelDeep("body0"); 
    if (modelPart.equals("body"))
      return modelSpider.root().getChildModelDeep("body1"); 
    if (modelPart.equals("leg1"))
      return modelSpider.root().getChildModelDeep("right_hind_leg"); 
    if (modelPart.equals("leg2"))
      return modelSpider.root().getChildModelDeep("left_hind_leg"); 
    if (modelPart.equals("leg3"))
      return modelSpider.root().getChildModelDeep("right_middle_hind_leg"); 
    if (modelPart.equals("leg4"))
      return modelSpider.root().getChildModelDeep("left_middle_hind_leg"); 
    if (modelPart.equals("leg5"))
      return modelSpider.root().getChildModelDeep("right_middle_front_leg"); 
    if (modelPart.equals("leg6"))
      return modelSpider.root().getChildModelDeep("left_middle_front_leg"); 
    if (modelPart.equals("leg7"))
      return modelSpider.root().getChildModelDeep("right_front_leg"); 
    if (modelPart.equals("leg8"))
      return modelSpider.root().getChildModelDeep("left_front_leg"); 
    if (modelPart.equals("root"))
      return modelSpider.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", 
        "leg8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SpiderRenderer render = new SpiderRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
