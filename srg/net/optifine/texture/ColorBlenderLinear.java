package srg.net.optifine.texture;

import net.optifine.texture.BlenderLinear;
import net.optifine.texture.ColorBlenderSeparate;
import net.optifine.texture.IBlender;

public class ColorBlenderLinear extends ColorBlenderSeparate {
  public ColorBlenderLinear() {
    super((IBlender)new BlenderLinear(), (IBlender)new BlenderLinear(), (IBlender)new BlenderLinear(), (IBlender)new BlenderLinear());
  }
  
  public int blend(int c1, int c2, int c3, int c4) {
    if (c1 == c2 && c2 == c3 && c3 == c4)
      return c1; 
    return super.blend(c1, c2, c3, c4);
  }
}
