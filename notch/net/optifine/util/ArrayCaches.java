package notch.net.optifine.util;

import java.lang.reflect.Array;
import net.optifine.util.ArrayCache;

public class ArrayCaches {
  private int[] sizes;
  
  private Class elementClass;
  
  private ArrayCache[] caches;
  
  public ArrayCaches(int[] sizes, Class elementClass, int maxCacheSize) {
    this.sizes = sizes;
    this.elementClass = elementClass;
    this.caches = new ArrayCache[sizes.length];
    for (int i = 0; i < this.caches.length; i++)
      this.caches[i] = new ArrayCache(elementClass, maxCacheSize); 
  }
  
  public Object allocate(int size) {
    for (int i = 0; i < this.sizes.length; i++) {
      if (size == this.sizes[i])
        return this.caches[i].allocate(size); 
    } 
    return Array.newInstance(this.elementClass, size);
  }
  
  public void free(Object arr) {
    if (arr == null)
      return; 
    int size = Array.getLength(arr);
    for (int i = 0; i < this.sizes.length; i++) {
      if (size == this.sizes[i]) {
        this.caches[i].free(arr);
        return;
      } 
    } 
  }
}
