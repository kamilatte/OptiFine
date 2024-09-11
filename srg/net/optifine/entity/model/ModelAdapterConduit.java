package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ConduitModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterConduit extends ModelAdapter {
  public ModelAdapterConduit() {
    super(BlockEntityType.CONDUIT, "conduit", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ConduitModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ConduitModel))
      return null; 
    ConduitModel modelConduit = (ConduitModel)model;
    if (modelPart.equals("eye"))
      return modelConduit.eye; 
    if (modelPart.equals("wind"))
      return modelConduit.wind; 
    if (modelPart.equals("base"))
      return modelConduit.base; 
    if (modelPart.equals("cage"))
      return modelConduit.cage; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "eye", "wind", "base", "cage" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.CONDUIT, index, () -> new ConduitRenderer(dispatcher.getContext()));
    if (!(renderer instanceof ConduitRenderer))
      return null; 
    if (!(modelBase instanceof ConduitModel)) {
      Config.warn("Not a conduit model: " + String.valueOf(modelBase));
      return null;
    } 
    ConduitModel conduitModel = (ConduitModel)modelBase;
    renderer = conduitModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
