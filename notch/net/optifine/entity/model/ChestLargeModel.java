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

public class ChestLargeModel extends fwg {
  public fyk lid_left;
  
  public fyk base_left;
  
  public fyk knob_left;
  
  public fyk lid_right;
  
  public fyk base_right;
  
  public fyk knob_right;
  
  public ChestLargeModel() {
    super(gfh::d);
    ggy dispatcher = Config.getMinecraft().aq();
    ghf renderer = new ghf(dispatcher.getContext());
    this.lid_right = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 3);
    this.base_right = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 4);
    this.knob_right = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 5);
    this.lid_left = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 6);
    this.base_left = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 7);
    this.knob_left = (fyk)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 8);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 3, this.lid_right);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 4, this.base_right);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 5, this.knob_right);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 6, this.lid_left);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 7, this.base_left);
    Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 8, this.knob_left);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
