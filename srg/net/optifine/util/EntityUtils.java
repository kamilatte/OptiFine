package srg.net.optifine.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class EntityUtils {
  private static final Map<EntityType, Integer> mapIdByType = new HashMap<>();
  
  private static final Map<String, Integer> mapIdByLocation = new HashMap<>();
  
  private static final Map<String, Integer> mapIdByName = new HashMap<>();
  
  static {
    for (Iterator<EntityType<?>> it = BuiltInRegistries.ENTITY_TYPE.iterator(); it.hasNext(); ) {
      EntityType type = it.next();
      int id = BuiltInRegistries.ENTITY_TYPE.getId(type);
      ResourceLocation loc = BuiltInRegistries.ENTITY_TYPE.getKey(type);
      String locStr = loc.toString();
      String name = loc.getPath();
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
  
  public static int getEntityIdByClass(Entity entity) {
    if (entity == null)
      return -1; 
    return getEntityIdByType(entity.getType());
  }
  
  public static int getEntityIdByType(EntityType type) {
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
