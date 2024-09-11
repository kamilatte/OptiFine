package srg.net.optifine.util;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.KeyMapping;
import net.optifine.reflect.Reflector;

public class KeyUtils {
  public static void fixKeyConflicts(KeyMapping[] keys, KeyMapping[] keysPrio) {
    Set<String> keyPrioNames = new HashSet<>();
    for (int i = 0; i < keysPrio.length; i++) {
      KeyMapping keyPrio = keysPrio[i];
      keyPrioNames.add(getId(keyPrio));
    } 
    Set<KeyMapping> setKeys = new HashSet<>(Arrays.asList(keys));
    setKeys.removeAll(Arrays.asList((Object[])keysPrio));
    for (Iterator<KeyMapping> iterator = setKeys.iterator(); iterator.hasNext(); ) {
      KeyMapping key = iterator.next();
      String name = getId(key);
      if (keyPrioNames.contains(name))
        key.setKey(InputConstants.UNKNOWN); 
    } 
  }
  
  public static String getId(KeyMapping keyMapping) {
    if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
      Object keyModifier = Reflector.call(keyMapping, Reflector.ForgeKeyBinding_getKeyModifier, new Object[0]);
      Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
      if (keyModifier != keyModifierNone)
        return String.valueOf(keyModifier) + "+" + String.valueOf(keyModifier); 
    } 
    return keyMapping.saveString();
  }
}
