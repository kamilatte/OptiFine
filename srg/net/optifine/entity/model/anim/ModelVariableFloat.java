package srg.net.optifine.entity.model.anim;

import net.minecraft.client.model.geom.ModelPart;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.IModelVariableFloat;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.expr.IExpressionFloat;

public class ModelVariableFloat implements IExpressionFloat, IModelVariableFloat, IModelRendererVariable {
  private String name;
  
  private ModelPart modelRenderer;
  
  private ModelVariableType enumModelVariable;
  
  public ModelVariableFloat(String name, ModelPart modelRenderer, ModelVariableType enumModelVariable) {
    this.name = name;
    this.modelRenderer = modelRenderer;
    this.enumModelVariable = enumModelVariable;
  }
  
  public ModelPart getModelRenderer() {
    return this.modelRenderer;
  }
  
  public float eval() {
    return getValue();
  }
  
  public float getValue() {
    return this.enumModelVariable.getFloat(this.modelRenderer);
  }
  
  public void setValue(float value) {
    this.enumModelVariable.setFloat(this.modelRenderer, value);
  }
  
  public String toString() {
    return this.name;
  }
}
