package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.DecoratedPotModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDecoratedPot extends ModelAdapter {
  public ModelAdapterDecoratedPot() {
    super(BlockEntityType.DECORATED_POT, "decorated_pot", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new DecoratedPotModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof DecoratedPotModel))
      return null; 
    DecoratedPotModel modelDecoratedPot = (DecoratedPotModel)model;
    if (modelPart.equals("neck"))
      return modelDecoratedPot.neck; 
    if (modelPart.equals("front"))
      return modelDecoratedPot.frontSide; 
    if (modelPart.equals("back"))
      return modelDecoratedPot.backSide; 
    if (modelPart.equals("left"))
      return modelDecoratedPot.leftSide; 
    if (modelPart.equals("right"))
      return modelDecoratedPot.rightSide; 
    if (modelPart.equals("top"))
      return modelDecoratedPot.top; 
    if (modelPart.equals("bottom"))
      return modelDecoratedPot.bottom; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "neck", "front", "back", "left", "right", "top", "bottom" };
  }
  
  public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.DECORATED_POT, index, () -> new DecoratedPotRenderer(dispatcher.getContext()));
    if (!(renderer instanceof DecoratedPotRenderer))
      return null; 
    if (!(model instanceof DecoratedPotModel)) {
      Config.warn("Not a decorated pot model: " + String.valueOf(model));
      return null;
    } 
    DecoratedPotModel decoratedPotModel = (DecoratedPotModel)model;
    renderer = decoratedPotModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
