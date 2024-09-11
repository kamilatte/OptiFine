package notch.net.optifine.shaders;

import fbn;
import fbo;

public class SVertexFormat {
  public static final int vertexSizeBlock = 18;
  
  public static final int offsetMidBlock = 8;
  
  public static final int offsetMidTexCoord = 9;
  
  public static final int offsetTangent = 11;
  
  public static final int offsetEntity = 13;
  
  public static final int offsetVelocity = 15;
  
  public static final fbo SHADERS_MIDBLOCK_3B = makeElement("SHADERS_MIDOFFSET_3B", 0, fbo.a.c, fbo.b.PADDING, 3);
  
  public static final fbo PADDING_1B = makeElement("PADDING_1B", 0, fbo.a.c, fbo.b.PADDING, 1);
  
  public static final fbo SHADERS_MIDTEXCOORD_2F = makeElement("SHADERS_MIDTEXCOORD_2F", 0, fbo.a.a, fbo.b.PADDING, 2);
  
  public static final fbo SHADERS_TANGENT_4S = makeElement("SHADERS_TANGENT_4S", 0, fbo.a.e, fbo.b.PADDING, 4);
  
  public static final fbo SHADERS_MC_ENTITY_4S = makeElement("SHADERS_MC_ENTITY_4S", 0, fbo.a.e, fbo.b.PADDING, 4);
  
  public static final fbo SHADERS_VELOCITY_3F = makeElement("SHADERS_VELOCITY_3F", 0, fbo.a.a, fbo.b.PADDING, 3);
  
  public static fbn makeExtendedFormatBlock(fbn blockVanilla) {
    fbn.a builder = new fbn.a();
    builder.addAll(blockVanilla);
    builder.a("MidOffset", SHADERS_MIDBLOCK_3B);
    builder.a("PaddingMO", PADDING_1B);
    builder.a("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
    builder.a("Tangent", SHADERS_TANGENT_4S);
    builder.a("McEntity", SHADERS_MC_ENTITY_4S);
    builder.a("Velocity", SHADERS_VELOCITY_3F);
    fbn vf = builder.a();
    vf.setExtended(true);
    return vf;
  }
  
  public static fbn makeExtendedFormatEntity(fbn entityVanilla) {
    fbn.a builder = new fbn.a();
    builder.addAll(entityVanilla);
    builder.a("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
    builder.a("Tangent", SHADERS_TANGENT_4S);
    builder.a("McEntity", SHADERS_MC_ENTITY_4S);
    builder.a("Velocity", SHADERS_VELOCITY_3F);
    fbn vf = builder.a();
    vf.setExtended(true);
    return vf;
  }
  
  private static fbo makeElement(String name, int indexIn, fbo.a typeIn, fbo.b usageIn, int count) {
    fbo vfe = fbo.register(fbo.getElementsCount(), indexIn, typeIn, usageIn, count, name, -1);
    return vfe;
  }
  
  public static int removeExtendedElements(int maskElements) {
    int maskVanilla = (fbo.h.a() << 1) - 1;
    maskElements &= maskVanilla;
    return maskElements;
  }
}
