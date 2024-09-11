package srg.net.optifine;

import java.util.Arrays;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.optifine.Config;

public class ItemOverrideProperty {
  private ResourceLocation location;
  
  private float[] values;
  
  public ItemOverrideProperty(ResourceLocation location, float[] values) {
    this.location = location;
    this.values = (float[])values.clone();
    Arrays.sort(this.values);
  }
  
  public Integer getValueIndex(ItemStack stack, ClientLevel world, LivingEntity entity) {
    ItemPropertyFunction itemPropertyGetter = ItemProperties.getProperty(stack, this.location);
    if (itemPropertyGetter == null)
      return null; 
    float val = itemPropertyGetter.call(stack, world, entity, 0);
    int index = Arrays.binarySearch(this.values, val);
    return Integer.valueOf(index);
  }
  
  public ResourceLocation getLocation() {
    return this.location;
  }
  
  public float[] getValues() {
    return this.values;
  }
  
  public String toString() {
    return "location: " + String.valueOf(this.location) + ", values: [" + Config.arrayToString(this.values) + "]";
  }
}
