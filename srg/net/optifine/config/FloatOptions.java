package srg.net.optifine.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.config.Option;

public class FloatOptions {
  public static Component getTextComponent(OptionInstance option, double val) {
    Options settings = (Minecraft.getInstance()).options;
    String s = I18n.get(option.getResourceKey(), new Object[0]) + ": ";
    if (option == settings.RENDER_DISTANCE)
      return Options.genericValueLabel(option.getResourceKey(), "options.chunks", (int)val); 
    if (option == settings.MIPMAP_LEVELS) {
      if (val >= 4.0D)
        return Options.genericValueLabel(option.getResourceKey(), "of.general.max"); 
      return (val == 0.0D) ? (Component)CommonComponents.optionStatus(option.getCaption(), false) : Options.genericValueLabel(option.getResourceKey(), (int)val);
    } 
    if (option == settings.BIOME_BLEND_RADIUS) {
      int i = (int)val * 2 + 1;
      return Options.genericValueLabel(option.getResourceKey(), "options.biomeBlendRadius." + i);
    } 
    String text = getText(option, val);
    if (text != null)
      return (Component)Component.literal(text); 
    return null;
  }
  
  public static String getText(OptionInstance option, double val) {
    String s = I18n.get(option.getResourceKey(), new Object[0]) + ": ";
    if (option == Option.AO_LEVEL) {
      if (val == 0.0D)
        return s + s; 
      return s + s + "%";
    } 
    if (option == Option.MIPMAP_TYPE) {
      int valInt = (int)val;
      switch (valInt) {
        case 0:
          return s + s;
        case 1:
          return s + s;
        case 2:
          return s + s;
        case 3:
          return s + s;
      } 
      return s + "of.options.mipmap.nearest";
    } 
    if (option == Option.AA_LEVEL) {
      int ofAaLevel = (int)val;
      String suffix = "";
      if (ofAaLevel != Config.getAntialiasingLevel())
        suffix = " (" + Lang.get("of.general.restart") + ")"; 
      if (ofAaLevel == 0)
        return s + s + Lang.getOff(); 
      return s + s + ofAaLevel;
    } 
    if (option == Option.AF_LEVEL) {
      int ofAfLevel = (int)val;
      if (ofAfLevel == 1)
        return s + s; 
      return s + s;
    } 
    return null;
  }
  
  public static boolean supportAdjusting(OptionInstance option) {
    Component text = getTextComponent(option, 0.0D);
    return (text != null);
  }
}
