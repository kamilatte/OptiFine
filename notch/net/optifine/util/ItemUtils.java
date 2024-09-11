package notch.net.optifine.util;

import akr;
import cul;
import cuq;
import kr;
import lt;
import net.optifine.reflect.Reflector;
import ub;

public class ItemUtils {
  private static ub EMPTY_TAG = new ub();
  
  public static cul getItem(akr loc) {
    if (!lt.g.d(loc))
      return null; 
    return (cul)lt.g.a(loc);
  }
  
  public static int getId(cul item) {
    return lt.g.a(item);
  }
  
  public static ub getTag(cuq itemStack) {
    if (itemStack == null)
      return EMPTY_TAG; 
    kr components = (kr)Reflector.ItemStack_components.getValue(itemStack);
    if (components == null)
      return EMPTY_TAG; 
    ub tag = components.getTag();
    return tag;
  }
}
