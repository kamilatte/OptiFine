package srg.net.optifine.render;

import net.minecraft.world.phys.AABB;

public interface ICamera {
  void setCameraPosition(double paramDouble1, double paramDouble2, double paramDouble3);
  
  boolean isBoundingBoxInFrustum(AABB paramAABB);
  
  boolean isBoxInFrustumFully(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
}
