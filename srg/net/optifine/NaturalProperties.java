package srg.net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.optifine.Config;

public class NaturalProperties {
  public int rotation = 1;
  
  public boolean flip = false;
  
  private Map[] quadMaps = new Map[8];
  
  public NaturalProperties(String type) {
    if (type.equals("4")) {
      this.rotation = 4;
      return;
    } 
    if (type.equals("2")) {
      this.rotation = 2;
      return;
    } 
    if (type.equals("F")) {
      this.flip = true;
      return;
    } 
    if (type.equals("4F")) {
      this.rotation = 4;
      this.flip = true;
      return;
    } 
    if (type.equals("2F")) {
      this.rotation = 2;
      this.flip = true;
      return;
    } 
    Config.warn("NaturalTextures: Unknown type: " + type);
  }
  
  public boolean isValid() {
    if (this.rotation == 2 || this.rotation == 4)
      return true; 
    if (this.flip)
      return true; 
    return false;
  }
  
  public synchronized BakedQuad getQuad(BakedQuad quadIn, int rotate, boolean flipU) {
    int index = rotate;
    if (flipU)
      index |= 0x4; 
    if (index <= 0 || index >= this.quadMaps.length)
      return quadIn; 
    Map<Object, Object> map = this.quadMaps[index];
    if (map == null) {
      map = new IdentityHashMap<>(1);
      this.quadMaps[index] = map;
    } 
    BakedQuad quad = (BakedQuad)map.get(quadIn);
    if (quad == null) {
      quad = makeQuad(quadIn, rotate, flipU);
      map.put(quadIn, quad);
    } 
    return quad;
  }
  
  private BakedQuad makeQuad(BakedQuad quad, int rotate, boolean flipU) {
    int[] vertexData = quad.getVertices();
    int tintIndex = quad.getTintIndex();
    Direction face = quad.getDirection();
    TextureAtlasSprite sprite = quad.getSprite();
    boolean shade = quad.isShade();
    if (!isFullSprite(quad))
      rotate = 0; 
    vertexData = transformVertexData(vertexData, rotate, flipU);
    BakedQuad bq = new BakedQuad(vertexData, tintIndex, face, sprite, shade);
    return bq;
  }
  
  private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
    int[] vertexData2 = (int[])vertexData.clone();
    int v2 = 4 - rotate;
    if (flipU)
      v2 += 3; 
    v2 %= 4;
    int step = vertexData2.length / 4;
    for (int v = 0; v < 4; v++) {
      int pos = v * step;
      int pos2 = v2 * step;
      vertexData2[pos2 + 4] = vertexData[pos + 4];
      vertexData2[pos2 + 4 + 1] = vertexData[pos + 4 + 1];
      if (flipU) {
        v2--;
        if (v2 < 0)
          v2 = 3; 
      } else {
        v2++;
        if (v2 > 3)
          v2 = 0; 
      } 
    } 
    return vertexData2;
  }
  
  private boolean isFullSprite(BakedQuad quad) {
    TextureAtlasSprite sprite = quad.getSprite();
    float uMin = sprite.getU0();
    float uMax = sprite.getU1();
    float uSize = uMax - uMin;
    float uDelta = uSize / 256.0F;
    float vMin = sprite.getV0();
    float vMax = sprite.getV1();
    float vSize = vMax - vMin;
    float vDelta = vSize / 256.0F;
    int[] vertexData = quad.getVertices();
    int step = vertexData.length / 4;
    for (int i = 0; i < 4; i++) {
      int pos = i * step;
      float u = Float.intBitsToFloat(vertexData[pos + 4]);
      float v = Float.intBitsToFloat(vertexData[pos + 4 + 1]);
      if (!equalsDelta(u, uMin, uDelta) && !equalsDelta(u, uMax, uDelta))
        return false; 
      if (!equalsDelta(v, vMin, vDelta) && !equalsDelta(v, vMax, vDelta))
        return false; 
    } 
    return true;
  }
  
  private boolean equalsDelta(float x1, float x2, float deltaMax) {
    float deltaAbs = Mth.abs(x1 - x2);
    return (deltaAbs < deltaMax);
  }
}
