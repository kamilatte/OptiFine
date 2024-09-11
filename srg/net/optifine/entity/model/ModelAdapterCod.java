package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterCod extends ModelAdapter {
  public ModelAdapterCod() {
    super(EntityType.COD, "cod", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new CodModel(bakeModelLayer(ModelLayers.COD));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof CodModel))
      return null; 
    CodModel modelCod = (CodModel)model;
    if (modelPart.equals("body"))
      return modelCod.root().getChildModelDeep("body"); 
    if (modelPart.equals("fin_back"))
      return modelCod.root().getChildModelDeep("top_fin"); 
    if (modelPart.equals("head"))
      return modelCod.root().getChildModelDeep("head"); 
    if (modelPart.equals("nose"))
      return modelCod.root().getChildModelDeep("nose"); 
    if (modelPart.equals("fin_right"))
      return modelCod.root().getChildModelDeep("right_fin"); 
    if (modelPart.equals("fin_left"))
      return modelCod.root().getChildModelDeep("left_fin"); 
    if (modelPart.equals("tail"))
      return modelCod.root().getChildModelDeep("tail_fin"); 
    if (modelPart.equals("root"))
      return modelCod.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "fin_back", "head", "nose", "fin_right", "fin_left", "tail", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    CodRenderer render = new CodRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
