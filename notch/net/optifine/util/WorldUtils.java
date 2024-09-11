package notch.net.optifine.util;

import akq;
import dcw;

public class WorldUtils {
  public static int getDimensionId(dcw world) {
    if (world == null)
      return 0; 
    return getDimensionId(world.af());
  }
  
  public static int getDimensionId(akq<dcw> dimension) {
    if (dimension == dcw.i)
      return -1; 
    if (dimension == dcw.h)
      return 0; 
    if (dimension == dcw.j)
      return 1; 
    return 0;
  }
  
  public static boolean isNether(dcw world) {
    return (world.af() == dcw.i);
  }
  
  public static boolean isOverworld(dcw world) {
    akq<dcw> dimension = world.af();
    return (getDimensionId(dimension) == 0);
  }
  
  public static boolean isEnd(dcw world) {
    return (world.af() == dcw.j);
  }
}
