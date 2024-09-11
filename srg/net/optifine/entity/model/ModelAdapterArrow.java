package srg.net.optifine.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.ArrowModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterArrow extends ModelAdapter {
  public ModelAdapterArrow() {
    super(EntityType.ARROW, "arrow", 0.0F);
  }
  
  protected ModelAdapterArrow(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new ArrowModel(new ModelPart(new ArrayList(), new HashMap<>()));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ArrowModel))
      return null; 
    ArrowModel modelArrow = (ArrowModel)model;
    if (modelPart.equals("body"))
      return modelArrow.body; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    TippableArrowRenderer render = new TippableArrowRenderer(renderManager.getContext());
    render.model = (ArrowModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
