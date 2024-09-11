package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggu;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BannerModel extends fwg {
  public fyk bannerSlate;
  
  public fyk bannerStand;
  
  public fyk bannerTop;
  
  public BannerModel() {
    super(gfh::e);
    ggy dispatcher = Config.getMinecraft().aq();
    ggu renderer = new ggu(dispatcher.getContext());
    this.bannerSlate = (fyk)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 0);
    this.bannerStand = (fyk)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 1);
    this.bannerTop = (fyk)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 2);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityBannerRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: TileEntityBannerRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 0, this.bannerSlate);
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 1, this.bannerStand);
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 2, this.bannerTop);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
