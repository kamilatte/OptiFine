package notch.net.optifine.gui;

import fgo;
import fhx;
import fhz;
import fik;
import fod;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipProvider;

public class TooltipManager {
  private fod guiScreen;
  
  private TooltipProvider tooltipProvider;
  
  private int lastMouseX = 0;
  
  private int lastMouseY = 0;
  
  private long mouseStillTime = 0L;
  
  public TooltipManager(fod guiScreen, TooltipProvider tooltipProvider) {
    this.guiScreen = guiScreen;
    this.tooltipProvider = tooltipProvider;
  }
  
  public void drawTooltips(fhz graphicsIn, int x, int y, List<fik> buttonList) {
    if (Math.abs(x - this.lastMouseX) > 5 || Math.abs(y - this.lastMouseY) > 5) {
      this.lastMouseX = x;
      this.lastMouseY = y;
      this.mouseStillTime = System.currentTimeMillis();
      return;
    } 
    int activateDelay = 700;
    if (System.currentTimeMillis() < this.mouseStillTime + activateDelay)
      return; 
    fik btn = GuiScreenOF.getSelectedButton(x, y, buttonList);
    if (btn == null)
      return; 
    Rectangle rect = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
    String[] lines = this.tooltipProvider.getTooltipLines(btn, rect.width);
    if (lines == null)
      return; 
    if (lines.length > 8) {
      lines = Arrays.<String>copyOf(lines, 8);
      lines[lines.length - 1] = lines[lines.length - 1] + " ...";
    } 
    graphicsIn.c().a();
    graphicsIn.c().a(0.0F, 0.0F, 400.0F);
    if (this.tooltipProvider.isRenderBorder()) {
      int colBorder = -528449408;
      drawRectBorder(graphicsIn, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, colBorder);
    } 
    graphicsIn.a(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, -536870912);
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      int col = 14540253;
      if (line.endsWith("!"))
        col = 16719904; 
      fhx fontRenderer = (fgo.Q()).h;
      graphicsIn.a(fontRenderer, line, rect.x + 5, rect.y + 5 + i * 11, col, true);
    } 
    graphicsIn.c().b();
  }
  
  private void drawRectBorder(fhz graphicsIn, int x1, int y1, int x2, int y2, int col) {
    graphicsIn.a(x1, y1 - 1, x2, y1, col);
    graphicsIn.a(x1, y2, x2, y2 + 1, col);
    graphicsIn.a(x1 - 1, y1, x1, y2, col);
    graphicsIn.a(x2, y1, x2 + 1, y2, col);
  }
}
