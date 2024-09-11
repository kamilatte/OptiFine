package srg.net.optifine.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.optifine.gui.IOptionControl;
import net.optifine.util.GuiUtils;

public class GuiScreenOF extends Screen {
  protected Font fontRenderer = (Minecraft.getInstance()).font;
  
  protected boolean mousePressed = false;
  
  protected Options settings = (Minecraft.getInstance()).options;
  
  public GuiScreenOF(Component title) {
    super(title);
  }
  
  public List<AbstractWidget> getButtonList() {
    List<AbstractWidget> buttons = new ArrayList<>();
    for (GuiEventListener gel : children()) {
      if (gel instanceof AbstractWidget)
        buttons.add((AbstractWidget)gel); 
    } 
    return buttons;
  }
  
  protected void actionPerformed(AbstractWidget button) {}
  
  protected void actionPerformedRightClick(AbstractWidget button) {}
  
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    boolean ret = super.mouseClicked(mouseX, mouseY, mouseButton);
    this.mousePressed = true;
    AbstractWidget btn = getSelectedButton((int)mouseX, (int)mouseY, getButtonList());
    if (btn != null)
      if (btn.active) {
        if (mouseButton == 1 && btn instanceof IOptionControl) {
          IOptionControl ioc = (IOptionControl)btn;
          if (ioc.getControlOption() == this.settings.GUI_SCALE)
            btn.playDownSound(this.minecraft.getSoundManager()); 
        } 
        if (mouseButton == 0) {
          actionPerformed(btn);
        } else if (mouseButton == 1) {
          actionPerformedRightClick(btn);
        } 
        return true;
      }  
    return ret;
  }
  
  public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
    boolean ret = super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    AbstractWidget btn = getSelectedButton((int)mouseX, (int)mouseY, getButtonList());
    if (btn != null && btn.active && btn instanceof IOptionControl) {
      actionPerformed(btn);
      return true;
    } 
    return ret;
  }
  
  public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
    if (!this.mousePressed)
      return false; 
    this.mousePressed = false;
    setDragging(false);
    if (getFocused() != null)
      if (getFocused().mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_))
        return true;  
    return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
  }
  
  public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
    if (!this.mousePressed)
      return false; 
    return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
  }
  
  public static AbstractWidget getSelectedButton(int x, int y, List<AbstractWidget> listButtons) {
    for (int i = 0; i < listButtons.size(); i++) {
      AbstractWidget btn = listButtons.get(i);
      if (btn.visible) {
        int btnWidth = GuiUtils.getWidth(btn);
        int btnHeight = GuiUtils.getHeight(btn);
        if (x >= btn.getX() && y >= btn.getY() && x < btn.getX() + btnWidth && y < btn.getY() + btnHeight)
          return btn; 
      } 
    } 
    return null;
  }
  
  public static void drawString(GuiGraphics graphicsIn, Font fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.drawCenteredString(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, FormattedCharSequence textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.drawCenteredString(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.drawCenteredString(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, Component textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.drawCenteredString(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
}
