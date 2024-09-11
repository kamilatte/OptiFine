package srg.net.optifine.util;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.optifine.reflect.Reflector;

public class PotionUtils {
  public static MobEffect getPotion(ResourceLocation loc) {
    if (!BuiltInRegistries.MOB_EFFECT.containsKey(loc))
      return null; 
    return (MobEffect)BuiltInRegistries.MOB_EFFECT.get(loc);
  }
  
  public static MobEffect getPotion(int potionID) {
    return (MobEffect)BuiltInRegistries.MOB_EFFECT.byId(potionID);
  }
  
  public static int getId(MobEffect potionIn) {
    return BuiltInRegistries.MOB_EFFECT.getId(potionIn);
  }
  
  public static String getPotionBaseName(Potion p) {
    if (p == null)
      return null; 
    return (String)Reflector.Potion_baseName.getValue(p);
  }
  
  public static Potion getPotion(ItemStack itemStack) {
    PotionContents pc = (PotionContents)itemStack.get(DataComponents.POTION_CONTENTS);
    if (pc == null)
      return null; 
    Optional<Holder<Potion>> opt = pc.potion();
    if (opt.isEmpty())
      return null; 
    Holder<Potion> holder = opt.get();
    if (!holder.isBound())
      return null; 
    Potion p = (Potion)holder.value();
    return p;
  }
}
