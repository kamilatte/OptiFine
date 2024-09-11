package srg.net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.PropertiesOrdered;

public class FontUtils {
  public static Properties readFontProperties(ResourceLocation locationFontTexture) {
    String fontFileName = locationFontTexture.getPath();
    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
    String suffix = ".png";
    if (!fontFileName.endsWith(suffix))
      return (Properties)propertiesOrdered; 
    String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";
    try {
      ResourceLocation locProp = new ResourceLocation(locationFontTexture.getNamespace(), fileName);
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
  
  public static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
    if (!Config.isCustomFonts())
      return fontLoc; 
    if (fontLoc == null)
      return fontLoc; 
    if (!Config.isMinecraftThread())
      return fontLoc; 
    String fontName = fontLoc.getPath();
    String texturesStr = "textures/";
    String optifineStr = "optifine/";
    if (!fontName.startsWith(texturesStr))
      return fontLoc; 
    fontName = fontName.substring(texturesStr.length());
    fontName = optifineStr + optifineStr;
    ResourceLocation fontLocHD = new ResourceLocation(fontLoc.getNamespace(), fontName);
    if (Config.hasResource(Config.getResourceManager(), fontLocHD))
      return fontLocHD; 
    return fontLoc;
  }
  
  public static void reloadFonts() {
    Object object = new Object();
    Executor ex = Util.backgroundExecutor();
    Minecraft mc = Minecraft.getInstance();
    FontManager frm = (FontManager)Reflector.getFieldValue(mc, Reflector.Minecraft_fontResourceManager);
    if (frm == null)
      return; 
    frm.reload((PreparableReloadListener.PreparationBarrier)object, Config.getResourceManager(), (ProfilerFiller)InactiveProfiler.INSTANCE, (ProfilerFiller)InactiveProfiler.INSTANCE, ex, (Executor)mc);
  }
}
