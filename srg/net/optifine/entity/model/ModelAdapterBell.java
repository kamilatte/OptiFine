package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.BellModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBell extends ModelAdapter {
  public ModelAdapterBell() {
    super(BlockEntityType.BELL, "bell", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new BellModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BellModel))
      return null; 
    BellModel modelBell = (BellModel)model;
    if (modelPart.equals("body"))
      return modelBell.bellBody; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.BELL, index, () -> new BellRenderer(dispatcher.getContext()));
    if (!(renderer instanceof BellRenderer))
      return null; 
    if (!(model instanceof BellModel)) {
      Config.warn("Not a bell model: " + String.valueOf(model));
      return null;
    } 
    BellModel bellModel = (BellModel)model;
    renderer = bellModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
