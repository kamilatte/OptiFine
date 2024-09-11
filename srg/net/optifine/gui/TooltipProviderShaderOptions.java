package srg.net.optifine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;

public class TooltipProviderShaderOptions extends TooltipProviderOptions {
  public String[] getTooltipLines(AbstractWidget btn, int width) {
    if (!(btn instanceof GuiButtonShaderOption))
      return null; 
    GuiButtonShaderOption btnSo = (GuiButtonShaderOption)btn;
    ShaderOption so = btnSo.getShaderOption();
    String[] lines = makeTooltipLines(so, width);
    return lines;
  }
  
  private String[] makeTooltipLines(ShaderOption so, int width) {
    String name = so.getNameText();
    String desc = Config.normalize(so.getDescriptionText()).trim();
    String[] descs = splitDescription(desc);
    Options settings = Config.getGameSettings();
    String id = null;
    if (!name.equals(so.getName()) && settings.advancedItemTooltips)
      id = "§8" + Lang.get("of.general.id") + ": " + so.getName(); 
    String source = null;
    if (so.getPaths() != null && settings.advancedItemTooltips)
      source = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths()); 
    String def = null;
    if (so.getValueDefault() != null && settings.advancedItemTooltips) {
      String defVal = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
      def = "§8" + Lang.getDefault() + ": " + defVal;
    } 
    List<String> list = new ArrayList<>();
    list.add(name);
    list.addAll(Arrays.asList(descs));
    if (id != null)
      list.add(id); 
    if (source != null)
      list.add(source); 
    if (def != null)
      list.add(def); 
    String[] lines = makeTooltipLines(width, list);
    return lines;
  }
  
  private String[] splitDescription(String desc) {
    if (desc.length() <= 0)
      return new String[0]; 
    desc = StrUtils.removePrefix(desc, "//");
    String[] descs = desc.split("\\. ");
    for (int i = 0; i < descs.length; i++) {
      descs[i] = "- " + descs[i].trim();
      descs[i] = StrUtils.removeSuffix(descs[i], ".");
    } 
    return descs;
  }
  
  private String[] makeTooltipLines(int width, List<String> args) {
    Font fr = (Config.getMinecraft()).font;
    List<String> list = new ArrayList<>();
    for (int i = 0; i < args.size(); i++) {
      String arg = args.get(i);
      if (arg != null && arg.length() > 0) {
        MutableComponent mutableComponent = Component.literal(arg);
        List<FormattedText> parts = fr.getSplitter().splitLines((FormattedText)mutableComponent, width, Style.EMPTY);
        for (Iterator<FormattedText> it = parts.iterator(); it.hasNext(); ) {
          FormattedText part = it.next();
          list.add(part.getString());
        } 
      } 
    } 
    String[] lines = list.<String>toArray(new String[list.size()]);
    return lines;
  }
}
