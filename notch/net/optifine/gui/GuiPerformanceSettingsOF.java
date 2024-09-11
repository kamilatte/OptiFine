package notch.net.optifine.gui;

import fgr;
import fgs;
import fhz;
import fik;
import fki;
import fod;
import grr;
import net.optifine.config.Option;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;
import wz;

public class GuiPerformanceSettingsOF extends GuiScreenOF {
  private fod prevScreen;
  
  private fgs settings;
  
  private TooltipManager tooltipManager = new TooltipManager((fod)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiPerformanceSettingsOF(fod guiscreen, fgs gamesettings) {
    super((wz)wz.b(grr.a("of.options.performanceTitle", new Object[0])));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void aT_() {
    p();
    fgr[] options = { Option.RENDER_REGIONS, Option.FAST_RENDER, Option.SMART_ANIMATIONS, Option.FAST_MATH, Option.SMOOTH_FPS, Option.SMOOTH_WORLD, Option.CHUNK_UPDATES, Option.CHUNK_UPDATES_DYNAMIC, Option.LAZY_CHUNK_LOADING, this.settings.PRIORITIZE_CHUNK_UPDATES };
    for (int i = 0; i < options.length; i++) {
      fgr option = options[i];
      int x = this.m / 2 - 155 + i % 2 * 160;
      int y = this.n / 6 + 21 * i / 2 - 12;
      fik guielement = (fik)c((fki)option.a(this.l.m, x, y, 150));
      guielement.a(null);
    } 
    c((fki)new GuiButtonOF(200, this.m / 2 - 100, this.n / 6 + 168 + 11, grr.a("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(fik guiElement) {
    if (!(guiElement instanceof GuiButtonOF))
      return; 
    GuiButtonOF guibutton = (GuiButtonOF)guiElement;
    if (!guibutton.j)
      return; 
    if (guibutton.id == 200) {
      this.l.m.aw();
      this.l.a(this.prevScreen);
    } 
  }
  
  public void j() {
    this.l.m.aw();
    super.j();
  }
  
  public void a(fhz graphicsIn, int x, int y, float partialTicks) {
    super.a(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.fontRenderer, this.k, this.m / 2, 15, 16777215);
    this.tooltipManager.drawTooltips(graphicsIn, x, y, getButtonList());
  }
}
