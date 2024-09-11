package notch.net.optifine.config;

import dtc;
import net.optifine.Config;
import net.optifine.config.Matches;

public class MatchBlock {
  private int blockId = -1;
  
  private int[] metadatas = null;
  
  public MatchBlock(int blockId) {
    this.blockId = blockId;
  }
  
  public MatchBlock(int blockId, int metadata) {
    this.blockId = blockId;
    if (metadata >= 0)
      this.metadatas = new int[] { metadata }; 
  }
  
  public MatchBlock(int blockId, int[] metadatas) {
    this.blockId = blockId;
    this.metadatas = metadatas;
  }
  
  public int getBlockId() {
    return this.blockId;
  }
  
  public int[] getMetadatas() {
    return this.metadatas;
  }
  
  public boolean matches(dtc blockState) {
    if (blockState.getBlockId() != this.blockId)
      return false; 
    if (!Matches.metadata(blockState.getMetadata(), this.metadatas))
      return false; 
    return true;
  }
  
  public boolean matches(int id, int metadata) {
    if (id != this.blockId)
      return false; 
    if (!Matches.metadata(metadata, this.metadatas))
      return false; 
    return true;
  }
  
  public void addMetadata(int metadata) {
    if (this.metadatas == null)
      return; 
    if (metadata < 0)
      return; 
    for (int i = 0; i < this.metadatas.length; i++) {
      if (this.metadatas[i] == metadata)
        return; 
    } 
    this.metadatas = Config.addIntToArray(this.metadatas, metadata);
  }
  
  public void addMetadatas(int[] mds) {
    for (int i = 0; i < mds.length; i++) {
      int md = mds[i];
      addMetadata(md);
    } 
  }
  
  public String toString() {
    return "" + this.blockId + ":" + this.blockId;
  }
}
