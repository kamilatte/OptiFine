package notch.net.optifine.shaders.uniform;

import java.util.Arrays;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL20;

public abstract class ShaderUniformBase {
  private String name;
  
  private int program = 0;
  
  private int[] locations = new int[] { -1 };
  
  private static final int LOCATION_UNDEFINED = -1;
  
  private static final int LOCATION_UNKNOWN = -2147483648;
  
  public ShaderUniformBase(String name) {
    this.name = name;
  }
  
  public void setProgram(int program) {
    if (this.program == program)
      return; 
    this.program = program;
    expandLocations();
    onProgramSet(program);
  }
  
  private void expandLocations() {
    if (this.program >= this.locations.length) {
      int[] locationsNew = new int[this.program * 2];
      Arrays.fill(locationsNew, -2147483648);
      System.arraycopy(this.locations, 0, locationsNew, 0, this.locations.length);
      this.locations = locationsNew;
    } 
  }
  
  protected abstract void onProgramSet(int paramInt);
  
  public String getName() {
    return this.name;
  }
  
  public int getProgram() {
    return this.program;
  }
  
  public int getLocation() {
    if (this.program <= 0)
      return -1; 
    int location = this.locations[this.program];
    if (location == Integer.MIN_VALUE) {
      location = GL20.glGetUniformLocation(this.program, this.name);
      this.locations[this.program] = location;
    } 
    return location;
  }
  
  public boolean isDefined() {
    return (getLocation() >= 0);
  }
  
  public void disable() {
    this.locations[this.program] = -1;
  }
  
  public void reset() {
    this.program = 0;
    this.locations = new int[] { -1 };
    resetValue();
  }
  
  protected abstract void resetValue();
  
  protected void checkGLError() {
    if (Shaders.checkGLError(this.name) != 0)
      disable(); 
  }
  
  protected static final void flushRenderBuffers() {
    Shaders.flushRenderBuffers();
  }
  
  public String toString() {
    return this.name;
  }
}
