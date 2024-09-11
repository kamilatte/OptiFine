package notch.net.optifine.util;

import akq;
import akr;
import dac;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import jm;
import jo;
import lu;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.ArrayUtils;
import net.optifine.util.ComponentUtils;
import pd;
import wz;

public class EnchantmentUtils {
  private static final akq[] ENCHANTMENT_KEYS = makeEnchantmentKeys();
  
  private static final Map<String, Integer> TRANSLATION_KEY_IDS = new HashMap<>();
  
  private static final Map<String, dac> MAP_ENCHANTMENTS = new HashMap<>();
  
  private static final Map<Integer, String> LEGACY_ID_NAMES = makeLegacyIdsMap();
  
  private static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+");
  
  public static dac getEnchantment(String name) {
    if (PATTERN_NUMBER.matcher(name).matches()) {
      int id = Config.parseInt(name, -1);
      String legacyName = LEGACY_ID_NAMES.get(Integer.valueOf(id));
      if (legacyName != null)
        name = legacyName; 
    } 
    dac enchantment = MAP_ENCHANTMENTS.get(name);
    if (enchantment == null) {
      jo.a holderlookup$provider = pd.a();
      jo.b b = holderlookup$provider.b(lu.aL);
      akr loc = new akr(name);
      akq<dac> key = akq.a(lu.aL, loc);
      Optional<jm.c<dac>> optRef = b.a(key);
      if (optRef.isPresent())
        enchantment = (dac)((jm.c)optRef.get()).a(); 
      MAP_ENCHANTMENTS.put(name, enchantment);
    } 
    return enchantment;
  }
  
  private static akq[] makeEnchantmentKeys() {
    return (akq[])Reflector.Enchantments_ResourceKeys.getFieldValues(null);
  }
  
  private static Map<Integer, String> makeLegacyIdsMap() {
    Map<Integer, String> map = new HashMap<>();
    map.put(Integer.valueOf(0), "protection");
    map.put(Integer.valueOf(1), "fire_protection");
    map.put(Integer.valueOf(2), "feather_falling");
    map.put(Integer.valueOf(3), "blast_protection");
    map.put(Integer.valueOf(4), "projectile_protection");
    map.put(Integer.valueOf(5), "respiration");
    map.put(Integer.valueOf(6), "aqua_affinity");
    map.put(Integer.valueOf(7), "thorns");
    map.put(Integer.valueOf(8), "depth_strider");
    map.put(Integer.valueOf(9), "frost_walker");
    map.put(Integer.valueOf(10), "binding_curse");
    map.put(Integer.valueOf(16), "sharpness");
    map.put(Integer.valueOf(17), "smite");
    map.put(Integer.valueOf(18), "bane_of_arthropods");
    map.put(Integer.valueOf(19), "knockback");
    map.put(Integer.valueOf(20), "fire_aspect");
    map.put(Integer.valueOf(21), "looting");
    map.put(Integer.valueOf(32), "efficiency");
    map.put(Integer.valueOf(33), "silk_touch");
    map.put(Integer.valueOf(34), "unbreaking");
    map.put(Integer.valueOf(35), "fortune");
    map.put(Integer.valueOf(48), "power");
    map.put(Integer.valueOf(49), "punch");
    map.put(Integer.valueOf(50), "flame");
    map.put(Integer.valueOf(51), "infinity");
    map.put(Integer.valueOf(61), "luck_of_the_sea");
    map.put(Integer.valueOf(62), "lure");
    map.put(Integer.valueOf(65), "loyalty");
    map.put(Integer.valueOf(66), "impaling");
    map.put(Integer.valueOf(67), "riptide");
    map.put(Integer.valueOf(68), "channeling");
    map.put(Integer.valueOf(70), "mending");
    map.put(Integer.valueOf(71), "vanishing_curse");
    return map;
  }
  
  public static int getId(dac en) {
    wz desc = en.f();
    String tranKey = ComponentUtils.getTranslationKey(desc);
    if (tranKey == null)
      return -1; 
    Integer id = TRANSLATION_KEY_IDS.get(tranKey);
    if (id == null) {
      akq resKey = getResourceKeyByTranslation(tranKey);
      if (resKey == null)
        return -1; 
      int index = ArrayUtils.indexOf((Object[])ENCHANTMENT_KEYS, resKey);
      if (index < 0)
        return -1; 
      id = Integer.valueOf(index);
      TRANSLATION_KEY_IDS.put(tranKey, id);
    } 
    return id.intValue();
  }
  
  private static akq getResourceKeyByTranslation(String tranKey) {
    String[] parts = Config.tokenize(tranKey, ".");
    if (parts.length != 3)
      return null; 
    String name = parts[2];
    for (int i = 0; i < ENCHANTMENT_KEYS.length; i++) {
      akq<dac> rk = ENCHANTMENT_KEYS[i];
      if (Config.equals(rk.a().a(), name))
        return rk; 
    } 
    return null;
  }
  
  public static int getMaxEnchantmentId() {
    return ENCHANTMENT_KEYS.length;
  }
}
