package notch.net.optifine.util;

public class PairInt {
  private int left;
  
  private int right;
  
  private final int hashCode;
  
  public PairInt(int left, int right) {
    this.left = left;
    this.right = right;
    this.hashCode = left + 37 * right;
  }
  
  public static net.optifine.util.PairInt of(int left, int right) {
    return new net.optifine.util.PairInt(left, right);
  }
  
  public int getLeft() {
    return this.left;
  }
  
  public int getRight() {
    return this.right;
  }
  
  public int hashCode() {
    return this.hashCode;
  }
  
  public boolean equals(Object obj) {
    if (obj == this)
      return true; 
    if (obj instanceof net.optifine.util.PairInt) {
      net.optifine.util.PairInt pi = (net.optifine.util.PairInt)obj;
      return (this.left == pi.left && this.right == pi.right);
    } 
    return false;
  }
  
  public String toString() {
    return "(" + this.left + ", " + this.right + ")";
  }
}
