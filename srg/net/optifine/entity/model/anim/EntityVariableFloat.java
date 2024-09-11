package srg.net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.optifine.entity.model.anim.IModelVariableFloat;

public class EntityVariableFloat implements IModelVariableFloat {
  private String name;
  
  private static EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
  
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
    SynchedEntityData entityData = getEntityData();
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
    SynchedEntityData entityData = getEntityData();
    if (entityData == null)
      return; 
    if (entityData.modelVariables == null)
      entityData.modelVariables = new HashMap<>(); 
    entityData.modelVariables.put(key, Float.valueOf(value));
  }
  
  private static SynchedEntityData getEntityData() {
    Entity entity = renderManager.getRenderedEntity();
    if (entity == null)
      return null; 
    return entity.getEntityData();
  }
}
