package notch.net.optifine.texture;

import net.optifine.texture.BlenderLinear;
import net.optifine.texture.BlenderSplit;
import net.optifine.texture.ColorBlenderSeparate;
import net.optifine.texture.IBlender;

public class ColorBlenderLabPbrSpecular extends ColorBlenderSeparate {
  public ColorBlenderLabPbrSpecular() {
    super((IBlender)new BlenderLinear(), (IBlender)new BlenderSplit(230, true), (IBlender)new BlenderSplit(65, false), (IBlender)new BlenderSplit(255, true));
  }
}
