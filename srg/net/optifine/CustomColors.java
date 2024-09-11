package srg.net.optifine;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColorFader;
import net.optifine.CustomColormap;
import net.optifine.LightMap;
import net.optifine.LightMapPack;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderEnv;
import net.optifine.util.BiomeUtils;
import net.optifine.util.EntityUtils;
import net.optifine.util.PotionUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {
  private static String paletteFormatDefault = "vanilla";
  
  private static CustomColormap waterColors = null;
  
  private static CustomColormap foliagePineColors = null;
  
  private static CustomColormap foliageBirchColors = null;
  
  private static CustomColormap swampFoliageColors = null;
  
  private static CustomColormap swampGrassColors = null;
  
  private static CustomColormap[] colorsBlockColormaps = null;
  
  private static CustomColormap[][] blockColormaps = null;
  
  private static CustomColormap skyColors = null;
  
  private static CustomColorFader skyColorFader = new CustomColorFader();
  
  private static CustomColormap fogColors = null;
  
  private static CustomColorFader fogColorFader = new CustomColorFader();
  
  private static CustomColormap underwaterColors = null;
  
  private static CustomColorFader underwaterColorFader = new CustomColorFader();
  
  private static CustomColormap underlavaColors = null;
  
  private static CustomColorFader underlavaColorFader = new CustomColorFader();
  
  private static LightMapPack[] lightMapPacks = null;
  
  private static int lightmapMinDimensionId = 0;
  
  private static CustomColormap redstoneColors = null;
  
  private static CustomColormap xpOrbColors = null;
  
  private static int xpOrbTime = -1;
  
  private static CustomColormap durabilityColors = null;
  
  private static CustomColormap stemColors = null;
  
  private static CustomColormap stemMelonColors = null;
  
  private static CustomColormap stemPumpkinColors = null;
  
  private static CustomColormap lavaDropColors = null;
  
  private static CustomColormap myceliumParticleColors = null;
  
  private static boolean useDefaultGrassFoliageColors = true;
  
  private static int particleWaterColor = -1;
  
  private static int particlePortalColor = -1;
  
  private static int lilyPadColor = -1;
  
  private static int expBarTextColor = -1;
  
  private static int bossTextColor = -1;
  
  private static int signTextColor = -1;
  
  private static Vec3 fogColorNether = null;
  
  private static Vec3 fogColorEnd = null;
  
  private static Vec3 skyColorEnd = null;
  
  private static int[] spawnEggPrimaryColors = null;
  
  private static int[] spawnEggSecondaryColors = null;
  
  private static int[] wolfCollarColors = null;
  
  private static int[] sheepColors = null;
  
  private static int[] textColors = null;
  
  private static int[] mapColorsOriginal = null;
  
  private static int[] dyeColorsOriginal = null;
  
  private static int[] potionColors = null;
  
  private static final BlockState BLOCK_STATE_DIRT = Blocks.DIRT.defaultBlockState();
  
  private static final BlockState BLOCK_STATE_WATER = Blocks.WATER.defaultBlockState();
  
  public static Random random = new Random();
  
  private static final IColorizer COLORIZER_GRASS = (IColorizer)new Object();
  
  private static final IColorizer COLORIZER_FOLIAGE = (IColorizer)new Object();
  
  private static final IColorizer COLORIZER_FOLIAGE_PINE = (IColorizer)new Object();
  
  private static final IColorizer COLORIZER_FOLIAGE_BIRCH = (IColorizer)new Object();
  
  private static final IColorizer COLORIZER_WATER = (IColorizer)new Object();
  
  public static void update() {
    paletteFormatDefault = "vanilla";
    waterColors = null;
    foliageBirchColors = null;
    foliagePineColors = null;
    swampGrassColors = null;
    swampFoliageColors = null;
    skyColors = null;
    fogColors = null;
    underwaterColors = null;
    underlavaColors = null;
    redstoneColors = null;
    xpOrbColors = null;
    xpOrbTime = -1;
    durabilityColors = null;
    stemColors = null;
    lavaDropColors = null;
    myceliumParticleColors = null;
    lightMapPacks = null;
    particleWaterColor = -1;
    particlePortalColor = -1;
    lilyPadColor = -1;
    expBarTextColor = -1;
    bossTextColor = -1;
    signTextColor = -1;
    fogColorNether = null;
    fogColorEnd = null;
    skyColorEnd = null;
    colorsBlockColormaps = null;
    blockColormaps = null;
    useDefaultGrassFoliageColors = true;
    spawnEggPrimaryColors = null;
    spawnEggSecondaryColors = null;
    wolfCollarColors = null;
    sheepColors = null;
    textColors = null;
    setMapColors(mapColorsOriginal);
    setDyeColors(dyeColorsOriginal);
    potionColors = null;
    paletteFormatDefault = getValidProperty("optifine/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
    String mcpColormap = "optifine/colormap/";
    String[] waterPaths = { "water.png", "watercolorx.png" };
    waterColors = getCustomColors(mcpColormap, waterPaths, 256, -1);
    updateUseDefaultGrassFoliageColors();
    if (!Config.isCustomColors())
      return; 
    String[] pinePaths = { "pine.png", "pinecolor.png" };
    foliagePineColors = getCustomColors(mcpColormap, pinePaths, 256, -1);
    String[] birchPaths = { "birch.png", "birchcolor.png" };
    foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 256, -1);
    String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
    swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 256, -1);
    String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
    swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 256, -1);
    String[] sky0Paths = { "sky0.png", "skycolor0.png" };
    skyColors = getCustomColors(mcpColormap, sky0Paths, 256, -1);
    String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
    fogColors = getCustomColors(mcpColormap, fog0Paths, 256, -1);
    String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
    underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 256, -1);
    String[] underlavaPaths = { "underlava.png", "underlavacolor.png" };
    underlavaColors = getCustomColors(mcpColormap, underlavaPaths, 256, -1);
    String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
    redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16, 1);
    xpOrbColors = getCustomColors(mcpColormap + "xporb.png", -1, -1);
    durabilityColors = getCustomColors(mcpColormap + "durability.png", -1, -1);
    String[] stemPaths = { "stem.png", "stemcolor.png" };
    stemColors = getCustomColors(mcpColormap, stemPaths, 8, 1);
    stemPumpkinColors = getCustomColors(mcpColormap + "pumpkinstem.png", 8, 1);
    stemMelonColors = getCustomColors(mcpColormap + "melonstem.png", 8, 1);
    lavaDropColors = getCustomColors(mcpColormap + "lavadrop.png", -1, 1);
    String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
    myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1, -1);
    Pair<LightMapPack[], Integer> lightMaps = parseLightMapPacks();
    lightMapPacks = (LightMapPack[])lightMaps.getLeft();
    lightmapMinDimensionId = ((Integer)lightMaps.getRight()).intValue();
    readColorProperties("optifine/color.properties");
    blockColormaps = readBlockColormaps(new String[] { mcpColormap + "custom/", mcpColormap + "blocks/" }, colorsBlockColormaps, 256, -1);
    updateUseDefaultGrassFoliageColors();
  }
  
  private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
    try {
      ResourceLocation loc = new ResourceLocation(fileName);
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return valDef; 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      String val = propertiesOrdered.getProperty(key);
      if (val == null)
        return valDef; 
      List<String> listValidValues = Arrays.asList(validValues);
      if (!listValidValues.contains(val)) {
        warn("Invalid value: " + key + "=" + val);
        warn("Expected values: " + Config.arrayToString((Object[])validValues));
        return valDef;
      } 
      dbg(key + "=" + key);
      return val;
    } catch (FileNotFoundException e) {
      return valDef;
    } catch (IOException e) {
      e.printStackTrace();
      return valDef;
    } 
  }
  
  private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
    String lightmapPrefix = "optifine/lightmap/world";
    String lightmapSuffix = ".png";
    String[] pathsLightmap = ResUtils.collectFiles(lightmapPrefix, lightmapSuffix);
    Map<Integer, String> mapLightmaps = new HashMap<>();
    for (int i = 0; i < pathsLightmap.length; i++) {
      String path = pathsLightmap[i];
      String dimIdStr = StrUtils.removePrefixSuffix(path, lightmapPrefix, lightmapSuffix);
      int dimId = Config.parseInt(dimIdStr, -2147483648);
      if (dimId != Integer.MIN_VALUE)
        mapLightmaps.put(Integer.valueOf(dimId), path); 
    } 
    Set<Integer> setDimIds = mapLightmaps.keySet();
    Integer[] dimIds = setDimIds.<Integer>toArray(new Integer[setDimIds.size()]);
    Arrays.sort((Object[])dimIds);
    if (dimIds.length <= 0)
      return (Pair<LightMapPack[], Integer>)new ImmutablePair(null, Integer.valueOf(0)); 
    int minDimId = dimIds[0].intValue();
    int maxDimId = dimIds[dimIds.length - 1].intValue();
    int countDim = maxDimId - minDimId + 1;
    CustomColormap[] colormaps = new CustomColormap[countDim];
    for (int j = 0; j < dimIds.length; j++) {
      Integer dimId = dimIds[j];
      String path = mapLightmaps.get(dimId);
      CustomColormap colors = getCustomColors(path, -1, -1);
      if (colors != null)
        if (colors.getWidth() < 16) {
          warn("Invalid lightmap width: " + colors.getWidth() + ", path: " + path);
        } else {
          int lightmapIndex = dimId.intValue() - minDimId;
          colormaps[lightmapIndex] = colors;
        }  
    } 
    LightMapPack[] lmps = new LightMapPack[colormaps.length];
    for (int k = 0; k < colormaps.length; k++) {
      CustomColormap cm = colormaps[k];
      if (cm != null) {
        String name = cm.name;
        String basePath = cm.basePath;
        CustomColormap cmRain = getCustomColors(basePath + "/" + basePath + "_rain.png", -1, -1);
        CustomColormap cmThunder = getCustomColors(basePath + "/" + basePath + "_thunder.png", -1, -1);
        LightMap lm = new LightMap(cm);
        LightMap lmRain = (cmRain != null) ? new LightMap(cmRain) : null;
        LightMap lmThunder = (cmThunder != null) ? new LightMap(cmThunder) : null;
        LightMapPack lmp = new LightMapPack(lm, lmRain, lmThunder);
        lmps[k] = lmp;
      } 
    } 
    return (Pair<LightMapPack[], Integer>)new ImmutablePair(lmps, Integer.valueOf(minDimId));
  }
  
  private static int getTextureHeight(String path, int defHeight) {
    try {
      InputStream in = Config.getResourceStream(new ResourceLocation(path));
      if (in == null)
        return defHeight; 
      BufferedImage bi = ImageIO.read(in);
      in.close();
      if (bi == null)
        return defHeight; 
      return bi.getHeight();
    } catch (IOException e) {
      return defHeight;
    } 
  }
  
  private static void readColorProperties(String fileName) {
    try {
      ResourceLocation loc = new ResourceLocation(fileName);
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return; 
      dbg("Loading " + fileName);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      particleWaterColor = readColor((Properties)propertiesOrdered, new String[] { "particle.water", "drop.water" });
      particlePortalColor = readColor((Properties)propertiesOrdered, "particle.portal");
      lilyPadColor = readColor((Properties)propertiesOrdered, "lilypad");
      expBarTextColor = readColor((Properties)propertiesOrdered, "text.xpbar");
      bossTextColor = readColor((Properties)propertiesOrdered, "text.boss");
      signTextColor = readColor((Properties)propertiesOrdered, "text.sign");
      fogColorNether = readColorVec3((Properties)propertiesOrdered, "fog.nether");
      fogColorEnd = readColorVec3((Properties)propertiesOrdered, "fog.end");
      skyColorEnd = readColorVec3((Properties)propertiesOrdered, "sky.end");
      colorsBlockColormaps = readCustomColormaps((Properties)propertiesOrdered, fileName);
      spawnEggPrimaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.shell.", "Spawn egg shell");
      spawnEggSecondaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.spots.", "Spawn egg spot");
      wolfCollarColors = readDyeColors((Properties)propertiesOrdered, fileName, "collar.", "Wolf collar");
      sheepColors = readDyeColors((Properties)propertiesOrdered, fileName, "sheep.", "Sheep");
      textColors = readTextColors((Properties)propertiesOrdered, fileName, "text.code.", "Text");
      int[] mapColors = readMapColors((Properties)propertiesOrdered, fileName, "map.", "Map");
      if (mapColors != null) {
        if (mapColorsOriginal == null)
          mapColorsOriginal = getMapColors(); 
        setMapColors(mapColors);
      } 
      int[] dyeColors = readDyeColors((Properties)propertiesOrdered, fileName, "dye.", "Dye");
      if (dyeColors != null) {
        if (dyeColorsOriginal == null)
          dyeColorsOriginal = getDyeColors(); 
        setDyeColors(dyeColors);
      } 
      potionColors = readPotionColors((Properties)propertiesOrdered, fileName, "potion.", "Potion");
      xpOrbTime = Config.parseInt(propertiesOrdered.getProperty("xporb.time"), -1);
    } catch (FileNotFoundException e) {
      return;
    } catch (IOException e) {
      Config.warn("Error parsing: " + fileName);
      Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
    } 
  }
  
  private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
    List<CustomColormap> list = new ArrayList();
    String palettePrefix = "palette.block.";
    Map<Object, Object> map = new HashMap<>();
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(palettePrefix))
        continue; 
      map.put(key, value);
    } 
    String[] propNames = (String[])map.keySet().toArray((Object[])new String[map.size()]);
    for (int i = 0; i < propNames.length; i++) {
      String name = propNames[i];
      String value = props.getProperty(name);
      dbg("Block palette: " + name + " = " + value);
      String path = name.substring(palettePrefix.length());
      String basePath = TextureUtils.getBasePath(fileName);
      path = TextureUtils.fixResourcePath(path, basePath);
      CustomColormap colors = getCustomColors(path, 256, -1);
      if (colors == null) {
        warn("Colormap not found: " + path);
      } else {
        ConnectedParser cp = new ConnectedParser("CustomColors");
        MatchBlock[] mbs = cp.parseMatchBlocks(value);
        if (mbs == null || mbs.length <= 0) {
          warn("Invalid match blocks: " + value);
        } else {
          for (int m = 0; m < mbs.length; m++) {
            MatchBlock mb = mbs[m];
            colors.addMatchBlock(mb);
          } 
          list.add(colors);
        } 
      } 
    } 
    if (list.size() <= 0)
      return null; 
    CustomColormap[] cms = list.<CustomColormap>toArray(new CustomColormap[list.size()]);
    return cms;
  }
  
  private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
    String[] paths = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
    Arrays.sort((Object[])paths);
    List blockList = new ArrayList();
    int i;
    for (i = 0; i < paths.length; i++) {
      String path = paths[i];
      dbg("Block colormap: " + path);
      try {
        ResourceLocation locFile = new ResourceLocation("minecraft", path);
        InputStream in = Config.getResourceStream(locFile);
        if (in == null) {
          warn("File not found: " + path);
        } else {
          PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
          propertiesOrdered.load(in);
          in.close();
          CustomColormap cm = new CustomColormap((Properties)propertiesOrdered, path, width, height, paletteFormatDefault);
          if (cm.isValid(path))
            if (cm.isValidMatchBlocks(path))
              addToBlockList(cm, blockList);  
        } 
      } catch (FileNotFoundException e) {
        warn("File not found: " + path);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if (basePalettes != null)
      for (i = 0; i < basePalettes.length; i++) {
        CustomColormap cm = basePalettes[i];
        addToBlockList(cm, blockList);
      }  
    if (blockList.size() <= 0)
      return null; 
    CustomColormap[][] cmArr = blockListToArray(blockList);
    return cmArr;
  }
  
  private static void addToBlockList(CustomColormap cm, List blockList) {
    int[] ids = cm.getMatchBlockIds();
    if (ids == null || ids.length <= 0) {
      warn("No match blocks: " + Config.arrayToString(ids));
      return;
    } 
    for (int i = 0; i < ids.length; i++) {
      int blockId = ids[i];
      if (blockId < 0) {
        warn("Invalid block ID: " + blockId);
      } else {
        addToList(cm, blockList, blockId);
      } 
    } 
  }
  
  private static void addToList(CustomColormap cm, List<List> list, int id) {
    while (id >= list.size())
      list.add(null); 
    List<CustomColormap> subList = list.get(id);
    if (subList == null) {
      subList = new ArrayList();
      list.set(id, subList);
    } 
    subList.add(cm);
  }
  
  private static CustomColormap[][] blockListToArray(List<List> list) {
    CustomColormap[][] colArr = new CustomColormap[list.size()][];
    for (int i = 0; i < list.size(); i++) {
      List subList = list.get(i);
      if (subList != null) {
        CustomColormap[] subArr = (CustomColormap[])subList.toArray((Object[])new CustomColormap[subList.size()]);
        colArr[i] = subArr;
      } 
    } 
    return colArr;
  }
  
  private static int readColor(Properties props, String[] names) {
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      int col = readColor(props, name);
      if (col >= 0)
        return col; 
    } 
    return -1;
  }
  
  private static int readColor(Properties props, String name) {
    String str = props.getProperty(name);
    if (str == null)
      return -1; 
    str = str.trim();
    int color = parseColor(str);
    if (color < 0) {
      warn("Invalid color: " + name + " = " + str);
      return color;
    } 
    dbg(name + " = " + name);
    return color;
  }
  
  private static int parseColor(String str) {
    if (str == null)
      return -1; 
    str = str.trim();
    try {
      int val = Integer.parseInt(str, 16) & 0xFFFFFF;
      return val;
    } catch (NumberFormatException e) {
      return -1;
    } 
  }
  
  private static Vec3 readColorVec3(Properties props, String name) {
    int col = readColor(props, name);
    if (col < 0)
      return null; 
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    return new Vec3(redF, greenF, blueF);
  }
  
  private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
    for (int i = 0; i < paths.length; i++) {
      String path = paths[i];
      path = basePath + basePath;
      CustomColormap cols = getCustomColors(path, width, height);
      if (cols != null)
        return cols; 
    } 
    return null;
  }
  
  public static CustomColormap getCustomColors(String pathImage, int width, int height) {
    try {
      ResourceLocation loc = new ResourceLocation(pathImage);
      if (!Config.hasResource(loc))
        return null; 
      dbg("Colormap " + pathImage);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
      ResourceLocation locProps = new ResourceLocation(pathProps);
      if (Config.hasResource(locProps)) {
        InputStream in = Config.getResourceStream(locProps);
        propertiesOrdered.load(in);
        in.close();
        dbg("Colormap properties: " + pathProps);
      } else {
        propertiesOrdered.put("format", paletteFormatDefault);
        propertiesOrdered.put("source", pathImage);
        pathProps = pathImage;
      } 
      CustomColormap cm = new CustomColormap((Properties)propertiesOrdered, pathProps, width, height, paletteFormatDefault);
      if (!cm.isValid(pathProps))
        return null; 
      return cm;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static void updateUseDefaultGrassFoliageColors() {
    useDefaultGrassFoliageColors = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null);
  }
  
  public static int getColorMultiplier(BakedQuad quad, BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
    return getColorMultiplier(quad.isTinted(), blockState, blockAccess, blockPos, renderEnv);
  }
  
  public static int getColorMultiplier(boolean quadHasTintIndex, BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
    IColorizer colorizer;
    Block block = blockState.getBlock();
    BlockState blockStateColormap = blockState;
    if (blockColormaps != null) {
      if (!quadHasTintIndex) {
        if (block == Blocks.GRASS_BLOCK)
          blockStateColormap = BLOCK_STATE_DIRT; 
        if (block == Blocks.REDSTONE_WIRE)
          return -1; 
      } 
      if (block instanceof DoublePlantBlock)
        if (blockState.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
          blockPos = blockPos.below();
          blockStateColormap = blockAccess.getBlockState(blockPos);
        }  
      CustomColormap cm = getBlockColormap(blockStateColormap);
      if (cm != null) {
        if (Config.isSmoothBiomes() && !cm.isColorConstant())
          return getSmoothColorMultiplier(blockState, blockAccess, blockPos, (IColorizer)cm, renderEnv.getColorizerBlockPosM()); 
        return cm.getColor(blockAccess, blockPos);
      } 
    } 
    if (!quadHasTintIndex)
      return -1; 
    if (block == Blocks.LILY_PAD)
      return getLilypadColorMultiplier(blockAccess, blockPos); 
    if (block == Blocks.REDSTONE_WIRE)
      return getRedstoneColor(renderEnv.getBlockState()); 
    if (block instanceof StemBlock)
      return getStemColorMultiplier(blockState, (BlockGetter)blockAccess, blockPos, renderEnv); 
    if (useDefaultGrassFoliageColors)
      return -1; 
    if (block == Blocks.GRASS_BLOCK || block instanceof net.minecraft.world.level.block.TallGrassBlock || block instanceof DoublePlantBlock || block == Blocks.SUGAR_CANE) {
      colorizer = COLORIZER_GRASS;
    } else if (block instanceof DoublePlantBlock) {
      colorizer = COLORIZER_GRASS;
      if (blockState.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER)
        blockPos = blockPos.below(); 
    } else if (block instanceof net.minecraft.world.level.block.LeavesBlock) {
      if (block == Blocks.SPRUCE_LEAVES) {
        colorizer = COLORIZER_FOLIAGE_PINE;
      } else if (block == Blocks.BIRCH_LEAVES) {
        colorizer = COLORIZER_FOLIAGE_BIRCH;
      } else {
        if (block == Blocks.CHERRY_LEAVES)
          return -1; 
        if (!blockState.getBlockLocation().isDefaultNamespace())
          return -1; 
        colorizer = COLORIZER_FOLIAGE;
      } 
    } else if (block == Blocks.VINE) {
      colorizer = COLORIZER_FOLIAGE;
    } else {
      return -1;
    } 
    if (Config.isSmoothBiomes() && !colorizer.isColorConstant())
      return getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM()); 
    return colorizer.getColor(blockStateColormap, blockAccess, blockPos);
  }
  
  protected static Biome getColorBiome(BlockAndTintGetter blockAccess, BlockPos blockPos) {
    Biome biome = BiomeUtils.getBiome(blockAccess, blockPos);
    biome = fixBiome(biome);
    return biome;
  }
  
  public static Biome fixBiome(Biome biome) {
    if ((biome == BiomeUtils.SWAMP || biome == BiomeUtils.MANGROVE_SWAMP) && !Config.isSwampColors())
      return BiomeUtils.PLAINS; 
    return biome;
  }
  
  private static CustomColormap getBlockColormap(BlockState blockState) {
    if (blockColormaps == null)
      return null; 
    if (!(blockState instanceof BlockState))
      return null; 
    BlockState bs = blockState;
    int blockId = bs.getBlockId();
    if (blockId < 0 || blockId >= blockColormaps.length)
      return null; 
    CustomColormap[] cms = blockColormaps[blockId];
    if (cms == null)
      return null; 
    for (int i = 0; i < cms.length; i++) {
      CustomColormap cm = cms[i];
      if (cm.matchesBlock(bs))
        return cm; 
    } 
    return null;
  }
  
  private static int getSmoothColorMultiplier(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, IColorizer colorizer, BlockPosM blockPosM) {
    int sumRed = 0;
    int sumGreen = 0;
    int sumBlue = 0;
    int x = blockPos.getX();
    int y = blockPos.getY();
    int z = blockPos.getZ();
    BlockPosM posM = blockPosM;
    int radius = Config.getBiomeBlendRadius();
    int width = radius * 2 + 1;
    int count = width * width;
    for (int ix = x - radius; ix <= x + radius; ix++) {
      for (int iz = z - radius; iz <= z + radius; iz++) {
        posM.setXyz(ix, y, iz);
        int col = colorizer.getColor(blockState, blockAccess, (BlockPos)posM);
        sumRed += col >> 16 & 0xFF;
        sumGreen += col >> 8 & 0xFF;
        sumBlue += col & 0xFF;
      } 
    } 
    int r = sumRed / count;
    int g = sumGreen / count;
    int b = sumBlue / count;
    return r << 16 | g << 8 | b;
  }
  
  public static int getFluidColor(BlockAndTintGetter blockAccess, BlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
    IColorizer iColorizer;
    Block block = blockState.getBlock();
    CustomColormap customColormap = getBlockColormap(blockState);
    if (customColormap == null)
      if (blockState.getBlock() == Blocks.WATER)
        iColorizer = COLORIZER_WATER;  
    if (iColorizer == null)
      return getBlockColors().getColor(blockState, blockAccess, blockPos, 0); 
    if (Config.isSmoothBiomes() && !iColorizer.isColorConstant())
      return getSmoothColorMultiplier(blockState, blockAccess, blockPos, iColorizer, renderEnv.getColorizerBlockPosM()); 
    return iColorizer.getColor(blockState, blockAccess, blockPos);
  }
  
  public static BlockColors getBlockColors() {
    return Minecraft.getInstance().getBlockColors();
  }
  
  public static void updatePortalFX(Particle fx) {
    if (particlePortalColor < 0)
      return; 
    int col = particlePortalColor;
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.setColor(redF, greenF, blueF);
  }
  
  public static void updateLavaFX(Particle fx) {
    if (lavaDropColors == null)
      return; 
    int age = fx.getAge();
    int col = lavaDropColors.getColor(age);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.setColor(redF, greenF, blueF);
  }
  
  public static void updateMyceliumFX(Particle fx) {
    if (myceliumParticleColors == null)
      return; 
    int col = myceliumParticleColors.getColorRandom();
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.setColor(redF, greenF, blueF);
  }
  
  private static int getRedstoneColor(BlockState blockState) {
    if (redstoneColors == null)
      return -1; 
    int level = getRedstoneLevel(blockState, 15);
    int col = redstoneColors.getColor(level);
    return col;
  }
  
  public static void updateReddustFX(Particle fx, BlockAndTintGetter blockAccess, double x, double y, double z) {
    if (redstoneColors == null)
      return; 
    BlockState state = blockAccess.getBlockState(BlockPos.containing(x, y, z));
    int level = getRedstoneLevel(state, 15);
    int col = redstoneColors.getColor(level);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.setColor(redF, greenF, blueF);
  }
  
  private static int getRedstoneLevel(BlockState state, int def) {
    Block block = state.getBlock();
    if (!(block instanceof RedStoneWireBlock))
      return def; 
    Object val = state.getValue((Property)RedStoneWireBlock.POWER);
    if (!(val instanceof Integer))
      return def; 
    Integer valInt = (Integer)val;
    return valInt.intValue();
  }
  
  public static float getXpOrbTimer(float timer) {
    if (xpOrbTime <= 0)
      return timer; 
    float kt = 628.0F / xpOrbTime;
    return timer * kt;
  }
  
  public static int getXpOrbColor(float timer) {
    if (xpOrbColors == null)
      return -1; 
    int index = (int)Math.round(((Mth.sin(timer) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
    int col = xpOrbColors.getColor(index);
    return col;
  }
  
  public static int getDurabilityColor(float dur, int color) {
    if (durabilityColors == null)
      return color; 
    int index = (int)(dur * durabilityColors.getLength());
    int col = durabilityColors.getColor(index);
    return col;
  }
  
  public static void updateWaterFX(Particle fx, BlockAndTintGetter blockAccess, double x, double y, double z, RenderEnv renderEnv) {
    if (waterColors == null && blockColormaps == null && particleWaterColor < 0)
      return; 
    BlockPos blockPos = BlockPos.containing(x, y, z);
    renderEnv.reset(BLOCK_STATE_WATER, blockPos);
    int col = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockPos, renderEnv);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    if (particleWaterColor >= 0) {
      int redDrop = particleWaterColor >> 16 & 0xFF;
      int greenDrop = particleWaterColor >> 8 & 0xFF;
      int blueDrop = particleWaterColor & 0xFF;
      redF = redDrop / 255.0F;
      greenF = greenDrop / 255.0F;
      blueF = blueDrop / 255.0F;
      redF *= redDrop / 255.0F;
      greenF *= greenDrop / 255.0F;
      blueF *= blueDrop / 255.0F;
    } 
    fx.setColor(redF, greenF, blueF);
  }
  
  private static int getLilypadColorMultiplier(BlockAndTintGetter blockAccess, BlockPos blockPos) {
    if (lilyPadColor < 0)
      return getBlockColors().getColor(Blocks.LILY_PAD.defaultBlockState(), blockAccess, blockPos, 0); 
    return lilyPadColor;
  }
  
  private static Vec3 getFogColorNether(Vec3 col) {
    if (fogColorNether == null)
      return col; 
    return fogColorNether;
  }
  
  private static Vec3 getFogColorEnd(Vec3 col) {
    if (fogColorEnd == null)
      return col; 
    return fogColorEnd;
  }
  
  private static Vec3 getSkyColorEnd(Vec3 col) {
    if (skyColorEnd == null)
      return col; 
    return skyColorEnd;
  }
  
  public static Vec3 getSkyColor(Vec3 skyColor3d, BlockAndTintGetter blockAccess, double x, double y, double z) {
    if (skyColors == null)
      return skyColor3d; 
    int col = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    float cRed = (float)skyColor3d.x / 0.5F;
    float cGreen = (float)skyColor3d.y / 0.66275F;
    float cBlue = (float)skyColor3d.z;
    redF *= cRed;
    greenF *= cGreen;
    blueF *= cBlue;
    Vec3 newCol = skyColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  private static Vec3 getFogColor(Vec3 fogColor3d, BlockAndTintGetter blockAccess, double x, double y, double z) {
    if (fogColors == null)
      return fogColor3d; 
    int col = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    float cRed = (float)fogColor3d.x / 0.753F;
    float cGreen = (float)fogColor3d.y / 0.8471F;
    float cBlue = (float)fogColor3d.z;
    redF *= cRed;
    greenF *= cGreen;
    blueF *= cBlue;
    Vec3 newCol = fogColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  public static Vec3 getUnderwaterColor(BlockAndTintGetter blockAccess, double x, double y, double z) {
    return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
  }
  
  public static Vec3 getUnderlavaColor(BlockAndTintGetter blockAccess, double x, double y, double z) {
    return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
  }
  
  public static Vec3 getUnderFluidColor(BlockAndTintGetter blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
    if (underFluidColors == null)
      return null; 
    int col = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    Vec3 newCol = underFluidColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  private static int getStemColorMultiplier(BlockState blockState, BlockGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
    CustomColormap colors = stemColors;
    Block blockStem = blockState.getBlock();
    if (blockStem == Blocks.PUMPKIN_STEM && stemPumpkinColors != null)
      colors = stemPumpkinColors; 
    if (blockStem == Blocks.MELON_STEM && stemMelonColors != null)
      colors = stemMelonColors; 
    if (colors == null)
      return -1; 
    if (!(blockStem instanceof StemBlock))
      return -1; 
    int level = ((Integer)blockState.getValue((Property)StemBlock.AGE)).intValue();
    return colors.getColor(level);
  }
  
  public static boolean updateLightmap(ClientLevel world, float torchFlickerX, NativeImage lmColors, boolean nightvision, float darkLight, float partialTicks) {
    if (world == null)
      return false; 
    if (lightMapPacks == null)
      return false; 
    int dimensionId = WorldUtils.getDimensionId((Level)world);
    int lightMapIndex = dimensionId - lightmapMinDimensionId;
    if (lightMapIndex < 0 || lightMapIndex >= lightMapPacks.length)
      return false; 
    LightMapPack lightMapPack = lightMapPacks[lightMapIndex];
    if (lightMapPack == null)
      return false; 
    return lightMapPack.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight, partialTicks);
  }
  
  public static Vec3 getWorldFogColor(Vec3 fogVec, Level world, Entity renderViewEntity, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    if (WorldUtils.isNether(world))
      return getFogColorNether(fogVec); 
    if (WorldUtils.isOverworld(world))
      return getFogColor(fogVec, (BlockAndTintGetter)mc.level, renderViewEntity.getX(), renderViewEntity.getY() + 1.0D, renderViewEntity.getZ()); 
    if (WorldUtils.isEnd(world))
      return getFogColorEnd(fogVec); 
    return fogVec;
  }
  
  public static Vec3 getWorldSkyColor(Vec3 skyVec, Level world, Entity renderViewEntity, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    if (WorldUtils.isOverworld(world) && renderViewEntity != null)
      return getSkyColor(skyVec, (BlockAndTintGetter)mc.level, renderViewEntity.getX(), renderViewEntity.getY() + 1.0D, renderViewEntity.getZ()); 
    if (WorldUtils.isEnd(world))
      return getSkyColorEnd(skyVec); 
    return skyVec;
  }
  
  private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
    List<Integer> list = new ArrayList<>();
    Set<Object> keys = props.keySet();
    int countColors = 0;
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = StrUtils.removePrefix(key, prefix);
      int id = EntityUtils.getEntityIdByName(name);
      try {
        if (id < 0)
          id = EntityUtils.getEntityIdByLocation((new ResourceLocation(name)).toString()); 
      } catch (ResourceLocationException e) {
        Config.warn("ResourceLocationException: " + e.getMessage());
      } 
      if (id < 0) {
        warn("Invalid spawn egg name: " + key);
        continue;
      } 
      int color = parseColor(value);
      if (color < 0) {
        warn("Invalid spawn egg color: " + key + " = " + value);
        continue;
      } 
      while (list.size() <= id)
        list.add(Integer.valueOf(-1)); 
      list.set(id, Integer.valueOf(color));
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    int[] colors = new int[list.size()];
    for (int i = 0; i < colors.length; i++)
      colors[i] = ((Integer)list.get(i)).intValue(); 
    return colors;
  }
  
  private static int getSpawnEggColor(SpawnEggItem item, ItemStack itemStack, int layer, int color) {
    if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null)
      return color; 
    EntityType entityType = item.getType(itemStack);
    if (entityType == null)
      return color; 
    int id = BuiltInRegistries.ENTITY_TYPE.getId(entityType);
    if (id < 0)
      return color; 
    int[] eggColors = (layer == 0) ? spawnEggPrimaryColors : spawnEggSecondaryColors;
    if (eggColors == null)
      return color; 
    if (id < 0 || id >= eggColors.length)
      return color; 
    int eggColor = eggColors[id];
    if (eggColor < 0)
      return color; 
    return eggColor;
  }
  
  public static int getColorFromItemStack(ItemStack itemStack, int layer, int color) {
    if (itemStack == null)
      return color; 
    Item item = itemStack.getItem();
    if (item == null)
      return color; 
    if (item instanceof SpawnEggItem)
      return getSpawnEggColor((SpawnEggItem)item, itemStack, layer, color); 
    if (item == Items.LILY_PAD)
      if (lilyPadColor != -1)
        return lilyPadColor;  
    return color;
  }
  
  private static int[] readDyeColors(Properties props, String fileName, String prefix, String logName) {
    DyeColor[] dyeValues = DyeColor.values();
    Map<String, DyeColor> mapDyes = new HashMap<>();
    for (int i = 0; i < dyeValues.length; i++) {
      DyeColor dye = dyeValues[i];
      mapDyes.put(dye.getSerializedName(), dye);
    } 
    mapDyes.put("lightBlue", DyeColor.LIGHT_BLUE);
    mapDyes.put("silver", DyeColor.LIGHT_GRAY);
    int[] colors = new int[dyeValues.length];
    int countColors = 0;
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = StrUtils.removePrefix(key, prefix);
      DyeColor dye = mapDyes.get(name);
      int color = parseColor(value);
      if (dye == null || color < 0) {
        warn("Invalid color: " + key + " = " + value);
        continue;
      } 
      int argb = FastColor.ARGB32.opaque(color);
      colors[dye.ordinal()] = argb;
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    return colors;
  }
  
  private static int getDyeColors(DyeColor dye, int[] dyeColors, int color) {
    if (dyeColors == null)
      return color; 
    if (dye == null)
      return color; 
    int customColor = dyeColors[dye.ordinal()];
    if (customColor == 0)
      return color; 
    return customColor;
  }
  
  public static int getWolfCollarColors(DyeColor dye, int color) {
    return getDyeColors(dye, wolfCollarColors, color);
  }
  
  public static int getSheepColors(DyeColor dye, int color) {
    return getDyeColors(dye, sheepColors, color);
  }
  
  private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
    int[] colors = new int[32];
    Arrays.fill(colors, -1);
    int countColors = 0;
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = StrUtils.removePrefix(key, prefix);
      int code = Config.parseInt(name, -1);
      int color = parseColor(value);
      if (code < 0 || code >= colors.length || color < 0) {
        warn("Invalid color: " + key + " = " + value);
        continue;
      } 
      colors[code] = color;
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    return colors;
  }
  
  public static int getTextColor(int index, int color) {
    if (textColors == null)
      return color; 
    if (index < 0 || index >= textColors.length)
      return color; 
    int customColor = textColors[index];
    if (customColor < 0)
      return color; 
    return customColor;
  }
  
  private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
    int[] colors = new int[MapColor.MATERIAL_COLORS.length];
    Arrays.fill(colors, -1);
    int countColors = 0;
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = StrUtils.removePrefix(key, prefix);
      int index = getMapColorIndex(name);
      int color = parseColor(value);
      if (index < 0 || index >= colors.length || color < 0) {
        warn("Invalid color: " + key + " = " + value);
        continue;
      } 
      colors[index] = color;
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    return colors;
  }
  
  private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
    int[] colors = new int[getMaxPotionId()];
    Arrays.fill(colors, -1);
    int countColors = 0;
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = key;
      int index = getPotionId(name);
      int color = parseColor(value);
      if (index < 0 || index >= colors.length || color < 0) {
        warn("Invalid color: " + key + " = " + value);
        continue;
      } 
      colors[index] = color;
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    return colors;
  }
  
  private static int getMaxPotionId() {
    int maxId = 0;
    Set<ResourceLocation> keys = BuiltInRegistries.MOB_EFFECT.keySet();
    for (Iterator<ResourceLocation> it = keys.iterator(); it.hasNext(); ) {
      ResourceLocation rl = it.next();
      MobEffect potion = PotionUtils.getPotion(rl);
      int id = PotionUtils.getId(potion);
      if (id > maxId)
        maxId = id; 
    } 
    return maxId;
  }
  
  private static int getPotionId(String name) {
    if (name.equals("potion.water"))
      return 0; 
    name = StrUtils.replacePrefix(name, "potion.", "effect.");
    String nameMc = StrUtils.replacePrefix(name, "effect.", "effect.minecraft.");
    Set<ResourceLocation> keys = BuiltInRegistries.MOB_EFFECT.keySet();
    for (Iterator<ResourceLocation> it = keys.iterator(); it.hasNext(); ) {
      ResourceLocation rl = it.next();
      MobEffect potion = PotionUtils.getPotion(rl);
      if (potion.getDescriptionId().equals(name))
        return PotionUtils.getId(potion); 
      if (potion.getDescriptionId().equals(nameMc))
        return PotionUtils.getId(potion); 
    } 
    return -1;
  }
  
  public static int getPotionColor(MobEffect potion, int color) {
    int potionId = 0;
    if (potion != null)
      potionId = PotionUtils.getId(potion); 
    return getPotionColor(potionId, color);
  }
  
  public static int getPotionColor(int potionId, int color) {
    if (potionColors == null)
      return color; 
    if (potionId < 0 || potionId >= potionColors.length)
      return color; 
    int potionColor = potionColors[potionId];
    if (potionColor < 0)
      return color; 
    return potionColor;
  }
  
  private static int getMapColorIndex(String name) {
    if (name == null)
      return -1; 
    if (name.equals("air"))
      return MapColor.NONE.id; 
    if (name.equals("grass"))
      return MapColor.GRASS.id; 
    if (name.equals("sand"))
      return MapColor.SAND.id; 
    if (name.equals("cloth"))
      return MapColor.WOOL.id; 
    if (name.equals("tnt"))
      return MapColor.FIRE.id; 
    if (name.equals("ice"))
      return MapColor.ICE.id; 
    if (name.equals("iron"))
      return MapColor.METAL.id; 
    if (name.equals("foliage"))
      return MapColor.PLANT.id; 
    if (name.equals("clay"))
      return MapColor.CLAY.id; 
    if (name.equals("dirt"))
      return MapColor.DIRT.id; 
    if (name.equals("stone"))
      return MapColor.STONE.id; 
    if (name.equals("water"))
      return MapColor.WATER.id; 
    if (name.equals("wood"))
      return MapColor.WOOD.id; 
    if (name.equals("quartz"))
      return MapColor.QUARTZ.id; 
    if (name.equals("gold"))
      return MapColor.GOLD.id; 
    if (name.equals("diamond"))
      return MapColor.DIAMOND.id; 
    if (name.equals("lapis"))
      return MapColor.LAPIS.id; 
    if (name.equals("emerald"))
      return MapColor.EMERALD.id; 
    if (name.equals("podzol"))
      return MapColor.PODZOL.id; 
    if (name.equals("netherrack"))
      return MapColor.NETHER.id; 
    if (name.equals("snow") || name.equals("white"))
      return MapColor.SNOW.id; 
    if (name.equals("adobe") || name.equals("orange"))
      return MapColor.COLOR_ORANGE.id; 
    if (name.equals("magenta"))
      return MapColor.COLOR_MAGENTA.id; 
    if (name.equals("light_blue") || name.equals("lightBlue"))
      return MapColor.COLOR_LIGHT_BLUE.id; 
    if (name.equals("yellow"))
      return MapColor.COLOR_YELLOW.id; 
    if (name.equals("lime"))
      return MapColor.COLOR_LIGHT_GREEN.id; 
    if (name.equals("pink"))
      return MapColor.COLOR_PINK.id; 
    if (name.equals("gray"))
      return MapColor.COLOR_GRAY.id; 
    if (name.equals("silver") || name.equals("light_gray"))
      return MapColor.COLOR_LIGHT_GRAY.id; 
    if (name.equals("cyan"))
      return MapColor.COLOR_CYAN.id; 
    if (name.equals("purple"))
      return MapColor.COLOR_PURPLE.id; 
    if (name.equals("blue"))
      return MapColor.COLOR_BLUE.id; 
    if (name.equals("brown"))
      return MapColor.COLOR_BROWN.id; 
    if (name.equals("green"))
      return MapColor.COLOR_GREEN.id; 
    if (name.equals("red"))
      return MapColor.COLOR_RED.id; 
    if (name.equals("black"))
      return MapColor.COLOR_BLACK.id; 
    if (name.equals("white_terracotta"))
      return MapColor.TERRACOTTA_WHITE.id; 
    if (name.equals("orange_terracotta"))
      return MapColor.TERRACOTTA_ORANGE.id; 
    if (name.equals("magenta_terracotta"))
      return MapColor.TERRACOTTA_MAGENTA.id; 
    if (name.equals("light_blue_terracotta"))
      return MapColor.TERRACOTTA_LIGHT_BLUE.id; 
    if (name.equals("yellow_terracotta"))
      return MapColor.TERRACOTTA_YELLOW.id; 
    if (name.equals("lime_terracotta"))
      return MapColor.TERRACOTTA_LIGHT_GREEN.id; 
    if (name.equals("pink_terracotta"))
      return MapColor.TERRACOTTA_PINK.id; 
    if (name.equals("gray_terracotta"))
      return MapColor.TERRACOTTA_GRAY.id; 
    if (name.equals("light_gray_terracotta"))
      return MapColor.TERRACOTTA_LIGHT_GRAY.id; 
    if (name.equals("cyan_terracotta"))
      return MapColor.TERRACOTTA_CYAN.id; 
    if (name.equals("purple_terracotta"))
      return MapColor.TERRACOTTA_PURPLE.id; 
    if (name.equals("blue_terracotta"))
      return MapColor.TERRACOTTA_BLUE.id; 
    if (name.equals("brown_terracotta"))
      return MapColor.TERRACOTTA_BROWN.id; 
    if (name.equals("green_terracotta"))
      return MapColor.TERRACOTTA_GREEN.id; 
    if (name.equals("red_terracotta"))
      return MapColor.TERRACOTTA_RED.id; 
    if (name.equals("black_terracotta"))
      return MapColor.TERRACOTTA_BLACK.id; 
    if (name.equals("crimson_nylium"))
      return MapColor.CRIMSON_NYLIUM.id; 
    if (name.equals("crimson_stem"))
      return MapColor.CRIMSON_STEM.id; 
    if (name.equals("crimson_hyphae"))
      return MapColor.CRIMSON_HYPHAE.id; 
    if (name.equals("warped_nylium"))
      return MapColor.WARPED_NYLIUM.id; 
    if (name.equals("warped_stem"))
      return MapColor.WARPED_STEM.id; 
    if (name.equals("warped_hyphae"))
      return MapColor.WARPED_HYPHAE.id; 
    if (name.equals("warped_wart_block"))
      return MapColor.WARPED_WART_BLOCK.id; 
    if (name.equals("deepslate"))
      return MapColor.DEEPSLATE.id; 
    if (name.equals("raw_iron"))
      return MapColor.RAW_IRON.id; 
    if (name.equals("glow_lichen"))
      return MapColor.GLOW_LICHEN.id; 
    return -1;
  }
  
  private static int[] getMapColors() {
    MapColor[] mapColors = MapColor.MATERIAL_COLORS;
    int[] colors = new int[mapColors.length];
    Arrays.fill(colors, -1);
    for (int i = 0; i < mapColors.length && i < colors.length; i++) {
      MapColor mapColor = mapColors[i];
      if (mapColor != null)
        colors[i] = mapColor.col; 
    } 
    return colors;
  }
  
  private static void setMapColors(int[] colors) {
    if (colors == null)
      return; 
    MapColor[] mapColors = MapColor.MATERIAL_COLORS;
    for (int i = 0; i < mapColors.length && i < colors.length; i++) {
      MapColor mapColor = mapColors[i];
      if (mapColor != null) {
        int color = colors[i];
        if (color >= 0)
          if (mapColor.col != color)
            mapColor.col = color;  
      } 
    } 
  }
  
  private static int[] getDyeColors() {
    DyeColor[] dyeColors = DyeColor.values();
    int[] colors = new int[dyeColors.length];
    for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
      DyeColor dyeColor = dyeColors[i];
      if (dyeColor != null)
        colors[i] = dyeColor.getTextureDiffuseColor(); 
    } 
    return colors;
  }
  
  private static void setDyeColors(int[] colors) {
    if (colors == null)
      return; 
    DyeColor[] dyeColors = DyeColor.values();
    for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
      DyeColor dyeColor = dyeColors[i];
      if (dyeColor != null) {
        int color = colors[i];
        if (color != 0)
          if (dyeColor.getTextureDiffuseColor() != color)
            dyeColor.setTextureDiffuseColor(color);  
      } 
    } 
  }
  
  private static void dbg(String str) {
    Config.dbg("CustomColors: " + str);
  }
  
  private static void warn(String str) {
    Config.warn("CustomColors: " + str);
  }
  
  public static int getExpBarTextColor(int color) {
    if (expBarTextColor < 0)
      return color; 
    return expBarTextColor;
  }
  
  public static int getBossTextColor(int color) {
    if (bossTextColor < 0)
      return color; 
    return bossTextColor;
  }
  
  public static int getSignTextColor(int color) {
    if (color != 0)
      return color; 
    if (signTextColor < 0)
      return color; 
    return signTextColor;
  }
}
