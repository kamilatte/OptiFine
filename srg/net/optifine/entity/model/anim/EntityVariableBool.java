package srg.net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.optifine.entity.model.anim.IModelVariableBool;

public class EntityVariableBool implements IModelVariableBool {
  private String name;
  
  private EntityRenderDispatcher renderManager;
  
  public EntityVariableBool(String name) {
    this.name = name;
    this.renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
  }
  
  public boolean eval() {
    return getValue();
  }
  
  public boolean getValue() {
    SynchedEntityData entityData = getEntityData();
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
    SynchedEntityData entityData = getEntityData();
    if (entityData == null)
      return; 
    if (entityData.modelVariables == null)
      entityData.modelVariables = new HashMap<>(); 
    entityData.modelVariables.put(this.name, Boolean.valueOf(value));
  }
  
  private SynchedEntityData getEntityData() {
    Entity entity = this.renderManager.getRenderedEntity();
    if (entity == null)
      return null; 
    return entity.getEntityData();
  }
}
