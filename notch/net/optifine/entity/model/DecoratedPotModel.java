package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;
import ggy;
import ggz;
import ghh;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class DecoratedPotModel extends fwg {
  public fyk neck;
  
  public fyk frontSide;
  
  public fyk backSide;
  
  public fyk leftSide;
  
  public fyk rightSide;
  
  public fyk top;
  
  public fyk bottom;
  
  public DecoratedPotModel() {
    super(gfh::e);
    ggy dispatcher = Config.getMinecraft().aq();
    ghh renderer = new ghh(dispatcher.getContext());
    this.neck = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 0);
    this.frontSide = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 1);
    this.backSide = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 2);
    this.leftSide = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 3);
    this.rightSide = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 4);
    this.top = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 5);
    this.bottom = (fyk)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 6);
  }
  
  public ggz updateRenderer(ggz renderer) {
    if (!Reflector.TileEntityDecoratedPotRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: DecoratedPotRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 0, this.neck);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 1, this.frontSide);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 2, this.backSide);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 3, this.leftSide);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 4, this.rightSide);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 5, this.top);
    Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 6, this.bottom);
    return renderer;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
