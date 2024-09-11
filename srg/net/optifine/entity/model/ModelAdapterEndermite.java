package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterEndermite extends ModelAdapter {
  public ModelAdapterEndermite() {
    super(EntityType.ENDERMITE, "endermite", 0.3F);
  }
  
  public Model makeModel() {
    return (Model)new EndermiteModel(bakeModelLayer(ModelLayers.ENDERMITE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof EndermiteModel))
      return null; 
    EndermiteModel modelEnderMite = (EndermiteModel)model;
    String PREFIX_BODY = "body";
    if (modelPart.startsWith(PREFIX_BODY)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelEnderMite.root().getChildModelDeep("segment" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelEnderMite.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body1", "body2", "body3", "body4", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    EndermiteRenderer render = new EndermiteRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
