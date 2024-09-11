package notch.net.optifine.util;

import fal;
import java.util.Comparator;

public class DisplayModeComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    fal dm1 = (fal)o1;
    fal dm2 = (fal)o2;
    if (dm1.a() != dm2.a())
      return dm1.a() - dm2.a(); 
    if (dm1.b() != dm2.b())
      return dm1.b() - dm2.b(); 
    int bits1 = dm1.c() + dm1.d() + dm1.e();
    int bits2 = dm2.c() + dm2.d() + dm2.e();
    if (bits1 != bits2)
      return bits1 - bits2; 
    if (dm1.f() != dm2.f())
      return dm1.f() - dm2.f(); 
    return 0;
  }
}
