package notch.net.optifine.util;

import fae;
import fgm;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.optifine.reflect.Reflector;

public class KeyUtils {
  public static void fixKeyConflicts(fgm[] keys, fgm[] keysPrio) {
    Set<String> keyPrioNames = new HashSet<>();
    for (int i = 0; i < keysPrio.length; i++) {
      fgm keyPrio = keysPrio[i];
      keyPrioNames.add(getId(keyPrio));
    } 
    Set<fgm> setKeys = new HashSet<>(Arrays.asList(keys));
    setKeys.removeAll(Arrays.asList((Object[])keysPrio));
    for (Iterator<fgm> iterator = setKeys.iterator(); iterator.hasNext(); ) {
      fgm key = iterator.next();
      String name = getId(key);
      if (keyPrioNames.contains(name))
        key.b(fae.bv); 
    } 
  }
  
  public static String getId(fgm keyMapping) {
    if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
      Object keyModifier = Reflector.call(keyMapping, Reflector.ForgeKeyBinding_getKeyModifier, new Object[0]);
      Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
      if (keyModifier != keyModifierNone)
        return String.valueOf(keyModifier) + "+" + String.valueOf(keyModifier); 
    } 
    return keyMapping.m();
  }
}
