package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggx;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BellModel extends fwg {
  public fyk bellBody;
  
  public BellModel() {
    super(gfh::e);
    ggy dispatcher = Config.getMinecraft().aq();
    ggx renderer = new ggx(dispatcher.getContext());
    this.bellBody = (fyk)Reflector.TileEntityBellRenderer_modelRenderer.getValue(renderer);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityBellRenderer_modelRenderer.exists()) {
      Config.warn("Field not found: TileEntityBellRenderer.modelRenderer");
      return null;
    } 
    Reflector.TileEntityBellRenderer_modelRenderer.setValue(renderer, this.bellBody);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
