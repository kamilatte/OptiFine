package notch.net.optifine.config;

import akr;
import dcw;
import ddw;
import fgo;
import fzf;
import jz;
import net.optifine.util.BiomeUtils;

public class BiomeId {
  private final akr resourceLocation;
  
  private fzf world;
  
  private ddw biome;
  
  private static fgo minecraft = fgo.Q();
  
  private BiomeId(akr resourceLocation) {
    this.resourceLocation = resourceLocation;
    this.world = minecraft.r;
    updateBiome();
  }
  
  private void updateBiome() {
    this.biome = null;
    jz<ddw> registry = BiomeUtils.getBiomeRegistry((dcw)this.world);
    if (!registry.d(this.resourceLocation))
      return; 
    this.biome = (ddw)registry.a(this.resourceLocation);
  }
  
  public ddw getBiome() {
    if (this.world != minecraft.r) {
      this.world = minecraft.r;
      updateBiome();
    } 
    return this.biome;
  }
  
  public akr getResourceLocation() {
    return this.resourceLocation;
  }
  
  public String toString() {
    return String.valueOf(this.resourceLocation);
  }
  
  public static net.optifine.config.BiomeId make(akr resourceLocation) {
    net.optifine.config.BiomeId bi = new net.optifine.config.BiomeId(resourceLocation);
    if (bi.biome == null)
      return null; 
    return bi;
  }
}
