package notch.net.optifine.util;

import akr;
import bsr;
import bsx;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import lt;
import net.optifine.Config;

public class EntityUtils {
  private static final Map<bsx, Integer> mapIdByType = new HashMap<>();
  
  private static final Map<String, Integer> mapIdByLocation = new HashMap<>();
  
  private static final Map<String, Integer> mapIdByName = new HashMap<>();
  
  static {
    for (Iterator<bsx<?>> it = lt.f.iterator(); it.hasNext(); ) {
      bsx type = it.next();
      int id = lt.f.a(type);
      akr loc = lt.f.b(type);
      String locStr = loc.toString();
      String name = loc.a();
      if (mapIdByType.containsKey(type))
        Config.warn("Duplicate entity type: " + String.valueOf(type) + ", id1: " + String.valueOf(mapIdByType.get(type)) + ", id2: " + id); 
      if (mapIdByLocation.containsKey(locStr))
        Config.warn("Duplicate entity location: " + locStr + ", id1: " + String.valueOf(mapIdByLocation.get(locStr)) + ", id2: " + id); 
      if (mapIdByName.containsKey(locStr))
        Config.warn("Duplicate entity name: " + name + ", id1: " + String.valueOf(mapIdByName.get(name)) + ", id2: " + id); 
      mapIdByType.put(type, Integer.valueOf(id));
      mapIdByLocation.put(locStr, Integer.valueOf(id));
      mapIdByName.put(name, Integer.valueOf(id));
    } 
  }
  
  public static int getEntityIdByClass(bsr entity) {
    if (entity == null)
      return -1; 
    return getEntityIdByType(entity.am());
  }
  
  public static int getEntityIdByType(bsx type) {
    Integer id = mapIdByType.get(type);
    if (id == null)
      return -1; 
    return id.intValue();
  }
  
  public static int getEntityIdByLocation(String locStr) {
    Integer id = mapIdByLocation.get(locStr);
    if (id == null)
      return -1; 
    return id.intValue();
  }
  
  public static int getEntityIdByName(String name) {
    name = name.toLowerCase(Locale.ROOT);
    Integer id = mapIdByName.get(name);
    if (id == null)
      return -1; 
    return id.intValue();
  }
}
