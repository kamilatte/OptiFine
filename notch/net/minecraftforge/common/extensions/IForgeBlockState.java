package notch.net.minecraftforge.common.extensions;

import dcc;
import dtc;
import jd;

public interface IForgeBlockState {
  private dtc self() {
    return (dtc)this;
  }
  
  default int getLightEmission(dcc level, jd pos) {
    return self().h();
  }
}
