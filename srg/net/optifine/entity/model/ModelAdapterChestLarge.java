package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ChestLargeModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterChestLarge extends ModelAdapter {
  public ModelAdapterChestLarge() {
    super(BlockEntityType.CHEST, "chest_large", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ChestLargeModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ChestLargeModel))
      return null; 
    ChestLargeModel modelChest = (ChestLargeModel)model;
    if (modelPart.equals("lid_left"))
      return modelChest.lid_left; 
    if (modelPart.equals("base_left"))
      return modelChest.base_left; 
    if (modelPart.equals("knob_left"))
      return modelChest.knob_left; 
    if (modelPart.equals("lid_right"))
      return modelChest.lid_right; 
    if (modelPart.equals("base_right"))
      return modelChest.base_right; 
    if (modelPart.equals("knob_right"))
      return modelChest.knob_right; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "lid_left", "base_left", "knob_left", "lid_right", "base_right", "knob_right" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.CHEST, index, () -> new ChestRenderer(dispatcher.getContext()));
    if (!(renderer instanceof ChestRenderer))
      return null; 
    if (!(modelBase instanceof ChestLargeModel)) {
      Config.warn("Not a large chest model: " + String.valueOf(modelBase));
      return null;
    } 
    ChestLargeModel chestModel = (ChestLargeModel)modelBase;
    renderer = chestModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
