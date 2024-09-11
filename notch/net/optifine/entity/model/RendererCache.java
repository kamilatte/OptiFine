package notch.net.optifine.entity.model;

import bsx;
import dqj;
import ggz;
import gki;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import lt;

public class RendererCache {
  private Map<String, gki> mapEntityRenderers = new HashMap<>();
  
  private Map<String, ggz> mapBlockEntityRenderers = new HashMap<>();
  
  public gki get(bsx type, int index, Supplier<gki> supplier) {
    String key = String.valueOf(lt.f.b(type)) + ":" + String.valueOf(lt.f.b(type));
    gki renderer = this.mapEntityRenderers.get(key);
    if (renderer == null) {
      renderer = supplier.get();
      this.mapEntityRenderers.put(key, renderer);
    } 
    return renderer;
  }
  
  public ggz get(dqj type, int index, Supplier<ggz> supplier) {
    String key = String.valueOf(lt.j.b(type)) + ":" + String.valueOf(lt.j.b(type));
    ggz renderer = this.mapBlockEntityRenderers.get(key);
    if (renderer == null) {
      renderer = supplier.get();
      this.mapBlockEntityRenderers.put(key, renderer);
    } 
    return renderer;
  }
  
  public void put(bsx type, int index, gki renderer) {
    String key = String.valueOf(lt.f.b(type)) + ":" + String.valueOf(lt.f.b(type));
    this.mapEntityRenderers.put(key, renderer);
  }
  
  public void put(dqj type, int index, ggz renderer) {
    String key = String.valueOf(lt.j.b(type)) + ":" + String.valueOf(lt.j.b(type));
    this.mapBlockEntityRenderers.put(key, renderer);
  }
  
  public void clear() {
    this.mapEntityRenderers.clear();
    this.mapBlockEntityRenderers.clear();
  }
}
