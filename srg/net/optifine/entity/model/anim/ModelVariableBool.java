package srg.net.optifine.entity.model.anim;

import net.minecraft.client.model.geom.ModelPart;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.IModelVariableBool;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.expr.IExpressionBool;

public class ModelVariableBool implements IExpressionBool, IModelVariableBool, IModelRendererVariable {
  private String name;
  
  private ModelPart modelRenderer;
  
  private ModelVariableType enumModelVariable;
  
  public ModelVariableBool(String name, ModelPart modelRenderer, ModelVariableType enumModelVariable) {
    this.name = name;
    this.modelRenderer = modelRenderer;
    this.enumModelVariable = enumModelVariable;
  }
  
  public ModelPart getModelRenderer() {
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
