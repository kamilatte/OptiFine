package notch.net.optifine;

import akr;
import ayw;
import dcc;
import dfy;
import dga;
import dtc;
import gqb;
import gqk;
import gql;
import gsm;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import jd;
import ji;
import net.optifine.Config;
import net.optifine.model.BlockModelUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.RandomUtils;

public class BetterGrass {
  private static boolean betterGrass = true;
  
  private static boolean betterDirtPath = true;
  
  private static boolean betterMycelium = true;
  
  private static boolean betterPodzol = true;
  
  private static boolean betterCrimsonNylium = true;
  
  private static boolean betterWarpedNylium = true;
  
  private static boolean betterGrassSnow = true;
  
  private static boolean betterMyceliumSnow = true;
  
  private static boolean betterPodzolSnow = true;
  
  private static boolean grassMultilayer = false;
  
  private static gql spriteGrass = null;
  
  private static gql spriteGrassSide = null;
  
  private static gql spriteDirtPath = null;
  
  private static gql spriteDirtPathSide = null;
  
  private static gql spriteMycelium = null;
  
  private static gql spritePodzol = null;
  
  private static gql spriteCrimsonNylium = null;
  
  private static gql spriteWarpedNylium = null;
  
  private static gql spriteSnow = null;
  
  private static boolean spritesLoaded = false;
  
  private static gsm modelCubeGrass = null;
  
  private static gsm modelDirtPath = null;
  
  private static gsm modelCubeDirtPath = null;
  
  private static gsm modelCubeMycelium = null;
  
  private static gsm modelCubePodzol = null;
  
  private static gsm modelCubeCrimsonNylium = null;
  
  private static gsm modelCubeWarpedNylium = null;
  
  private static gsm modelCubeSnow = null;
  
  private static boolean modelsLoaded = false;
  
  private static final String TEXTURE_GRASS_DEFAULT = "block/grass_block_top";
  
  private static final String TEXTURE_GRASS_SIDE_DEFAULT = "block/grass_block_side";
  
  private static final String TEXTURE_DIRT_PATH_DEFAULT = "block/dirt_path_top";
  
  private static final String TEXTURE_DIRT_PATH_SIDE_DEFAULT = "block/dirt_path_side";
  
  private static final String TEXTURE_MYCELIUM_DEFAULT = "block/mycelium_top";
  
  private static final String TEXTURE_PODZOL_DEFAULT = "block/podzol_top";
  
  private static final String TEXTURE_CRIMSON_NYLIUM = "block/crimson_nylium";
  
  private static final String TEXTURE_WARPED_NYLIUM = "block/warped_nylium";
  
  private static final String TEXTURE_SNOW_DEFAULT = "block/snow";
  
  private static final ayw RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static void updateIcons(gqk textureMap) {
    spritesLoaded = false;
    modelsLoaded = false;
    loadProperties(textureMap);
  }
  
  public static void update() {
    if (!spritesLoaded)
      return; 
    modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
    if (grassMultilayer) {
      gsm modelCubeGrassSide = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
      modelCubeGrass = BlockModelUtils.joinModelsCube(modelCubeGrassSide, modelCubeGrass);
    } 
    modelDirtPath = BlockModelUtils.makeModel("dirt_path", spriteDirtPathSide, spriteDirtPath);
    modelCubeDirtPath = BlockModelUtils.makeModelCube(spriteDirtPath, -1);
    modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
    modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
    modelCubeCrimsonNylium = BlockModelUtils.makeModelCube(spriteCrimsonNylium, -1);
    modelCubeWarpedNylium = BlockModelUtils.makeModelCube(spriteWarpedNylium, -1);
    modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
    modelsLoaded = true;
  }
  
  private static void loadProperties(gqk textureMap) {
    betterGrass = true;
    betterDirtPath = true;
    betterMycelium = true;
    betterPodzol = true;
    betterCrimsonNylium = true;
    betterWarpedNylium = true;
    betterGrassSnow = true;
    betterMyceliumSnow = true;
    betterPodzolSnow = true;
    spriteGrass = textureMap.registerSprite(new akr("block/grass_block_top"));
    spriteGrassSide = textureMap.registerSprite(new akr("block/grass_block_side"));
    spriteDirtPath = textureMap.registerSprite(new akr("block/dirt_path_top"));
    spriteDirtPathSide = textureMap.registerSprite(new akr("block/dirt_path_side"));
    spriteMycelium = textureMap.registerSprite(new akr("block/mycelium_top"));
    spritePodzol = textureMap.registerSprite(new akr("block/podzol_top"));
    spriteCrimsonNylium = textureMap.registerSprite(new akr("block/crimson_nylium"));
    spriteWarpedNylium = textureMap.registerSprite(new akr("block/warped_nylium"));
    spriteSnow = textureMap.registerSprite(new akr("block/snow"));
    spritesLoaded = true;
    String name = "optifine/bettergrass.properties";
    try {
      akr locFile = new akr(name);
      if (!Config.hasResource(locFile))
        return; 
      InputStream in = Config.getResourceStream(locFile);
      if (in == null)
        return; 
      boolean defaultConfig = Config.isFromDefaultResourcePack(locFile);
      if (defaultConfig) {
        Config.dbg("BetterGrass: Parsing default configuration " + name);
      } else {
        Config.dbg("BetterGrass: Parsing configuration " + name);
      } 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      betterGrass = getBoolean((Properties)propertiesOrdered, "grass", true);
      betterDirtPath = getBoolean((Properties)propertiesOrdered, "dirt_path", true);
      betterMycelium = getBoolean((Properties)propertiesOrdered, "mycelium", true);
      betterPodzol = getBoolean((Properties)propertiesOrdered, "podzol", true);
      betterCrimsonNylium = getBoolean((Properties)propertiesOrdered, "crimson_nylium", true);
      betterWarpedNylium = getBoolean((Properties)propertiesOrdered, "warped_nylium", true);
      betterGrassSnow = getBoolean((Properties)propertiesOrdered, "grass.snow", true);
      betterMyceliumSnow = getBoolean((Properties)propertiesOrdered, "mycelium.snow", true);
      betterPodzolSnow = getBoolean((Properties)propertiesOrdered, "podzol.snow", true);
      grassMultilayer = getBoolean((Properties)propertiesOrdered, "grass.multilayer", false);
      spriteGrass = registerSprite((Properties)propertiesOrdered, "texture.grass", "block/grass_block_top", textureMap);
      spriteGrassSide = registerSprite((Properties)propertiesOrdered, "texture.grass_side", "block/grass_block_side", textureMap);
      spriteDirtPath = registerSprite((Properties)propertiesOrdered, "texture.dirt_path", "block/dirt_path_top", textureMap);
      spriteDirtPathSide = registerSprite((Properties)propertiesOrdered, "texture.dirt_path_side", "block/dirt_path_side", textureMap);
      spriteMycelium = registerSprite((Properties)propertiesOrdered, "texture.mycelium", "block/mycelium_top", textureMap);
      spritePodzol = registerSprite((Properties)propertiesOrdered, "texture.podzol", "block/podzol_top", textureMap);
      spriteCrimsonNylium = registerSprite((Properties)propertiesOrdered, "texture.crimson_nylium", "block/crimson_nylium", textureMap);
      spriteWarpedNylium = registerSprite((Properties)propertiesOrdered, "texture.warped_nylium", "block/warped_nylium", textureMap);
      spriteSnow = registerSprite((Properties)propertiesOrdered, "texture.snow", "block/snow", textureMap);
    } catch (IOException e) {
      Config.warn("Error reading: " + name + ", " + e.getClass().getName() + ": " + e.getMessage());
    } 
  }
  
  public static void refreshIcons(gqk textureMap) {
    spriteGrass = getSprite(textureMap, spriteGrass.getName());
    spriteGrassSide = getSprite(textureMap, spriteGrassSide.getName());
    spriteDirtPath = getSprite(textureMap, spriteDirtPath.getName());
    spriteDirtPathSide = getSprite(textureMap, spriteDirtPathSide.getName());
    spriteMycelium = getSprite(textureMap, spriteMycelium.getName());
    spritePodzol = getSprite(textureMap, spritePodzol.getName());
    spriteCrimsonNylium = getSprite(textureMap, spriteCrimsonNylium.getName());
    spriteWarpedNylium = getSprite(textureMap, spriteWarpedNylium.getName());
    spriteSnow = getSprite(textureMap, spriteSnow.getName());
  }
  
  private static gql getSprite(gqk textureMap, akr loc) {
    gql sprite = textureMap.a(loc);
    if (sprite == null || gqb.isMisingSprite(sprite))
      Config.warn("Missing BetterGrass sprite: " + String.valueOf(loc)); 
    return sprite;
  }
  
  private static gql registerSprite(Properties props, String key, String textureDefault, gqk textureMap) {
    String texture = props.getProperty(key);
    if (texture == null)
      texture = textureDefault; 
    akr locPng = new akr("textures/" + texture + ".png");
    if (!Config.hasResource(locPng)) {
      Config.warn("BetterGrass texture not found: " + String.valueOf(locPng));
      texture = textureDefault;
    } 
    akr locSprite = new akr(texture);
    gql sprite = textureMap.registerSprite(locSprite);
    return sprite;
  }
  
  public static List getFaceQuads(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    if (facing == ji.b || facing == ji.a)
      return quads; 
    if (!modelsLoaded)
      return quads; 
    dfy block = blockState.b();
    if (block instanceof dlb)
      return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads); 
    if (block instanceof dic)
      return getFaceQuadsDirtPath(blockAccess, blockState, blockPos, facing, quads); 
    if (block == dga.l)
      return getFaceQuadsPodzol(blockAccess, blockState, blockPos, facing, quads); 
    if (block == dga.ow)
      return getFaceQuadsCrimsonNylium(blockAccess, blockState, blockPos, facing, quads); 
    if (block == dga.on)
      return getFaceQuadsWarpedNylium(blockAccess, blockState, blockPos, facing, quads); 
    if (block == dga.j)
      return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads); 
    if (block instanceof djj)
      return getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads); 
    return quads;
  }
  
  private static List getFaceQuadsMycelium(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    dfy blockUp = blockAccess.a_(blockPos.d()).b();
    boolean snowy = (blockUp == dga.dP || blockUp == dga.dN);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == dga.dN)
          return modelCubeSnow.a(blockState, facing, RANDOM); 
      } else if (betterMycelium && getBlockAt(blockPos.e(), facing, blockAccess) == dga.fl) {
        return modelCubeMycelium.a(blockState, facing, RANDOM);
      } 
    } else if (snowy) {
      if (betterMyceliumSnow)
        return modelCubeSnow.a(blockState, facing, RANDOM); 
    } else if (betterMycelium) {
      return modelCubeMycelium.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsDirtPath(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    if (!betterDirtPath)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.e(), facing, blockAccess) == dga.kE)
        return modelDirtPath.a(blockState, facing, RANDOM); 
    } else {
      return modelDirtPath.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsPodzol(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    dfy blockTop = getBlockAt(blockPos, ji.b, blockAccess);
    boolean snowy = (blockTop == dga.dP || blockTop == dga.dN);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == dga.dN)
          return modelCubeSnow.a(blockState, facing, RANDOM); 
      } else if (betterPodzol) {
        jd posSide = blockPos.e().a(facing);
        dtc stateSide = blockAccess.a_(posSide);
        if (stateSide.b() == dga.l)
          return modelCubePodzol.a(blockState, facing, RANDOM); 
      } 
    } else if (snowy) {
      if (betterPodzolSnow)
        return modelCubeSnow.a(blockState, facing, RANDOM); 
    } else if (betterPodzol) {
      return modelCubePodzol.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsCrimsonNylium(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    if (!betterCrimsonNylium)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.e(), facing, blockAccess) == dga.ow)
        return modelCubeCrimsonNylium.a(blockState, facing, RANDOM); 
    } else {
      return modelCubeCrimsonNylium.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsWarpedNylium(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    if (!betterWarpedNylium)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.e(), facing, blockAccess) == dga.on)
        return modelCubeWarpedNylium.a(blockState, facing, RANDOM); 
    } else {
      return modelCubeWarpedNylium.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsDirt(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    dfy blockTop = getBlockAt(blockPos, ji.b, blockAccess);
    if (blockTop == dga.kE)
      if (betterDirtPath && getBlockAt(blockPos, facing, blockAccess) == dga.kE)
        return modelCubeDirtPath.a(blockState, facing, RANDOM);  
    return quads;
  }
  
  private static List getFaceQuadsGrass(dcc blockAccess, dtc blockState, jd blockPos, ji facing, List quads) {
    dfy blockUp = blockAccess.a_(blockPos.d()).b();
    boolean snowy = (blockUp == dga.dP || blockUp == dga.dN);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == dga.dN)
          return modelCubeSnow.a(blockState, facing, RANDOM); 
      } else if (betterGrass && getBlockAt(blockPos.e(), facing, blockAccess) == dga.i) {
        return modelCubeGrass.a(blockState, facing, RANDOM);
      } 
    } else if (snowy) {
      if (betterGrassSnow)
        return modelCubeSnow.a(blockState, facing, RANDOM); 
    } else if (betterGrass) {
      return modelCubeGrass.a(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static dfy getBlockAt(jd blockPos, ji facing, dcc blockAccess) {
    jd pos = blockPos.a(facing);
    dfy block = blockAccess.a_(pos).b();
    return block;
  }
  
  private static boolean getBoolean(Properties props, String key, boolean def) {
    String str = props.getProperty(key);
    if (str == null)
      return def; 
    return Boolean.parseBoolean(str);
  }
}
