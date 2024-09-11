package notch.net.optifine.render;

import gfh;
import gql;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.SpriteRenderData;
import net.optifine.util.ArrayUtils;

public class MultiTextureData {
  private SpriteRenderData[] spriteRenderDatas;
  
  private int vertexCount;
  
  private gfh blockLayer;
  
  private gql[] quadSprites;
  
  private SpriteRenderData[] spriteRenderDatasSorted;
  
  public MultiTextureData(SpriteRenderData[] spriteRenderDatas) {
    this.spriteRenderDatas = spriteRenderDatas;
  }
  
  public SpriteRenderData[] getSpriteRenderDatas() {
    return this.spriteRenderDatas;
  }
  
  public void setResortParameters(int vertexCountIn, gfh blockLayerIn, gql[] quadSpritesIn) {
    this.vertexCount = vertexCountIn;
    this.blockLayer = blockLayerIn;
    this.quadSprites = quadSpritesIn;
  }
  
  public void prepareSort(MultiTextureBuilder multiTextureBuilder, int[] quadOrdering) {
    this.spriteRenderDatasSorted = multiTextureBuilder.buildRenderDatas(this.vertexCount, this.blockLayer, this.quadSprites, quadOrdering);
  }
  
  public void applySort() {
    if (this.spriteRenderDatasSorted == null)
      return; 
    this.spriteRenderDatas = this.spriteRenderDatasSorted;
    this.spriteRenderDatasSorted = null;
  }
  
  public String toString() {
    return ArrayUtils.arrayToString((Object[])this.spriteRenderDatas);
  }
}
