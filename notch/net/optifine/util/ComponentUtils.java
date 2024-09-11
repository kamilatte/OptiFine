package notch.net.optifine.util;

import wz;
import xa;
import yk;

public class ComponentUtils {
  public static String getTranslationKey(wz comp) {
    if (comp == null)
      return null; 
    xa cont = comp.b();
    if (!(cont instanceof yk))
      return null; 
    yk tran = (yk)cont;
    return tran.b();
  }
}
