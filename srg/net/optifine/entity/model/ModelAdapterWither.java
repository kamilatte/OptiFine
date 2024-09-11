package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWither extends ModelAdapter {
  public ModelAdapterWither() {
    super(EntityType.WITHER, "wither", 0.5F);
  }
  
  public ModelAdapterWither(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new WitherBossModel(bakeModelLayer(ModelLayers.WITHER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof WitherBossModel))
      return null; 
    WitherBossModel modelWither = (WitherBossModel)model;
    if (modelPart.equals("body1"))
      return modelWither.root().getChildModelDeep("shoulders"); 
    if (modelPart.equals("body2"))
      return modelWither.root().getChildModelDeep("ribcage"); 
    if (modelPart.equals("body3"))
      return modelWither.root().getChildModelDeep("tail"); 
    if (modelPart.equals("head1"))
      return modelWither.root().getChildModelDeep("center_head"); 
    if (modelPart.equals("head2"))
      return modelWither.root().getChildModelDeep("right_head"); 
    if (modelPart.equals("head3"))
      return modelWither.root().getChildModelDeep("left_head"); 
    if (modelPart.equals("root"))
      return modelWither.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body1", "body2", "body3", "head1", "head2", "head3", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    WitherBossRenderer render = new WitherBossRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
