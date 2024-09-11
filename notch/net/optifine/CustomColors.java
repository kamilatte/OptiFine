package notch.net.optifine;

import aa;
import akr;
import axy;
import ayo;
import brx;
import bsr;
import bsx;
import cti;
import cul;
import cuq;
import cut;
import cwa;
import dbz;
import dcc;
import dcw;
import ddw;
import dfy;
import dga;
import dig;
import dmb;
import dnv;
import dtc;
import dty;
import duf;
import epi;
import exc;
import faj;
import fgo;
import fhq;
import fzf;
import gcn;
import gfw;
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
import jd;
import lt;
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
  
  private static exc fogColorNether = null;
  
  private static exc fogColorEnd = null;
  
  private static exc skyColorEnd = null;
  
  private static int[] spawnEggPrimaryColors = null;
  
  private static int[] spawnEggSecondaryColors = null;
  
  private static int[] wolfCollarColors = null;
  
  private static int[] sheepColors = null;
  
  private static int[] textColors = null;
  
  private static int[] mapColorsOriginal = null;
  
  private static int[] dyeColorsOriginal = null;
  
  private static int[] potionColors = null;
  
  private static final dtc BLOCK_STATE_DIRT = dga.j.o();
  
  private static final dtc BLOCK_STATE_WATER = dga.G.o();
  
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
      akr loc = new akr(fileName);
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
      InputStream in = Config.getResourceStream(new akr(path));
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
      akr loc = new akr(fileName);
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
        akr locFile = new akr("minecraft", path);
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
  
  private static exc readColorVec3(Properties props, String name) {
    int col = readColor(props, name);
    if (col < 0)
      return null; 
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    return new exc(redF, greenF, blueF);
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
      akr loc = new akr(pathImage);
      if (!Config.hasResource(loc))
        return null; 
      dbg("Colormap " + pathImage);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
      akr locProps = new akr(pathProps);
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
  
  public static int getColorMultiplier(gfw quad, dtc blockState, dbz blockAccess, jd blockPos, RenderEnv renderEnv) {
    return getColorMultiplier(quad.c(), blockState, blockAccess, blockPos, renderEnv);
  }
  
  public static int getColorMultiplier(boolean quadHasTintIndex, dtc blockState, dbz blockAccess, jd blockPos, RenderEnv renderEnv) {
    IColorizer colorizer;
    dfy block = blockState.b();
    dtc blockStateColormap = blockState;
    if (blockColormaps != null) {
      if (!quadHasTintIndex) {
        if (block == dga.i)
          blockStateColormap = BLOCK_STATE_DIRT; 
        if (block == dga.cw)
          return -1; 
      } 
      if (block instanceof dig)
        if (blockState.c((duf)dig.b) == dty.a) {
          blockPos = blockPos.e();
          blockStateColormap = blockAccess.a_(blockPos);
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
    if (block == dga.fm)
      return getLilypadColorMultiplier(blockAccess, blockPos); 
    if (block == dga.cw)
      return getRedstoneColor(renderEnv.getBlockState()); 
    if (block instanceof dnv)
      return getStemColorMultiplier(blockState, (dcc)blockAccess, blockPos, renderEnv); 
    if (useDefaultGrassFoliageColors)
      return -1; 
    if (block == dga.i || block instanceof doe || block instanceof dig || block == dga.dS) {
      colorizer = COLORIZER_GRASS;
    } else if (block instanceof dig) {
      colorizer = COLORIZER_GRASS;
      if (blockState.c((duf)dig.b) == dty.a)
        blockPos = blockPos.e(); 
    } else if (block instanceof dki) {
      if (block == dga.aF) {
        colorizer = COLORIZER_FOLIAGE_PINE;
      } else if (block == dga.aG) {
        colorizer = COLORIZER_FOLIAGE_BIRCH;
      } else {
        if (block == dga.aJ)
          return -1; 
        if (!blockState.getBlockLocation().isDefaultNamespace())
          return -1; 
        colorizer = COLORIZER_FOLIAGE;
      } 
    } else if (block == dga.ff) {
      colorizer = COLORIZER_FOLIAGE;
    } else {
      return -1;
    } 
    if (Config.isSmoothBiomes() && !colorizer.isColorConstant())
      return getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM()); 
    return colorizer.getColor(blockStateColormap, blockAccess, blockPos);
  }
  
  protected static ddw getColorBiome(dbz blockAccess, jd blockPos) {
    ddw biome = BiomeUtils.getBiome(blockAccess, blockPos);
    biome = fixBiome(biome);
    return biome;
  }
  
  public static ddw fixBiome(ddw biome) {
    if ((biome == BiomeUtils.SWAMP || biome == BiomeUtils.MANGROVE_SWAMP) && !Config.isSwampColors())
      return BiomeUtils.PLAINS; 
    return biome;
  }
  
  private static CustomColormap getBlockColormap(dtc blockState) {
    if (blockColormaps == null)
      return null; 
    if (!(blockState instanceof dtc))
      return null; 
    dtc bs = blockState;
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
  
  private static int getSmoothColorMultiplier(dtc blockState, dbz blockAccess, jd blockPos, IColorizer colorizer, BlockPosM blockPosM) {
    int sumRed = 0;
    int sumGreen = 0;
    int sumBlue = 0;
    int x = blockPos.u();
    int y = blockPos.v();
    int z = blockPos.w();
    BlockPosM posM = blockPosM;
    int radius = Config.getBiomeBlendRadius();
    int width = radius * 2 + 1;
    int count = width * width;
    for (int ix = x - radius; ix <= x + radius; ix++) {
      for (int iz = z - radius; iz <= z + radius; iz++) {
        posM.setXyz(ix, y, iz);
        int col = colorizer.getColor(blockState, blockAccess, (jd)posM);
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
  
  public static int getFluidColor(dbz blockAccess, dtc blockState, jd blockPos, RenderEnv renderEnv) {
    IColorizer iColorizer;
    dfy block = blockState.b();
    CustomColormap customColormap = getBlockColormap(blockState);
    if (customColormap == null)
      if (blockState.b() == dga.G)
        iColorizer = COLORIZER_WATER;  
    if (iColorizer == null)
      return getBlockColors().a(blockState, blockAccess, blockPos, 0); 
    if (Config.isSmoothBiomes() && !iColorizer.isColorConstant())
      return getSmoothColorMultiplier(blockState, blockAccess, blockPos, iColorizer, renderEnv.getColorizerBlockPosM()); 
    return iColorizer.getColor(blockState, blockAccess, blockPos);
  }
  
  public static fhq getBlockColors() {
    return fgo.Q().au();
  }
  
  public static void updatePortalFX(gcn fx) {
    if (particlePortalColor < 0)
      return; 
    int col = particlePortalColor;
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.a(redF, greenF, blueF);
  }
  
  public static void updateLavaFX(gcn fx) {
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
    fx.a(redF, greenF, blueF);
  }
  
  public static void updateMyceliumFX(gcn fx) {
    if (myceliumParticleColors == null)
      return; 
    int col = myceliumParticleColors.getColorRandom();
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.a(redF, greenF, blueF);
  }
  
  private static int getRedstoneColor(dtc blockState) {
    if (redstoneColors == null)
      return -1; 
    int level = getRedstoneLevel(blockState, 15);
    int col = redstoneColors.getColor(level);
    return col;
  }
  
  public static void updateReddustFX(gcn fx, dbz blockAccess, double x, double y, double z) {
    if (redstoneColors == null)
      return; 
    dtc state = blockAccess.a_(jd.a(x, y, z));
    int level = getRedstoneLevel(state, 15);
    int col = redstoneColors.getColor(level);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    fx.a(redF, greenF, blueF);
  }
  
  private static int getRedstoneLevel(dtc state, int def) {
    dfy block = state.b();
    if (!(block instanceof dmb))
      return def; 
    Object val = state.c((duf)dmb.f);
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
    int index = (int)Math.round(((ayo.a(timer) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
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
  
  public static void updateWaterFX(gcn fx, dbz blockAccess, double x, double y, double z, RenderEnv renderEnv) {
    if (waterColors == null && blockColormaps == null && particleWaterColor < 0)
      return; 
    jd blockPos = jd.a(x, y, z);
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
    fx.a(redF, greenF, blueF);
  }
  
  private static int getLilypadColorMultiplier(dbz blockAccess, jd blockPos) {
    if (lilyPadColor < 0)
      return getBlockColors().a(dga.fm.o(), blockAccess, blockPos, 0); 
    return lilyPadColor;
  }
  
  private static exc getFogColorNether(exc col) {
    if (fogColorNether == null)
      return col; 
    return fogColorNether;
  }
  
  private static exc getFogColorEnd(exc col) {
    if (fogColorEnd == null)
      return col; 
    return fogColorEnd;
  }
  
  private static exc getSkyColorEnd(exc col) {
    if (skyColorEnd == null)
      return col; 
    return skyColorEnd;
  }
  
  public static exc getSkyColor(exc skyColor3d, dbz blockAccess, double x, double y, double z) {
    if (skyColors == null)
      return skyColor3d; 
    int col = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    float cRed = (float)skyColor3d.c / 0.5F;
    float cGreen = (float)skyColor3d.d / 0.66275F;
    float cBlue = (float)skyColor3d.e;
    redF *= cRed;
    greenF *= cGreen;
    blueF *= cBlue;
    exc newCol = skyColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  private static exc getFogColor(exc fogColor3d, dbz blockAccess, double x, double y, double z) {
    if (fogColors == null)
      return fogColor3d; 
    int col = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    float cRed = (float)fogColor3d.c / 0.753F;
    float cGreen = (float)fogColor3d.d / 0.8471F;
    float cBlue = (float)fogColor3d.e;
    redF *= cRed;
    greenF *= cGreen;
    blueF *= cBlue;
    exc newCol = fogColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  public static exc getUnderwaterColor(dbz blockAccess, double x, double y, double z) {
    return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
  }
  
  public static exc getUnderlavaColor(dbz blockAccess, double x, double y, double z) {
    return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
  }
  
  public static exc getUnderFluidColor(dbz blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
    if (underFluidColors == null)
      return null; 
    int col = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
    int red = col >> 16 & 0xFF;
    int green = col >> 8 & 0xFF;
    int blue = col & 0xFF;
    float redF = red / 255.0F;
    float greenF = green / 255.0F;
    float blueF = blue / 255.0F;
    exc newCol = underFluidColorFader.getColor(redF, greenF, blueF);
    return newCol;
  }
  
  private static int getStemColorMultiplier(dtc blockState, dcc blockAccess, jd blockPos, RenderEnv renderEnv) {
    CustomColormap colors = stemColors;
    dfy blockStem = blockState.b();
    if (blockStem == dga.fd && stemPumpkinColors != null)
      colors = stemPumpkinColors; 
    if (blockStem == dga.fe && stemMelonColors != null)
      colors = stemMelonColors; 
    if (colors == null)
      return -1; 
    if (!(blockStem instanceof dnv))
      return -1; 
    int level = ((Integer)blockState.c((duf)dnv.c)).intValue();
    return colors.getColor(level);
  }
  
  public static boolean updateLightmap(fzf world, float torchFlickerX, faj lmColors, boolean nightvision, float darkLight, float partialTicks) {
    if (world == null)
      return false; 
    if (lightMapPacks == null)
      return false; 
    int dimensionId = WorldUtils.getDimensionId((dcw)world);
    int lightMapIndex = dimensionId - lightmapMinDimensionId;
    if (lightMapIndex < 0 || lightMapIndex >= lightMapPacks.length)
      return false; 
    LightMapPack lightMapPack = lightMapPacks[lightMapIndex];
    if (lightMapPack == null)
      return false; 
    return lightMapPack.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight, partialTicks);
  }
  
  public static exc getWorldFogColor(exc fogVec, dcw world, bsr renderViewEntity, float partialTicks) {
    fgo mc = fgo.Q();
    if (WorldUtils.isNether(world))
      return getFogColorNether(fogVec); 
    if (WorldUtils.isOverworld(world))
      return getFogColor(fogVec, (dbz)mc.r, renderViewEntity.dt(), renderViewEntity.dv() + 1.0D, renderViewEntity.dz()); 
    if (WorldUtils.isEnd(world))
      return getFogColorEnd(fogVec); 
    return fogVec;
  }
  
  public static exc getWorldSkyColor(exc skyVec, dcw world, bsr renderViewEntity, float partialTicks) {
    fgo mc = fgo.Q();
    if (WorldUtils.isOverworld(world) && renderViewEntity != null)
      return getSkyColor(skyVec, (dbz)mc.r, renderViewEntity.dt(), renderViewEntity.dv() + 1.0D, renderViewEntity.dz()); 
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
          id = EntityUtils.getEntityIdByLocation((new akr(name)).toString()); 
      } catch (aa e) {
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
  
  private static int getSpawnEggColor(cwa item, cuq itemStack, int layer, int color) {
    if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null)
      return color; 
    bsx entityType = item.i(itemStack);
    if (entityType == null)
      return color; 
    int id = lt.f.a(entityType);
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
  
  public static int getColorFromItemStack(cuq itemStack, int layer, int color) {
    if (itemStack == null)
      return color; 
    cul item = itemStack.g();
    if (item == null)
      return color; 
    if (item instanceof cwa)
      return getSpawnEggColor((cwa)item, itemStack, layer, color); 
    if (item == cut.gb)
      if (lilyPadColor != -1)
        return lilyPadColor;  
    return color;
  }
  
  private static int[] readDyeColors(Properties props, String fileName, String prefix, String logName) {
    cti[] dyeValues = cti.values();
    Map<String, cti> mapDyes = new HashMap<>();
    for (int i = 0; i < dyeValues.length; i++) {
      cti dye = dyeValues[i];
      mapDyes.put(dye.c(), dye);
    } 
    mapDyes.put("lightBlue", cti.d);
    mapDyes.put("silver", cti.i);
    int[] colors = new int[dyeValues.length];
    int countColors = 0;
    Set<Object> keys = props.keySet();
    for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String value = props.getProperty(key);
      if (!key.startsWith(prefix))
        continue; 
      String name = StrUtils.removePrefix(key, prefix);
      cti dye = mapDyes.get(name);
      int color = parseColor(value);
      if (dye == null || color < 0) {
        warn("Invalid color: " + key + " = " + value);
        continue;
      } 
      int argb = axy.b.e(color);
      colors[dye.ordinal()] = argb;
      countColors++;
    } 
    if (countColors <= 0)
      return null; 
    dbg(logName + " colors: " + logName);
    return colors;
  }
  
  private static int getDyeColors(cti dye, int[] dyeColors, int color) {
    if (dyeColors == null)
      return color; 
    if (dye == null)
      return color; 
    int customColor = dyeColors[dye.ordinal()];
    if (customColor == 0)
      return color; 
    return customColor;
  }
  
  public static int getWolfCollarColors(cti dye, int color) {
    return getDyeColors(dye, wolfCollarColors, color);
  }
  
  public static int getSheepColors(cti dye, int color) {
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
    int[] colors = new int[epi.am.length];
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
    Set<akr> keys = lt.d.f();
    for (Iterator<akr> it = keys.iterator(); it.hasNext(); ) {
      akr rl = it.next();
      brx potion = PotionUtils.getPotion(rl);
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
    Set<akr> keys = lt.d.f();
    for (Iterator<akr> it = keys.iterator(); it.hasNext(); ) {
      akr rl = it.next();
      brx potion = PotionUtils.getPotion(rl);
      if (potion.d().equals(name))
        return PotionUtils.getId(potion); 
      if (potion.d().equals(nameMc))
        return PotionUtils.getId(potion); 
    } 
    return -1;
  }
  
  public static int getPotionColor(brx potion, int color) {
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
      return epi.a.al; 
    if (name.equals("grass"))
      return epi.b.al; 
    if (name.equals("sand"))
      return epi.c.al; 
    if (name.equals("cloth"))
      return epi.d.al; 
    if (name.equals("tnt"))
      return epi.e.al; 
    if (name.equals("ice"))
      return epi.f.al; 
    if (name.equals("iron"))
      return epi.g.al; 
    if (name.equals("foliage"))
      return epi.h.al; 
    if (name.equals("clay"))
      return epi.j.al; 
    if (name.equals("dirt"))
      return epi.k.al; 
    if (name.equals("stone"))
      return epi.l.al; 
    if (name.equals("water"))
      return epi.m.al; 
    if (name.equals("wood"))
      return epi.n.al; 
    if (name.equals("quartz"))
      return epi.o.al; 
    if (name.equals("gold"))
      return epi.E.al; 
    if (name.equals("diamond"))
      return epi.F.al; 
    if (name.equals("lapis"))
      return epi.G.al; 
    if (name.equals("emerald"))
      return epi.H.al; 
    if (name.equals("podzol"))
      return epi.I.al; 
    if (name.equals("netherrack"))
      return epi.J.al; 
    if (name.equals("snow") || name.equals("white"))
      return epi.i.al; 
    if (name.equals("adobe") || name.equals("orange"))
      return epi.p.al; 
    if (name.equals("magenta"))
      return epi.q.al; 
    if (name.equals("light_blue") || name.equals("lightBlue"))
      return epi.r.al; 
    if (name.equals("yellow"))
      return epi.s.al; 
    if (name.equals("lime"))
      return epi.t.al; 
    if (name.equals("pink"))
      return epi.u.al; 
    if (name.equals("gray"))
      return epi.v.al; 
    if (name.equals("silver") || name.equals("light_gray"))
      return epi.w.al; 
    if (name.equals("cyan"))
      return epi.x.al; 
    if (name.equals("purple"))
      return epi.y.al; 
    if (name.equals("blue"))
      return epi.z.al; 
    if (name.equals("brown"))
      return epi.A.al; 
    if (name.equals("green"))
      return epi.B.al; 
    if (name.equals("red"))
      return epi.C.al; 
    if (name.equals("black"))
      return epi.D.al; 
    if (name.equals("white_terracotta"))
      return epi.K.al; 
    if (name.equals("orange_terracotta"))
      return epi.L.al; 
    if (name.equals("magenta_terracotta"))
      return epi.M.al; 
    if (name.equals("light_blue_terracotta"))
      return epi.N.al; 
    if (name.equals("yellow_terracotta"))
      return epi.O.al; 
    if (name.equals("lime_terracotta"))
      return epi.P.al; 
    if (name.equals("pink_terracotta"))
      return epi.Q.al; 
    if (name.equals("gray_terracotta"))
      return epi.R.al; 
    if (name.equals("light_gray_terracotta"))
      return epi.S.al; 
    if (name.equals("cyan_terracotta"))
      return epi.T.al; 
    if (name.equals("purple_terracotta"))
      return epi.U.al; 
    if (name.equals("blue_terracotta"))
      return epi.V.al; 
    if (name.equals("brown_terracotta"))
      return epi.W.al; 
    if (name.equals("green_terracotta"))
      return epi.X.al; 
    if (name.equals("red_terracotta"))
      return epi.Y.al; 
    if (name.equals("black_terracotta"))
      return epi.Z.al; 
    if (name.equals("crimson_nylium"))
      return epi.aa.al; 
    if (name.equals("crimson_stem"))
      return epi.ab.al; 
    if (name.equals("crimson_hyphae"))
      return epi.ac.al; 
    if (name.equals("warped_nylium"))
      return epi.ad.al; 
    if (name.equals("warped_stem"))
      return epi.ae.al; 
    if (name.equals("warped_hyphae"))
      return epi.af.al; 
    if (name.equals("warped_wart_block"))
      return epi.ag.al; 
    if (name.equals("deepslate"))
      return epi.ah.al; 
    if (name.equals("raw_iron"))
      return epi.ai.al; 
    if (name.equals("glow_lichen"))
      return epi.aj.al; 
    return -1;
  }
  
  private static int[] getMapColors() {
    epi[] mapColors = epi.am;
    int[] colors = new int[mapColors.length];
    Arrays.fill(colors, -1);
    for (int i = 0; i < mapColors.length && i < colors.length; i++) {
      epi mapColor = mapColors[i];
      if (mapColor != null)
        colors[i] = mapColor.ak; 
    } 
    return colors;
  }
  
  private static void setMapColors(int[] colors) {
    if (colors == null)
      return; 
    epi[] mapColors = epi.am;
    for (int i = 0; i < mapColors.length && i < colors.length; i++) {
      epi mapColor = mapColors[i];
      if (mapColor != null) {
        int color = colors[i];
        if (color >= 0)
          if (mapColor.ak != color)
            mapColor.ak = color;  
      } 
    } 
  }
  
  private static int[] getDyeColors() {
    cti[] dyeColors = cti.values();
    int[] colors = new int[dyeColors.length];
    for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
      cti dyeColor = dyeColors[i];
      if (dyeColor != null)
        colors[i] = dyeColor.d(); 
    } 
    return colors;
  }
  
  private static void setDyeColors(int[] colors) {
    if (colors == null)
      return; 
    cti[] dyeColors = cti.values();
    for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
      cti dyeColor = dyeColors[i];
      if (dyeColor != null) {
        int color = colors[i];
        if (color != 0)
          if (dyeColor.d() != color)
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
