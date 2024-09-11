package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox extends ModelAdapter {
  public ModelAdapterShulkerBox() {
    super(BlockEntityType.SHULKER_BOX, "shulker_box", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new ShulkerModel(bakeModelLayer(ModelLayers.SHULKER));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof ShulkerModel))
      return null; 
    ShulkerModel modelShulker = (ShulkerModel)model;
    if (modelPart.equals("base"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0); 
    if (modelPart.equals("lid"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1); 
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "lid", "head" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.SHULKER_BOX, index, () -> new ShulkerBoxRenderer(dispatcher.getContext()));
    if (!(renderer instanceof ShulkerBoxRenderer))
      return null; 
    if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
      Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
      return null;
    } 
    Reflector.setFieldValue(renderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
    return (IEntityRenderer)renderer;
  }
}
