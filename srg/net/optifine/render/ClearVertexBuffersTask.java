package srg.net.optifine.render;

import com.mojang.blaze3d.vertex.VertexBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

public class ClearVertexBuffersTask implements Runnable {
  List<VertexBuffer> listBuffers;
  
  public ClearVertexBuffersTask(List<VertexBuffer> listBuffers) {
    this.listBuffers = listBuffers;
  }
  
  public void run() {
    for (int i = 0; i < this.listBuffers.size(); i++) {
      VertexBuffer vb = this.listBuffers.get(i);
      vb.clearData();
    } 
  }
  
  public String toString() {
    return String.valueOf(this.listBuffers);
  }
  
  public static net.optifine.render.ClearVertexBuffersTask make(Set<RenderType> renderedLayers, SectionRenderDispatcher.RenderSection renderChunk) {
    List<VertexBuffer> listBuffers = null;
    for (RenderType rt : SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
      VertexBuffer vb = renderChunk.getBuffer(rt);
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
  
  public static CompletableFuture<Void> makeFuture(Set<RenderType> renderedLayers, SectionRenderDispatcher.RenderSection renderChunk, Executor executor) {
    net.optifine.render.ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
    if (task == null)
      return null; 
    return CompletableFuture.runAsync(() -> task.run(), executor);
  }
}
