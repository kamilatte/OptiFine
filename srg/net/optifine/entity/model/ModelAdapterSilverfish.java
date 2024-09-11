package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterSilverfish extends ModelAdapter {
  public ModelAdapterSilverfish() {
    super(EntityType.SILVERFISH, "silverfish", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new SilverfishModel(bakeModelLayer(ModelLayers.SILVERFISH));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SilverfishModel))
      return null; 
    SilverfishModel modelSilverfish = (SilverfishModel)model;
    String PREFIX_BODY = "body";
    if (modelPart.startsWith(PREFIX_BODY)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSilverfish.root().getChildModelDeep("segment" + indexPart);
    } 
    String PREFIX_WINGS = "wing";
    if (modelPart.startsWith(PREFIX_WINGS)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_WINGS);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSilverfish.root().getChildModelDeep("layer" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelSilverfish.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3", 
        "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    SilverfishRenderer render = new SilverfishRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
