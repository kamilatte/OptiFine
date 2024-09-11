package notch.net.optifine.shaders;

import fbo;

public class SVertexAttrib {
  public int index;
  
  public int count;
  
  public fbo.a type;
  
  public int offset;
  
  public SVertexAttrib(int index, int count, fbo.a type) {
    this.index = index;
    this.count = count;
    this.type = type;
  }
}
