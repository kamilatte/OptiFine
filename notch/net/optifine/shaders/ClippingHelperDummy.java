package notch.net.optifine.shaders;

import ewx;
import gie;
import org.joml.Matrix4f;

public class ClippingHelperDummy extends gie {
  public ClippingHelperDummy() {
    super(new Matrix4f(), new Matrix4f());
  }
  
  public boolean a(ewx aabbIn) {
    return true;
  }
  
  public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
    return true;
  }
}
