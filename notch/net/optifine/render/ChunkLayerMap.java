package notch.net.optifine.render;

import gfh;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChunkLayerMap<T> {
  private T[] values = (T[])new Object[gfh.CHUNK_RENDER_TYPES.length];
  
  private Supplier<T> defaultValue;
  
  public ChunkLayerMap(Function<gfh, T> initialValue) {
    gfh[] renderTypes = gfh.CHUNK_RENDER_TYPES;
    this.values = (T[])new Object[renderTypes.length];
    int i;
    for (i = 0; i < renderTypes.length; i++) {
      gfh renderType = renderTypes[i];
      T t = initialValue.apply(renderType);
      this.values[renderType.ordinal()] = t;
    } 
    for (i = 0; i < this.values.length; i++) {
      if (this.values[i] == null)
        throw new RuntimeException("Missing value at index: " + i); 
    } 
  }
  
  public T get(gfh layer) {
    return this.values[layer.ordinal()];
  }
  
  public Collection<T> values() {
    return Arrays.asList(this.values);
  }
}
