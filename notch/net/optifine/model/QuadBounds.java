package notch.net.optifine.model;

import ji;

public class QuadBounds {
  private float minX = Float.MAX_VALUE;
  
  private float minY = Float.MAX_VALUE;
  
  private float minZ = Float.MAX_VALUE;
  
  private float maxX = -3.4028235E38F;
  
  private float maxY = -3.4028235E38F;
  
  private float maxZ = -3.4028235E38F;
  
  public QuadBounds(int[] vertexData) {
    int step = vertexData.length / 4;
    for (int i = 0; i < 4; i++) {
      int pos = i * step;
      float x = Float.intBitsToFloat(vertexData[pos + 0]);
      float y = Float.intBitsToFloat(vertexData[pos + 1]);
      float z = Float.intBitsToFloat(vertexData[pos + 2]);
      if (this.minX > x)
        this.minX = x; 
      if (this.minY > y)
        this.minY = y; 
      if (this.minZ > z)
        this.minZ = z; 
      if (this.maxX < x)
        this.maxX = x; 
      if (this.maxY < y)
        this.maxY = y; 
      if (this.maxZ < z)
        this.maxZ = z; 
    } 
  }
  
  public float getMinX() {
    return this.minX;
  }
  
  public float getMinY() {
    return this.minY;
  }
  
  public float getMinZ() {
    return this.minZ;
  }
  
  public float getMaxX() {
    return this.maxX;
  }
  
  public float getMaxY() {
    return this.maxY;
  }
  
  public float getMaxZ() {
    return this.maxZ;
  }
  
  public boolean isFaceQuad(ji face) {
    float min;
    float max;
    float val;
    switch (null.$SwitchMap$net$minecraft$core$Direction[face.ordinal()]) {
      case 1:
        min = getMinY();
        max = getMaxY();
        val = 0.0F;
        break;
      case 2:
        min = getMinY();
        max = getMaxY();
        val = 1.0F;
        break;
      case 3:
        min = getMinZ();
        max = getMaxZ();
        val = 0.0F;
        break;
      case 4:
        min = getMinZ();
        max = getMaxZ();
        val = 1.0F;
        break;
      case 5:
        min = getMinX();
        max = getMaxX();
        val = 0.0F;
        break;
      case 6:
        min = getMinX();
        max = getMaxX();
        val = 1.0F;
        break;
      default:
        return false;
    } 
    return (min == val && max == val);
  }
  
  public boolean isFullQuad(ji face) {
    float min1;
    float max1;
    float min2;
    float max2;
    switch (null.$SwitchMap$net$minecraft$core$Direction[face.ordinal()]) {
      case 1:
      case 2:
        min1 = getMinX();
        max1 = getMaxX();
        min2 = getMinZ();
        max2 = getMaxZ();
        break;
      case 3:
      case 4:
        min1 = getMinX();
        max1 = getMaxX();
        min2 = getMinY();
        max2 = getMaxY();
        break;
      case 5:
      case 6:
        min1 = getMinY();
        max1 = getMaxY();
        min2 = getMinZ();
        max2 = getMaxZ();
        break;
      default:
        return false;
    } 
    return (min1 == 0.0F && max1 == 1.0F && min2 == 0.0F && max2 == 1.0F);
  }
}
