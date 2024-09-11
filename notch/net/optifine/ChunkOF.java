package notch.net.optifine;

import bsr;
import dcd;
import dcw;
import dvi;

public class ChunkOF extends dvi {
  private boolean hasEntitiesOF;
  
  private boolean loadedOF;
  
  public ChunkOF(dcw worldIn, dcd chunkPosIn) {
    super(worldIn, chunkPosIn);
  }
  
  public void a(bsr entityIn) {
    this.hasEntitiesOF = true;
    super.a(entityIn);
  }
  
  public boolean hasEntities() {
    return this.hasEntitiesOF;
  }
  
  public void c(boolean loaded) {
    this.loadedOF = loaded;
    super.c(loaded);
  }
  
  public boolean isLoaded() {
    return this.loadedOF;
  }
}
