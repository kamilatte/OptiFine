package srg.net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.GL20;

public class ShaderUniform1f extends ShaderUniformBase {
  private float[] programValues;
  
  private static final float VALUE_UNKNOWN = -3.4028235E38F;
  
  public ShaderUniform1f(String name) {
    super(name);
    resetValue();
  }
  
  public void setValue(float valueNew) {
    int program = getProgram();
    float valueOld = this.programValues[program];
    if (valueNew == valueOld)
      return; 
    this.programValues[program] = valueNew;
    int location = getLocation();
    if (location < 0)
      return; 
    flushRenderBuffers();
    GL20.glUniform1f(location, valueNew);
    checkGLError();
  }
  
  public float getValue() {
    int program = getProgram();
    float value = this.programValues[program];
    return value;
  }
  
  protected void onProgramSet(int program) {
    if (program >= this.programValues.length) {
      float[] valuesOld = this.programValues;
      float[] valuesNew = new float[program + 10];
      System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
      for (int i = valuesOld.length; i < valuesNew.length; i++)
        valuesNew[i] = -3.4028235E38F; 
      this.programValues = valuesNew;
    } 
  }
  
  protected void resetValue() {
    this.programValues = new float[] { -3.4028235E38F };
  }
}
