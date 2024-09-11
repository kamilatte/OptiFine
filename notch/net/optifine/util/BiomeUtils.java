package notch.net.optifine.util;

import akq;
import akr;
import awd;
import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import dbz;
import dcw;
import dcz;
import ddw;
import ddx;
import dec;
import ded;
import dei;
import fgo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import jd;
import jm;
import ju;
import jy;
import jz;
import lu;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;
import net.optifine.util.BiomeCategory;

public class BiomeUtils {
  private static jz<ddw> defaultBiomeRegistry = makeDefaultBiomeRegistry();
  
  private static jz<ddw> biomeRegistry = getBiomeRegistry((dcw)(fgo.Q()).r);
  
  private static dcw biomeWorld = (dcw)(fgo.Q()).r;
  
  public static ddw PLAINS = (ddw)biomeRegistry.a(ded.b);
  
  public static ddw SUNFLOWER_PLAINS = (ddw)biomeRegistry.a(ded.c);
  
  public static ddw SNOWY_PLAINS = (ddw)biomeRegistry.a(ded.d);
  
  public static ddw ICE_SPIKES = (ddw)biomeRegistry.a(ded.e);
  
  public static ddw DESERT = (ddw)biomeRegistry.a(ded.f);
  
  public static ddw WINDSWEPT_HILLS = (ddw)biomeRegistry.a(ded.t);
  
  public static ddw WINDSWEPT_GRAVELLY_HILLS = (ddw)biomeRegistry.a(ded.u);
  
  public static ddw MUSHROOM_FIELDS = (ddw)biomeRegistry.a(ded.Y);
  
  public static ddw SWAMP = (ddw)biomeRegistry.a(ded.g);
  
  public static ddw MANGROVE_SWAMP = (ddw)biomeRegistry.a(ded.h);
  
  public static ddw THE_VOID = (ddw)biomeRegistry.a(ded.a);
  
  public static void onWorldChanged(dcw worldIn) {
    biomeRegistry = getBiomeRegistry(worldIn);
    biomeWorld = worldIn;
    PLAINS = (ddw)biomeRegistry.a(ded.b);
    SUNFLOWER_PLAINS = (ddw)biomeRegistry.a(ded.c);
    SNOWY_PLAINS = (ddw)biomeRegistry.a(ded.d);
    ICE_SPIKES = (ddw)biomeRegistry.a(ded.e);
    DESERT = (ddw)biomeRegistry.a(ded.f);
    WINDSWEPT_HILLS = (ddw)biomeRegistry.a(ded.t);
    WINDSWEPT_GRAVELLY_HILLS = (ddw)biomeRegistry.a(ded.u);
    MUSHROOM_FIELDS = (ddw)biomeRegistry.a(ded.Y);
    SWAMP = (ddw)biomeRegistry.a(ded.g);
    MANGROVE_SWAMP = (ddw)biomeRegistry.a(ded.h);
    THE_VOID = (ddw)biomeRegistry.a(ded.a);
  }
  
  private static ddw getBiomeSafe(jz<ddw> registry, akq<ddw> biomeKey, Supplier<ddw> biomeDefault) {
    ddw biome = (ddw)registry.a(biomeKey);
    if (biome == null)
      biome = biomeDefault.get(); 
    return biome;
  }
  
  public static jz<ddw> getBiomeRegistry(dcw worldIn) {
    if (worldIn != null) {
      if (worldIn == biomeWorld)
        return biomeRegistry; 
      jz<ddw> worldBiomeRegistry = worldIn.H_().d(lu.aF);
      jz<ddw> fixedBiomeRegistry = fixBiomeIds(defaultBiomeRegistry, worldBiomeRegistry);
      return fixedBiomeRegistry;
    } 
    return defaultBiomeRegistry;
  }
  
  private static jz<ddw> makeDefaultBiomeRegistry() {
    ju<ddw> registry = new ju(akq.a(new akr("biomes")), Lifecycle.stable(), true);
    Set<akq<ddw>> biomeKeys = ded.getBiomeKeys();
    for (akq<ddw> biomeKey : biomeKeys) {
      ddw.a bb = new ddw.a();
      bb.a(false);
      bb.a(0.0F);
      bb.b(0.0F);
      bb.a((new dec.a()).a(0).b(0).c(0).d(0).a());
      bb.a((new dei.a()).a());
      bb.a((new ddx.a(null, null)).a());
      ddw biome = bb.a();
      registry.f(biome);
      jm.c c = registry.a(biomeKey, biome, jy.a);
    } 
    return (jz<ddw>)registry;
  }
  
  private static jz<ddw> fixBiomeIds(jz<ddw> idRegistry, jz<ddw> valueRegistry) {
    ju<ddw> registry = new ju(akq.a(new akr("biomes")), Lifecycle.stable(), true);
    Set<akq<ddw>> biomeKeys = ded.getBiomeKeys();
    for (akq<ddw> biomeKey : biomeKeys) {
      ddw biome = (ddw)valueRegistry.a(biomeKey);
      if (biome == null)
        biome = (ddw)idRegistry.a(biomeKey); 
      int id = idRegistry.a(idRegistry.a(biomeKey));
      registry.f(biome);
      jm.c c = registry.a(biomeKey, biome, jy.a);
    } 
    Set<akq<ddw>> biomeValueKeys = valueRegistry.g();
    for (akq<ddw> biomeKey : biomeValueKeys) {
      if (registry.d(biomeKey))
        continue; 
      ddw biome = (ddw)valueRegistry.a(biomeKey);
      registry.f(biome);
      jm.c c = registry.a(biomeKey, biome, jy.a);
    } 
    return (jz<ddw>)registry;
  }
  
  public static jz<ddw> getBiomeRegistry() {
    return biomeRegistry;
  }
  
  public static akr getLocation(ddw biome) {
    return getBiomeRegistry().b(biome);
  }
  
  public static int getId(ddw biome) {
    return getBiomeRegistry().a(biome);
  }
  
  public static int getId(akr loc) {
    ddw biome = getBiome(loc);
    return getBiomeRegistry().a(biome);
  }
  
  public static BiomeId getBiomeId(akr loc) {
    return BiomeId.make(loc);
  }
  
  public static ddw getBiome(akr loc) {
    return (ddw)getBiomeRegistry().a(loc);
  }
  
  public static Set<akr> getLocations() {
    return getBiomeRegistry().f();
  }
  
  public static List<ddw> getBiomes() {
    return Lists.newArrayList((Iterable)biomeRegistry);
  }
  
  public static List<BiomeId> getBiomeIds() {
    return getBiomeIds(getLocations());
  }
  
  public static List<BiomeId> getBiomeIds(Collection<akr> locations) {
    List<BiomeId> biomeIds = new ArrayList<>();
    for (akr loc : locations) {
      BiomeId bi = BiomeId.make(loc);
      if (bi == null)
        continue; 
      biomeIds.add(bi);
    } 
    return biomeIds;
  }
  
  public static ddw getBiome(dbz lightReader, jd blockPos) {
    ddw biome = PLAINS;
    if (lightReader instanceof ChunkCacheOF) {
      biome = ((ChunkCacheOF)lightReader).getBiome(blockPos);
    } else if (lightReader instanceof dcz) {
      biome = (ddw)((dcz)lightReader).t(blockPos).a();
    } 
    return biome;
  }
  
  public static BiomeCategory getBiomeCategory(jm<ddw> holder) {
    if (holder.a() == THE_VOID)
      return BiomeCategory.NONE; 
    if (holder.a(awd.h))
      return BiomeCategory.TAIGA; 
    if (holder.a() == WINDSWEPT_HILLS || holder.a() == WINDSWEPT_GRAVELLY_HILLS)
      return BiomeCategory.EXTREME_HILLS; 
    if (holder.a(awd.i))
      return BiomeCategory.JUNGLE; 
    if (holder.a(awd.f))
      return BiomeCategory.MESA; 
    if (holder.a() == PLAINS || holder.a() == PLAINS)
      return BiomeCategory.PLAINS; 
    if (holder.a(awd.k))
      return BiomeCategory.SAVANNA; 
    if (holder.a() == SNOWY_PLAINS || holder.a() == ICE_SPIKES)
      return BiomeCategory.ICY; 
    if (holder.a(awd.n))
      return BiomeCategory.THEEND; 
    if (holder.a(awd.c))
      return BiomeCategory.BEACH; 
    if (holder.a(awd.j))
      return BiomeCategory.FOREST; 
    if (holder.a(awd.b))
      return BiomeCategory.OCEAN; 
    if (holder.a() == DESERT)
      return BiomeCategory.DESERT; 
    if (holder.a(awd.d))
      return BiomeCategory.RIVER; 
    if (holder.a() == SWAMP || holder.a() == MANGROVE_SWAMP)
      return BiomeCategory.SWAMP; 
    if (holder.a() == MUSHROOM_FIELDS)
      return BiomeCategory.MUSHROOM; 
    if (holder.a(awd.m))
      return BiomeCategory.NETHER; 
    if (holder.a(awd.Z))
      return BiomeCategory.UNDERGROUND; 
    if (holder.a(awd.e))
      return BiomeCategory.MOUNTAIN; 
    return BiomeCategory.PLAINS;
  }
  
  public static float getDownfall(ddw biome) {
    return ded.getDownfall(biome);
  }
  
  public static ddw.c getPrecipitation(ddw biome) {
    if (!biome.c())
      return ddw.c.a; 
    if (biome.g() < 0.1D)
      return ddw.c.c; 
    return ddw.c.b;
  }
}
