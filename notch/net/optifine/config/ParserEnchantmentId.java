package notch.net.optifine.config;

import dac;
import net.optifine.config.IParserInt;
import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId implements IParserInt {
  public int parse(String str, int defVal) {
    dac en = EnchantmentUtils.getEnchantment(str);
    if (en == null)
      return defVal; 
    return EnchantmentUtils.getId(en);
  }
}
