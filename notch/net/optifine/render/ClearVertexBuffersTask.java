package notch.net.optifine.render;

import fbl;
import gfh;
import gia;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ClearVertexBuffersTask implements Runnable {
  List<fbl> listBuffers;
  
  public ClearVertexBuffersTask(List<fbl> listBuffers) {
    this.listBuffers = listBuffers;
  }
  
  public void run() {
    for (int i = 0; i < this.listBuffers.size(); i++) {
      fbl vb = this.listBuffers.get(i);
      vb.clearData();
    } 
  }
  
  public String toString() {
    return String.valueOf(this.listBuffers);
  }
  
  public static net.optifine.render.ClearVertexBuffersTask make(Set<gfh> renderedLayers, gia.b renderChunk) {
    List<fbl> listBuffers = null;
    for (gfh rt : gia.BLOCK_RENDER_LAYERS) {
      fbl vb = renderChunk.a(rt);
      if (vb != null && !vb.isEmpty())
        if (renderedLayers == null || !renderedLayers.contains(rt)) {
          if (listBuffers == null)
            listBuffers = new ArrayList<>(); 
          listBuffers.add(vb);
        }  
    } 
    if (listBuffers == null)
      return null; 
    return new net.optifine.render.ClearVertexBuffersTask(listBuffers);
  }
  
  public static CompletableFuture<Void> makeFuture(Set<gfh> renderedLayers, gia.b renderChunk, Executor executor) {
    net.optifine.render.ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
    if (task == null)
      return null; 
    return CompletableFuture.runAsync(() -> task.run(), executor);
  }
}
