package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterSquid extends ModelAdapter {
  public ModelAdapterSquid() {
    super(EntityType.SQUID, "squid", 0.7F);
  }
  
  protected ModelAdapterSquid(EntityType entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public Model makeModel() {
    return (Model)new SquidModel(bakeModelLayer(ModelLayers.SQUID));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SquidModel))
      return null; 
    SquidModel modelSquid = (SquidModel)model;
    if (modelPart.equals("body"))
      return modelSquid.root().getChildModelDeep("body"); 
    String PREFIX_TENTACLE = "tentacle";
    if (modelPart.startsWith(PREFIX_TENTACLE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSquid.root().getChildModelDeep("tentacle" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelSquid.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SquidRenderer render = new SquidRenderer(renderManager.getContext(), (SquidModel)modelBase);
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
