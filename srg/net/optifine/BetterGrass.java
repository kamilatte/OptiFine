package srg.net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
  
  private static TextureAtlasSprite spriteGrass = null;
  
  private static TextureAtlasSprite spriteGrassSide = null;
  
  private static TextureAtlasSprite spriteDirtPath = null;
  
  private static TextureAtlasSprite spriteDirtPathSide = null;
  
  private static TextureAtlasSprite spriteMycelium = null;
  
  private static TextureAtlasSprite spritePodzol = null;
  
  private static TextureAtlasSprite spriteCrimsonNylium = null;
  
  private static TextureAtlasSprite spriteWarpedNylium = null;
  
  private static TextureAtlasSprite spriteSnow = null;
  
  private static boolean spritesLoaded = false;
  
  private static BakedModel modelCubeGrass = null;
  
  private static BakedModel modelDirtPath = null;
  
  private static BakedModel modelCubeDirtPath = null;
  
  private static BakedModel modelCubeMycelium = null;
  
  private static BakedModel modelCubePodzol = null;
  
  private static BakedModel modelCubeCrimsonNylium = null;
  
  private static BakedModel modelCubeWarpedNylium = null;
  
  private static BakedModel modelCubeSnow = null;
  
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
  
  private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static void updateIcons(TextureAtlas textureMap) {
    spritesLoaded = false;
    modelsLoaded = false;
    loadProperties(textureMap);
  }
  
  public static void update() {
    if (!spritesLoaded)
      return; 
    modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
    if (grassMultilayer) {
      BakedModel modelCubeGrassSide = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
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
  
  private static void loadProperties(TextureAtlas textureMap) {
    betterGrass = true;
    betterDirtPath = true;
    betterMycelium = true;
    betterPodzol = true;
    betterCrimsonNylium = true;
    betterWarpedNylium = true;
    betterGrassSnow = true;
    betterMyceliumSnow = true;
    betterPodzolSnow = true;
    spriteGrass = textureMap.registerSprite(new ResourceLocation("block/grass_block_top"));
    spriteGrassSide = textureMap.registerSprite(new ResourceLocation("block/grass_block_side"));
    spriteDirtPath = textureMap.registerSprite(new ResourceLocation("block/dirt_path_top"));
    spriteDirtPathSide = textureMap.registerSprite(new ResourceLocation("block/dirt_path_side"));
    spriteMycelium = textureMap.registerSprite(new ResourceLocation("block/mycelium_top"));
    spritePodzol = textureMap.registerSprite(new ResourceLocation("block/podzol_top"));
    spriteCrimsonNylium = textureMap.registerSprite(new ResourceLocation("block/crimson_nylium"));
    spriteWarpedNylium = textureMap.registerSprite(new ResourceLocation("block/warped_nylium"));
    spriteSnow = textureMap.registerSprite(new ResourceLocation("block/snow"));
    spritesLoaded = true;
    String name = "optifine/bettergrass.properties";
    try {
      ResourceLocation locFile = new ResourceLocation(name);
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
  
  public static void refreshIcons(TextureAtlas textureMap) {
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
  
  private static TextureAtlasSprite getSprite(TextureAtlas textureMap, ResourceLocation loc) {
    TextureAtlasSprite sprite = textureMap.getSprite(loc);
    if (sprite == null || MissingTextureAtlasSprite.isMisingSprite(sprite))
      Config.warn("Missing BetterGrass sprite: " + String.valueOf(loc)); 
    return sprite;
  }
  
  private static TextureAtlasSprite registerSprite(Properties props, String key, String textureDefault, TextureAtlas textureMap) {
    String texture = props.getProperty(key);
    if (texture == null)
      texture = textureDefault; 
    ResourceLocation locPng = new ResourceLocation("textures/" + texture + ".png");
    if (!Config.hasResource(locPng)) {
      Config.warn("BetterGrass texture not found: " + String.valueOf(locPng));
      texture = textureDefault;
    } 
    ResourceLocation locSprite = new ResourceLocation(texture);
    TextureAtlasSprite sprite = textureMap.registerSprite(locSprite);
    return sprite;
  }
  
  public static List getFaceQuads(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    if (facing == Direction.UP || facing == Direction.DOWN)
      return quads; 
    if (!modelsLoaded)
      return quads; 
    Block block = blockState.getBlock();
    if (block instanceof net.minecraft.world.level.block.MyceliumBlock)
      return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads); 
    if (block instanceof net.minecraft.world.level.block.DirtPathBlock)
      return getFaceQuadsDirtPath(blockAccess, blockState, blockPos, facing, quads); 
    if (block == Blocks.PODZOL)
      return getFaceQuadsPodzol(blockAccess, blockState, blockPos, facing, quads); 
    if (block == Blocks.CRIMSON_NYLIUM)
      return getFaceQuadsCrimsonNylium(blockAccess, blockState, blockPos, facing, quads); 
    if (block == Blocks.WARPED_NYLIUM)
      return getFaceQuadsWarpedNylium(blockAccess, blockState, blockPos, facing, quads); 
    if (block == Blocks.DIRT)
      return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads); 
    if (block instanceof net.minecraft.world.level.block.GrassBlock)
      return getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads); 
    return quads;
  }
  
  private static List getFaceQuadsMycelium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    Block blockUp = blockAccess.getBlockState(blockPos.above()).getBlock();
    boolean snowy = (blockUp == Blocks.SNOW_BLOCK || blockUp == Blocks.SNOW);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.SNOW)
          return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
      } else if (betterMycelium && getBlockAt(blockPos.below(), facing, blockAccess) == Blocks.MYCELIUM) {
        return modelCubeMycelium.getQuads(blockState, facing, RANDOM);
      } 
    } else if (snowy) {
      if (betterMyceliumSnow)
        return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
    } else if (betterMycelium) {
      return modelCubeMycelium.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsDirtPath(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    if (!betterDirtPath)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.below(), facing, blockAccess) == Blocks.DIRT_PATH)
        return modelDirtPath.getQuads(blockState, facing, RANDOM); 
    } else {
      return modelDirtPath.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsPodzol(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    Block blockTop = getBlockAt(blockPos, Direction.UP, blockAccess);
    boolean snowy = (blockTop == Blocks.SNOW_BLOCK || blockTop == Blocks.SNOW);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.SNOW)
          return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
      } else if (betterPodzol) {
        BlockPos posSide = blockPos.below().relative(facing);
        BlockState stateSide = blockAccess.getBlockState(posSide);
        if (stateSide.getBlock() == Blocks.PODZOL)
          return modelCubePodzol.getQuads(blockState, facing, RANDOM); 
      } 
    } else if (snowy) {
      if (betterPodzolSnow)
        return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
    } else if (betterPodzol) {
      return modelCubePodzol.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsCrimsonNylium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    if (!betterCrimsonNylium)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.below(), facing, blockAccess) == Blocks.CRIMSON_NYLIUM)
        return modelCubeCrimsonNylium.getQuads(blockState, facing, RANDOM); 
    } else {
      return modelCubeCrimsonNylium.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsWarpedNylium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    if (!betterWarpedNylium)
      return quads; 
    if (Config.isBetterGrassFancy()) {
      if (getBlockAt(blockPos.below(), facing, blockAccess) == Blocks.WARPED_NYLIUM)
        return modelCubeWarpedNylium.getQuads(blockState, facing, RANDOM); 
    } else {
      return modelCubeWarpedNylium.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static List getFaceQuadsDirt(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    Block blockTop = getBlockAt(blockPos, Direction.UP, blockAccess);
    if (blockTop == Blocks.DIRT_PATH)
      if (betterDirtPath && getBlockAt(blockPos, facing, blockAccess) == Blocks.DIRT_PATH)
        return modelCubeDirtPath.getQuads(blockState, facing, RANDOM);  
    return quads;
  }
  
  private static List getFaceQuadsGrass(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
    Block blockUp = blockAccess.getBlockState(blockPos.above()).getBlock();
    boolean snowy = (blockUp == Blocks.SNOW_BLOCK || blockUp == Blocks.SNOW);
    if (Config.isBetterGrassFancy()) {
      if (snowy) {
        if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.SNOW)
          return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
      } else if (betterGrass && getBlockAt(blockPos.below(), facing, blockAccess) == Blocks.GRASS_BLOCK) {
        return modelCubeGrass.getQuads(blockState, facing, RANDOM);
      } 
    } else if (snowy) {
      if (betterGrassSnow)
        return modelCubeSnow.getQuads(blockState, facing, RANDOM); 
    } else if (betterGrass) {
      return modelCubeGrass.getQuads(blockState, facing, RANDOM);
    } 
    return quads;
  }
  
  private static Block getBlockAt(BlockPos blockPos, Direction facing, BlockGetter blockAccess) {
    BlockPos pos = blockPos.relative(facing);
    Block block = blockAccess.getBlockState(pos).getBlock();
    return block;
  }
  
  private static boolean getBoolean(Properties props, String key, boolean def) {
    String str = props.getProperty(key);
    if (str == null)
      return def; 
    return Boolean.parseBoolean(str);
  }
}
