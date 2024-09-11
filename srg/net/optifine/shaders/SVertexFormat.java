package srg.net.optifine.shaders;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public class SVertexFormat {
  public static final int vertexSizeBlock = 18;
  
  public static final int offsetMidBlock = 8;
  
  public static final int offsetMidTexCoord = 9;
  
  public static final int offsetTangent = 11;
  
  public static final int offsetEntity = 13;
  
  public static final int offsetVelocity = 15;
  
  public static final VertexFormatElement SHADERS_MIDBLOCK_3B = makeElement("SHADERS_MIDOFFSET_3B", 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 3);
  
  public static final VertexFormatElement PADDING_1B = makeElement("PADDING_1B", 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 1);
  
  public static final VertexFormatElement SHADERS_MIDTEXCOORD_2F = makeElement("SHADERS_MIDTEXCOORD_2F", 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.PADDING, 2);
  
  public static final VertexFormatElement SHADERS_TANGENT_4S = makeElement("SHADERS_TANGENT_4S", 0, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.PADDING, 4);
  
  public static final VertexFormatElement SHADERS_MC_ENTITY_4S = makeElement("SHADERS_MC_ENTITY_4S", 0, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.PADDING, 4);
  
  public static final VertexFormatElement SHADERS_VELOCITY_3F = makeElement("SHADERS_VELOCITY_3F", 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.PADDING, 3);
  
  public static VertexFormat makeExtendedFormatBlock(VertexFormat blockVanilla) {
    VertexFormat.Builder builder = new VertexFormat.Builder();
    builder.addAll(blockVanilla);
    builder.add("MidOffset", SHADERS_MIDBLOCK_3B);
    builder.add("PaddingMO", PADDING_1B);
    builder.add("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
    builder.add("Tangent", SHADERS_TANGENT_4S);
    builder.add("McEntity", SHADERS_MC_ENTITY_4S);
    builder.add("Velocity", SHADERS_VELOCITY_3F);
    VertexFormat vf = builder.build();
    vf.setExtended(true);
    return vf;
  }
  
  public static VertexFormat makeExtendedFormatEntity(VertexFormat entityVanilla) {
    VertexFormat.Builder builder = new VertexFormat.Builder();
    builder.addAll(entityVanilla);
    builder.add("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
    builder.add("Tangent", SHADERS_TANGENT_4S);
    builder.add("McEntity", SHADERS_MC_ENTITY_4S);
    builder.add("Velocity", SHADERS_VELOCITY_3F);
    VertexFormat vf = builder.build();
    vf.setExtended(true);
    return vf;
  }
  
  private static VertexFormatElement makeElement(String name, int indexIn, VertexFormatElement.Type typeIn, VertexFormatElement.Usage usageIn, int count) {
    VertexFormatElement vfe = VertexFormatElement.register(VertexFormatElement.getElementsCount(), indexIn, typeIn, usageIn, count, name, -1);
    return vfe;
  }
  
  public static int removeExtendedElements(int maskElements) {
    int maskVanilla = (VertexFormatElement.NORMAL.mask() << 1) - 1;
    maskElements &= maskVanilla;
    return maskElements;
  }
}
