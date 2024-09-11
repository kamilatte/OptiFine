package notch.net.optifine.shaders;

import gfq;
import gia;
import java.util.Iterator;
import jd;
import net.optifine.BlockPosM;
import net.optifine.shaders.Iterator3d;

public class IteratorRenderChunks implements Iterator<gia.b> {
  private gfq viewFrustum;
  
  private Iterator3d Iterator3d;
  
  private BlockPosM posBlock = new BlockPosM(0, 0, 0);
  
  public IteratorRenderChunks(gfq viewFrustum, jd posStart, jd posEnd, int width, int height) {
    this.viewFrustum = viewFrustum;
    this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
  }
  
  public boolean hasNext() {
    return this.Iterator3d.hasNext();
  }
  
  public gia.b next() {
    jd pos = this.Iterator3d.next();
    this.posBlock.setXyz(pos.u() << 4, pos.v() << 4, pos.w() << 4);
    gia.b rc = this.viewFrustum.a((jd)this.posBlock);
    return rc;
  }
  
  public void remove() {
    throw new RuntimeException("Not implemented");
  }
}
