package srg.net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.BannerModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBanner extends ModelAdapter {
  public ModelAdapterBanner() {
    super(BlockEntityType.BANNER, "banner", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new BannerModel();
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof BannerModel))
      return null; 
    BannerModel modelBanner = (BannerModel)model;
    if (modelPart.equals("slate"))
      return modelBanner.bannerSlate; 
    if (modelPart.equals("stand"))
      return modelBanner.bannerStand; 
    if (modelPart.equals("top"))
      return modelBanner.bannerTop; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "slate", "stand", "top" };
  }
  
  public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.BANNER, index, () -> new BannerRenderer(dispatcher.getContext()));
    if (!(renderer instanceof BannerRenderer))
      return null; 
    if (!(model instanceof BannerModel)) {
      Config.warn("Not a banner model: " + String.valueOf(model));
      return null;
    } 
    BannerModel bannerModel = (BannerModel)model;
    renderer = bannerModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
