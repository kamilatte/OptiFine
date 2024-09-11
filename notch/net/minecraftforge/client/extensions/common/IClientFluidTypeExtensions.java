package notch.net.minecraftforge.client.extensions.common;

import dbz;
import epe;
import fbi;
import fgo;
import jd;
import net.minecraftforge.fluids.FluidType;

public interface IClientFluidTypeExtensions {
  public static final net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions DUMMY = (net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions)new Object();
  
  static net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions of(epe fluidState) {
    return DUMMY;
  }
  
  static net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions of(FluidType fluidType) {
    return DUMMY;
  }
  
  default int getColorTint() {
    return -1;
  }
  
  default void renderOverlay(fgo mc, fbi stack) {}
  
  default int getTintColor(epe state, dbz getter, jd pos) {
    return getColorTint();
  }
}
