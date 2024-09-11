package notch.net.optifine.shaders;

import ayo;
import gie;

public class ClippingHelperShadow extends gie {
  public ClippingHelperShadow() {
    super(null, null);
    this.frustumTest = new float[6];
    this.shadowClipPlanes = new float[10][4];
    this.matInvMP = new float[16];
    this.vecIntersection = new float[4];
  }
  
  private static net.optifine.shaders.ClippingHelperShadow instance = new net.optifine.shaders.ClippingHelperShadow();
  
  float[] frustumTest;
  
  float[][] shadowClipPlanes;
  
  int shadowClipPlaneCount;
  
  float[] matInvMP;
  
  float[] vecIntersection;
  
  float[][] frustum;
  
  public boolean a(double x1, double y1, double z1, double x2, double y2, double z2) {
    for (int index = 0; index < this.shadowClipPlaneCount; index++) {
      float[] plane = this.shadowClipPlanes[index];
      if (dot4(plane, x1, y1, z1) <= 0.0D && dot4(plane, x2, y1, z1) <= 0.0D && 
        dot4(plane, x1, y2, z1) <= 0.0D && dot4(plane, x2, y2, z1) <= 0.0D && 
        dot4(plane, x1, y1, z2) <= 0.0D && dot4(plane, x2, y1, z2) <= 0.0D && 
        dot4(plane, x1, y2, z2) <= 0.0D && dot4(plane, x2, y2, z2) <= 0.0D)
        return false; 
    } 
    return true;
  }
  
  private double dot4(float[] plane, double x, double y, double z) {
    return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
  }
  
  private double dot3(float[] vecA, float[] vecB) {
    return vecA[0] * vecB[0] + vecA[1] * vecB[1] + vecA[2] * vecB[2];
  }
  
  public static gie getInstance() {
    instance.init();
    return instance;
  }
  
  private void normalizePlane(float[] plane) {
    float length = ayo.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
    plane[0] = plane[0] / length;
    plane[1] = plane[1] / length;
    plane[2] = plane[2] / length;
    plane[3] = plane[3] / length;
  }
  
  private void normalize3(float[] plane) {
    float length = ayo.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
    if (length == 0.0F)
      length = 1.0F; 
    plane[0] = plane[0] / length;
    plane[1] = plane[1] / length;
    plane[2] = plane[2] / length;
  }
  
  private void assignPlane(float[] plane, float a, float b, float c, float d) {
    float length = (float)Math.sqrt((a * a + b * b + c * c));
    plane[0] = a / length;
    plane[1] = b / length;
    plane[2] = c / length;
    plane[3] = d / length;
  }
  
  private void copyPlane(float[] dst, float[] src) {
    dst[0] = src[0];
    dst[1] = src[1];
    dst[2] = src[2];
    dst[3] = src[3];
  }
  
  private void cross3(float[] out, float[] a, float[] b) {
    out[0] = a[1] * b[2] - a[2] * b[1];
    out[1] = a[2] * b[0] - a[0] * b[2];
    out[2] = a[0] * b[1] - a[1] * b[0];
  }
  
  private void addShadowClipPlane(float[] plane) {
    copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
  }
  
  private float length(float x, float y, float z) {
    return (float)Math.sqrt((x * x + y * y + z * z));
  }
  
  private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
    return length(x1 - x2, y1 - y2, z1 - z2);
  }
  
  private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
    cross3(this.vecIntersection, positivePlane, negativePlane);
    cross3(shadowPlane, this.vecIntersection, vecSun);
    normalize3(shadowPlane);
    float dotPN = (float)dot3(positivePlane, negativePlane);
    float dotNP = dotPN;
    float dotSN = (float)dot3(shadowPlane, negativePlane);
    float disSN = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN, negativePlane[1] * dotSN, negativePlane[2] * dotSN);
    float disPN = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN, negativePlane[1] * dotPN, negativePlane[2] * dotPN);
    float k1 = disSN / disPN;
    float dotSP = (float)dot3(shadowPlane, positivePlane);
    float disSP = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP, positivePlane[1] * dotSP, positivePlane[2] * dotSP);
    float disNP = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotNP, positivePlane[1] * dotNP, positivePlane[2] * dotNP);
    float k2 = disSP / disNP;
    shadowPlane[3] = positivePlane[3] * k1 + negativePlane[3] * k2;
  }
  
  public void init() {}
}
