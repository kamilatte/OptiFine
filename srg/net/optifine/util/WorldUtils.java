package srg.net.optifine.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class WorldUtils {
  public static int getDimensionId(Level world) {
    if (world == null)
      return 0; 
    return getDimensionId(world.dimension());
  }
  
  public static int getDimensionId(ResourceKey<Level> dimension) {
    if (dimension == Level.NETHER)
      return -1; 
    if (dimension == Level.OVERWORLD)
      return 0; 
    if (dimension == Level.END)
      return 1; 
    return 0;
  }
  
  public static boolean isNether(Level world) {
    return (world.dimension() == Level.NETHER);
  }
  
  public static boolean isOverworld(Level world) {
    ResourceKey<Level> dimension = world.dimension();
    return (getDimensionId(dimension) == 0);
  }
  
  public static boolean isEnd(Level world) {
    return (world.dimension() == Level.END);
  }
}
