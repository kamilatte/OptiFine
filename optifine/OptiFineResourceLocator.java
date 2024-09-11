package optifine;

import java.io.InputStream;

public class OptiFineResourceLocator {
  private static IOptiFineResourceLocator resourceLocator;
  
  public static void setResourceLocator(IOptiFineResourceLocator resourceLocator) {
    OptiFineResourceLocator.resourceLocator = resourceLocator;
    Class<OptiFineResourceLocator> cls = OptiFineResourceLocator.class;
    System.getProperties().put(String.valueOf(cls.getName()) + ".class", cls);
  }
  
  public static InputStream getOptiFineResourceStream(String name) {
    if (resourceLocator == null)
      return null; 
    return resourceLocator.getOptiFineResourceStream(name);
  }
}
