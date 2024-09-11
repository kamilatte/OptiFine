package srg.net.optifine.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ComponentUtils {
  public static String getTranslationKey(Component comp) {
    if (comp == null)
      return null; 
    ComponentContents cont = comp.getContents();
    if (!(cont instanceof TranslatableContents))
      return null; 
    TranslatableContents tran = (TranslatableContents)cont;
    return tran.getKey();
  }
}
