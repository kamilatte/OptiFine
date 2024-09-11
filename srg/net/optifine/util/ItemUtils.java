package srg.net.optifine.util;

import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.optifine.reflect.Reflector;

public class ItemUtils {
  private static CompoundTag EMPTY_TAG = new CompoundTag();
  
  public static Item getItem(ResourceLocation loc) {
    if (!BuiltInRegistries.ITEM.containsKey(loc))
      return null; 
    return (Item)BuiltInRegistries.ITEM.get(loc);
  }
  
  public static int getId(Item item) {
    return BuiltInRegistries.ITEM.getId(item);
  }
  
  public static CompoundTag getTag(ItemStack itemStack) {
    if (itemStack == null)
      return EMPTY_TAG; 
    PatchedDataComponentMap components = (PatchedDataComponentMap)Reflector.ItemStack_components.getValue(itemStack);
    if (components == null)
      return EMPTY_TAG; 
    CompoundTag tag = components.getTag();
    return tag;
  }
}
