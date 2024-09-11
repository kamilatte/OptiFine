package srg.net.optifine.config;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.optifine.config.IterableOptionBool;
import net.optifine.config.IterableOptionInt;
import net.optifine.config.IteratableOptionOF;
import net.optifine.config.OptionValueInt;
import net.optifine.config.SliderPercentageOptionOF;

public class Option {
  public static final OptionValueInt DEFAULT = new OptionValueInt(0, "generator.minecraft.normal");
  
  public static final OptionValueInt FAST = new OptionValueInt(1, "options.graphics.fast");
  
  public static final OptionValueInt FANCY = new OptionValueInt(2, "options.graphics.fancy");
  
  public static final OptionValueInt OFF = new OptionValueInt(3, "options.off");
  
  public static final OptionValueInt SMART = new OptionValueInt(4, "of.general.smart");
  
  public static final OptionValueInt COMPACT = new OptionValueInt(5, "of.general.compact");
  
  public static final OptionValueInt FULL = new OptionValueInt(6, "of.general.full");
  
  public static final OptionValueInt DETAILED = new OptionValueInt(7, "of.general.detailed");
  
  public static final OptionValueInt[] OFF_COMPACT_FULL = new OptionValueInt[] { OFF, COMPACT, FULL };
  
  public static final OptionValueInt[] COMPACT_FULL_DETAILED = new OptionValueInt[] { COMPACT, FULL, DETAILED };
  
  public static final OptionValueInt ANIM_ON = new OptionValueInt(0, "options.on");
  
  public static final OptionValueInt ANIM_GENERATED = new OptionValueInt(1, "of.options.animation.dynamic");
  
  public static final OptionValueInt ANIM_OFF = new OptionValueInt(2, "options.off");
  
  public static final OptionInstance FOG_FANCY = (OptionInstance)new IteratableOptionOF("of.options.FOG_FANCY");
  
  public static final OptionInstance FOG_START = (OptionInstance)new IteratableOptionOF("of.options.FOG_START");
  
  public static final OptionInstance MIPMAP_TYPE = (OptionInstance)new SliderPercentageOptionOF("of.options.MIPMAP_TYPE", 0, 3, 1, 3);
  
  public static final OptionInstance SMOOTH_FPS = (OptionInstance)new IteratableOptionOF("of.options.SMOOTH_FPS");
  
  public static final OptionInstance CLOUDS = (OptionInstance)new IteratableOptionOF("of.options.CLOUDS");
  
  public static final OptionInstance CLOUD_HEIGHT = (OptionInstance)new SliderPercentageOptionOF("of.options.CLOUD_HEIGHT", 0.0D);
  
  public static final OptionInstance TREES = (OptionInstance)new IteratableOptionOF("of.options.TREES");
  
  public static final OptionInstance RAIN = (OptionInstance)new IteratableOptionOF("of.options.RAIN");
  
  public static final OptionInstance ANIMATED_WATER = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_WATER");
  
  public static final OptionInstance ANIMATED_LAVA = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_LAVA");
  
  public static final OptionInstance ANIMATED_FIRE = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_FIRE");
  
  public static final OptionInstance ANIMATED_PORTAL = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_PORTAL");
  
  public static final OptionInstance AO_LEVEL = (OptionInstance)new SliderPercentageOptionOF("of.options.AO_LEVEL", 1.0D);
  
  public static final OptionInstance LAGOMETER = (OptionInstance)new IteratableOptionOF("of.options.LAGOMETER");
  
  public static final OptionInstance AUTOSAVE_TICKS = (OptionInstance)new IteratableOptionOF("of.options.AUTOSAVE_TICKS");
  
  public static final OptionInstance BETTER_GRASS = (OptionInstance)new IteratableOptionOF("of.options.BETTER_GRASS");
  
  public static final OptionInstance ANIMATED_REDSTONE = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_REDSTONE");
  
  public static final OptionInstance ANIMATED_EXPLOSION = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_EXPLOSION");
  
  public static final OptionInstance ANIMATED_FLAME = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_FLAME");
  
  public static final OptionInstance ANIMATED_SMOKE = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_SMOKE");
  
  public static final OptionInstance WEATHER = (OptionInstance)new IteratableOptionOF("of.options.WEATHER");
  
  public static final OptionInstance SKY = (OptionInstance)new IteratableOptionOF("of.options.SKY");
  
  public static final OptionInstance STARS = (OptionInstance)new IteratableOptionOF("of.options.STARS");
  
  public static final OptionInstance SUN_MOON = (OptionInstance)new IteratableOptionOF("of.options.SUN_MOON");
  
  public static final OptionInstance VIGNETTE = (OptionInstance)new IteratableOptionOF("of.options.VIGNETTE");
  
  public static final OptionInstance CHUNK_UPDATES = (OptionInstance)new IteratableOptionOF("of.options.CHUNK_UPDATES");
  
  public static final OptionInstance CHUNK_UPDATES_DYNAMIC = (OptionInstance)new IteratableOptionOF("of.options.CHUNK_UPDATES_DYNAMIC");
  
  public static final OptionInstance TIME = (OptionInstance)new IteratableOptionOF("of.options.TIME");
  
  public static final OptionInstance SMOOTH_WORLD = (OptionInstance)new IteratableOptionOF("of.options.SMOOTH_WORLD");
  
  public static final OptionInstance VOID_PARTICLES = (OptionInstance)new IteratableOptionOF("of.options.VOID_PARTICLES");
  
  public static final OptionInstance WATER_PARTICLES = (OptionInstance)new IteratableOptionOF("of.options.WATER_PARTICLES");
  
  public static final OptionInstance RAIN_SPLASH = (OptionInstance)new IteratableOptionOF("of.options.RAIN_SPLASH");
  
  public static final OptionInstance PORTAL_PARTICLES = (OptionInstance)new IteratableOptionOF("of.options.PORTAL_PARTICLES");
  
  public static final OptionInstance POTION_PARTICLES = (OptionInstance)new IteratableOptionOF("of.options.POTION_PARTICLES");
  
  public static final OptionInstance FIREWORK_PARTICLES = (OptionInstance)new IteratableOptionOF("of.options.FIREWORK_PARTICLES");
  
  public static final OptionInstance PROFILER = (OptionInstance)new IteratableOptionOF("of.options.PROFILER");
  
  public static final OptionInstance DRIPPING_WATER_LAVA = (OptionInstance)new IteratableOptionOF("of.options.DRIPPING_WATER_LAVA");
  
  public static final OptionInstance BETTER_SNOW = (OptionInstance)new IteratableOptionOF("of.options.BETTER_SNOW");
  
  public static final OptionInstance ANIMATED_TERRAIN = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_TERRAIN");
  
  public static final OptionInstance SWAMP_COLORS = (OptionInstance)new IteratableOptionOF("of.options.SWAMP_COLORS");
  
  public static final OptionInstance RANDOM_ENTITIES = (OptionInstance)new IteratableOptionOF("of.options.RANDOM_ENTITIES");
  
  public static final OptionInstance SMOOTH_BIOMES = (OptionInstance)new IteratableOptionOF("of.options.SMOOTH_BIOMES");
  
  public static final OptionInstance CUSTOM_FONTS = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_FONTS");
  
  public static final OptionInstance CUSTOM_COLORS = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_COLORS");
  
  public static final OptionInstance SHOW_CAPES = (OptionInstance)new IteratableOptionOF("of.options.SHOW_CAPES");
  
  public static final OptionInstance CONNECTED_TEXTURES = (OptionInstance)new IteratableOptionOF("of.options.CONNECTED_TEXTURES");
  
  public static final OptionInstance CUSTOM_ITEMS = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_ITEMS");
  
  public static final OptionInstance AA_LEVEL = (OptionInstance)new SliderPercentageOptionOF("of.options.AA_LEVEL", 0, 16, new int[] { 0, 2, 4, 6, 8, 12, 16 }, 0);
  
  public static final OptionInstance AF_LEVEL = (OptionInstance)new SliderPercentageOptionOF("of.options.AF_LEVEL", 1, 16, new int[] { 1, 2, 4, 8, 16 }, 1);
  
  public static final OptionInstance ANIMATED_TEXTURES = (OptionInstance)new IteratableOptionOF("of.options.ANIMATED_TEXTURES");
  
  public static final OptionInstance NATURAL_TEXTURES = (OptionInstance)new IteratableOptionOF("of.options.NATURAL_TEXTURES");
  
  public static final OptionInstance EMISSIVE_TEXTURES = (OptionInstance)new IteratableOptionOF("of.options.EMISSIVE_TEXTURES");
  
  public static final OptionInstance HELD_ITEM_TOOLTIPS = (OptionInstance)new IteratableOptionOF("of.options.HELD_ITEM_TOOLTIPS");
  
  public static final OptionInstance LAZY_CHUNK_LOADING = (OptionInstance)new IteratableOptionOF("of.options.LAZY_CHUNK_LOADING");
  
  public static final OptionInstance CUSTOM_SKY = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_SKY");
  
  public static final OptionInstance FAST_MATH = (OptionInstance)new IteratableOptionOF("of.options.FAST_MATH");
  
  public static final OptionInstance FAST_RENDER = (OptionInstance)new IteratableOptionOF("of.options.FAST_RENDER");
  
  public static final OptionInstance DYNAMIC_FOV = (OptionInstance)new IteratableOptionOF("of.options.DYNAMIC_FOV");
  
  public static final OptionInstance DYNAMIC_LIGHTS = (OptionInstance)new IteratableOptionOF("of.options.DYNAMIC_LIGHTS");
  
  public static final OptionInstance ALTERNATE_BLOCKS = (OptionInstance)new IteratableOptionOF("of.options.ALTERNATE_BLOCKS");
  
  public static final OptionInstance CUSTOM_ENTITY_MODELS = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_ENTITY_MODELS");
  
  public static final OptionInstance ADVANCED_TOOLTIPS = (OptionInstance)new IteratableOptionOF("of.options.ADVANCED_TOOLTIPS");
  
  public static final OptionInstance SCREENSHOT_SIZE = (OptionInstance)new IteratableOptionOF("of.options.SCREENSHOT_SIZE");
  
  public static final OptionInstance CUSTOM_GUIS = (OptionInstance)new IteratableOptionOF("of.options.CUSTOM_GUIS");
  
  public static final OptionInstance RENDER_REGIONS = (OptionInstance)new IteratableOptionOF("of.options.RENDER_REGIONS");
  
  public static final OptionInstance SHOW_GL_ERRORS = (OptionInstance)new IteratableOptionOF("of.options.SHOW_GL_ERRORS");
  
  public static final OptionInstance SMART_ANIMATIONS = (OptionInstance)new IteratableOptionOF("of.options.SMART_ANIMATIONS");
  
  public static final OptionInstance CHAT_BACKGROUND = (OptionInstance)new IteratableOptionOF("of.options.CHAT_BACKGROUND");
  
  public static final OptionInstance CHAT_SHADOW = (OptionInstance)new IteratableOptionOF("of.options.CHAT_SHADOW");
  
  public static final OptionInstance TELEMETRY = (OptionInstance)new IteratableOptionOF("of.options.TELEMETRY");
  
  public static final OptionInstance QUICK_INFO;
  
  public static final OptionInstance QUICK_INFO_FPS;
  
  public static final OptionInstance QUICK_INFO_CHUNKS;
  
  public static final OptionInstance QUICK_INFO_ENTITIES;
  
  public static final OptionInstance QUICK_INFO_PARTICLES;
  
  public static final OptionInstance QUICK_INFO_UPDATES;
  
  public static final OptionInstance QUICK_INFO_GPU;
  
  public static final OptionInstance QUICK_INFO_POS;
  
  public static final OptionInstance QUICK_INFO_FACING;
  
  public static final OptionInstance QUICK_INFO_BIOME;
  
  public static final OptionInstance QUICK_INFO_LIGHT;
  
  public static final OptionInstance QUICK_INFO_MEMORY;
  
  public static final OptionInstance QUICK_INFO_NATIVE_MEMORY;
  
  public static final OptionInstance QUICK_INFO_TARGET_BLOCK;
  
  public static final OptionInstance QUICK_INFO_TARGET_FLUID;
  
  public static final OptionInstance QUICK_INFO_TARGET_ENTITY;
  
  public static final OptionInstance QUICK_INFO_LABELS;
  
  public static final OptionInstance QUICK_INFO_BACKGROUND;
  
  static {
    QUICK_INFO = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO", opts -> opts.ofQuickInfo, (opts, val) -> opts.ofQuickInfo = val.booleanValue(), "ofQuickInfo");
    QUICK_INFO_FPS = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_FPS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFps, (opts, val) -> opts.ofQuickInfoFps = val, "ofQuickInfoFps");
    QUICK_INFO_CHUNKS = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_CHUNKS", opts -> opts.ofQuickInfoChunks, (opts, val) -> opts.ofQuickInfoChunks = val.booleanValue(), "ofQuickInfoChunks");
    QUICK_INFO_ENTITIES = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_ENTITIES", opts -> opts.ofQuickInfoEntities, (opts, val) -> opts.ofQuickInfoEntities = val.booleanValue(), "ofQuickInfoEntities");
    QUICK_INFO_PARTICLES = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_PARTICLES", opts -> opts.ofQuickInfoParticles, (opts, val) -> opts.ofQuickInfoParticles = val.booleanValue(), "ofQuickInfoParticles");
    QUICK_INFO_UPDATES = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_UPDATES", opts -> opts.ofQuickInfoUpdates, (opts, val) -> opts.ofQuickInfoUpdates = val.booleanValue(), "ofQuickInfoUpdates");
    QUICK_INFO_GPU = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_GPU", opts -> opts.ofQuickInfoGpu, (opts, val) -> opts.ofQuickInfoGpu = val.booleanValue(), "ofQuickInfoGpu");
    QUICK_INFO_POS = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_POS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoPos, (opts, val) -> opts.ofQuickInfoPos = val, "ofQuickInfoPos");
    QUICK_INFO_FACING = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_FACING", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFacing, (opts, val) -> opts.ofQuickInfoFacing = val, "ofQuickInfoFacing");
    QUICK_INFO_BIOME = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_BIOME", opts -> opts.ofQuickInfoBiome, (opts, val) -> opts.ofQuickInfoBiome = val.booleanValue(), "ofQuickInfoBiome");
    QUICK_INFO_LIGHT = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_LIGHT", opts -> opts.ofQuickInfoLight, (opts, val) -> opts.ofQuickInfoLight = val.booleanValue(), "ofQuickInfoLight");
    QUICK_INFO_MEMORY = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_MEMORY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoMemory, (opts, val) -> opts.ofQuickInfoMemory = val, "ofQuickInfoMemory");
    QUICK_INFO_NATIVE_MEMORY = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_NATIVE_MEMORY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoNativeMemory, (opts, val) -> opts.ofQuickInfoNativeMemory = val, "ofQuickInfoNativeMemory");
    QUICK_INFO_TARGET_BLOCK = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_TARGET_BLOCK", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetBlock, (opts, val) -> opts.ofQuickInfoTargetBlock = val, "ofQuickInfoTargetBlock");
    QUICK_INFO_TARGET_FLUID = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_TARGET_FLUID", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetFluid, (opts, val) -> opts.ofQuickInfoTargetFluid = val, "ofQuickInfoTargetFluid");
    QUICK_INFO_TARGET_ENTITY = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_TARGET_ENTITY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetEntity, (opts, val) -> opts.ofQuickInfoTargetEntity = val, "ofQuickInfoTargetEntity");
    QUICK_INFO_LABELS = (OptionInstance)new IterableOptionInt("of.options.QUICK_INFO_LABELS", COMPACT_FULL_DETAILED, opts -> opts.ofQuickInfoLabels, (opts, val) -> opts.ofQuickInfoLabels = val, "ofQuickInfoLabels");
    QUICK_INFO_BACKGROUND = (OptionInstance)new IterableOptionBool("of.options.QUICK_INFO_BACKGROUND", opts -> opts.ofQuickInfoBackground, (opts, val) -> opts.ofQuickInfoBackground = val.booleanValue(), "ofQuickInfoBackground");
  }
  
  public static boolean isDefault(int value) {
    return (value == DEFAULT.getValue());
  }
  
  public static boolean isFast(int value) {
    return (value == FAST.getValue());
  }
  
  public static boolean isFancy(int value) {
    return (value == FANCY.getValue());
  }
  
  public static boolean isOff(int value) {
    return (value == OFF.getValue());
  }
  
  public static boolean isSmart(int value) {
    return (value == SMART.getValue());
  }
  
  public static boolean isCompact(int value) {
    return (value == COMPACT.getValue());
  }
  
  public static boolean isFull(int value) {
    return (value == FULL.getValue());
  }
  
  public static boolean isDetailed(int value) {
    return (value == DETAILED.getValue());
  }
}
