package notch.net.optifine;

import akr;
import ayw;
import dfy;
import dtc;
import gfw;
import gsm;
import gst;
import gsu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ji;
import net.optifine.Config;
import net.optifine.model.ModelUtils;
import net.optifine.util.RandomUtils;

public class SmartLeaves {
  private static gsm modelLeavesCullAcacia = null;
  
  private static gsm modelLeavesCullBirch = null;
  
  private static gsm modelLeavesCullDarkOak = null;
  
  private static gsm modelLeavesCullJungle = null;
  
  private static gsm modelLeavesCullOak = null;
  
  private static gsm modelLeavesCullSpruce = null;
  
  private static List generalQuadsCullAcacia = null;
  
  private static List generalQuadsCullBirch = null;
  
  private static List generalQuadsCullDarkOak = null;
  
  private static List generalQuadsCullJungle = null;
  
  private static List generalQuadsCullOak = null;
  
  private static List generalQuadsCullSpruce = null;
  
  private static gsm modelLeavesDoubleAcacia = null;
  
  private static gsm modelLeavesDoubleBirch = null;
  
  private static gsm modelLeavesDoubleDarkOak = null;
  
  private static gsm modelLeavesDoubleJungle = null;
  
  private static gsm modelLeavesDoubleOak = null;
  
  private static gsm modelLeavesDoubleSpruce = null;
  
  private static final ayw RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static gsm getLeavesModel(gsm model, dtc stateIn) {
    if (!Config.isTreesSmart())
      return model; 
    List generalQuads = model.a(stateIn, null, RANDOM);
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
  
  public static boolean isSameLeaves(dtc state1, dtc state2) {
    if (state1 == state2)
      return true; 
    dfy block1 = state1.b();
    dfy block2 = state2.b();
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
  
  private static List getGeneralQuadsSafe(gsm model) {
    if (model == null)
      return null; 
    return model.a(null, null, RANDOM);
  }
  
  static gsm getModelCull(String type, List<String> updatedTypes) {
    gst modelManager = Config.getModelManager();
    if (modelManager == null)
      return null; 
    akr locState = new akr("blockstates/" + type + "_leaves.json");
    if (!Config.isFromDefaultResourcePack(locState))
      return null; 
    akr locModel = new akr("models/block/" + type + "_leaves.json");
    if (!Config.isFromDefaultResourcePack(locModel))
      return null; 
    gsu mrl = gsu.a(type + "_leaves", "normal");
    gsm model = modelManager.a(mrl);
    if (model == null || model == modelManager.a())
      return null; 
    List listGeneral = model.a(null, null, RANDOM);
    if (listGeneral.size() == 0)
      return model; 
    if (listGeneral.size() != 6)
      return null; 
    for (Iterator<gfw> it = listGeneral.iterator(); it.hasNext(); ) {
      gfw quad = it.next();
      List<gfw> listFace = model.a(null, quad.e(), RANDOM);
      if (listFace.size() > 0)
        return null; 
      listFace.add(quad);
    } 
    listGeneral.clear();
    updatedTypes.add(type + "_leaves");
    return model;
  }
  
  private static gsm getModelDoubleFace(gsm model) {
    if (model == null)
      return null; 
    if (model.a(null, null, RANDOM).size() > 0) {
      Config.warn("SmartLeaves: Model is not cube, general quads: " + model.a(null, null, RANDOM).size() + ", model: " + String.valueOf(model));
      return model;
    } 
    ji[] faces = ji.r;
    for (int i = 0; i < faces.length; i++) {
      ji face = faces[i];
      List<gfw> quads = model.a(null, face, RANDOM);
      if (quads.size() != 1) {
        Config.warn("SmartLeaves: Model is not cube, side: " + String.valueOf(face) + ", quads: " + quads.size() + ", model: " + String.valueOf(model));
        return model;
      } 
    } 
    gsm model2 = ModelUtils.duplicateModel(model);
    List[] faceQuads = new List[faces.length];
    for (int j = 0; j < faces.length; j++) {
      ji face = faces[j];
      List<gfw> quads = model2.a(null, face, RANDOM);
      gfw quad = quads.get(0);
      gfw quad2 = new gfw((int[])quad.b().clone(), quad.d(), quad.e(), quad.a(), quad.f());
      int[] vd = quad2.b();
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
