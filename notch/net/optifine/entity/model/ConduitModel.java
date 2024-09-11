package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggy;
import ggz;
import ghg;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ConduitModel extends fwg {
  public fyk eye;
  
  public fyk wind;
  
  public fyk base;
  
  public fyk cage;
  
  public ConduitModel() {
    super(gfh::d);
    ggy dispatcher = Config.getMinecraft().aq();
    ghg renderer = new ghg(dispatcher.getContext());
    this.eye = (fyk)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 0);
    this.wind = (fyk)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 1);
    this.base = (fyk)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 2);
    this.cage = (fyk)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 3);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityConduitRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: TileEntityConduitRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 0, this.eye);
    Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 1, this.wind);
    Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 2, this.base);
    Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 3, this.cage);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
