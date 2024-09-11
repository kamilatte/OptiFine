package notch.net.optifine.render;

import ewx;

public interface ICamera {
  void setCameraPosition(double paramDouble1, double paramDouble2, double paramDouble3);
  
  boolean isBoundingBoxInFrustum(ewx paramewx);
  
  boolean isBoxInFrustumFully(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
}
