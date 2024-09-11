package notch.net.optifine.gui;

import fgs;
import fhx;
import fik;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;
import wz;
import xe;
import xn;
import xw;

public class TooltipProviderShaderOptions extends TooltipProviderOptions {
  public String[] getTooltipLines(fik btn, int width) {
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
    fgs settings = Config.getGameSettings();
    String id = null;
    if (!name.equals(so.getName()) && settings.m)
      id = "§8" + Lang.get("of.general.id") + ": " + so.getName(); 
    String source = null;
    if (so.getPaths() != null && settings.m)
      source = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths()); 
    String def = null;
    if (so.getValueDefault() != null && settings.m) {
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
    fhx fr = (Config.getMinecraft()).h;
    List<String> list = new ArrayList<>();
    for (int i = 0; i < args.size(); i++) {
      String arg = args.get(i);
      if (arg != null && arg.length() > 0) {
        xn xn = wz.b(arg);
        List<xe> parts = fr.b().b((xe)xn, width, xw.a);
        for (Iterator<xe> it = parts.iterator(); it.hasNext(); ) {
          xe part = it.next();
          list.add(part.getString());
        } 
      } 
    } 
    String[] lines = list.<String>toArray(new String[list.size()]);
    return lines;
  }
}
