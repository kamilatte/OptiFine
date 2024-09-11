package notch.net.optifine.util;

import fac;
import fbd;
import fbn;
import fbo;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
  public static String getBufferHex(fbd bb) {
    fbn.c drawMode = bb.getDrawMode();
    String primitiveName = "";
    int vertexPerPrimitive = -1;
    if (drawMode == fbn.c.h) {
      primitiveName = "quad";
      vertexPerPrimitive = 4;
    } else if (drawMode == fbn.c.e) {
      primitiveName = "triangle";
      vertexPerPrimitive = 3;
    } else {
      return "Invalid draw mode: " + String.valueOf(drawMode);
    } 
    StringBuffer sb = new StringBuffer();
    int vertexCount = bb.getVertexCount();
    for (int v = 0; v < vertexCount; v++) {
      if (v % vertexPerPrimitive == 0)
        sb.append(primitiveName + " " + primitiveName + "\n"); 
      String vs = getVertexHex(v, bb);
      sb.append(vs);
      sb.append("\n");
    } 
    return sb.toString();
  }
  
  private static String getVertexHex(int vertex, fbd bb) {
    StringBuffer sb = new StringBuffer();
    ByteBuffer buf = bb.getByteBuffer();
    fbn vf = bb.getVertexFormat();
    int pos = bb.getStartPosition() + vertex * vf.b();
    for (fbo vfe : vf.c()) {
      if (vfe.getElementCount() > 0)
        sb.append("("); 
      for (int i = 0; i < vfe.getElementCount(); i++) {
        if (i > 0)
          sb.append(" "); 
        switch (null.$SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[vfe.e().ordinal()]) {
          case 1:
            sb.append(buf.getFloat(pos));
            break;
          case 2:
          case 3:
            sb.append(buf.get(pos));
            break;
          case 4:
          case 5:
            sb.append(buf.getShort(pos));
            break;
          case 6:
          case 7:
            sb.append(buf.getShort(pos));
            break;
          default:
            sb.append("??");
            break;
        } 
        pos += vfe.e().a();
      } 
      if (vfe.getElementCount() > 0)
        sb.append(")"); 
    } 
    return sb.toString();
  }
  
  public static String getBufferString(IntBuffer buf) {
    if (buf == null)
      return "null"; 
    StringBuffer sb = new StringBuffer();
    sb.append("(pos=" + buf.position() + " lim=" + buf.limit() + " cap=" + buf.capacity() + ")");
    sb.append("[");
    int len = Math.min(buf.limit(), 1024);
    for (int i = 0; i < len; i++) {
      if (i > 0)
        sb.append(", "); 
      sb.append(buf.get(i));
    } 
    sb.append("]");
    return sb.toString();
  }
  
  public static int[] toArray(IntBuffer buf) {
    int[] arr = new int[buf.limit()];
    for (int i = 0; i < arr.length; i++)
      arr[i] = buf.get(i); 
    return arr;
  }
  
  public static FloatBuffer createDirectFloatBuffer(int capacity) {
    return fac.a(capacity << 2).asFloatBuffer();
  }
  
  public static void fill(FloatBuffer buf, float val) {
    buf.clear();
    for (int i = 0; i < buf.capacity(); i++)
      buf.put(i, val); 
    buf.clear();
  }
}
