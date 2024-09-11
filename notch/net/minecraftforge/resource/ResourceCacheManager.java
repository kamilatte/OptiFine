package notch.net.minecraftforge.resource;

import akr;
import ass;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraftforge.common.ForgeConfigSpec;

public class ResourceCacheManager {
  public ResourceCacheManager(boolean supportsReloading, ForgeConfigSpec.BooleanValue indexOffThreadConfig, BiFunction<ass, String, Path> pathBuilder) {}
  
  public ResourceCacheManager(boolean supportsReloading, String indexOnThreadConfigurationKey, BiFunction<ass, String, Path> pathBuilder) {}
  
  public static boolean shouldUseCache() {
    return false;
  }
  
  public boolean hasCached(ass packType, String namespace) {
    return false;
  }
  
  public Collection<akr> getResources(ass type, String resourceNamespace, Path inputPath, Predicate<akr> filter) {
    return null;
  }
  
  public void index(String nameSpace) {}
}
