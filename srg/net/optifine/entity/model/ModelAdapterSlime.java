package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSlime extends ModelAdapter {
  public ModelAdapterSlime() {
    super(EntityType.SLIME, "slime", 0.25F);
  }
  
  public ModelAdapterSlime(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new SlimeModel(bakeModelLayer(ModelLayers.SLIME));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SlimeModel))
      return null; 
    SlimeModel modelSlime = (SlimeModel)model;
    if (modelPart.equals("body"))
      return modelSlime.root().getChildModelDeep("cube"); 
    if (modelPart.equals("left_eye"))
      return modelSlime.root().getChildModelDeep("left_eye"); 
    if (modelPart.equals("right_eye"))
      return modelSlime.root().getChildModelDeep("right_eye"); 
    if (modelPart.equals("mouth"))
      return modelSlime.root().getChildModelDeep("mouth"); 
    if (modelPart.equals("root"))
      return modelSlime.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "left_eye", "right_eye", "mouth", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SlimeRenderer render = new SlimeRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
