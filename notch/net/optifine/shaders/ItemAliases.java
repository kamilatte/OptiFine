package notch.net.optifine.shaders;

import akr;
import fgo;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ItemAliases {
  private static int[] itemAliases = null;
  
  private static boolean updateOnResourcesReloaded;
  
  public static int getItemAliasId(int itemId) {
    if (itemAliases == null)
      return -1; 
    if (itemId < 0 || itemId >= itemAliases.length)
      return -1; 
    int aliasId = itemAliases[itemId];
    return aliasId;
  }
  
  public static void resourcesReloaded() {
    if (!updateOnResourcesReloaded)
      return; 
    updateOnResourcesReloaded = false;
    update(Shaders.getShaderPack());
  }
  
  public static void update(IShaderPack shaderPack) {
    reset();
    if (shaderPack == null)
      return; 
    if (Reflector.ModList.exists() && fgo.Q().ab() == null) {
      Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
      updateOnResourcesReloaded = true;
      return;
    } 
    List<Integer> listItemAliases = new ArrayList<>();
    String path = "/shaders/item.properties";
    InputStream in = shaderPack.getResourceAsStream(path);
    if (in != null)
      loadItemAliases(in, path, listItemAliases); 
    loadModItemAliases(listItemAliases);
    if (listItemAliases.size() <= 0)
      return; 
    itemAliases = toArray(listItemAliases);
  }
  
  private static void loadModItemAliases(List<Integer> listItemAliases) {
    String[] modIds = ReflectorForge.getForgeModIds();
    for (int i = 0; i < modIds.length; i++) {
      String modId = modIds[i];
      try {
        akr loc = new akr(modId, "shaders/item.properties");
        InputStream in = Config.getResourceStream(loc);
        loadItemAliases(in, loc.toString(), listItemAliases);
      } catch (IOException iOException) {}
    } 
  }
  
  private static void loadItemAliases(InputStream in, String path, List<Integer> listItemAliases) {
    if (in == null)
      return; 
    try {
      in = MacroProcessor.process(in, path, true);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      Config.dbg("[Shaders] Parsing item mappings: " + path);
      ConnectedParser cp = new ConnectedParser("Shaders");
      Set<Object> keys = propertiesOrdered.keySet();
      for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
        String key = it.next();
        String val = propertiesOrdered.getProperty(key);
        String prefix = "item.";
        if (!key.startsWith(prefix)) {
          Config.warn("[Shaders] Invalid item ID: " + key);
          continue;
        } 
        String aliasIdStr = StrUtils.removePrefix(key, prefix);
        int aliasId = Config.parseInt(aliasIdStr, -1);
        if (aliasId < 0) {
          Config.warn("[Shaders] Invalid item alias ID: " + aliasId);
          continue;
        } 
        int[] itemIds = cp.parseItems(val);
        if (itemIds == null || itemIds.length < 1) {
          Config.warn("[Shaders] Invalid item ID mapping: " + key + "=" + val);
          continue;
        } 
        for (int i = 0; i < itemIds.length; i++) {
          int itemId = itemIds[i];
          addToList(listItemAliases, itemId, aliasId);
        } 
      } 
    } catch (IOException e) {
      Config.warn("[Shaders] Error reading: " + path);
    } 
  }
  
  private static void addToList(List<Integer> list, int index, int val) {
    while (list.size() <= index)
      list.add(Integer.valueOf(-1)); 
    list.set(index, Integer.valueOf(val));
  }
  
  private static int[] toArray(List<Integer> list) {
    int[] arr = new int[list.size()];
    for (int i = 0; i < arr.length; i++)
      arr[i] = ((Integer)list.get(i)).intValue(); 
    return arr;
  }
  
  public static void reset() {
    itemAliases = null;
  }
}
