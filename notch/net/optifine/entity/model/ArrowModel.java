package notch.net.optifine.entity.model;

import fbi;
import fbm;
import fwg;
import fyk;
import gfh;

public class ArrowModel extends fwg {
  public fyk body;
  
  public ArrowModel(fyk body) {
    super(gfh::e);
    this.body = body;
  }
  
  public void a(fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
    this.body.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
  }
}
