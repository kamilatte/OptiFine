package notch.net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.GL20;

public class ShaderUniform4i extends ShaderUniformBase {
  private int[][] programValues;
  
  private static final int VALUE_UNKNOWN = -2147483648;
  
  public ShaderUniform4i(String name) {
    super(name);
    resetValue();
  }
  
  public void setValue(int v0, int v1, int v2, int v3) {
    int program = getProgram();
    int[] valueOld = this.programValues[program];
    if (valueOld[0] == v0 && valueOld[1] == v1 && valueOld[2] == v2 && valueOld[3] == v3)
      return; 
    valueOld[0] = v0;
    valueOld[1] = v1;
    valueOld[2] = v2;
    valueOld[3] = v3;
    int location = getLocation();
    if (location < 0)
      return; 
    flushRenderBuffers();
    GL20.glUniform4i(location, v0, v1, v2, v3);
    checkGLError();
  }
  
  public int[] getValue() {
    int program = getProgram();
    int[] value = this.programValues[program];
    return value;
  }
  
  protected void onProgramSet(int program) {
    if (program >= this.programValues.length) {
      int[][] valuesOld = this.programValues;
      int[][] valuesNew = new int[program + 10][];
      System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
      this.programValues = valuesNew;
    } 
    if (this.programValues[program] == null) {
      (new int[4])[0] = Integer.MIN_VALUE;
      (new int[4])[1] = Integer.MIN_VALUE;
      (new int[4])[2] = Integer.MIN_VALUE;
      (new int[4])[3] = Integer.MIN_VALUE;
      this.programValues[program] = new int[4];
    } 
  }
  
  protected void resetValue() {
    this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE } };
  }
}
