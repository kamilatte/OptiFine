package srg.net.optifine.shaders;

public class MultiTexID {
  public int base;
  
  public int norm;
  
  public int spec;
  
  public MultiTexID(int baseTex, int normTex, int specTex) {
    this.base = baseTex;
    this.norm = normTex;
    this.spec = specTex;
  }
  
  public String toString() {
    return "base: " + this.base + ", norm: " + this.norm + ", spec: " + this.spec;
  }
}
