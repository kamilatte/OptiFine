package srg.net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.GL20;

public class ShaderUniform1i extends ShaderUniformBase {
  private int[] programValues;
  
  private static final int VALUE_UNKNOWN = -2147483648;
  
  public ShaderUniform1i(String name) {
    super(name);
    resetValue();
  }
  
  public void setValue(int valueNew) {
    int program = getProgram();
    int valueOld = this.programValues[program];
    if (valueNew == valueOld)
      return; 
    this.programValues[program] = valueNew;
    int location = getLocation();
    if (location < 0)
      return; 
    flushRenderBuffers();
    GL20.glUniform1i(location, valueNew);
    checkGLError();
  }
  
  public int getValue() {
    int program = getProgram();
    int value = this.programValues[program];
    return value;
  }
  
  protected void onProgramSet(int program) {
    if (program >= this.programValues.length) {
      int[] valuesOld = this.programValues;
      int[] valuesNew = new int[program + 10];
      System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
      for (int i = valuesOld.length; i < valuesNew.length; i++)
        valuesNew[i] = Integer.MIN_VALUE; 
      this.programValues = valuesNew;
    } 
  }
  
  protected void resetValue() {
    this.programValues = new int[] { Integer.MIN_VALUE };
  }
}
