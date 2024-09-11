package notch.net.optifine.model;

import com.google.common.collect.ImmutableList;
import dbz;
import dcc;
import dtc;
import gfh;
import gfw;
import gsm;
import java.util.List;
import jd;
import ji;
import net.optifine.BetterGrass;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;

public class BlockModelCustomizer {
  private static final List<gfw> NO_QUADS = (List<gfw>)ImmutableList.of();
  
  public static gsm getRenderModel(gsm modelIn, dtc stateIn, RenderEnv renderEnv) {
    if (renderEnv.isSmartLeaves())
      modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn); 
    return modelIn;
  }
  
  public static List<gfw> getRenderQuads(List<gfw> quads, dbz worldIn, dtc stateIn, jd posIn, ji enumfacing, gfh layer, long rand, RenderEnv renderEnv) {
    if (enumfacing != null) {
      if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.a_(posIn.a(enumfacing)), stateIn))
        return NO_QUADS; 
      if (!renderEnv.isBreakingAnimation(quads))
        if (Config.isBetterGrass())
          quads = BetterGrass.getFaceQuads((dcc)worldIn, stateIn, posIn, enumfacing, quads);  
    } 
    List<gfw> quadsNew = renderEnv.getListQuadsCustomizer();
    quadsNew.clear();
    for (int i = 0; i < quads.size(); i++) {
      gfw quad = quads.get(i);
      gfw[] quadArr = getRenderQuads(quad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
      if (i == 0 && quads.size() == 1 && quadArr.length == 1 && quadArr[0] == quad && quad.getQuadEmissive() == null)
        return quads; 
      for (int q = 0; q < quadArr.length; q++) {
        gfw quadSingle = quadArr[q];
        quadsNew.add(quadSingle);
        if (quadSingle.getQuadEmissive() != null) {
          renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(quadSingle.getQuadEmissive(), stateIn);
          renderEnv.setOverlaysRendered(true);
        } 
      } 
    } 
    return quadsNew;
  }
  
  private static gfh getEmissiveLayer(gfh layer) {
    if (layer == null || layer == RenderTypes.SOLID)
      return RenderTypes.CUTOUT_MIPPED; 
    return layer;
  }
  
  private static gfw[] getRenderQuads(gfw quad, dbz worldIn, dtc stateIn, jd posIn, ji enumfacing, long rand, RenderEnv renderEnv) {
    if (renderEnv.isBreakingAnimation(quad))
      return renderEnv.getArrayQuadsCtm(quad); 
    gfw quadOriginal = quad;
    if (Config.isConnectedTextures()) {
      gfw[] quads = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
      if (quads.length != 1 || quads[0] != quad)
        return quads; 
    } 
    if (Config.isNaturalTextures()) {
      quad = NaturalTextures.getNaturalTexture(stateIn, posIn, quad);
      if (quad != quadOriginal)
        return renderEnv.getArrayQuadsCtm(quad); 
    } 
    return renderEnv.getArrayQuadsCtm(quad);
  }
}
