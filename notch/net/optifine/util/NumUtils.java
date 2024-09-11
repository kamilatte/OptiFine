package notch.net.optifine.util;

public class NumUtils {
  public static int limit(int val, int min, int max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static float limit(float val, float min, float max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static double limit(double val, double min, double max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static int mod(int x, int y) {
    int result = x % y;
    if (result < 0)
      result += y; 
    return result;
  }
}
