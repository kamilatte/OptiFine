package notch.net.optifine.util;

import akr;
import brx;
import cuq;
import cws;
import cwu;
import java.util.Optional;
import jm;
import kq;
import lt;
import net.optifine.reflect.Reflector;

public class PotionUtils {
  public static brx getPotion(akr loc) {
    if (!lt.d.d(loc))
      return null; 
    return (brx)lt.d.a(loc);
  }
  
  public static brx getPotion(int potionID) {
    return (brx)lt.d.a(potionID);
  }
  
  public static int getId(brx potionIn) {
    return lt.d.a(potionIn);
  }
  
  public static String getPotionBaseName(cws p) {
    if (p == null)
      return null; 
    return (String)Reflector.Potion_baseName.getValue(p);
  }
  
  public static cws getPotion(cuq itemStack) {
    cwu pc = (cwu)itemStack.a(kq.G);
    if (pc == null)
      return null; 
    Optional<jm<cws>> opt = pc.e();
    if (opt.isEmpty())
      return null; 
    jm<cws> holder = opt.get();
    if (!holder.b())
      return null; 
    cws p = (cws)holder.a();
    return p;
  }
}
