package srg.net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.optifine.BlockPosM;
import net.optifine.shaders.Iterator3d;

public class IteratorRenderChunks implements Iterator<SectionRenderDispatcher.RenderSection> {
  private ViewArea viewFrustum;
  
  private Iterator3d Iterator3d;
  
  private BlockPosM posBlock = new BlockPosM(0, 0, 0);
  
  public IteratorRenderChunks(ViewArea viewFrustum, BlockPos posStart, BlockPos posEnd, int width, int height) {
    this.viewFrustum = viewFrustum;
    this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
  }
  
  public boolean hasNext() {
    return this.Iterator3d.hasNext();
  }
  
  public SectionRenderDispatcher.RenderSection next() {
    BlockPos pos = this.Iterator3d.next();
    this.posBlock.setXyz(pos.getX() << 4, pos.getY() << 4, pos.getZ() << 4);
    SectionRenderDispatcher.RenderSection rc = this.viewFrustum.getRenderSectionAt((BlockPos)this.posBlock);
    return rc;
  }
  
  public void remove() {
    throw new RuntimeException("Not implemented");
  }
}
