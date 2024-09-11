package notch.net.optifine.config;

import ddw;
import dtc;
import gql;
import net.optifine.config.BiomeId;
import net.optifine.config.MatchBlock;

public class Matches {
  public static boolean block(dtc blockStateBase, MatchBlock[] matchBlocks) {
    if (matchBlocks == null)
      return true; 
    for (int i = 0; i < matchBlocks.length; i++) {
      MatchBlock mb = matchBlocks[i];
      if (mb.matches(blockStateBase))
        return true; 
    } 
    return false;
  }
  
  public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
    if (matchBlocks == null)
      return true; 
    for (int i = 0; i < matchBlocks.length; i++) {
      MatchBlock mb = matchBlocks[i];
      if (mb.matches(blockId, metadata))
        return true; 
    } 
    return false;
  }
  
  public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
    if (matchBlocks == null)
      return true; 
    for (int i = 0; i < matchBlocks.length; i++) {
      MatchBlock mb = matchBlocks[i];
      if (mb.getBlockId() == blockId)
        return true; 
    } 
    return false;
  }
  
  public static boolean metadata(int metadata, int[] metadatas) {
    if (metadatas == null)
      return true; 
    for (int i = 0; i < metadatas.length; i++) {
      if (metadatas[i] == metadata)
        return true; 
    } 
    return false;
  }
  
  public static boolean sprite(gql sprite, gql[] sprites) {
    if (sprites == null)
      return true; 
    for (int i = 0; i < sprites.length; i++) {
      if (sprites[i] == sprite)
        return true; 
    } 
    return false;
  }
  
  public static boolean biome(ddw biome, BiomeId[] biomes) {
    if (biomes == null)
      return true; 
    for (int i = 0; i < biomes.length; i++) {
      BiomeId bi = biomes[i];
      if (bi != null)
        if (bi.getBiome() == biome)
          return true;  
    } 
    return false;
  }
}