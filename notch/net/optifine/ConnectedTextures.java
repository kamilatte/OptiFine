package notch.net.optifine;

import akr;
import asq;
import ass;
import ayw;
import cti;
import dbz;
import dcc;
import ddw;
import dfy;
import dga;
import dhu;
import dml;
import dns;
import dtc;
import duf;
import fgo;
import gfw;
import gqb;
import gqk;
import gql;
import gsm;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jd;
import ji;
import net.optifine.BetterGrass;
import net.optifine.BlockDir;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.ConnectedTexturesCompact;
import net.optifine.config.Matches;
import net.optifine.model.BlockModelUtils;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.render.RenderEnv;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.RandomUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.TileEntityUtils;

public class ConnectedTextures {
  private static Map[] spriteQuadMaps = null;
  
  private static Map[] spriteQuadFullMaps = null;
  
  private static Map[][] spriteQuadCompactMaps = null;
  
  private static ConnectedProperties[][] blockProperties = null;
  
  private static ConnectedProperties[][] tileProperties = null;
  
  private static boolean multipass = false;
  
  protected static final int UNKNOWN = -1;
  
  protected static final int Y_NEG_DOWN = 0;
  
  protected static final int Y_POS_UP = 1;
  
  protected static final int Z_NEG_NORTH = 2;
  
  protected static final int Z_POS_SOUTH = 3;
  
  protected static final int X_NEG_WEST = 4;
  
  protected static final int X_POS_EAST = 5;
  
  private static final int Y_AXIS = 0;
  
  private static final int Z_AXIS = 1;
  
  private static final int X_AXIS = 2;
  
  public static final dtc AIR_DEFAULT_STATE = dga.a.o();
  
  private static gql emptySprite = null;
  
  public static akr LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
  
  private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
  
  private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
  
  private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
  
  private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
  
  private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
  
  private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
  
  private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
  
  private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
  
  private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
  
  private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
  
  private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
  
  private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
  
  public static final gql SPRITE_DEFAULT = new gql(gqk.e, new akr("default"));
  
  private static final ayw RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static gfw[] getConnectedTexture(dbz blockAccess, dtc blockState, jd blockPos, gfw quad, RenderEnv renderEnv) {
    gql spriteIn = quad.a();
    if (spriteIn == null)
      return renderEnv.getArrayQuadsCtm(quad); 
    if (skipConnectedTexture((dcc)blockAccess, blockState, blockPos, quad, renderEnv)) {
      quad = getQuad(emptySprite, quad);
      return renderEnv.getArrayQuadsCtm(quad);
    } 
    ji side = quad.e();
    gfw[] quads = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, quad, renderEnv);
    return quads;
  }
  
  private static boolean skipConnectedTexture(dcc blockAccess, dtc blockState, jd blockPos, gfw quad, RenderEnv renderEnv) {
    dfy block = blockState.b();
    if (block instanceof djz) {
      ji face = quad.e();
      if (face != ji.b && face != ji.a)
        return false; 
      if (!quad.isFaceQuad())
        return false; 
      jd posNeighbour = blockPos.a(quad.e());
      dtc stateNeighbour = blockAccess.a_(posNeighbour);
      if (stateNeighbour.b() != block)
        return false; 
      dfy blockNeighbour = stateNeighbour.b();
      if (block instanceof dns && blockNeighbour instanceof dns) {
        cti color = ((dns)block).b();
        cti colorNeighbour = ((dns)blockNeighbour).b();
        if (color != colorNeighbour)
          return false; 
      } 
      double midX = quad.getMidX();
      if (midX < 0.4D) {
        if (((Boolean)stateNeighbour.c((duf)dhu.d)).booleanValue())
          return true; 
      } else if (midX > 0.6D) {
        if (((Boolean)stateNeighbour.c((duf)dhu.b)).booleanValue())
          return true; 
      } else {
        double midZ = quad.getMidZ();
        if (midZ < 0.4D) {
          if (((Boolean)stateNeighbour.c((duf)dhu.a)).booleanValue())
            return true; 
        } else if (midZ > 0.6D) {
          if (((Boolean)stateNeighbour.c((duf)dhu.c)).booleanValue())
            return true; 
        } else {
          return true;
        } 
      } 
    } 
    return false;
  }
  
  protected static gfw[] getQuads(gql sprite, gfw quadIn, RenderEnv renderEnv) {
    if (sprite == null)
      return null; 
    if (sprite == SPRITE_DEFAULT)
      return renderEnv.getArrayQuadsCtm(quadIn); 
    gfw quad = getQuad(sprite, quadIn);
    gfw[] quads = renderEnv.getArrayQuadsCtm(quad);
    return quads;
  }
  
  private static synchronized gfw getQuad(gql sprite, gfw quadIn) {
    if (spriteQuadMaps == null)
      return quadIn; 
    int spriteIndex = sprite.getIndexInMap();
    if (spriteIndex < 0 || spriteIndex >= spriteQuadMaps.length)
      return quadIn; 
    Map<Object, Object> quadMap = spriteQuadMaps[spriteIndex];
    if (quadMap == null) {
      quadMap = new IdentityHashMap<>(1);
      spriteQuadMaps[spriteIndex] = quadMap;
    } 
    gfw quad = (gfw)quadMap.get(quadIn);
    if (quad == null) {
      quad = makeSpriteQuad(quadIn, sprite);
      quadMap.put(quadIn, quad);
    } 
    return quad;
  }
  
  private static synchronized gfw getQuadFull(gql sprite, gfw quadIn, int tintIndex) {
    if (spriteQuadFullMaps == null)
      return null; 
    if (sprite == null)
      return null; 
    int spriteIndex = sprite.getIndexInMap();
    if (spriteIndex < 0 || spriteIndex >= spriteQuadFullMaps.length)
      return null; 
    Map<ji, Object> quadMap = spriteQuadFullMaps[spriteIndex];
    if (quadMap == null) {
      quadMap = new EnumMap<>(ji.class);
      spriteQuadFullMaps[spriteIndex] = quadMap;
    } 
    ji face = quadIn.e();
    gfw quad = (gfw)quadMap.get(face);
    if (quad == null) {
      quad = BlockModelUtils.makeBakedQuad(face, sprite, tintIndex);
      quadMap.put(face, quad);
    } 
    return quad;
  }
  
  private static gfw makeSpriteQuad(gfw quad, gql sprite) {
    int[] data = (int[])quad.b().clone();
    gql spriteFrom = quad.a();
    for (int i = 0; i < 4; i++)
      fixVertex(data, i, spriteFrom, sprite); 
    gfw bq = new gfw(data, quad.d(), quad.e(), sprite, quad.f());
    return bq;
  }
  
  private static void fixVertex(int[] data, int vertex, gql spriteFrom, gql spriteTo) {
    int mul = data.length / 4;
    int pos = mul * vertex;
    float u = Float.intBitsToFloat(data[pos + 4]);
    float v = Float.intBitsToFloat(data[pos + 4 + 1]);
    double su16 = spriteFrom.getSpriteU16(u);
    double sv16 = spriteFrom.getSpriteV16(v);
    data[pos + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU16(su16));
    data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV16(sv16));
  }
  
  private static gfw[] getConnectedTextureMultiPass(dbz blockAccess, dtc blockState, jd blockPos, ji side, gfw quad, RenderEnv renderEnv) {
    gfw[] quads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
    if (!multipass)
      return quads; 
    if (quads.length == 1 && quads[0] == quad)
      return quads; 
    List<gfw> listQuads = renderEnv.getListQuadsCtmMultipass(quads);
    for (int q = 0; q < listQuads.size(); q++) {
      gfw newQuad = listQuads.get(q);
      gfw mpQuad = newQuad;
      for (int j = 0; j < 3; j++) {
        gfw[] newMpQuads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, mpQuad, false, j + 1, renderEnv);
        if (newMpQuads.length != 1)
          break; 
        if (newMpQuads[0] == mpQuad)
          break; 
        mpQuad = newMpQuads[0];
      } 
      listQuads.set(q, mpQuad);
    } 
    for (int i = 0; i < quads.length; i++)
      quads[i] = listQuads.get(i); 
    return quads;
  }
  
  public static gfw[] getConnectedTextureSingle(dbz blockAccess, dtc blockState, jd blockPos, ji facing, gfw quad, boolean checkBlocks, int pass, RenderEnv renderEnv) {
    dfy block = blockState.b();
    gql icon = quad.a();
    if (tileProperties != null) {
      int iconId = icon.getIndexInMap();
      if (iconId >= 0 && iconId < tileProperties.length) {
        ConnectedProperties[] cps = tileProperties[iconId];
        if (cps != null) {
          int side = getSide(facing);
          for (int i = 0; i < cps.length; i++) {
            ConnectedProperties cp = cps[i];
            if (cp != null)
              if (cp.matchesBlockId(blockState.getBlockId())) {
                gfw[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
                if (newQuads != null)
                  return newQuads; 
              }  
          } 
        } 
      } 
    } 
    if (blockProperties != null && checkBlocks) {
      int blockId = renderEnv.getBlockId();
      if (blockId >= 0 && blockId < blockProperties.length) {
        ConnectedProperties[] cps = blockProperties[blockId];
        if (cps != null) {
          int side = getSide(facing);
          for (int i = 0; i < cps.length; i++) {
            ConnectedProperties cp = cps[i];
            if (cp != null)
              if (cp.matchesIcon(icon)) {
                gfw[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
                if (newQuads != null)
                  return newQuads; 
              }  
          } 
        } 
      } 
    } 
    return renderEnv.getArrayQuadsCtm(quad);
  }
  
  public static int getSide(ji facing) {
    if (facing == null)
      return -1; 
    switch (null.$SwitchMap$net$minecraft$core$Direction[facing.ordinal()]) {
      case 1:
        return 0;
      case 2:
        return 1;
      case 3:
        return 5;
      case 4:
        return 4;
      case 5:
        return 2;
      case 6:
        return 3;
    } 
    return -1;
  }
  
  private static ji getFacing(int side) {
    switch (side) {
      case 0:
        return ji.a;
      case 1:
        return ji.b;
      case 5:
        return ji.f;
      case 4:
        return ji.e;
      case 2:
        return ji.c;
      case 3:
        return ji.d;
    } 
    return ji.b;
  }
  
  private static gfw[] getConnectedTexture(ConnectedProperties cp, dbz blockAccess, dtc blockState, jd blockPos, int side, gfw quad, int pass, RenderEnv renderEnv) {
    int vertAxis = 0;
    int metadata = blockState.getMetadata();
    dfy block = blockState.b();
    if (block instanceof dml)
      vertAxis = getPillarAxis(blockState); 
    if (!cp.matchesBlock(blockState.getBlockId(), metadata))
      return null; 
    if (side >= 0)
      if (cp.faces != 63) {
        int sideCheck = side;
        if (vertAxis != 0)
          sideCheck = fixSideByAxis(side, vertAxis); 
        if ((1 << sideCheck & cp.faces) == 0)
          return null; 
      }  
    int y = blockPos.v();
    if (cp.heights != null && !cp.heights.isInRange(y))
      return null; 
    if (cp.biomes != null) {
      ddw blockBiome = BiomeUtils.getBiome(blockAccess, blockPos);
      if (!cp.matchesBiome(blockBiome))
        return null; 
    } 
    if (cp.nbtName != null) {
      String name = TileEntityUtils.getTileEntityName((dcc)blockAccess, blockPos);
      if (!cp.nbtName.matchesValue(name))
        return null; 
    } 
    gql icon = quad.a();
    switch (cp.method) {
      case 10:
        if (pass != 0)
          break; 
        return getConnectedTextureCtmCompact(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
      case 1:
        return getQuads(getConnectedTextureCtm(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv), quad, renderEnv);
      case 2:
        return getQuads(getConnectedTextureHorizontal(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
      case 6:
        return getQuads(getConnectedTextureVertical(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
      case 3:
        return getQuads(getConnectedTextureTop(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
      case 4:
        return getQuads(getConnectedTextureRandom(cp, (dcc)blockAccess, blockState, blockPos, side), quad, renderEnv);
      case 5:
        return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
      case 7:
        return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
      case 8:
        return getQuads(getConnectedTextureHorizontalVertical(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
      case 9:
        return getQuads(getConnectedTextureVerticalHorizontal(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
      case 11:
        return getConnectedTextureOverlay(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
      case 12:
        return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
      case 13:
        return getConnectedTextureOverlayRandom(cp, (dcc)blockAccess, blockState, blockPos, side, quad, renderEnv);
      case 14:
        return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
      case 15:
        return getConnectedTextureOverlayCtm(cp, (dcc)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
    } 
    return null;
  }
  
  private static int fixSideByAxis(int side, int vertAxis) {
    switch (vertAxis) {
      case 0:
        return side;
      case 1:
        switch (side) {
          case 0:
            return 2;
          case 1:
            return 3;
          case 2:
            return 1;
          case 3:
            return 0;
        } 
        return side;
      case 2:
        switch (side) {
          case 0:
            return 4;
          case 1:
            return 5;
          case 4:
            return 1;
          case 5:
            return 0;
        } 
        return side;
    } 
    return side;
  }
  
  private static int getPillarAxis(dtc blockState) {
    ji.a axis = (ji.a)blockState.c((duf)dml.i);
    switch (null.$SwitchMap$net$minecraft$core$Direction$Axis[axis.ordinal()]) {
      case 1:
        return 2;
      case 2:
        return 1;
    } 
    return 0;
  }
  
  private static gql getConnectedTextureRandom(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int side) {
    if (cp.tileIcons.length == 1)
      return cp.tileIcons[0]; 
    int face = side / cp.symmetry * cp.symmetry;
    if (cp.linked) {
      jd posDown = blockPos.e();
      dtc bsDown = blockAccess.a_(posDown);
      while (bsDown.b() == blockState.b()) {
        blockPos = posDown;
        posDown = blockPos.e();
        if (posDown.v() < 0)
          break; 
        bsDown = blockAccess.a_(posDown);
      } 
    } 
    int rand = Config.getRandom(blockPos, face) & Integer.MAX_VALUE;
    for (int i = 0; i < cp.randomLoops; i++)
      rand = Config.intHash(rand); 
    int index = 0;
    if (cp.weights == null) {
      index = rand % cp.tileIcons.length;
    } else {
      int randWeight = rand % cp.sumAllWeights;
      int[] sumWeights = cp.sumWeights;
      for (int j = 0; j < sumWeights.length; j++) {
        if (randWeight < sumWeights[j]) {
          index = j;
          break;
        } 
      } 
    } 
    return cp.tileIcons[index];
  }
  
  private static gql getConnectedTextureFixed(ConnectedProperties cp) {
    return cp.tileIcons[0];
  }
  
  private static gql getConnectedTextureRepeat(ConnectedProperties cp, jd blockPos, int side) {
    if (cp.tileIcons.length == 1)
      return cp.tileIcons[0]; 
    int x = blockPos.u();
    int y = blockPos.v();
    int z = blockPos.w();
    int nx = 0;
    int ny = 0;
    switch (side) {
      case 0:
        nx = x;
        ny = -z - 1;
        break;
      case 1:
        nx = x;
        ny = z;
        break;
      case 2:
        nx = -x - 1;
        ny = -y;
        break;
      case 3:
        nx = x;
        ny = -y;
        break;
      case 4:
        nx = z;
        ny = -y;
        break;
      case 5:
        nx = -z - 1;
        ny = -y;
        break;
    } 
    nx %= cp.width;
    ny %= cp.height;
    if (nx < 0)
      nx += cp.width; 
    if (ny < 0)
      ny += cp.height; 
    int index = ny * cp.width + nx;
    return cp.tileIcons[index];
  }
  
  private static gql getConnectedTextureCtm(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata, RenderEnv renderEnv) {
    int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
    return cp.tileIcons[index];
  }
  
  private static synchronized gfw[] getConnectedTextureCtmCompact(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gfw quad, int metadata, RenderEnv renderEnv) {
    gql icon = quad.a();
    int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
    return ConnectedTexturesCompact.getConnectedTextureCtmCompact(index, cp, side, quad, renderEnv);
  }
  
  private static gfw[] getConnectedTextureOverlay(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gfw quad, int metadata, RenderEnv renderEnv) {
    if (!quad.isFullQuad())
      return null; 
    gql icon = quad.a();
    BlockDir[] dirSides = getSideDirections(side, vertAxis);
    boolean[] sides = renderEnv.getBorderFlags();
    for (int i = 0; i < 4; i++)
      sides[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata); 
    ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
    try {
      if (sides[0] && sides[1] && sides[2] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
        return null;
      } 
      if (sides[0] && sides[1] && sides[2]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
        return null;
      } 
      if (sides[0] && sides[2] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
        return null;
      } 
      if (sides[1] && sides[2] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
        return null;
      } 
      if (sides[0] && sides[1] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
        return null;
      } 
      BlockDir[] dirEdges = getEdgeDirections(side, vertAxis);
      boolean[] edges = renderEnv.getBorderFlags2();
      for (int j = 0; j < 4; j++)
        edges[j] = isNeighbourOverlay(cp, blockAccess, blockState, dirEdges[j].offset(blockPos), side, icon, metadata); 
      if (sides[1] && sides[2]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
        if (edges[3])
          listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState); 
        return null;
      } 
      if (sides[0] && sides[2]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
        if (edges[2])
          listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState); 
        return null;
      } 
      if (sides[1] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
        if (edges[1])
          listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState); 
        return null;
      } 
      if (sides[0] && sides[3]) {
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
        if (edges[0])
          listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState); 
        return null;
      } 
      boolean[] sidesMatch = renderEnv.getBorderFlags3();
      for (int k = 0; k < 4; k++)
        sidesMatch[k] = isNeighbourMatching(cp, blockAccess, blockState, dirSides[k].offset(blockPos), side, icon, metadata); 
      if (sides[0])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState); 
      if (sides[1])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState); 
      if (sides[2])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState); 
      if (sides[3])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState); 
      if (edges[0] && (sidesMatch[1] || sidesMatch[2]) && !sides[1] && !sides[2])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState); 
      if (edges[1] && (sidesMatch[0] || sidesMatch[2]) && !sides[0] && !sides[2])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState); 
      if (edges[2] && (sidesMatch[1] || sidesMatch[3]) && !sides[1] && !sides[3])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState); 
      if (edges[3] && (sidesMatch[0] || sidesMatch[3]) && !sides[0] && !sides[3])
        listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState); 
      return null;
    } finally {
      if (listQuadsOverlay.size() > 0)
        renderEnv.setOverlaysRendered(true); 
    } 
  }
  
  private static gfw[] getConnectedTextureOverlayFixed(ConnectedProperties cp, gfw quad, RenderEnv renderEnv) {
    if (!quad.isFullQuad())
      return null; 
    ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
    try {
      gql sprite = getConnectedTextureFixed(cp);
      if (sprite != null)
        listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState); 
      return null;
    } finally {
      if (listQuadsOverlay.size() > 0)
        renderEnv.setOverlaysRendered(true); 
    } 
  }
  
  private static gfw[] getConnectedTextureOverlayRandom(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int side, gfw quad, RenderEnv renderEnv) {
    if (!quad.isFullQuad())
      return null; 
    ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
    try {
      gql sprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
      if (sprite != null)
        listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState); 
      return null;
    } finally {
      if (listQuadsOverlay.size() > 0)
        renderEnv.setOverlaysRendered(true); 
    } 
  }
  
  private static gfw[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, jd blockPos, int side, gfw quad, RenderEnv renderEnv) {
    if (!quad.isFullQuad())
      return null; 
    ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
    try {
      gql sprite = getConnectedTextureRepeat(cp, blockPos, side);
      if (sprite != null)
        listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState); 
      return null;
    } finally {
      if (listQuadsOverlay.size() > 0)
        renderEnv.setOverlaysRendered(true); 
    } 
  }
  
  private static gfw[] getConnectedTextureOverlayCtm(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gfw quad, int metadata, RenderEnv renderEnv) {
    if (!quad.isFullQuad())
      return null; 
    ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
    try {
      gql sprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.a(), metadata, renderEnv);
      if (sprite != null)
        listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState); 
      return null;
    } finally {
      if (listQuadsOverlay.size() > 0)
        renderEnv.setOverlaysRendered(true); 
    } 
  }
  
  private static BlockDir[] getSideDirections(int side, int vertAxis) {
    switch (side) {
      case 0:
        return SIDES_Y_NEG_DOWN;
      case 1:
        return SIDES_Y_POS_UP;
      case 2:
        return SIDES_Z_NEG_NORTH;
      case 3:
        return SIDES_Z_POS_SOUTH;
      case 4:
        return SIDES_X_NEG_WEST;
      case 5:
        return SIDES_X_POS_EAST;
    } 
    throw new IllegalArgumentException("Unknown side: " + side);
  }
  
  private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
    switch (side) {
      case 0:
        return EDGES_Y_NEG_DOWN;
      case 1:
        return EDGES_Y_POS_UP;
      case 2:
        return EDGES_Z_NEG_NORTH;
      case 3:
        return EDGES_Z_POS_SOUTH;
      case 4:
        return EDGES_X_NEG_WEST;
      case 5:
        return EDGES_X_POS_EAST;
    } 
    throw new IllegalArgumentException("Unknown side: " + side);
  }
  
  protected static Map[][] getSpriteQuadCompactMaps() {
    return spriteQuadCompactMaps;
  }
  
  private static int getConnectedTextureCtmIndex(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata, RenderEnv renderEnv) {
    boolean[] borders = renderEnv.getBorderFlags();
    switch (side) {
      case 0:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.e();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.i(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
        } 
        break;
      case 1:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.d();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.i(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
        } 
        break;
      case 2:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.f();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.i(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
        } 
        break;
      case 3:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.g();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.i(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
        } 
        break;
      case 4:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.h();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
        } 
        break;
      case 5:
        borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
        borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
        borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.i();
          borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
          borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
          borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
          borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
        } 
        break;
    } 
    int index = 0;
    if ((borders[0] & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
      index = 3;
    } else if (((!borders[0] ? 1 : 0) & borders[1] & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
      index = 1;
    } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
      index = 12;
    } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
      index = 36;
    } else if ((borders[0] & borders[1] & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
      index = 2;
    } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & borders[2] & borders[3]) != 0) {
      index = 24;
    } else if ((borders[0] & (!borders[1] ? 1 : 0) & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
      index = 15;
    } else if ((borders[0] & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
      index = 39;
    } else if (((!borders[0] ? 1 : 0) & borders[1] & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
      index = 13;
    } else if (((!borders[0] ? 1 : 0) & borders[1] & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
      index = 37;
    } else if (((!borders[0] ? 1 : 0) & borders[1] & borders[2] & borders[3]) != 0) {
      index = 25;
    } else if ((borders[0] & (!borders[1] ? 1 : 0) & borders[2] & borders[3]) != 0) {
      index = 27;
    } else if ((borders[0] & borders[1] & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
      index = 38;
    } else if ((borders[0] & borders[1] & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
      index = 14;
    } else if ((borders[0] & borders[1] & borders[2] & borders[3]) != 0) {
      index = 26;
    } 
    if (index == 0)
      return index; 
    if (!Config.isConnectedTexturesFancy())
      return index; 
    boolean[] edges = borders;
    switch (side) {
      case 0:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().f(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().f(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().g(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().g(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.e();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.i().f(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.h().f(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.i().g(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.h().g(), side, icon, metadata));
        } 
        break;
      case 1:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().g(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().g(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().f(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().f(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.d();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.i().g(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.h().g(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.i().f(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.h().f(), side, icon, metadata));
        } 
        break;
      case 2:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().e(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().e(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().d(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.f();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.h().e(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.i().e(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.h().d(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.i().d(), side, icon, metadata));
        } 
        break;
      case 3:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().e(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().e(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.i().d(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().d(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.g();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.i().e(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.h().e(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.i().d(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.h().d(), side, icon, metadata));
        } 
        break;
      case 4:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().g(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().f(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().g(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().f(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.h();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.e().g(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.e().f(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.d().g(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.d().f(), side, icon, metadata));
        } 
        break;
      case 5:
        edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().f(), side, icon, metadata);
        edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().g(), side, icon, metadata);
        edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().f(), side, icon, metadata);
        edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().g(), side, icon, metadata);
        if (cp.innerSeams) {
          jd posFront = blockPos.i();
          edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront.e().f(), side, icon, metadata));
          edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront.e().g(), side, icon, metadata));
          edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront.d().f(), side, icon, metadata));
          edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront.d().g(), side, icon, metadata));
        } 
        break;
    } 
    if (index == 13 && edges[0]) {
      index = 4;
    } else if (index == 15 && edges[1]) {
      index = 5;
    } else if (index == 37 && edges[2]) {
      index = 16;
    } else if (index == 39 && edges[3]) {
      index = 17;
    } else if (index == 14 && edges[0] && edges[1]) {
      index = 7;
    } else if (index == 25 && edges[0] && edges[2]) {
      index = 6;
    } else if (index == 27 && edges[3] && edges[1]) {
      index = 19;
    } else if (index == 38 && edges[3] && edges[2]) {
      index = 18;
    } else if (index == 14 && !edges[0] && edges[1]) {
      index = 31;
    } else if (index == 25 && edges[0] && !edges[2]) {
      index = 30;
    } else if (index == 27 && !edges[3] && edges[1]) {
      index = 41;
    } else if (index == 38 && edges[3] && !edges[2]) {
      index = 40;
    } else if (index == 14 && edges[0] && !edges[1]) {
      index = 29;
    } else if (index == 25 && !edges[0] && edges[2]) {
      index = 28;
    } else if (index == 27 && edges[3] && !edges[1]) {
      index = 43;
    } else if (index == 38 && !edges[3] && edges[2]) {
      index = 42;
    } else if (index == 26 && edges[0] && edges[1] && edges[2] && edges[3]) {
      index = 46;
    } else if (index == 26 && !edges[0] && edges[1] && edges[2] && edges[3]) {
      index = 9;
    } else if (index == 26 && edges[0] && !edges[1] && edges[2] && edges[3]) {
      index = 21;
    } else if (index == 26 && edges[0] && edges[1] && !edges[2] && edges[3]) {
      index = 8;
    } else if (index == 26 && edges[0] && edges[1] && edges[2] && !edges[3]) {
      index = 20;
    } else if (index == 26 && edges[0] && edges[1] && !edges[2] && !edges[3]) {
      index = 11;
    } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && edges[3]) {
      index = 22;
    } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && edges[3]) {
      index = 23;
    } else if (index == 26 && edges[0] && !edges[1] && edges[2] && !edges[3]) {
      index = 10;
    } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && edges[3]) {
      index = 34;
    } else if (index == 26 && !edges[0] && edges[1] && edges[2] && !edges[3]) {
      index = 35;
    } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && !edges[3]) {
      index = 32;
    } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && !edges[3]) {
      index = 33;
    } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && !edges[3]) {
      index = 44;
    } else if (index == 26 && !edges[0] && !edges[1] && !edges[2] && edges[3]) {
      index = 45;
    } 
    return index;
  }
  
  private static void switchValues(int ix1, int ix2, boolean[] arr) {
    boolean prev1 = arr[ix1];
    arr[ix1] = arr[ix2];
    arr[ix2] = prev1;
  }
  
  private static boolean isNeighbourOverlay(ConnectedProperties cp, dcc worldReader, dtc blockState, jd blockPos, int side, gql icon, int metadata) {
    dtc neighbourState = worldReader.a_(blockPos);
    if (!isFullCubeModel(neighbourState, worldReader, blockPos))
      return false; 
    if (cp.connectBlocks != null)
      if (!Matches.block(neighbourState.getBlockId(), neighbourState.getMetadata(), cp.connectBlocks))
        return false;  
    if (cp.connectTileIcons != null) {
      gql neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
      if (!Config.isSameOne(neighbourIcon, (Object[])cp.connectTileIcons))
        return false; 
    } 
    jd posNeighbourStateAbove = blockPos.a(getFacing(side));
    dtc neighbourStateAbove = worldReader.a_(posNeighbourStateAbove);
    if (neighbourStateAbove.i(worldReader, posNeighbourStateAbove))
      return false; 
    if (side == 1 && neighbourStateAbove.b() == dga.dN)
      return false; 
    return !isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
  }
  
  private static boolean isFullCubeModel(dtc state, dcc blockReader, jd pos) {
    if (BlockUtils.isFullCube(state, blockReader, pos))
      return true; 
    dfy block = state.b();
    if (block == dga.aQ)
      return true; 
    if (block instanceof dnr)
      return true; 
    return false;
  }
  
  private static boolean isNeighbourMatching(ConnectedProperties cp, dcc worldReader, dtc blockState, jd blockPos, int side, gql icon, int metadata) {
    dtc neighbourState = worldReader.a_(blockPos);
    if (neighbourState == AIR_DEFAULT_STATE)
      return false; 
    if (cp.matchBlocks != null)
      if (!cp.matchesBlock(neighbourState.getBlockId(), neighbourState.getMetadata()))
        return false;  
    if (cp.matchTileIcons != null) {
      gql neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
      if (neighbourIcon != icon)
        return false; 
    } 
    jd posNeighbourAbove = blockPos.a(getFacing(side));
    dtc neighbourStateAbove = worldReader.a_(posNeighbourAbove);
    if (neighbourStateAbove.i(worldReader, posNeighbourAbove))
      return false; 
    if (side == 1 && neighbourStateAbove.b() == dga.dN)
      return false; 
    return true;
  }
  
  private static boolean isNeighbour(ConnectedProperties cp, dcc worldReader, dtc blockState, jd blockPos, int side, gql icon, int metadata) {
    dtc neighbourState = worldReader.a_(blockPos);
    return isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
  }
  
  private static boolean isNeighbour(ConnectedProperties cp, dcc worldReader, dtc blockState, jd blockPos, dtc neighbourState, int side, gql icon, int metadata) {
    if (blockState == neighbourState)
      return true; 
    if (cp.connect == 2) {
      if (neighbourState == null)
        return false; 
      if (neighbourState == AIR_DEFAULT_STATE)
        return false; 
      gql neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
      return (neighbourIcon == icon);
    } 
    if (cp.connect == 1) {
      dfy block = blockState.b();
      dfy neighbourBlock = neighbourState.b();
      return (neighbourBlock == block);
    } 
    return false;
  }
  
  private static gql getNeighbourIcon(dcc worldReader, dtc blockState, jd blockPos, dtc neighbourState, int side) {
    gsm model = fgo.Q().ao().a().b(neighbourState);
    if (model == null)
      return null; 
    ji facing = getFacing(side);
    List<gfw> quads = model.a(neighbourState, facing, RANDOM);
    if (quads == null)
      return null; 
    if (Config.isBetterGrass())
      quads = BetterGrass.getFaceQuads(worldReader, neighbourState, blockPos, facing, quads); 
    if (quads.size() > 0) {
      gfw quad = quads.get(0);
      return quad.a();
    } 
    List<gfw> quadsGeneral = model.a(neighbourState, null, RANDOM);
    if (quadsGeneral == null)
      return null; 
    for (int i = 0; i < quadsGeneral.size(); i++) {
      gfw quad = quadsGeneral.get(i);
      if (quad.e() == facing)
        return quad.a(); 
    } 
    return null;
  }
  
  private static gql getConnectedTextureHorizontal(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata) {
    boolean left = false;
    boolean right = false;
    switch (vertAxis) {
      case 0:
        switch (side) {
          case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            break;
          case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            break;
          case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break;
        } 
        break;
      case 1:
        switch (side) {
          case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            break;
          case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
            break;
          case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break;
          case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            break;
        } 
        break;
      case 2:
        switch (side) {
          case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            break;
          case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            break;
          case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break;
          case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            break;
          case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break;
          case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
            break;
        } 
        break;
    } 
    int index = 3;
    if (left) {
      if (right) {
        index = 1;
      } else {
        index = 2;
      } 
    } else if (right) {
      index = 0;
    } else {
      index = 3;
    } 
    return cp.tileIcons[index];
  }
  
  private static gql getConnectedTextureVertical(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata) {
    boolean bottom = false;
    boolean top = false;
    switch (vertAxis) {
      case 0:
        if (side == 1) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
          break;
        } 
        if (side == 0) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
          break;
        } 
        bottom = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
        top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        break;
      case 1:
        if (side == 3) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
          break;
        } 
        if (side == 2) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
          break;
        } 
        bottom = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
        break;
      case 2:
        if (side == 5) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
          break;
        } 
        if (side == 4) {
          bottom = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
          top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
          break;
        } 
        bottom = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
        top = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        break;
    } 
    int index = 3;
    if (bottom) {
      if (top) {
        index = 1;
      } else {
        index = 2;
      } 
    } else if (top) {
      index = 0;
    } else {
      index = 3;
    } 
    return cp.tileIcons[index];
  }
  
  private static gql getConnectedTextureHorizontalVertical(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata) {
    gql[] tileIcons = cp.tileIcons;
    gql iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
    if (iconH != null && iconH != icon && iconH != tileIcons[3])
      return iconH; 
    gql iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
    if (iconV == tileIcons[0])
      return tileIcons[4]; 
    if (iconV == tileIcons[1])
      return tileIcons[5]; 
    if (iconV == tileIcons[2])
      return tileIcons[6]; 
    return iconV;
  }
  
  private static gql getConnectedTextureVerticalHorizontal(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata) {
    gql[] tileIcons = cp.tileIcons;
    gql iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
    if (iconV != null && iconV != icon && iconV != tileIcons[3])
      return iconV; 
    gql iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
    if (iconH == tileIcons[0])
      return tileIcons[4]; 
    if (iconH == tileIcons[1])
      return tileIcons[5]; 
    if (iconH == tileIcons[2])
      return tileIcons[6]; 
    return iconH;
  }
  
  private static gql getConnectedTextureTop(ConnectedProperties cp, dcc blockAccess, dtc blockState, jd blockPos, int vertAxis, int side, gql icon, int metadata) {
    boolean top = false;
    switch (vertAxis) {
      case 0:
        if (side == 1 || side == 0)
          return null; 
        top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
        break;
      case 1:
        if (side == 3 || side == 2)
          return null; 
        top = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
        break;
      case 2:
        if (side == 5 || side == 4)
          return null; 
        top = isNeighbour(cp, blockAccess, blockState, blockPos.i(), side, icon, metadata);
        break;
    } 
    if (top)
      return cp.tileIcons[0]; 
    return null;
  }
  
  public static void updateIcons(gqk textureMap) {
    blockProperties = null;
    tileProperties = null;
    spriteQuadMaps = null;
    spriteQuadCompactMaps = null;
    if (!Config.isConnectedTextures())
      return; 
    asq[] rps = Config.getResourcePacks();
    for (int i = rps.length - 1; i >= 0; i--) {
      asq rp = rps[i];
      updateIcons(textureMap, rp);
    } 
    updateIcons(textureMap, (asq)Config.getDefaultResourcePack());
    emptySprite = textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
    spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
    spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
    spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
    if (blockProperties.length <= 0)
      blockProperties = null; 
    if (tileProperties.length <= 0)
      tileProperties = null; 
  }
  
  public static void updateIcons(gqk textureMap, asq rp) {
    String[] names = ResUtils.collectFiles(rp, "optifine/ctm/", ".properties", getDefaultCtmPaths());
    Arrays.sort((Object[])names);
    List tileList = makePropertyList(tileProperties);
    List blockList = makePropertyList(blockProperties);
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      Config.dbg("ConnectedTextures: " + name);
      try {
        akr locFile = new akr(name);
        InputStream in = Config.getResourceStream(rp, ass.a, locFile);
        if (in == null) {
          Config.warn("ConnectedTextures file not found: " + name);
        } else {
          PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
          propertiesOrdered.load(in);
          in.close();
          ConnectedProperties cp = new ConnectedProperties((Properties)propertiesOrdered, name);
          if (cp.isValid(name)) {
            cp.updateIcons(textureMap);
            addToTileList(cp, tileList);
            addToBlockList(cp, blockList);
          } 
        } 
      } catch (FileNotFoundException e) {
        Config.warn("ConnectedTextures file not found: " + name);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    blockProperties = propertyListToArray(blockList);
    tileProperties = propertyListToArray(tileList);
    multipass = detectMultipass();
    Config.dbg("Multipass connected textures: " + multipass);
  }
  
  public static void refreshIcons(gqk textureMap) {
    refreshIcons(blockProperties, textureMap);
    refreshIcons(tileProperties, textureMap);
    emptySprite = getSprite(textureMap, LOCATION_SPRITE_EMPTY);
  }
  
  private static gql getSprite(gqk textureMap, akr loc) {
    gql sprite = textureMap.a(loc);
    if (sprite == null || gqb.isMisingSprite(sprite))
      Config.warn("Missing CTM sprite: " + String.valueOf(loc)); 
    return sprite;
  }
  
  private static void refreshIcons(ConnectedProperties[][] propertiesArray, gqk textureMap) {
    if (propertiesArray == null)
      return; 
    for (int i = 0; i < propertiesArray.length; i++) {
      ConnectedProperties[] properties = propertiesArray[i];
      if (properties != null)
        for (int c = 0; c < properties.length; c++) {
          ConnectedProperties cp = properties[c];
          if (cp != null)
            cp.refreshIcons(textureMap); 
        }  
    } 
  }
  
  private static List makePropertyList(ConnectedProperties[][] propsArr) {
    List<List> list = new ArrayList();
    if (propsArr != null)
      for (int i = 0; i < propsArr.length; i++) {
        ConnectedProperties[] props = propsArr[i];
        List propList = null;
        if (props != null)
          propList = new ArrayList(Arrays.asList((Object[])props)); 
        list.add(propList);
      }  
    return list;
  }
  
  private static boolean detectMultipass() {
    List propList = new ArrayList();
    int i;
    for (i = 0; i < tileProperties.length; i++) {
      ConnectedProperties[] cps = tileProperties[i];
      if (cps != null)
        propList.addAll(Arrays.asList(cps)); 
    } 
    for (i = 0; i < blockProperties.length; i++) {
      ConnectedProperties[] cps = blockProperties[i];
      if (cps != null)
        propList.addAll(Arrays.asList(cps)); 
    } 
    ConnectedProperties[] props = (ConnectedProperties[])propList.toArray((Object[])new ConnectedProperties[propList.size()]);
    Set matchIconSet = new HashSet();
    Set<?> tileIconSet = new HashSet();
    for (int j = 0; j < props.length; j++) {
      ConnectedProperties cp = props[j];
      if (cp.matchTileIcons != null)
        matchIconSet.addAll(Arrays.asList(cp.matchTileIcons)); 
      if (cp.tileIcons != null)
        tileIconSet.addAll(Arrays.asList(cp.tileIcons)); 
    } 
    matchIconSet.retainAll(tileIconSet);
    return !matchIconSet.isEmpty();
  }
  
  private static ConnectedProperties[][] propertyListToArray(List<List> list) {
    ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
    for (int i = 0; i < list.size(); i++) {
      List subList = list.get(i);
      if (subList != null) {
        ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray((Object[])new ConnectedProperties[subList.size()]);
        propArr[i] = subArr;
      } 
    } 
    return propArr;
  }
  
  private static void addToTileList(ConnectedProperties cp, List tileList) {
    if (cp.matchTileIcons == null)
      return; 
    for (int i = 0; i < cp.matchTileIcons.length; i++) {
      gql icon = cp.matchTileIcons[i];
      if (!(icon instanceof gql)) {
        Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + String.valueOf(icon) + ", name: " + String.valueOf(icon.getName()));
      } else {
        gql ts = icon;
        int tileId = ts.getIndexInMap();
        if (tileId < 0) {
          Config.warn("Invalid tile ID: " + tileId + ", icon: " + String.valueOf(ts.getName()));
        } else {
          addToList(cp, tileList, tileId);
        } 
      } 
    } 
  }
  
  private static void addToBlockList(ConnectedProperties cp, List blockList) {
    if (cp.matchBlocks == null)
      return; 
    for (int i = 0; i < cp.matchBlocks.length; i++) {
      int blockId = cp.matchBlocks[i].getBlockId();
      if (blockId < 0) {
        Config.warn("Invalid block ID: " + blockId);
      } else {
        addToList(cp, blockList, blockId);
      } 
    } 
  }
  
  private static void addToList(ConnectedProperties cp, List<List> list, int id) {
    while (id >= list.size())
      list.add(null); 
    List<ConnectedProperties> subList = list.get(id);
    if (subList == null) {
      subList = new ArrayList();
      list.set(id, subList);
    } 
    subList.add(cp);
  }
  
  private static String[] getDefaultCtmPaths() {
    List list = new ArrayList();
    addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass.properties");
    addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass_pane.properties");
    addDefaultLocation(list, "textures/block/tinted_glass.png", "21_tinted_glass/tinted_glass.properties");
    addDefaultLocation(list, "textures/block/bookshelf.png", "30_bookshelf/bookshelf.properties");
    addDefaultLocation(list, "textures/block/sandstone.png", "40_sandstone/sandstone.properties");
    addDefaultLocation(list, "textures/block/red_sandstone.png", "41_red_sandstone/red_sandstone.properties");
    String[] colors = { 
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", 
        "purple", "blue", "brown", "green", "red", "black" };
    for (int i = 0; i < colors.length; i++) {
      String color = colors[i];
      String prefix = StrUtils.fillLeft("" + i, 2, '0');
      addDefaultLocation(list, "textures/block/" + color + "_stained_glass.png", prefix + "_glass_" + prefix + "/glass_" + color + ".properties");
      addDefaultLocation(list, "textures/block/" + color + "_stained_glass.png", prefix + "_glass_" + prefix + "/glass_pane_" + color + ".properties");
    } 
    String[] paths = (String[])list.toArray((Object[])new String[list.size()]);
    return paths;
  }
  
  private static void addDefaultLocation(List<String> list, String locBase, String pathSuffix) {
    String defPath = "optifine/ctm/default/";
    akr loc = new akr(locBase);
    asq rp = Config.getDefiningResourcePack(loc);
    if (rp == null)
      return; 
    if (rp.b().equals("programmer_art")) {
      String defPathPa = defPath + "programmer_art/";
      list.add(defPathPa + defPathPa);
      return;
    } 
    if (rp == Config.getDefaultResourcePack())
      list.add(defPath + defPath); 
  }
}
