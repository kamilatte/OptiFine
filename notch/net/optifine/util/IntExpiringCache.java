package notch.net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;

public abstract class IntExpiringCache<T> {
  private final int intervalMs;
  
  private long timeCheckMs;
  
  private Int2ObjectOpenHashMap<Wrapper<T>> map = new Int2ObjectOpenHashMap();
  
  public IntExpiringCache(int intervalMs) {
    this.intervalMs = intervalMs;
  }
  
  public T get(int key) {
    long timeNowMs = System.currentTimeMillis();
    if (!this.map.isEmpty() && timeNowMs >= this.timeCheckMs) {
      this.timeCheckMs = timeNowMs + this.intervalMs;
      long timeMinMs = timeNowMs - this.intervalMs;
      IntSet keys = this.map.keySet();
      for (IntIterator it = keys.iterator(); it.hasNext(); ) {
        int k = it.nextInt();
        if (k == key)
          continue; 
        Wrapper<T> wrapper = (Wrapper<T>)this.map.get(k);
        if (wrapper.getAccessTimeMs() > timeMinMs)
          continue; 
        it.remove();
      } 
    } 
    Wrapper<T> w = (Wrapper<T>)this.map.get(key);
    if (w == null) {
      T obj = make();
      w = new Wrapper(obj);
      this.map.put(key, w);
    } 
    w.setAccessTimeMs(timeNowMs);
    return (T)w.getValue();
  }
  
  protected abstract T make();
}
