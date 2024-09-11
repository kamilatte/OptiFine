package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggy;
import ggz;
import ghf;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestModel extends fwg {
  public fyk lid;
  
  public fyk base;
  
  public fyk knob;
  
  public ChestModel() {
    super(gfh::d);
    ggy dispatcher = Config.getMinecraft().aq();
    ghf renderer = new ghf(dispatcher.getContext());
    this.lid = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 0);
    this.base = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 1);
    this.knob = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 2);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 0, this.lid);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 1, this.base);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 2, this.knob);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
