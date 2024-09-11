package srg.net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public interface TooltipProvider {
  Rectangle getTooltipBounds(Screen paramScreen, int paramInt1, int paramInt2);
  
  String[] getTooltipLines(AbstractWidget paramAbstractWidget, int paramInt);
  
  boolean isRenderBorder();
}
