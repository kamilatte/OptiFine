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

public class EntityAliases {
  private static int[] entityAliases = null;
  
  private static boolean updateOnResourcesReloaded;
  
  public static int getEntityAliasId(int entityId) {
    if (entityAliases == null)
      return -1; 
    if (entityId < 0 || entityId >= entityAliases.length)
      return -1; 
    int aliasId = entityAliases[entityId];
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
      Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
      updateOnResourcesReloaded = true;
      return;
    } 
    List<Integer> listEntityAliases = new ArrayList<>();
    String path = "/shaders/entity.properties";
    InputStream in = shaderPack.getResourceAsStream(path);
    if (in != null)
      loadEntityAliases(in, path, listEntityAliases); 
    loadModEntityAliases(listEntityAliases);
    if (listEntityAliases.size() <= 0)
      return; 
    entityAliases = toArray(listEntityAliases);
  }
  
  private static void loadModEntityAliases(List<Integer> listEntityAliases) {
    String[] modIds = ReflectorForge.getForgeModIds();
    for (int i = 0; i < modIds.length; i++) {
      String modId = modIds[i];
      try {
        akr loc = new akr(modId, "shaders/entity.properties");
        InputStream in = Config.getResourceStream(loc);
        loadEntityAliases(in, loc.toString(), listEntityAliases);
      } catch (IOException iOException) {}
    } 
  }
  
  private static void loadEntityAliases(InputStream in, String path, List<Integer> listEntityAliases) {
    if (in == null)
      return; 
    try {
      in = MacroProcessor.process(in, path, true);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      Config.dbg("[Shaders] Parsing entity mappings: " + path);
      ConnectedParser cp = new ConnectedParser("Shaders");
      Set<Object> keys = propertiesOrdered.keySet();
      for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
        String key = it.next();
        String val = propertiesOrdered.getProperty(key);
        String prefix = "entity.";
        if (!key.startsWith(prefix)) {
          Config.warn("[Shaders] Invalid entity ID: " + key);
          continue;
        } 
        String aliasIdStr = StrUtils.removePrefix(key, prefix);
        int aliasId = Config.parseInt(aliasIdStr, -1);
        if (aliasId < 0) {
          Config.warn("[Shaders] Invalid entity alias ID: " + aliasId);
          continue;
        } 
        int[] entityIds = cp.parseEntities(val);
        if (entityIds == null || entityIds.length < 1) {
          Config.warn("[Shaders] Invalid entity ID mapping: " + key + "=" + val);
          continue;
        } 
        for (int i = 0; i < entityIds.length; i++) {
          int entityId = entityIds[i];
          addToList(listEntityAliases, entityId, aliasId);
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
    entityAliases = null;
  }
}
