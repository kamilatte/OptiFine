package srg.net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.RandomUtils;

public class ModelUtils {
  private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static void dbgModel(BakedModel model) {
    if (model == null)
      return; 
    Config.dbg("Model: " + String.valueOf(model) + ", ao: " + model.useAmbientOcclusion() + ", gui3d: " + model.isGui3d() + ", builtIn: " + model.isCustomRenderer() + ", particle: " + String.valueOf(model.getParticleIcon()));
    Direction[] faces = Direction.VALUES;
    for (int i = 0; i < faces.length; i++) {
      Direction face = faces[i];
      List faceQuads = model.getQuads(null, face, RANDOM);
      dbgQuads(face.getSerializedName(), faceQuads, "  ");
    } 
    List generalQuads = model.getQuads(null, null, RANDOM);
    dbgQuads("General", generalQuads, "  ");
  }
  
  private static void dbgQuads(String name, List quads, String prefix) {
    for (Iterator<BakedQuad> it = quads.iterator(); it.hasNext(); ) {
      BakedQuad quad = it.next();
      dbgQuad(name, quad, prefix);
    } 
  }
  
  public static void dbgQuad(String name, BakedQuad quad, String prefix) {
    Config.dbg(prefix + "Quad: " + prefix + ", type: " + quad.getClass().getName() + ", face: " + name + ", tint: " + String.valueOf(quad.getDirection()) + ", sprite: " + quad.getTintIndex());
    dbgVertexData(quad.getVertices(), "  " + prefix);
  }
  
  public static void dbgVertexData(int[] vd, String prefix) {
    int step = vd.length / 4;
    Config.dbg(prefix + "Length: " + prefix + ", step: " + vd.length);
    for (int i = 0; i < 4; i++) {
      int pos = i * step;
      float x = Float.intBitsToFloat(vd[pos + 0]);
      float y = Float.intBitsToFloat(vd[pos + 1]);
      float z = Float.intBitsToFloat(vd[pos + 2]);
      int col = vd[pos + 3];
      float u = Float.intBitsToFloat(vd[pos + 4]);
      float v = Float.intBitsToFloat(vd[pos + 5]);
      Config.dbg(prefix + prefix + " xyz: " + i + "," + x + "," + y + " col: " + z + " u,v: " + col + "," + u);
    } 
  }
  
  public static BakedModel duplicateModel(BakedModel model) {
    List<BakedQuad> generalQuads2 = duplicateQuadList(model.getQuads(null, null, RANDOM));
    Direction[] faces = Direction.VALUES;
    Map<Direction, List<BakedQuad>> faceQuads2 = new HashMap<>();
    for (int i = 0; i < faces.length; i++) {
      Direction face = faces[i];
      List<BakedQuad> quads = model.getQuads(null, face, RANDOM);
      List<BakedQuad> quads2 = duplicateQuadList(quads);
      faceQuads2.put(face, quads2);
    } 
    List<BakedQuad> generalQuads2Copy = new ArrayList<>(generalQuads2);
    Map<Direction, List<BakedQuad>> faceQuads2Copy = new HashMap<>(faceQuads2);
    SimpleBakedModel model2 = new SimpleBakedModel(generalQuads2Copy, faceQuads2Copy, model.useAmbientOcclusion(), model.isGui3d(), true, model.getParticleIcon(), model.getTransforms(), model.getOverrides());
    Reflector.SimpleBakedModel_generalQuads.setValue(model2, generalQuads2);
    Reflector.SimpleBakedModel_faceQuads.setValue(model2, faceQuads2);
    return (BakedModel)model2;
  }
  
  public static List duplicateQuadList(List<BakedQuad> list) {
    List<BakedQuad> list2 = new ArrayList<>();
    for (Iterator<BakedQuad> it = list.iterator(); it.hasNext(); ) {
      BakedQuad quad = it.next();
      BakedQuad quad2 = duplicateQuad(quad);
      list2.add(quad2);
    } 
    return list2;
  }
  
  public static BakedQuad duplicateQuad(BakedQuad quad) {
    BakedQuad quad2 = new BakedQuad((int[])quad.getVertices().clone(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    return quad2;
  }
}
