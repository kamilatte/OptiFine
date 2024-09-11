package notch.net.optifine.shaders.config;

public abstract class HeaderLine {
  public abstract String getText();
  
  public abstract boolean matches(String paramString);
  
  public abstract String removeFrom(String paramString);
  
  public boolean equals(Object obj) {
    if (!(obj instanceof net.optifine.shaders.config.HeaderLine))
      return false; 
    String objText = ((net.optifine.shaders.config.HeaderLine)obj).getText();
    return getText().equals(objText);
  }
  
  public int hashCode() {
    return getText().hashCode();
  }
  
  public String toString() {
    return getText();
  }
}
