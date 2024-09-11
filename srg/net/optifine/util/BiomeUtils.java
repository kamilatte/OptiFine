package srg.net.optifine.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;
import net.optifine.util.BiomeCategory;

public class BiomeUtils {
  private static Registry<Biome> defaultBiomeRegistry = makeDefaultBiomeRegistry();
  
  private static Registry<Biome> biomeRegistry = getBiomeRegistry((Level)(Minecraft.getInstance()).level);
  
  private static Level biomeWorld = (Level)(Minecraft.getInstance()).level;
  
  public static Biome PLAINS = (Biome)biomeRegistry.get(Biomes.PLAINS);
  
  public static Biome SUNFLOWER_PLAINS = (Biome)biomeRegistry.get(Biomes.SUNFLOWER_PLAINS);
  
  public static Biome SNOWY_PLAINS = (Biome)biomeRegistry.get(Biomes.SNOWY_PLAINS);
  
  public static Biome ICE_SPIKES = (Biome)biomeRegistry.get(Biomes.ICE_SPIKES);
  
  public static Biome DESERT = (Biome)biomeRegistry.get(Biomes.DESERT);
  
  public static Biome WINDSWEPT_HILLS = (Biome)biomeRegistry.get(Biomes.WINDSWEPT_HILLS);
  
  public static Biome WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.get(Biomes.WINDSWEPT_GRAVELLY_HILLS);
  
  public static Biome MUSHROOM_FIELDS = (Biome)biomeRegistry.get(Biomes.MUSHROOM_FIELDS);
  
  public static Biome SWAMP = (Biome)biomeRegistry.get(Biomes.SWAMP);
  
  public static Biome MANGROVE_SWAMP = (Biome)biomeRegistry.get(Biomes.MANGROVE_SWAMP);
  
  public static Biome THE_VOID = (Biome)biomeRegistry.get(Biomes.THE_VOID);
  
  public static void onWorldChanged(Level worldIn) {
    biomeRegistry = getBiomeRegistry(worldIn);
    biomeWorld = worldIn;
    PLAINS = (Biome)biomeRegistry.get(Biomes.PLAINS);
    SUNFLOWER_PLAINS = (Biome)biomeRegistry.get(Biomes.SUNFLOWER_PLAINS);
    SNOWY_PLAINS = (Biome)biomeRegistry.get(Biomes.SNOWY_PLAINS);
    ICE_SPIKES = (Biome)biomeRegistry.get(Biomes.ICE_SPIKES);
    DESERT = (Biome)biomeRegistry.get(Biomes.DESERT);
    WINDSWEPT_HILLS = (Biome)biomeRegistry.get(Biomes.WINDSWEPT_HILLS);
    WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.get(Biomes.WINDSWEPT_GRAVELLY_HILLS);
    MUSHROOM_FIELDS = (Biome)biomeRegistry.get(Biomes.MUSHROOM_FIELDS);
    SWAMP = (Biome)biomeRegistry.get(Biomes.SWAMP);
    MANGROVE_SWAMP = (Biome)biomeRegistry.get(Biomes.MANGROVE_SWAMP);
    THE_VOID = (Biome)biomeRegistry.get(Biomes.THE_VOID);
  }
  
  private static Biome getBiomeSafe(Registry<Biome> registry, ResourceKey<Biome> biomeKey, Supplier<Biome> biomeDefault) {
    Biome biome = (Biome)registry.get(biomeKey);
    if (biome == null)
      biome = biomeDefault.get(); 
    return biome;
  }
  
  public static Registry<Biome> getBiomeRegistry(Level worldIn) {
    if (worldIn != null) {
      if (worldIn == biomeWorld)
        return biomeRegistry; 
      Registry<Biome> worldBiomeRegistry = worldIn.registryAccess().registryOrThrow(Registries.BIOME);
      Registry<Biome> fixedBiomeRegistry = fixBiomeIds(defaultBiomeRegistry, worldBiomeRegistry);
      return fixedBiomeRegistry;
    } 
    return defaultBiomeRegistry;
  }
  
  private static Registry<Biome> makeDefaultBiomeRegistry() {
    MappedRegistry<Biome> registry = new MappedRegistry(ResourceKey.createRegistryKey(new ResourceLocation("biomes")), Lifecycle.stable(), true);
    Set<ResourceKey<Biome>> biomeKeys = Biomes.getBiomeKeys();
    for (ResourceKey<Biome> biomeKey : biomeKeys) {
      Biome.BiomeBuilder bb = new Biome.BiomeBuilder();
      bb.hasPrecipitation(false);
      bb.temperature(0.0F);
      bb.downfall(0.0F);
      bb.specialEffects((new BiomeSpecialEffects.Builder()).fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build());
      bb.mobSpawnSettings((new MobSpawnSettings.Builder()).build());
      bb.generationSettings((new BiomeGenerationSettings.Builder(null, null)).build());
      Biome biome = bb.build();
      registry.createIntrusiveHolder(biome);
      Holder.Reference reference = registry.register(biomeKey, biome, RegistrationInfo.BUILT_IN);
    } 
    return (Registry<Biome>)registry;
  }
  
  private static Registry<Biome> fixBiomeIds(Registry<Biome> idRegistry, Registry<Biome> valueRegistry) {
    MappedRegistry<Biome> registry = new MappedRegistry(ResourceKey.createRegistryKey(new ResourceLocation("biomes")), Lifecycle.stable(), true);
    Set<ResourceKey<Biome>> biomeKeys = Biomes.getBiomeKeys();
    for (ResourceKey<Biome> biomeKey : biomeKeys) {
      Biome biome = (Biome)valueRegistry.get(biomeKey);
      if (biome == null)
        biome = (Biome)idRegistry.get(biomeKey); 
      int id = idRegistry.getId(idRegistry.get(biomeKey));
      registry.createIntrusiveHolder(biome);
      Holder.Reference reference = registry.register(biomeKey, biome, RegistrationInfo.BUILT_IN);
    } 
    Set<ResourceKey<Biome>> biomeValueKeys = valueRegistry.registryKeySet();
    for (ResourceKey<Biome> biomeKey : biomeValueKeys) {
      if (registry.containsKey(biomeKey))
        continue; 
      Biome biome = (Biome)valueRegistry.get(biomeKey);
      registry.createIntrusiveHolder(biome);
      Holder.Reference reference = registry.register(biomeKey, biome, RegistrationInfo.BUILT_IN);
    } 
    return (Registry<Biome>)registry;
  }
  
  public static Registry<Biome> getBiomeRegistry() {
    return biomeRegistry;
  }
  
  public static ResourceLocation getLocation(Biome biome) {
    return getBiomeRegistry().getKey(biome);
  }
  
  public static int getId(Biome biome) {
    return getBiomeRegistry().getId(biome);
  }
  
  public static int getId(ResourceLocation loc) {
    Biome biome = getBiome(loc);
    return getBiomeRegistry().getId(biome);
  }
  
  public static BiomeId getBiomeId(ResourceLocation loc) {
    return BiomeId.make(loc);
  }
  
  public static Biome getBiome(ResourceLocation loc) {
    return (Biome)getBiomeRegistry().get(loc);
  }
  
  public static Set<ResourceLocation> getLocations() {
    return getBiomeRegistry().keySet();
  }
  
  public static List<Biome> getBiomes() {
    return Lists.newArrayList((Iterable)biomeRegistry);
  }
  
  public static List<BiomeId> getBiomeIds() {
    return getBiomeIds(getLocations());
  }
  
  public static List<BiomeId> getBiomeIds(Collection<ResourceLocation> locations) {
    List<BiomeId> biomeIds = new ArrayList<>();
    for (ResourceLocation loc : locations) {
      BiomeId bi = BiomeId.make(loc);
      if (bi == null)
        continue; 
      biomeIds.add(bi);
    } 
    return biomeIds;
  }
  
  public static Biome getBiome(BlockAndTintGetter lightReader, BlockPos blockPos) {
    Biome biome = PLAINS;
    if (lightReader instanceof ChunkCacheOF) {
      biome = ((ChunkCacheOF)lightReader).getBiome(blockPos);
    } else if (lightReader instanceof LevelReader) {
      biome = (Biome)((LevelReader)lightReader).getBiome(blockPos).value();
    } 
    return biome;
  }
  
  public static BiomeCategory getBiomeCategory(Holder<Biome> holder) {
    if (holder.value() == THE_VOID)
      return BiomeCategory.NONE; 
    if (holder.is(BiomeTags.IS_TAIGA))
      return BiomeCategory.TAIGA; 
    if (holder.value() == WINDSWEPT_HILLS || holder.value() == WINDSWEPT_GRAVELLY_HILLS)
      return BiomeCategory.EXTREME_HILLS; 
    if (holder.is(BiomeTags.IS_JUNGLE))
      return BiomeCategory.JUNGLE; 
    if (holder.is(BiomeTags.IS_BADLANDS))
      return BiomeCategory.MESA; 
    if (holder.value() == PLAINS || holder.value() == PLAINS)
      return BiomeCategory.PLAINS; 
    if (holder.is(BiomeTags.IS_SAVANNA))
      return BiomeCategory.SAVANNA; 
    if (holder.value() == SNOWY_PLAINS || holder.value() == ICE_SPIKES)
      return BiomeCategory.ICY; 
    if (holder.is(BiomeTags.IS_END))
      return BiomeCategory.THEEND; 
    if (holder.is(BiomeTags.IS_BEACH))
      return BiomeCategory.BEACH; 
    if (holder.is(BiomeTags.IS_FOREST))
      return BiomeCategory.FOREST; 
    if (holder.is(BiomeTags.IS_OCEAN))
      return BiomeCategory.OCEAN; 
    if (holder.value() == DESERT)
      return BiomeCategory.DESERT; 
    if (holder.is(BiomeTags.IS_RIVER))
      return BiomeCategory.RIVER; 
    if (holder.value() == SWAMP || holder.value() == MANGROVE_SWAMP)
      return BiomeCategory.SWAMP; 
    if (holder.value() == MUSHROOM_FIELDS)
      return BiomeCategory.MUSHROOM; 
    if (holder.is(BiomeTags.IS_NETHER))
      return BiomeCategory.NETHER; 
    if (holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))
      return BiomeCategory.UNDERGROUND; 
    if (holder.is(BiomeTags.IS_MOUNTAIN))
      return BiomeCategory.MOUNTAIN; 
    return BiomeCategory.PLAINS;
  }
  
  public static float getDownfall(Biome biome) {
    return Biomes.getDownfall(biome);
  }
  
  public static Biome.Precipitation getPrecipitation(Biome biome) {
    if (!biome.hasPrecipitation())
      return Biome.Precipitation.NONE; 
    if (biome.getBaseTemperature() < 0.1D)
      return Biome.Precipitation.SNOW; 
    return Biome.Precipitation.RAIN;
  }
}
