package notch.net.optifine;

import akr;
import asq;
import ass;
import atw;
import auc;
import aue;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import grr;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.optifine.Config;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import wz;
import xn;

public class Lang {
  private static final Splitter splitter = Splitter.on('=').limit(2);
  
  private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
  
  public static void resourcesReloaded() {
    Map<Object, Object> localeProperties = new HashMap<>();
    List<String> listFiles = new ArrayList<>();
    String PREFIX = "optifine/lang/";
    String EN_US = "en_us";
    String SUFFIX = ".lang";
    listFiles.add(PREFIX + PREFIX + EN_US);
    if (!(Config.getGameSettings()).ac.equals(EN_US))
      listFiles.add(PREFIX + PREFIX + (Config.getGameSettings()).ac); 
    String[] files = listFiles.<String>toArray(new String[listFiles.size()]);
    loadResources((asq)Config.getDefaultResourcePack(), files, localeProperties);
    asq[] resourcePacks = Config.getResourcePacks();
    for (int i = 0; i < resourcePacks.length; i++) {
      asq rp = resourcePacks[i];
      loadResources(rp, files, localeProperties);
    } 
  }
  
  private static void loadResources(asq rp, String[] files, Map localeProperties) {
    try {
      for (int i = 0; i < files.length; i++) {
        String file = files[i];
        akr loc = new akr(file);
        atw<InputStream> supplier = rp.a(ass.a, loc);
        if (supplier != null) {
          InputStream in = (InputStream)supplier.get();
          if (in != null)
            loadLocaleData(in, localeProperties); 
        } 
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void loadLocaleData(InputStream is, Map<String, String> localeProperties) throws IOException {
    Iterator<String> it = IOUtils.readLines(is, Charsets.UTF_8).iterator();
    is.close();
    while (it.hasNext()) {
      String line = it.next();
      if (!line.isEmpty() && line.charAt(0) != '#') {
        String[] parts = (String[])Iterables.toArray(splitter.split(line), String.class);
        if (parts != null && parts.length == 2) {
          String key = parts[0];
          String value = pattern.matcher(parts[1]).replaceAll("%$1s");
          localeProperties.put(key, value);
        } 
      } 
    } 
  }
  
  public static void loadResources(aue resourceManager, String langCode, Map<String, String> map) {
    try {
      String pathLang = "optifine/lang/" + langCode + ".lang";
      akr locLang = new akr(pathLang);
      auc res = resourceManager.getResourceOrThrow(locLang);
      InputStream is = res.d();
      loadLocaleData(is, map);
    } catch (IOException iOException) {}
  }
  
  public static String get(String key) {
    return grr.a(key, new Object[0]);
  }
  
  public static xn getComponent(String key) {
    return wz.c(key);
  }
  
  public static String get(String key, String def) {
    String str = grr.a(key, new Object[0]);
    if (str == null || str.equals(key))
      return def; 
    return str;
  }
  
  public static String getOn() {
    return grr.a("options.on", new Object[0]);
  }
  
  public static String getOff() {
    return grr.a("options.off", new Object[0]);
  }
  
  public static String getFast() {
    return grr.a("options.graphics.fast", new Object[0]);
  }
  
  public static String getFancy() {
    return grr.a("options.graphics.fancy", new Object[0]);
  }
  
  public static String getDefault() {
    return grr.a("generator.minecraft.normal", new Object[0]);
  }
}
