package notch.net.optifine.util;

import ad;
import akr;
import aty;
import bnc;
import bnf;
import fgo;
import flb;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.PropertiesOrdered;

public class FontUtils {
  public static Properties readFontProperties(akr locationFontTexture) {
    String fontFileName = locationFontTexture.a();
    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
    String suffix = ".png";
    if (!fontFileName.endsWith(suffix))
      return (Properties)propertiesOrdered; 
    String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";
    try {
      akr locProp = new akr(locationFontTexture.b(), fileName);
      InputStream in = Config.getResourceStream(Config.getResourceManager(), locProp);
      if (in == null)
        return (Properties)propertiesOrdered; 
      Config.log("Loading " + fileName);
      propertiesOrdered.load(in);
      in.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return (Properties)propertiesOrdered;
  }
  
  public static Int2ObjectMap<Float> readCustomCharWidths(Properties props) {
    Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
    Set<Object> keySet = props.keySet();
    for (Iterator<String> iter = keySet.iterator(); iter.hasNext(); ) {
      String key = iter.next();
      String prefix = "width.";
      if (key.startsWith(prefix)) {
        String numStr = key.substring(prefix.length());
        int num = Config.parseInt(numStr, -1);
        if (num >= 0) {
          String value = props.getProperty(key);
          float width = Config.parseFloat(value, -1.0F);
          if (width >= 0.0F) {
            char ch = (char)num;
            int2ObjectOpenHashMap.put(ch, new Float(width));
          } 
        } 
      } 
    } 
    return (Int2ObjectMap<Float>)int2ObjectOpenHashMap;
  }
  
  public static float readFloat(Properties props, String key, float defOffset) {
    String str = props.getProperty(key);
    if (str == null)
      return defOffset; 
    float offset = Config.parseFloat(str, Float.MIN_VALUE);
    if (offset == Float.MIN_VALUE) {
      Config.warn("Invalid value for " + key + ": " + str);
      return defOffset;
    } 
    return offset;
  }
  
  public static boolean readBoolean(Properties props, String key, boolean defVal) {
    String str = props.getProperty(key);
    if (str == null)
      return defVal; 
    String strLow = str.toLowerCase().trim();
    if (strLow.equals("true") || strLow.equals("on"))
      return true; 
    if (strLow.equals("false") || strLow.equals("off"))
      return false; 
    Config.warn("Invalid value for " + key + ": " + str);
    return defVal;
  }
  
  public static akr getHdFontLocation(akr fontLoc) {
    if (!Config.isCustomFonts())
      return fontLoc; 
    if (fontLoc == null)
      return fontLoc; 
    if (!Config.isMinecraftThread())
      return fontLoc; 
    String fontName = fontLoc.a();
    String texturesStr = "textures/";
    String optifineStr = "optifine/";
    if (!fontName.startsWith(texturesStr))
      return fontLoc; 
    fontName = fontName.substring(texturesStr.length());
    fontName = optifineStr + optifineStr;
    akr fontLocHD = new akr(fontLoc.b(), fontName);
    if (Config.hasResource(Config.getResourceManager(), fontLocHD))
      return fontLocHD; 
    return fontLoc;
  }
  
  public static void reloadFonts() {
    Object object = new Object();
    Executor ex = ad.g();
    fgo mc = fgo.Q();
    flb frm = (flb)Reflector.getFieldValue(mc, Reflector.Minecraft_fontResourceManager);
    if (frm == null)
      return; 
    frm.a((aty.a)object, Config.getResourceManager(), (bnf)bnc.a, (bnf)bnc.a, ex, (Executor)mc);
  }
}
