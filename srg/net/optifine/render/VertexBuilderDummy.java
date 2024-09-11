package srg.net.optifine.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;

public class VertexBuilderDummy implements VertexConsumer {
  private MultiBufferSource.BufferSource renderTypeBuffer = null;
  
  public VertexBuilderDummy(MultiBufferSource.BufferSource renderTypeBuffer) {
    this.renderTypeBuffer = renderTypeBuffer;
  }
  
  public MultiBufferSource.BufferSource getRenderTypeBuffer() {
    return this.renderTypeBuffer;
  }
  
  public VertexConsumer addVertex(float x, float y, float z) {
    return this;
  }
  
  public VertexConsumer setColor(int red, int green, int blue, int alpha) {
    return this;
  }
  
  public VertexConsumer setUv(float u, float v) {
    return this;
  }
  
  public VertexConsumer setUv1(int u, int v) {
    return this;
  }
  
  public VertexConsumer setUv2(int u, int v) {
    return this;
  }
  
  public VertexConsumer setNormal(float x, float y, float z) {
    return this;
  }
}
