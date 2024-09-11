package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterGhast extends ModelAdapter {
  public ModelAdapterGhast() {
    super(EntityType.GHAST, "ghast", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new GhastModel(bakeModelLayer(ModelLayers.GHAST));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof GhastModel))
      return null; 
    GhastModel modelGhast = (GhastModel)model;
    if (modelPart.equals("body"))
      return modelGhast.root().getChildModelDeep("body"); 
    String PREFIX_TENTACLE = "tentacle";
    if (modelPart.startsWith(PREFIX_TENTACLE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGhast.root().getChildModelDeep("tentacle" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelGhast.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9", 
        "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    GhastRenderer render = new GhastRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
