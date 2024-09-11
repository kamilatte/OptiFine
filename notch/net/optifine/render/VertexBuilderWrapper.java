package notch.net.optifine.render;

import fbm;
import gez;
import gfh;
import gql;
import net.optifine.render.VertexPosition;
import org.joml.Vector3f;

public abstract class VertexBuilderWrapper implements fbm {
  private fbm vertexBuilder;
  
  public VertexBuilderWrapper(fbm vertexBuilder) {
    this.vertexBuilder = vertexBuilder;
  }
  
  public fbm getVertexBuilder() {
    return this.vertexBuilder;
  }
  
  public void putSprite(gql sprite) {
    this.vertexBuilder.putSprite(sprite);
  }
  
  public void setSprite(gql sprite) {
    this.vertexBuilder.setSprite(sprite);
  }
  
  public boolean isMultiTexture() {
    return this.vertexBuilder.isMultiTexture();
  }
  
  public gfh getRenderType() {
    return this.vertexBuilder.getRenderType();
  }
  
  public Vector3f getTempVec3f() {
    return this.vertexBuilder.getTempVec3f();
  }
  
  public float[] getTempFloat4(float f1, float f2, float f3, float f4) {
    return this.vertexBuilder.getTempFloat4(f1, f2, f3, f4);
  }
  
  public int[] getTempInt4(int i1, int i2, int i3, int i4) {
    return this.vertexBuilder.getTempInt4(i1, i2, i3, i4);
  }
  
  public gez.a getRenderTypeBuffer() {
    return this.vertexBuilder.getRenderTypeBuffer();
  }
  
  public void setQuadVertexPositions(VertexPosition[] vps) {
    this.vertexBuilder.setQuadVertexPositions(vps);
  }
  
  public void setMidBlock(float mbx, float mby, float mbz) {
    this.vertexBuilder.setMidBlock(mbx, mby, mbz);
  }
  
  public int getVertexCount() {
    return this.vertexBuilder.getVertexCount();
  }
}
