package notch.net.optifine.util;

import ayo;
import net.optifine.util.MathUtils;

public class MathUtilsTest {
  public static void main(String[] args) throws Exception {
    OPER[] values = OPER.values();
    for (int i = 0; i < values.length; i++) {
      OPER oper = values[i];
      dbg("******** " + String.valueOf(oper) + " ***********");
      test(oper, false);
    } 
  }
  
  private static void test(OPER oper, boolean fast) {
    double min, max;
    ayo.fastMath = fast;
    switch (oper.ordinal()) {
      case 0:
      case 1:
        min = -3.1415927410125732D;
        max = 3.1415927410125732D;
        break;
      case 2:
      case 3:
        min = -1.0D;
        max = 1.0D;
        break;
      default:
        return;
    } 
    int count = 10;
    for (int i = 0; i <= count; i++) {
      float res1, res2;
      double val = min + i * (max - min) / count;
      switch (oper.ordinal()) {
        case 0:
          res1 = (float)Math.sin(val);
          res2 = ayo.a((float)val);
          break;
        case 1:
          res1 = (float)Math.cos(val);
          res2 = ayo.b((float)val);
          break;
        case 2:
          res1 = (float)Math.asin(val);
          res2 = MathUtils.asin((float)val);
          break;
        case 3:
          res1 = (float)Math.acos(val);
          res2 = MathUtils.acos((float)val);
          break;
        default:
          return;
      } 
      dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", new Object[] { Double.valueOf(val), Float.valueOf(res1), Float.valueOf(res2), Float.valueOf(Math.abs(res1 - res2)) }));
    } 
  }
  
  public static void dbg(String str) {
    System.out.println(str);
  }
}
