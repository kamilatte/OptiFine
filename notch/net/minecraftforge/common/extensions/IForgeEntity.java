package notch.net.minecraftforge.common.extensions;

public interface IForgeEntity {
  default boolean isAddedToWorld() {
    return false;
  }
}
