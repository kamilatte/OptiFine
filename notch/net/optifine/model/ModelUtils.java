package notch.net.optifine.model;

import ayw;
import gfw;
import gsm;
import gsx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ji;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.RandomUtils;

public class ModelUtils {
  private static final ayw RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static void dbgModel(gsm model) {
    if (model == null)
      return; 
    Config.dbg("Model: " + String.valueOf(model) + ", ao: " + model.a() + ", gui3d: " + model.b() + ", builtIn: " + model.d() + ", particle: " + String.valueOf(model.e()));
    ji[] faces = ji.r;
    for (int i = 0; i < faces.length; i++) {
      ji face = faces[i];
      List faceQuads = model.a(null, face, RANDOM);
      dbgQuads(face.c(), faceQuads, "  ");
    } 
    List generalQuads = model.a(null, null, RANDOM);
    dbgQuads("General", generalQuads, "  ");
  }
  
  private static void dbgQuads(String name, List quads, String prefix) {
    for (Iterator<gfw> it = quads.iterator(); it.hasNext(); ) {
      gfw quad = it.next();
      dbgQuad(name, quad, prefix);
    } 
  }
  
  public static void dbgQuad(String name, gfw quad, String prefix) {
    Config.dbg(prefix + "Quad: " + prefix + ", type: " + quad.getClass().getName() + ", face: " + name + ", tint: " + String.valueOf(quad.e()) + ", sprite: " + quad.d());
    dbgVertexData(quad.b(), "  " + prefix);
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
  
  public static gsm duplicateModel(gsm model) {
    List<gfw> generalQuads2 = duplicateQuadList(model.a(null, null, RANDOM));
    ji[] faces = ji.r;
    Map<ji, List<gfw>> faceQuads2 = new HashMap<>();
    for (int i = 0; i < faces.length; i++) {
      ji face = faces[i];
      List<gfw> quads = model.a(null, face, RANDOM);
      List<gfw> quads2 = duplicateQuadList(quads);
      faceQuads2.put(face, quads2);
    } 
    List<gfw> generalQuads2Copy = new ArrayList<>(generalQuads2);
    Map<ji, List<gfw>> faceQuads2Copy = new HashMap<>(faceQuads2);
    gsx model2 = new gsx(generalQuads2Copy, faceQuads2Copy, model.a(), model.b(), true, model.e(), model.f(), model.g());
    Reflector.SimpleBakedModel_generalQuads.setValue(model2, generalQuads2);
    Reflector.SimpleBakedModel_faceQuads.setValue(model2, faceQuads2);
    return (gsm)model2;
  }
  
  public static List duplicateQuadList(List<gfw> list) {
    List<gfw> list2 = new ArrayList<>();
    for (Iterator<gfw> it = list.iterator(); it.hasNext(); ) {
      gfw quad = it.next();
      gfw quad2 = duplicateQuad(quad);
      list2.add(quad2);
    } 
    return list2;
  }
  
  public static gfw duplicateQuad(gfw quad) {
    gfw quad2 = new gfw((int[])quad.b().clone(), quad.d(), quad.e(), quad.a(), quad.f());
    return quad2;
  }
}
