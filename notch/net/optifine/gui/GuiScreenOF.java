package notch.net.optifine.gui;

import aya;
import fgo;
import fgs;
import fhx;
import fhz;
import fik;
import fki;
import fod;
import java.util.ArrayList;
import java.util.List;
import net.optifine.gui.IOptionControl;
import net.optifine.util.GuiUtils;
import wz;

public class GuiScreenOF extends fod {
  protected fhx fontRenderer = (fgo.Q()).h;
  
  protected boolean mousePressed = false;
  
  protected fgs settings = (fgo.Q()).m;
  
  public GuiScreenOF(wz title) {
    super(title);
  }
  
  public List<fik> getButtonList() {
    List<fik> buttons = new ArrayList<>();
    for (fki gel : aK_()) {
      if (gel instanceof fik)
        buttons.add((fik)gel); 
    } 
    return buttons;
  }
  
  protected void actionPerformed(fik button) {}
  
  protected void actionPerformedRightClick(fik button) {}
  
  public boolean a(double mouseX, double mouseY, int mouseButton) {
    boolean ret = super.a(mouseX, mouseY, mouseButton);
    this.mousePressed = true;
    fik btn = getSelectedButton((int)mouseX, (int)mouseY, getButtonList());
    if (btn != null)
      if (btn.j) {
        if (mouseButton == 1 && btn instanceof IOptionControl) {
          IOptionControl ioc = (IOptionControl)btn;
          if (ioc.getControlOption() == this.settings.GUI_SCALE)
            btn.a(this.l.aj()); 
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
  
  public boolean a(double mouseX, double mouseY, double deltaX, double deltaY) {
    boolean ret = super.a(mouseX, mouseY, deltaX, deltaY);
    fik btn = getSelectedButton((int)mouseX, (int)mouseY, getButtonList());
    if (btn != null && btn.j && btn instanceof IOptionControl) {
      actionPerformed(btn);
      return true;
    } 
    return ret;
  }
  
  public boolean b(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
    if (!this.mousePressed)
      return false; 
    this.mousePressed = false;
    b_(false);
    if (aN_() != null)
      if (aN_().b(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_))
        return true;  
    return super.b(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
  }
  
  public boolean a(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
    if (!this.mousePressed)
      return false; 
    return super.a(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
  }
  
  public static fik getSelectedButton(int x, int y, List<fik> listButtons) {
    for (int i = 0; i < listButtons.size(); i++) {
      fik btn = listButtons.get(i);
      if (btn.k) {
        int btnWidth = GuiUtils.getWidth(btn);
        int btnHeight = GuiUtils.getHeight(btn);
        if (x >= btn.D() && y >= btn.E() && x < btn.D() + btnWidth && y < btn.E() + btnHeight)
          return btn; 
      } 
    } 
    return null;
  }
  
  public static void drawString(fhz graphicsIn, fhx fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.a(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(fhz graphicsIn, fhx fontRendererIn, aya textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.a(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(fhz graphicsIn, fhx fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.a(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
  
  public static void drawCenteredString(fhz graphicsIn, fhx fontRendererIn, wz textIn, int xIn, int yIn, int colorIn) {
    graphicsIn.a(fontRendererIn, textIn, xIn, yIn, colorIn);
  }
}
