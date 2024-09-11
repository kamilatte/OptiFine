package notch.net.optifine.gui;

import aya;
import com.google.common.collect.Lists;
import fhz;
import fik;
import fim;
import fki;
import fod;
import grr;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.optifine.Config;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import wz;
import xe;

public class GuiMessage extends GuiScreenOF {
  private fod parentScreen;
  
  private wz messageLine1;
  
  private wz messageLine2;
  
  private final List<aya> listLines2 = Lists.newArrayList();
  
  protected String confirmButtonText;
  
  private int ticksUntilEnable;
  
  public GuiMessage(fod parentScreen, String line1, String line2) {
    super((wz)wz.c("of.options.detailsTitle"));
    this.parentScreen = parentScreen;
    this.messageLine1 = (wz)wz.b(line1);
    this.messageLine2 = (wz)wz.b(line2);
    this.confirmButtonText = grr.a("gui.done", new Object[0]);
  }
  
  public void aT_() {
    c((fki)new GuiButtonOF(0, this.m / 2 - 100, this.n / 6 + 96, this.confirmButtonText));
    this.listLines2.clear();
    this.listLines2.addAll(this.l.h.c((xe)this.messageLine2, this.m - 50));
  }
  
  protected void actionPerformed(fik button) {
    Config.getMinecraft().a(this.parentScreen);
  }
  
  public void a(fhz graphicsIn, int mouseX, int mouseY, float partialTicks) {
    super.a(graphicsIn, mouseX, mouseY, partialTicks);
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, this.messageLine1, this.m / 2, 70, 16777215);
    int var4 = 90;
    for (Iterator<aya> var5 = this.listLines2.iterator(); var5.hasNext(); Objects.requireNonNull(this.fontRenderer), var4 += 9) {
      aya line = var5.next();
      this;
      drawCenteredString(graphicsIn, this.fontRenderer, line, this.m / 2, var4, 16777215);
    } 
  }
  
  public void setButtonDelay(int ticksUntilEnable) {
    this.ticksUntilEnable = ticksUntilEnable;
    for (Iterator<fim> var2 = getButtonList().iterator(); var2.hasNext(); var3.j = false)
      fim var3 = var2.next(); 
  }
  
  public void e() {
    super.e();
    if (--this.ticksUntilEnable == 0)
      for (Iterator<fim> var1 = getButtonList().iterator(); var1.hasNext(); var2.j = true)
        fim var2 = var1.next();  
  }
}
