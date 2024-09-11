package srg.net.optifine.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class EntityTypeUtils {
  public static EntityType getEntityType(ResourceLocation loc) {
    if (!BuiltInRegistries.ENTITY_TYPE.containsKey(loc))
      return null; 
    return (EntityType)BuiltInRegistries.ENTITY_TYPE.get(loc);
  }
}
