package srg.net.optifine.util;

import com.mojang.blaze3d.platform.NativeImage;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.LongSupplier;
import net.optifine.Config;

public class NativeMemory {
  private static long imageAllocated = 0L;
  
  private static LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][] { { "sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" }, , { "jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" },  }, makeDefaultAllocatedSupplier());
  
  private static LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][] { { "sun.misc.VM", "maxDirectMemory" }, , { "jdk.internal.misc.VM", "maxDirectMemory" },  }, makeDefaultMaximumSupplier());
  
  public static long getBufferAllocated() {
    if (bufferAllocatedSupplier == null)
      return -1L; 
    return bufferAllocatedSupplier.getAsLong();
  }
  
  public static long getBufferMaximum() {
    if (bufferMaximumSupplier == null)
      return -1L; 
    return bufferMaximumSupplier.getAsLong();
  }
  
  public static synchronized void imageAllocated(NativeImage nativeImage) {
    imageAllocated += nativeImage.getSize();
  }
  
  public static synchronized void imageFreed(NativeImage nativeImage) {
    imageAllocated -= nativeImage.getSize();
  }
  
  public static long getImageAllocated() {
    return imageAllocated;
  }
  
  private static BufferPoolMXBean getDirectBufferPoolMXBean() {
    List<BufferPoolMXBean> mxBeans = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
    for (BufferPoolMXBean mxBean : mxBeans) {
      if (Config.equals(mxBean.getName(), "direct"))
        return mxBean; 
    } 
    return null;
  }
  
  private static LongSupplier makeDefaultAllocatedSupplier() {
    BufferPoolMXBean mxBean = getDirectBufferPoolMXBean();
    if (mxBean == null)
      return null; 
    return (LongSupplier)new Object(mxBean);
  }
  
  private static LongSupplier makeDefaultMaximumSupplier() {
    return (LongSupplier)new Object();
  }
  
  private static LongSupplier makeLongSupplier(String[][] paths, LongSupplier defaultSupplier) {
    List<Throwable> exceptions = new ArrayList<>();
    for (int i = 0; i < paths.length; i++) {
      String[] path = paths[i];
      try {
        LongSupplier supplier = makeLongSupplier(path);
        if (supplier != null)
          return supplier; 
      } catch (Throwable e) {
        exceptions.add(e);
      } 
    } 
    for (Iterator<Throwable> it = exceptions.iterator(); it.hasNext(); ) {
      Throwable t = it.next();
      Config.warn("(Reflector) " + t.getClass().getName() + ": " + t.getMessage());
    } 
    return defaultSupplier;
  }
  
  private static LongSupplier makeLongSupplier(String[] path) throws Exception {
    if (path.length < 2)
      return null; 
    Class<?> cls = Class.forName(path[0]);
    Method method = cls.getMethod(path[1], new Class[0]);
    method.setAccessible(true);
    Object object = null;
    for (int i = 2; i < path.length; i++) {
      String name = path[i];
      object = method.invoke(object, new Object[0]);
      method = object.getClass().getMethod(name, new Class[0]);
      method.setAccessible(true);
    } 
    Object objectF = object;
    Method methodF = method;
    return (LongSupplier)new Object(methodF, objectF);
  }
}
