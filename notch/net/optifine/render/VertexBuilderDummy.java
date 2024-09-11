package notch.net.optifine.render;

import fbm;
import gez;

public class VertexBuilderDummy implements fbm {
  private gez.a renderTypeBuffer = null;
  
  public VertexBuilderDummy(gez.a renderTypeBuffer) {
    this.renderTypeBuffer = renderTypeBuffer;
  }
  
  public gez.a getRenderTypeBuffer() {
    return this.renderTypeBuffer;
  }
  
  public fbm a(float x, float y, float z) {
    return this;
  }
  
  public fbm a(int red, int green, int blue, int alpha) {
    return this;
  }
  
  public fbm a(float u, float v) {
    return this;
  }
  
  public fbm a(int u, int v) {
    return this;
  }
  
  public fbm b(int u, int v) {
    return this;
  }
  
  public fbm b(float x, float y, float z) {
    return this;
  }
}
