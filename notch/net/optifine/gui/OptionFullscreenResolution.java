package notch.net.optifine.gui;

import fah;
import fal;
import fam;
import fgo;
import fgr;
import fgs;
import java.util.Objects;
import java.util.Optional;
import wz;

public class OptionFullscreenResolution {
  public static fgr make() {
    int j;
    fam window = fgo.Q().aM();
    fah monitor = window.u();
    if (monitor == null) {
      j = -1;
    } else {
      Optional<fal> optional = window.g();
      Objects.requireNonNull(monitor);
      j = ((Integer)optional.<Integer>map(monitor::a).orElse(Integer.valueOf(-1))).intValue();
    } 
    fgr<Integer> optioninstance = new fgr("options.fullscreen.resolution", fgr.a(), (p_232804_1_, p_232804_2_) -> (monitor == null) ? wz.c("options.fullscreen.unavailable") : ((p_232804_2_.intValue() == -1) ? fgs.a(p_232804_1_, (wz)wz.c("options.fullscreen.current")) : fgs.a(p_232804_1_, (wz)wz.b(monitor.a(p_232804_2_.intValue()).toString()))), (fgr.n)new fgr.f(-1, (monitor != null) ? (monitor.e() - 1) : -1), Integer.valueOf(j), p_232800_2_ -> {
          if (monitor != null)
            window.a((p_232800_2_.intValue() == -1) ? Optional.empty() : Optional.<fal>of(monitor.a(p_232800_2_.intValue()))); 
        });
    return optioninstance;
  }
}
