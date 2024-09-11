package notch.net.optifine.texture;

import net.optifine.texture.IBlender;

public class BlenderLinear implements IBlender {
  public int blend(int v1, int v2, int v3, int v4) {
    return (v1 + v2 + v3 + v4) / 4;
  }
}