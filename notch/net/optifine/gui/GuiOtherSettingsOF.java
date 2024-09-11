package notch.net.optifine.gui;

import fgr;
import fgs;
import fhz;
import fik;
import fki;
import fnb;
import fod;
import grr;
import net.optifine.config.Option;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.OptionFullscreenResolution;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;
import wz;

public class GuiOtherSettingsOF extends GuiScreenOF {
  private fod prevScreen;
  
  private fgs settings;
  
  private TooltipManager tooltipManager = new TooltipManager((fod)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiOtherSettingsOF(fod guiscreen, fgs gamesettings) {
    super((wz)wz.b(grr.a("of.options.otherTitle", new Object[0])));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void aT_() {
    p();
    fgr fullscreenResolution = OptionFullscreenResolution.make();
    fgr[] options = { 
        Option.LAGOMETER, Option.PROFILER, this.settings.ATTACK_INDICATOR, Option.ADVANCED_TOOLTIPS, Option.WEATHER, Option.TIME, this.settings.FULLSCREEN, Option.AUTOSAVE_TICKS, Option.SCREENSHOT_SIZE, Option.SHOW_GL_ERRORS, 
        Option.TELEMETRY, null, fullscreenResolution, null };
    for (int i = 0; i < options.length; i++) {
      fgr option = options[i];
      if (option != null) {
        int x = this.m / 2 - 155 + i % 2 * 160;
        int y = this.n / 6 + 21 * i / 2 - 12;
        fik guielement = (fik)c((fki)option.a(this.l.m, x, y, 150));
        guielement.a(null);
        if (option == fullscreenResolution)
          guielement.k(310); 
      } 
    } 
    c((fki)new GuiButtonOF(210, this.m / 2 - 100, this.n / 6 + 168 + 11 - 44, grr.a("of.options.other.reset", new Object[0])));
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
      this.l.aM().h();
      this.l.a(this.prevScreen);
    } 
    if (guibutton.id == 210) {
      this.l.m.aw();
      String msg = grr.a("of.message.other.reset", new Object[0]);
      fnb guiyesno = new fnb(this::confirmResult, (wz)wz.b(msg), (wz)wz.b(""));
      this.l.a((fod)guiyesno);
    } 
  }
  
  public void j() {
    this.l.m.aw();
    this.l.aM().h();
    super.j();
  }
  
  public void confirmResult(boolean flag) {
    if (flag)
      this.l.m.resetSettings(); 
    this.l.a((fod)this);
  }
  
  public void a(fhz graphicsIn, int x, int y, float partialTicks) {
    super.a(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.fontRenderer, this.k, this.m / 2, 15, 16777215);
    this.tooltipManager.drawTooltips(graphicsIn, x, y, getButtonList());
  }
}
