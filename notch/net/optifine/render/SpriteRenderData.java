package notch.net.optifine.render;

import gql;

public class SpriteRenderData {
  private gql sprite;
  
  private int[] positions;
  
  private int[] counts;
  
  public SpriteRenderData(gql sprite, int[] positions, int[] counts) {
    this.sprite = sprite;
    this.positions = positions;
    this.counts = counts;
    if (positions.length != counts.length)
      throw new IllegalArgumentException("" + positions.length + " != " + positions.length); 
  }
  
  public gql getSprite() {
    return this.sprite;
  }
  
  public int[] getPositions() {
    return this.positions;
  }
  
  public int[] getCounts() {
    return this.counts;
  }
  
  public String toString() {
    return String.valueOf(this.sprite.getName()) + ", " + String.valueOf(this.sprite.getName());
  }
}
