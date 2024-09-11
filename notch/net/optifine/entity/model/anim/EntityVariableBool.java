package notch.net.optifine.entity.model.anim;

import aka;
import bsr;
import fgo;
import gkh;
import java.util.HashMap;
import net.optifine.entity.model.anim.IModelVariableBool;

public class EntityVariableBool implements IModelVariableBool {
  private String name;
  
  private gkh renderManager;
  
  public EntityVariableBool(String name) {
    this.name = name;
    this.renderManager = fgo.Q().ap();
  }
  
  public boolean eval() {
    return getValue();
  }
  
  public boolean getValue() {
    aka entityData = getEntityData();
    if (entityData == null)
      return false; 
    if (entityData.modelVariables == null)
      return false; 
    Boolean val = (Boolean)entityData.modelVariables.get(this.name);
    if (val == null)
      return false; 
    return val.booleanValue();
  }
  
  public void setValue(boolean value) {
    aka entityData = getEntityData();
    if (entityData == null)
      return; 
    if (entityData.modelVariables == null)
      entityData.modelVariables = new HashMap<>(); 
    entityData.modelVariables.put(this.name, Boolean.valueOf(value));
  }
  
  private aka getEntityData() {
    bsr entity = this.renderManager.getRenderedEntity();
    if (entity == null)
      return null; 
    return entity.ar();
  }
}
