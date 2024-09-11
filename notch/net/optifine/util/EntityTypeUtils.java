package notch.net.optifine.util;

import akr;
import bsx;
import lt;

public class EntityTypeUtils {
  public static bsx getEntityType(akr loc) {
    if (!lt.f.d(loc))
      return null; 
    return (bsx)lt.f.a(loc);
  }
}
