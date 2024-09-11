package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggu;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.entity.model.BannerModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBanner extends ModelAdapter {
  public ModelAdapterBanner() {
    super(dqj.t, "banner", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new BannerModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
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
  
  public IEntityRenderer makeEntityRender(fwg model, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.t, index, () -> new ggu(dispatcher.getContext()));
    if (!(renderer instanceof ggu))
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
