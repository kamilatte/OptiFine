package notch.net.optifine.entity.model.anim;

import aka;
import bsr;
import fgo;
import gkh;
import java.util.HashMap;
import net.optifine.entity.model.anim.IModelVariableFloat;

public class EntityVariableFloat implements IModelVariableFloat {
  private String name;
  
  private static gkh renderManager = fgo.Q().ap();
  
  public EntityVariableFloat(String name) {
    this.name = name;
  }
  
  public float eval() {
    return getValue();
  }
  
  public float getValue() {
    return getEntityValue(this.name);
  }
  
  public static float getEntityValue(String key) {
    aka entityData = getEntityData();
    if (entityData == null)
      return 0.0F; 
    if (entityData.modelVariables == null)
      return 0.0F; 
    Float val = (Float)entityData.modelVariables.get(key);
    if (val == null)
      return 0.0F; 
    return val.floatValue();
  }
  
  public void setValue(float value) {
    setEntityValue(this.name, value);
  }
  
  public static void setEntityValue(String key, float value) {
    aka entityData = getEntityData();
    if (entityData == null)
      return; 
    if (entityData.modelVariables == null)
      entityData.modelVariables = new HashMap<>(); 
    entityData.modelVariables.put(key, Float.valueOf(value));
  }
  
  private static aka getEntityData() {
    bsr entity = renderManager.getRenderedEntity();
    if (entity == null)
      return null; 
    return entity.ar();
  }
}
