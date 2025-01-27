package notch.net.optifine.config;

public class GlVersion {
  private int major;
  
  private int minor;
  
  private int release;
  
  private String suffix;
  
  public GlVersion(int major, int minor) {
    this(major, minor, 0);
  }
  
  public GlVersion(int major, int minor, int release) {
    this(major, minor, release, null);
  }
  
  public GlVersion(int major, int minor, int release, String suffix) {
    this.major = major;
    this.minor = minor;
    this.release = release;
    this.suffix = suffix;
  }
  
  public int getMajor() {
    return this.major;
  }
  
  public int getMinor() {
    return this.minor;
  }
  
  public int getRelease() {
    return this.release;
  }
  
  public int toInt() {
    if (this.minor > 9)
      return this.major * 100 + this.minor; 
    if (this.release > 9)
      return this.major * 100 + this.minor * 10 + 9; 
    return this.major * 100 + this.minor * 10 + this.release;
  }
  
  public String toString() {
    if (this.suffix == null)
      return "" + this.major + "." + this.major + "." + this.minor; 
    return "" + this.major + "." + this.major + "." + this.minor + this.release;
  }
}
