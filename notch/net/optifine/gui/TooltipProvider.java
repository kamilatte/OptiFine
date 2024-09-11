package notch.net.optifine.gui;

import fik;
import fod;
import java.awt.Rectangle;

public interface TooltipProvider {
  Rectangle getTooltipBounds(fod paramfod, int paramInt1, int paramInt2);
  
  String[] getTooltipLines(fik paramfik, int paramInt);
  
  boolean isRenderBorder();
}
