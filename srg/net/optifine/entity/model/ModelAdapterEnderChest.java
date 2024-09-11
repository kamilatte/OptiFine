package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ChestModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterEnderChest extends ModelAdapter {
  public ModelAdapterEnderChest() {
    super(BlockEntityType.ENDER_CHEST, "ender_chest", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ChestModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChestModel))
      return null; 
    ChestModel modelChest = (ChestModel)model;
    if (modelPart.equals("lid"))
      return modelChest.lid; 
    if (modelPart.equals("base"))
      return modelChest.base; 
    if (modelPart.equals("knob"))
      return modelChest.knob; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "lid", "base", "knob" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.ENDER_CHEST, index, () -> new ChestRenderer(dispatcher.getContext()));
    if (!(renderer instanceof ChestRenderer))
      return null; 
    if (!(modelBase instanceof ChestModel)) {
      Config.warn("Not a chest model: " + String.valueOf(modelBase));
      return null;
    } 
    ChestModel chestModel = (ChestModel)modelBase;
    renderer = chestModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
