package notch.net.optifine.config;

import fgr;
import fgs;
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
  
  public static final fgr FOG_FANCY = (fgr)new IteratableOptionOF("of.options.FOG_FANCY");
  
  public static final fgr FOG_START = (fgr)new IteratableOptionOF("of.options.FOG_START");
  
  public static final fgr MIPMAP_TYPE = (fgr)new SliderPercentageOptionOF("of.options.MIPMAP_TYPE", 0, 3, 1, 3);
  
  public static final fgr SMOOTH_FPS = (fgr)new IteratableOptionOF("of.options.SMOOTH_FPS");
  
  public static final fgr CLOUDS = (fgr)new IteratableOptionOF("of.options.CLOUDS");
  
  public static final fgr CLOUD_HEIGHT = (fgr)new SliderPercentageOptionOF("of.options.CLOUD_HEIGHT", 0.0D);
  
  public static final fgr TREES = (fgr)new IteratableOptionOF("of.options.TREES");
  
  public static final fgr RAIN = (fgr)new IteratableOptionOF("of.options.RAIN");
  
  public static final fgr ANIMATED_WATER = (fgr)new IteratableOptionOF("of.options.ANIMATED_WATER");
  
  public static final fgr ANIMATED_LAVA = (fgr)new IteratableOptionOF("of.options.ANIMATED_LAVA");
  
  public static final fgr ANIMATED_FIRE = (fgr)new IteratableOptionOF("of.options.ANIMATED_FIRE");
  
  public static final fgr ANIMATED_PORTAL = (fgr)new IteratableOptionOF("of.options.ANIMATED_PORTAL");
  
  public static final fgr AO_LEVEL = (fgr)new SliderPercentageOptionOF("of.options.AO_LEVEL", 1.0D);
  
  public static final fgr LAGOMETER = (fgr)new IteratableOptionOF("of.options.LAGOMETER");
  
  public static final fgr AUTOSAVE_TICKS = (fgr)new IteratableOptionOF("of.options.AUTOSAVE_TICKS");
  
  public static final fgr BETTER_GRASS = (fgr)new IteratableOptionOF("of.options.BETTER_GRASS");
  
  public static final fgr ANIMATED_REDSTONE = (fgr)new IteratableOptionOF("of.options.ANIMATED_REDSTONE");
  
  public static final fgr ANIMATED_EXPLOSION = (fgr)new IteratableOptionOF("of.options.ANIMATED_EXPLOSION");
  
  public static final fgr ANIMATED_FLAME = (fgr)new IteratableOptionOF("of.options.ANIMATED_FLAME");
  
  public static final fgr ANIMATED_SMOKE = (fgr)new IteratableOptionOF("of.options.ANIMATED_SMOKE");
  
  public static final fgr WEATHER = (fgr)new IteratableOptionOF("of.options.WEATHER");
  
  public static final fgr SKY = (fgr)new IteratableOptionOF("of.options.SKY");
  
  public static final fgr STARS = (fgr)new IteratableOptionOF("of.options.STARS");
  
  public static final fgr SUN_MOON = (fgr)new IteratableOptionOF("of.options.SUN_MOON");
  
  public static final fgr VIGNETTE = (fgr)new IteratableOptionOF("of.options.VIGNETTE");
  
  public static final fgr CHUNK_UPDATES = (fgr)new IteratableOptionOF("of.options.CHUNK_UPDATES");
  
  public static final fgr CHUNK_UPDATES_DYNAMIC = (fgr)new IteratableOptionOF("of.options.CHUNK_UPDATES_DYNAMIC");
  
  public static final fgr TIME = (fgr)new IteratableOptionOF("of.options.TIME");
  
  public static final fgr SMOOTH_WORLD = (fgr)new IteratableOptionOF("of.options.SMOOTH_WORLD");
  
  public static final fgr VOID_PARTICLES = (fgr)new IteratableOptionOF("of.options.VOID_PARTICLES");
  
  public static final fgr WATER_PARTICLES = (fgr)new IteratableOptionOF("of.options.WATER_PARTICLES");
  
  public static final fgr RAIN_SPLASH = (fgr)new IteratableOptionOF("of.options.RAIN_SPLASH");
  
  public static final fgr PORTAL_PARTICLES = (fgr)new IteratableOptionOF("of.options.PORTAL_PARTICLES");
  
  public static final fgr POTION_PARTICLES = (fgr)new IteratableOptionOF("of.options.POTION_PARTICLES");
  
  public static final fgr FIREWORK_PARTICLES = (fgr)new IteratableOptionOF("of.options.FIREWORK_PARTICLES");
  
  public static final fgr PROFILER = (fgr)new IteratableOptionOF("of.options.PROFILER");
  
  public static final fgr DRIPPING_WATER_LAVA = (fgr)new IteratableOptionOF("of.options.DRIPPING_WATER_LAVA");
  
  public static final fgr BETTER_SNOW = (fgr)new IteratableOptionOF("of.options.BETTER_SNOW");
  
  public static final fgr ANIMATED_TERRAIN = (fgr)new IteratableOptionOF("of.options.ANIMATED_TERRAIN");
  
  public static final fgr SWAMP_COLORS = (fgr)new IteratableOptionOF("of.options.SWAMP_COLORS");
  
  public static final fgr RANDOM_ENTITIES = (fgr)new IteratableOptionOF("of.options.RANDOM_ENTITIES");
  
  public static final fgr SMOOTH_BIOMES = (fgr)new IteratableOptionOF("of.options.SMOOTH_BIOMES");
  
  public static final fgr CUSTOM_FONTS = (fgr)new IteratableOptionOF("of.options.CUSTOM_FONTS");
  
  public static final fgr CUSTOM_COLORS = (fgr)new IteratableOptionOF("of.options.CUSTOM_COLORS");
  
  public static final fgr SHOW_CAPES = (fgr)new IteratableOptionOF("of.options.SHOW_CAPES");
  
  public static final fgr CONNECTED_TEXTURES = (fgr)new IteratableOptionOF("of.options.CONNECTED_TEXTURES");
  
  public static final fgr CUSTOM_ITEMS = (fgr)new IteratableOptionOF("of.options.CUSTOM_ITEMS");
  
  public static final fgr AA_LEVEL = (fgr)new SliderPercentageOptionOF("of.options.AA_LEVEL", 0, 16, new int[] { 0, 2, 4, 6, 8, 12, 16 }, 0);
  
  public static final fgr AF_LEVEL = (fgr)new SliderPercentageOptionOF("of.options.AF_LEVEL", 1, 16, new int[] { 1, 2, 4, 8, 16 }, 1);
  
  public static final fgr ANIMATED_TEXTURES = (fgr)new IteratableOptionOF("of.options.ANIMATED_TEXTURES");
  
  public static final fgr NATURAL_TEXTURES = (fgr)new IteratableOptionOF("of.options.NATURAL_TEXTURES");
  
  public static final fgr EMISSIVE_TEXTURES = (fgr)new IteratableOptionOF("of.options.EMISSIVE_TEXTURES");
  
  public static final fgr HELD_ITEM_TOOLTIPS = (fgr)new IteratableOptionOF("of.options.HELD_ITEM_TOOLTIPS");
  
  public static final fgr LAZY_CHUNK_LOADING = (fgr)new IteratableOptionOF("of.options.LAZY_CHUNK_LOADING");
  
  public static final fgr CUSTOM_SKY = (fgr)new IteratableOptionOF("of.options.CUSTOM_SKY");
  
  public static final fgr FAST_MATH = (fgr)new IteratableOptionOF("of.options.FAST_MATH");
  
  public static final fgr FAST_RENDER = (fgr)new IteratableOptionOF("of.options.FAST_RENDER");
  
  public static final fgr DYNAMIC_FOV = (fgr)new IteratableOptionOF("of.options.DYNAMIC_FOV");
  
  public static final fgr DYNAMIC_LIGHTS = (fgr)new IteratableOptionOF("of.options.DYNAMIC_LIGHTS");
  
  public static final fgr ALTERNATE_BLOCKS = (fgr)new IteratableOptionOF("of.options.ALTERNATE_BLOCKS");
  
  public static final fgr CUSTOM_ENTITY_MODELS = (fgr)new IteratableOptionOF("of.options.CUSTOM_ENTITY_MODELS");
  
  public static final fgr ADVANCED_TOOLTIPS = (fgr)new IteratableOptionOF("of.options.ADVANCED_TOOLTIPS");
  
  public static final fgr SCREENSHOT_SIZE = (fgr)new IteratableOptionOF("of.options.SCREENSHOT_SIZE");
  
  public static final fgr CUSTOM_GUIS = (fgr)new IteratableOptionOF("of.options.CUSTOM_GUIS");
  
  public static final fgr RENDER_REGIONS = (fgr)new IteratableOptionOF("of.options.RENDER_REGIONS");
  
  public static final fgr SHOW_GL_ERRORS = (fgr)new IteratableOptionOF("of.options.SHOW_GL_ERRORS");
  
  public static final fgr SMART_ANIMATIONS = (fgr)new IteratableOptionOF("of.options.SMART_ANIMATIONS");
  
  public static final fgr CHAT_BACKGROUND = (fgr)new IteratableOptionOF("of.options.CHAT_BACKGROUND");
  
  public static final fgr CHAT_SHADOW = (fgr)new IteratableOptionOF("of.options.CHAT_SHADOW");
  
  public static final fgr TELEMETRY = (fgr)new IteratableOptionOF("of.options.TELEMETRY");
  
  public static final fgr QUICK_INFO;
  
  public static final fgr QUICK_INFO_FPS;
  
  public static final fgr QUICK_INFO_CHUNKS;
  
  public static final fgr QUICK_INFO_ENTITIES;
  
  public static final fgr QUICK_INFO_PARTICLES;
  
  public static final fgr QUICK_INFO_UPDATES;
  
  public static final fgr QUICK_INFO_GPU;
  
  public static final fgr QUICK_INFO_POS;
  
  public static final fgr QUICK_INFO_FACING;
  
  public static final fgr QUICK_INFO_BIOME;
  
  public static final fgr QUICK_INFO_LIGHT;
  
  public static final fgr QUICK_INFO_MEMORY;
  
  public static final fgr QUICK_INFO_NATIVE_MEMORY;
  
  public static final fgr QUICK_INFO_TARGET_BLOCK;
  
  public static final fgr QUICK_INFO_TARGET_FLUID;
  
  public static final fgr QUICK_INFO_TARGET_ENTITY;
  
  public static final fgr QUICK_INFO_LABELS;
  
  public static final fgr QUICK_INFO_BACKGROUND;
  
  static {
    QUICK_INFO = (fgr)new IterableOptionBool("of.options.QUICK_INFO", opts -> opts.ofQuickInfo, (opts, val) -> opts.ofQuickInfo = val.booleanValue(), "ofQuickInfo");
    QUICK_INFO_FPS = (fgr)new IterableOptionInt("of.options.QUICK_INFO_FPS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFps, (opts, val) -> opts.ofQuickInfoFps = val, "ofQuickInfoFps");
    QUICK_INFO_CHUNKS = (fgr)new IterableOptionBool("of.options.QUICK_INFO_CHUNKS", opts -> opts.ofQuickInfoChunks, (opts, val) -> opts.ofQuickInfoChunks = val.booleanValue(), "ofQuickInfoChunks");
    QUICK_INFO_ENTITIES = (fgr)new IterableOptionBool("of.options.QUICK_INFO_ENTITIES", opts -> opts.ofQuickInfoEntities, (opts, val) -> opts.ofQuickInfoEntities = val.booleanValue(), "ofQuickInfoEntities");
    QUICK_INFO_PARTICLES = (fgr)new IterableOptionBool("of.options.QUICK_INFO_PARTICLES", opts -> opts.ofQuickInfoParticles, (opts, val) -> opts.ofQuickInfoParticles = val.booleanValue(), "ofQuickInfoParticles");
    QUICK_INFO_UPDATES = (fgr)new IterableOptionBool("of.options.QUICK_INFO_UPDATES", opts -> opts.ofQuickInfoUpdates, (opts, val) -> opts.ofQuickInfoUpdates = val.booleanValue(), "ofQuickInfoUpdates");
    QUICK_INFO_GPU = (fgr)new IterableOptionBool("of.options.QUICK_INFO_GPU", opts -> opts.ofQuickInfoGpu, (opts, val) -> opts.ofQuickInfoGpu = val.booleanValue(), "ofQuickInfoGpu");
    QUICK_INFO_POS = (fgr)new IterableOptionInt("of.options.QUICK_INFO_POS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoPos, (opts, val) -> opts.ofQuickInfoPos = val, "ofQuickInfoPos");
    QUICK_INFO_FACING = (fgr)new IterableOptionInt("of.options.QUICK_INFO_FACING", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFacing, (opts, val) -> opts.ofQuickInfoFacing = val, "ofQuickInfoFacing");
    QUICK_INFO_BIOME = (fgr)new IterableOptionBool("of.options.QUICK_INFO_BIOME", opts -> opts.ofQuickInfoBiome, (opts, val) -> opts.ofQuickInfoBiome = val.booleanValue(), "ofQuickInfoBiome");
    QUICK_INFO_LIGHT = (fgr)new IterableOptionBool("of.options.QUICK_INFO_LIGHT", opts -> opts.ofQuickInfoLight, (opts, val) -> opts.ofQuickInfoLight = val.booleanValue(), "ofQuickInfoLight");
    QUICK_INFO_MEMORY = (fgr)new IterableOptionInt("of.options.QUICK_INFO_MEMORY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoMemory, (opts, val) -> opts.ofQuickInfoMemory = val, "ofQuickInfoMemory");
    QUICK_INFO_NATIVE_MEMORY = (fgr)new IterableOptionInt("of.options.QUICK_INFO_NATIVE_MEMORY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoNativeMemory, (opts, val) -> opts.ofQuickInfoNativeMemory = val, "ofQuickInfoNativeMemory");
    QUICK_INFO_TARGET_BLOCK = (fgr)new IterableOptionInt("of.options.QUICK_INFO_TARGET_BLOCK", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetBlock, (opts, val) -> opts.ofQuickInfoTargetBlock = val, "ofQuickInfoTargetBlock");
    QUICK_INFO_TARGET_FLUID = (fgr)new IterableOptionInt("of.options.QUICK_INFO_TARGET_FLUID", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetFluid, (opts, val) -> opts.ofQuickInfoTargetFluid = val, "ofQuickInfoTargetFluid");
    QUICK_INFO_TARGET_ENTITY = (fgr)new IterableOptionInt("of.options.QUICK_INFO_TARGET_ENTITY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoTargetEntity, (opts, val) -> opts.ofQuickInfoTargetEntity = val, "ofQuickInfoTargetEntity");
    QUICK_INFO_LABELS = (fgr)new IterableOptionInt("of.options.QUICK_INFO_LABELS", COMPACT_FULL_DETAILED, opts -> opts.ofQuickInfoLabels, (opts, val) -> opts.ofQuickInfoLabels = val, "ofQuickInfoLabels");
    QUICK_INFO_BACKGROUND = (fgr)new IterableOptionBool("of.options.QUICK_INFO_BACKGROUND", opts -> opts.ofQuickInfoBackground, (opts, val) -> opts.ofQuickInfoBackground = val.booleanValue(), "ofQuickInfoBackground");
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
