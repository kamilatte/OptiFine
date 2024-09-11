package srg.net.optifine.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.optifine.util.BiomeUtils;

public class BiomeId {
  private final ResourceLocation resourceLocation;
  
  private ClientLevel world;
  
  private Biome biome;
  
  private static Minecraft minecraft = Minecraft.getInstance();
  
  private BiomeId(ResourceLocation resourceLocation) {
    this.resourceLocation = resourceLocation;
    this.world = minecraft.level;
    updateBiome();
  }
  
  private void updateBiome() {
    this.biome = null;
    Registry<Biome> registry = BiomeUtils.getBiomeRegistry((Level)this.world);
    if (!registry.containsKey(this.resourceLocation))
      return; 
    this.biome = (Biome)registry.get(this.resourceLocation);
  }
  
  public Biome getBiome() {
    if (this.world != minecraft.level) {
      this.world = minecraft.level;
      updateBiome();
    } 
    return this.biome;
  }
  
  public ResourceLocation getResourceLocation() {
    return this.resourceLocation;
  }
  
  public String toString() {
    return String.valueOf(this.resourceLocation);
  }
  
  public static net.optifine.config.BiomeId make(ResourceLocation resourceLocation) {
    net.optifine.config.BiomeId bi = new net.optifine.config.BiomeId(resourceLocation);
    if (bi.biome == null)
      return null; 
    return bi;
  }
}
