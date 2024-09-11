package notch.net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.GL20;

public class ShaderUniform3f extends ShaderUniformBase {
  private float[][] programValues;
  
  private static final float VALUE_UNKNOWN = -3.4028235E38F;
  
  public ShaderUniform3f(String name) {
    super(name);
    resetValue();
  }
  
  public void setValue(float v0, float v1, float v2) {
    int program = getProgram();
    float[] valueOld = this.programValues[program];
    if (valueOld[0] == v0 && valueOld[1] == v1 && valueOld[2] == v2)
      return; 
    valueOld[0] = v0;
    valueOld[1] = v1;
    valueOld[2] = v2;
    int location = getLocation();
    if (location < 0)
      return; 
    flushRenderBuffers();
    GL20.glUniform3f(location, v0, v1, v2);
    checkGLError();
  }
  
  public float[] getValue() {
    int program = getProgram();
    float[] value = this.programValues[program];
    return value;
  }
  
  protected void onProgramSet(int program) {
    if (program >= this.programValues.length) {
      float[][] valuesOld = this.programValues;
      float[][] valuesNew = new float[program + 10][];
      System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
      this.programValues = valuesNew;
    } 
    if (this.programValues[program] == null) {
      (new float[3])[0] = -3.4028235E38F;
      (new float[3])[1] = -3.4028235E38F;
      (new float[3])[2] = -3.4028235E38F;
      this.programValues[program] = new float[3];
    } 
  }
  
  protected void resetValue() {
    this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F, -3.4028235E38F } };
  }
}
