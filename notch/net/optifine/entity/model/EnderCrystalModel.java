package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fgo;
import fwg;
import fyk;
import gfh;
import gkd;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnderCrystalModel extends fwg {
  public fyk cube;
  
  public fyk glass;
  
  public fyk base;
  
  public EnderCrystalModel() {
    super(gfh::e);
    gkd renderer = new gkd(fgo.Q().ap().getContext());
    this.cube = (fyk)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 0);
    this.glass = (fyk)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 1);
    this.base = (fyk)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 2);
  }
  
  public gkd updateRenderer(gkd render) {
    if (!Reflector.RenderEnderCrystal_modelRenderers.exists()) {
      Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
      return null;
    } 
    Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 0, this.cube);
    Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 1, this.glass);
    Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 2, this.base);
    return render;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
