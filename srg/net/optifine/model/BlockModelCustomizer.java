package srg.net.optifine.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.BetterGrass;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;

public class BlockModelCustomizer {
  private static final List<BakedQuad> NO_QUADS = (List<BakedQuad>)ImmutableList.of();
  
  public static BakedModel getRenderModel(BakedModel modelIn, BlockState stateIn, RenderEnv renderEnv) {
    if (renderEnv.isSmartLeaves())
      modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn); 
    return modelIn;
  }
  
  public static List<BakedQuad> getRenderQuads(List<BakedQuad> quads, BlockAndTintGetter worldIn, BlockState stateIn, BlockPos posIn, Direction enumfacing, RenderType layer, long rand, RenderEnv renderEnv) {
    if (enumfacing != null) {
      if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.getBlockState(posIn.relative(enumfacing)), stateIn))
        return NO_QUADS; 
      if (!renderEnv.isBreakingAnimation(quads))
        if (Config.isBetterGrass())
          quads = BetterGrass.getFaceQuads((BlockGetter)worldIn, stateIn, posIn, enumfacing, quads);  
    } 
    List<BakedQuad> quadsNew = renderEnv.getListQuadsCustomizer();
    quadsNew.clear();
    for (int i = 0; i < quads.size(); i++) {
      BakedQuad quad = quads.get(i);
      BakedQuad[] quadArr = getRenderQuads(quad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
      if (i == 0 && quads.size() == 1 && quadArr.length == 1 && quadArr[0] == quad && quad.getQuadEmissive() == null)
        return quads; 
      for (int q = 0; q < quadArr.length; q++) {
        BakedQuad quadSingle = quadArr[q];
        quadsNew.add(quadSingle);
        if (quadSingle.getQuadEmissive() != null) {
          renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(quadSingle.getQuadEmissive(), stateIn);
          renderEnv.setOverlaysRendered(true);
        } 
      } 
    } 
    return quadsNew;
  }
  
  private static RenderType getEmissiveLayer(RenderType layer) {
    if (layer == null || layer == RenderTypes.SOLID)
      return RenderTypes.CUTOUT_MIPPED; 
    return layer;
  }
  
  private static BakedQuad[] getRenderQuads(BakedQuad quad, BlockAndTintGetter worldIn, BlockState stateIn, BlockPos posIn, Direction enumfacing, long rand, RenderEnv renderEnv) {
    if (renderEnv.isBreakingAnimation(quad))
      return renderEnv.getArrayQuadsCtm(quad); 
    BakedQuad quadOriginal = quad;
    if (Config.isConnectedTextures()) {
      BakedQuad[] quads = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
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
