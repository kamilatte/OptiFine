package srg.net.optifine.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.optifine.Lang;
import net.optifine.gui.IOptionControl;
import net.optifine.gui.TooltipProvider;

public class TooltipProviderOptions implements TooltipProvider {
  public Rectangle getTooltipBounds(Screen guiScreen, int x, int y) {
    int x1 = guiScreen.width / 2 - 150;
    int y1 = guiScreen.height / 6 - 7;
    if (y <= y1 + 98)
      y1 += 105; 
    int x2 = x1 + 150 + 150;
    int y2 = y1 + 84 + 10;
    return new Rectangle(x1, y1, x2 - x1, y2 - y1);
  }
  
  public boolean isRenderBorder() {
    return false;
  }
  
  public String[] getTooltipLines(AbstractWidget btn, int width) {
    if (!(btn instanceof IOptionControl))
      return null; 
    IOptionControl ctl = (IOptionControl)btn;
    OptionInstance option = ctl.getControlOption();
    if (option == null)
      return null; 
    String[] lines = getTooltipLines(option.getResourceKey());
    return lines;
  }
  
  public static String[] getTooltipLines(String key) {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String lineKey = key + ".tooltip." + key;
      String line = Lang.get(lineKey, null);
      if (line == null)
        break; 
      list.add(line);
    } 
    if (list.size() <= 0)
      return null; 
    String[] lines = list.<String>toArray(new String[list.size()]);
    return lines;
  }
}
