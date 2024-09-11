package srg.net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterMagmaCube extends ModelAdapter {
  public ModelAdapterMagmaCube() {
    super(EntityType.MAGMA_CUBE, "magma_cube", 0.5F);
  }
  
  public Model makeModel() {
    return (Model)new LavaSlimeModel(bakeModelLayer(ModelLayers.MAGMA_CUBE));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof LavaSlimeModel))
      return null; 
    LavaSlimeModel modelMagmaCube = (LavaSlimeModel)model;
    if (modelPart.equals("core"))
      return modelMagmaCube.root().getChildModelDeep("inside_cube"); 
    String PREFIX_SEGMENT = "segment";
    if (modelPart.startsWith(PREFIX_SEGMENT)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_SEGMENT);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelMagmaCube.root().getChildModelDeep("cube" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelMagmaCube.root(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
    MagmaCubeRenderer render = new MagmaCubeRenderer(renderManager.getContext());
    render.model = (EntityModel)modelBase;
    render.shadowRadius = shadowSize;
    return (IEntityRenderer)render;
  }
}
