package notch.net.optifine.entity.model.anim;

import fyk;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.IModelVariableBool;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.expr.IExpressionBool;

public class ModelVariableBool implements IExpressionBool, IModelVariableBool, IModelRendererVariable {
  private String name;
  
  private fyk modelRenderer;
  
  private ModelVariableType enumModelVariable;
  
  public ModelVariableBool(String name, fyk modelRenderer, ModelVariableType enumModelVariable) {
    this.name = name;
    this.modelRenderer = modelRenderer;
    this.enumModelVariable = enumModelVariable;
  }
  
  public fyk getModelRenderer() {
    return this.modelRenderer;
  }
  
  public boolean eval() {
    return getValue();
  }
  
  public boolean getValue() {
    return this.enumModelVariable.getBool(this.modelRenderer);
  }
  
  public void setValue(boolean value) {
    this.enumModelVariable.setBool(this.modelRenderer, value);
  }
  
  public String toString() {
    return this.name;
  }
}
