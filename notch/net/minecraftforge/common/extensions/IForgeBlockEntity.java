package notch.net.minecraftforge.common.extensions;

import ewx;

public interface IForgeBlockEntity {
  default void requestModelDataUpdate() {}
  
  default void onChunkUnloaded() {}
  
  public static final ewx INFINITE_EXTENT_AABB = new ewx(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
}
