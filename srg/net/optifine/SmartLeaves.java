package srg.net.optifine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.model.ModelUtils;
import net.optifine.util.RandomUtils;

public class SmartLeaves {
  private static BakedModel modelLeavesCullAcacia = null;
  
  private static BakedModel modelLeavesCullBirch = null;
  
  private static BakedModel modelLeavesCullDarkOak = null;
  
  private static BakedModel modelLeavesCullJungle = null;
  
  private static BakedModel modelLeavesCullOak = null;
  
  private static BakedModel modelLeavesCullSpruce = null;
  
  private static List generalQuadsCullAcacia = null;
  
  private static List generalQuadsCullBirch = null;
  
  private static List generalQuadsCullDarkOak = null;
  
  private static List generalQuadsCullJungle = null;
  
  private static List generalQuadsCullOak = null;
  
  private static List generalQuadsCullSpruce = null;
  
  private static BakedModel modelLeavesDoubleAcacia = null;
  
  private static BakedModel modelLeavesDoubleBirch = null;
  
  private static BakedModel modelLeavesDoubleDarkOak = null;
  
  private static BakedModel modelLeavesDoubleJungle = null;
  
  private static BakedModel modelLeavesDoubleOak = null;
  
  private static BakedModel modelLeavesDoubleSpruce = null;
  
  private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static BakedModel getLeavesModel(BakedModel model, BlockState stateIn) {
    if (!Config.isTreesSmart())
      return model; 
    List generalQuads = model.getQuads(stateIn, null, RANDOM);
    if (generalQuads == generalQuadsCullAcacia)
      return modelLeavesDoubleAcacia; 
    if (generalQuads == generalQuadsCullBirch)
      return modelLeavesDoubleBirch; 
    if (generalQuads == generalQuadsCullDarkOak)
      return modelLeavesDoubleDarkOak; 
    if (generalQuads == generalQuadsCullJungle)
      return modelLeavesDoubleJungle; 
    if (generalQuads == generalQuadsCullOak)
      return modelLeavesDoubleOak; 
    if (generalQuads == generalQuadsCullSpruce)
      return modelLeavesDoubleSpruce; 
    return model;
  }
  
  public static boolean isSameLeaves(BlockState state1, BlockState state2) {
    if (state1 == state2)
      return true; 
    Block block1 = state1.getBlock();
    Block block2 = state2.getBlock();
    return (block1 == block2);
  }
  
  public static void updateLeavesModels() {
    List updatedTypes = new ArrayList();
    modelLeavesCullAcacia = getModelCull("acacia", updatedTypes);
    modelLeavesCullBirch = getModelCull("birch", updatedTypes);
    modelLeavesCullDarkOak = getModelCull("dark_oak", updatedTypes);
    modelLeavesCullJungle = getModelCull("jungle", updatedTypes);
    modelLeavesCullOak = getModelCull("oak", updatedTypes);
    modelLeavesCullSpruce = getModelCull("spruce", updatedTypes);
    generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
    generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
    generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
    generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
    generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
    generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
    modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
    modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
    modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
    modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
    modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
    modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
    if (updatedTypes.size() > 0)
      Config.dbg("Enable face culling: " + Config.arrayToString(updatedTypes.toArray())); 
  }
  
  private static List getGeneralQuadsSafe(BakedModel model) {
    if (model == null)
      return null; 
    return model.getQuads(null, null, RANDOM);
  }
  
  static BakedModel getModelCull(String type, List<String> updatedTypes) {
    ModelManager modelManager = Config.getModelManager();
    if (modelManager == null)
      return null; 
    ResourceLocation locState = new ResourceLocation("blockstates/" + type + "_leaves.json");
    if (!Config.isFromDefaultResourcePack(locState))
      return null; 
    ResourceLocation locModel = new ResourceLocation("models/block/" + type + "_leaves.json");
    if (!Config.isFromDefaultResourcePack(locModel))
      return null; 
    ModelResourceLocation mrl = ModelResourceLocation.vanilla(type + "_leaves", "normal");
    BakedModel model = modelManager.getModel(mrl);
    if (model == null || model == modelManager.getMissingModel())
      return null; 
    List listGeneral = model.getQuads(null, null, RANDOM);
    if (listGeneral.size() == 0)
      return model; 
    if (listGeneral.size() != 6)
      return null; 
    for (Iterator<BakedQuad> it = listGeneral.iterator(); it.hasNext(); ) {
      BakedQuad quad = it.next();
      List<BakedQuad> listFace = model.getQuads(null, quad.getDirection(), RANDOM);
      if (listFace.size() > 0)
        return null; 
      listFace.add(quad);
    } 
    listGeneral.clear();
    updatedTypes.add(type + "_leaves");
    return model;
  }
  
  private static BakedModel getModelDoubleFace(BakedModel model) {
    if (model == null)
      return null; 
    if (model.getQuads(null, null, RANDOM).size() > 0) {
      Config.warn("SmartLeaves: Model is not cube, general quads: " + model.getQuads(null, null, RANDOM).size() + ", model: " + String.valueOf(model));
      return model;
    } 
    Direction[] faces = Direction.VALUES;
    for (int i = 0; i < faces.length; i++) {
      Direction face = faces[i];
      List<BakedQuad> quads = model.getQuads(null, face, RANDOM);
      if (quads.size() != 1) {
        Config.warn("SmartLeaves: Model is not cube, side: " + String.valueOf(face) + ", quads: " + quads.size() + ", model: " + String.valueOf(model));
        return model;
      } 
    } 
    BakedModel model2 = ModelUtils.duplicateModel(model);
    List[] faceQuads = new List[faces.length];
    for (int j = 0; j < faces.length; j++) {
      Direction face = faces[j];
      List<BakedQuad> quads = model2.getQuads(null, face, RANDOM);
      BakedQuad quad = quads.get(0);
      BakedQuad quad2 = new BakedQuad((int[])quad.getVertices().clone(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
      int[] vd = quad2.getVertices();
      int[] vd2 = (int[])vd.clone();
      int step = vd.length / 4;
      System.arraycopy(vd, 0 * step, vd2, 3 * step, step);
      System.arraycopy(vd, 1 * step, vd2, 2 * step, step);
      System.arraycopy(vd, 2 * step, vd2, 1 * step, step);
      System.arraycopy(vd, 3 * step, vd2, 0 * step, step);
      System.arraycopy(vd2, 0, vd, 0, vd2.length);
      quads.add(quad2);
    } 
    return model2;
  }
}
