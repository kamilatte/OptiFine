package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterBlaze extends ModelAdapter {
  public ModelAdapterBlaze() {
    super(EntityType.BLAZE, "blaze", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new BlazeModel(bakeModelLayer(ModelLayers.BLAZE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BlazeModel))
      return null; 
    BlazeModel modelBlaze = (BlazeModel)model;
    if (modelPart.equals("head"))
      return modelBlaze.root().getChildModelDeep("head"); 
    String PREFIX_STICK = "stick";
    if (modelPart.startsWith(PREFIX_STICK)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_STICK);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelBlaze.root().getChildModelDeep("part" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelBlaze.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", 
        "stick10", "stick11", "stick12", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    BlazeRenderer render = new BlazeRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
