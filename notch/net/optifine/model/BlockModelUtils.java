package notch.net.optifine.model;

import akr;
import ayw;
import dtb;
import ewx;
import gfw;
import gfy;
import gfz;
import gga;
import ggd;
import ggg;
import ggi;
import gqk;
import gql;
import gsm;
import gsn;
import gst;
import gsu;
import gsv;
import gsx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jd;
import ji;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.ModelUtils;
import net.optifine.util.RandomUtils;
import org.joml.Vector3f;

public class BlockModelUtils {
  private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
  
  private static final ayw RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
  
  public static gsm makeModelCube(String spriteName, int tintIndex) {
    gql sprite = Config.getTextureMap().getUploadedSprite(spriteName);
    return makeModelCube(sprite, tintIndex);
  }
  
  public static gsm makeModelCube(gql sprite, int tintIndex) {
    List generalQuads = new ArrayList();
    ji[] facings = ji.r;
    Map<ji, List<gfw>> faceQuads = new HashMap<>();
    for (int i = 0; i < facings.length; i++) {
      ji facing = facings[i];
      List<gfw> quads = new ArrayList();
      quads.add(makeBakedQuad(facing, sprite, tintIndex));
      faceQuads.put(facing, quads);
    } 
    ggg itemOverrideList = ggg.a;
    return (gsm)new gsx(generalQuads, faceQuads, true, true, true, sprite, ggi.a, itemOverrideList);
  }
  
  public static gsm joinModelsCube(gsm modelBase, gsm modelAdd) {
    List<gfw> generalQuads = new ArrayList<>();
    generalQuads.addAll(modelBase.a(null, null, RANDOM));
    generalQuads.addAll(modelAdd.a(null, null, RANDOM));
    ji[] facings = ji.r;
    Map<ji, List<gfw>> faceQuads = new HashMap<>();
    for (int i = 0; i < facings.length; i++) {
      ji facing = facings[i];
      List<gfw> quads = new ArrayList();
      quads.addAll(modelBase.a(null, facing, RANDOM));
      quads.addAll(modelAdd.a(null, facing, RANDOM));
      faceQuads.put(facing, quads);
    } 
    boolean ao = modelBase.a();
    boolean builtIn = modelBase.d();
    gql sprite = modelBase.e();
    ggi transforms = modelBase.f();
    ggg itemOverrideList = modelBase.g();
    return (gsm)new gsx(generalQuads, faceQuads, ao, builtIn, true, sprite, transforms, itemOverrideList);
  }
  
  public static gfw makeBakedQuad(ji facing, gql sprite, int tintIndex) {
    Vector3f posFrom = new Vector3f(0.0F, 0.0F, 0.0F);
    Vector3f posTo = new Vector3f(16.0F, 16.0F, 16.0F);
    gga uv = new gga(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
    gfy face = new gfy(facing, tintIndex, "#" + facing.c(), uv);
    gsn modelRotation = gsn.a;
    gfz partRotation = null;
    boolean shade = true;
    akr modelLoc = sprite.getName();
    ggd faceBakery = new ggd();
    gfw quad = faceBakery.a(posFrom, posTo, face, sprite, facing, (gsv)modelRotation, partRotation, shade);
    return quad;
  }
  
  public static gsm makeModel(String modelName, String spriteOldName, String spriteNewName) {
    gqk textureMap = Config.getTextureMap();
    gql spriteOld = textureMap.getUploadedSprite(spriteOldName);
    gql spriteNew = textureMap.getUploadedSprite(spriteNewName);
    return makeModel(modelName, spriteOld, spriteNew);
  }
  
  public static gsm makeModel(String modelName, gql spriteOld, gql spriteNew) {
    if (spriteOld == null || spriteNew == null)
      return null; 
    gst modelManager = Config.getModelManager();
    if (modelManager == null)
      return null; 
    gsu mrl = gsu.a(modelName, "");
    gsm model = modelManager.a(mrl);
    if (model == null || model == modelManager.a())
      return null; 
    gsm modelNew = ModelUtils.duplicateModel(model);
    ji[] faces = ji.r;
    for (int i = 0; i < faces.length; i++) {
      ji face = faces[i];
      List<gfw> quads = modelNew.a(null, face, RANDOM);
      replaceTexture(quads, spriteOld, spriteNew);
    } 
    List<gfw> quadsGeneral = modelNew.a(null, null, RANDOM);
    replaceTexture(quadsGeneral, spriteOld, spriteNew);
    return modelNew;
  }
  
  private static void replaceTexture(List<gfw> quads, gql spriteOld, gql spriteNew) {
    List<gfw> quadsNew = new ArrayList<>();
    for (Iterator<gfw> it = quads.iterator(); it.hasNext(); ) {
      BakedQuadRetextured bakedQuadRetextured;
      gfw quad = it.next();
      if (quad.a() == spriteOld)
        bakedQuadRetextured = new BakedQuadRetextured(quad, spriteNew); 
      quadsNew.add(bakedQuadRetextured);
    } 
    quads.clear();
    quads.addAll(quadsNew);
  }
  
  public static void snapVertexPosition(Vector3f pos) {
    pos.set(snapVertexCoord(pos.x()), snapVertexCoord(pos.y()), snapVertexCoord(pos.z()));
  }
  
  private static float snapVertexCoord(float x) {
    if (x > -1.0E-6F && x < 1.0E-6F)
      return 0.0F; 
    if (x > 0.999999F && x < 1.000001F)
      return 1.0F; 
    return x;
  }
  
  public static ewx getOffsetBoundingBox(ewx aabb, dtb.c offsetType, jd pos) {
    int x = pos.u();
    int z = pos.w();
    long k = (x * 3129871) ^ z * 116129781L;
    k = k * k * 42317861L + k * 11L;
    double dx = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
    double dz = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
    double dy = 0.0D;
    if (offsetType == dtb.c.c)
      dy = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D; 
    return aabb.d(dx, dy, dz);
  }
}
