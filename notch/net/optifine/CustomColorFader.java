package notch.net.optifine;

import exc;
import net.optifine.Config;

public class CustomColorFader {
  private exc color = null;
  
  private long timeUpdate = System.currentTimeMillis();
  
  public exc getColor(double x, double y, double z) {
    if (this.color == null) {
      this.color = new exc(x, y, z);
      return this.color;
    } 
    long timeNow = System.currentTimeMillis();
    long timeDiff = timeNow - this.timeUpdate;
    if (timeDiff == 0L)
      return this.color; 
    this.timeUpdate = timeNow;
    if (Math.abs(x - this.color.c) < 0.004D && Math.abs(y - this.color.d) < 0.004D && Math.abs(z - this.color.e) < 0.004D)
      return this.color; 
    double k = timeDiff * 0.001D;
    k = Config.limit(k, 0.0D, 1.0D);
    double dx = x - this.color.c;
    double dy = y - this.color.d;
    double dz = z - this.color.e;
    double xn = this.color.c + dx * k;
    double yn = this.color.d + dy * k;
    double zn = this.color.e + dz * k;
    this.color = new exc(xn, yn, zn);
    return this.color;
  }
}
