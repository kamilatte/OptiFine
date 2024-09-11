package srg.net.optifine.config;

import net.minecraft.world.item.enchantment.Enchantment;
import net.optifine.config.IParserInt;
import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId implements IParserInt {
  public int parse(String str, int defVal) {
    Enchantment en = EnchantmentUtils.getEnchantment(str);
    if (en == null)
      return defVal; 
    return EnchantmentUtils.getId(en);
  }
}
