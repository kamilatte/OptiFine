package notch.net.optifine.render;

import ewx;
import net.optifine.render.ICamera;

public class AabbFrame extends ewx {
  private int frameCount = -1;
  
  private boolean inFrustumFully = false;
  
  public AabbFrame(double x1, double y1, double z1, double x2, double y2, double z2) {
    super(x1, y1, z1, x2, y2, z2);
  }
  
  public boolean isBoundingBoxInFrustumFully(ICamera camera, int frameCount) {
    if (this.frameCount != frameCount) {
      this.inFrustumFully = camera.isBoxInFrustumFully(this.a, this.b, this.c, this.d, this.e, this.f);
      this.frameCount = frameCount;
    } 
    return this.inFrustumFully;
  }
}
