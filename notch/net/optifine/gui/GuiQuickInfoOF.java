package notch.net.optifine.gui;

import fgr;
import fhz;
import fik;
import fki;
import fod;
import grr;
import net.optifine.Lang;
import net.optifine.config.Option;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.IOptionControl;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;
import wz;

public class GuiQuickInfoOF extends GuiScreenOF {
  private fod prevScreen;
  
  private TooltipManager tooltipManager = new TooltipManager((fod)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiQuickInfoOF(fod guiscreen) {
    super((wz)wz.c("of.options.quickInfoTitle"));
    this.prevScreen = guiscreen;
  }
  
  public void aT_() {
    p();
    fgr[] options = { 
        Option.QUICK_INFO, Option.QUICK_INFO_FPS, Option.QUICK_INFO_CHUNKS, Option.QUICK_INFO_ENTITIES, Option.QUICK_INFO_PARTICLES, Option.QUICK_INFO_UPDATES, Option.QUICK_INFO_GPU, Option.QUICK_INFO_POS, Option.QUICK_INFO_BIOME, Option.QUICK_INFO_FACING, 
        Option.QUICK_INFO_LIGHT, Option.QUICK_INFO_MEMORY, Option.QUICK_INFO_NATIVE_MEMORY, Option.QUICK_INFO_TARGET_BLOCK, Option.QUICK_INFO_TARGET_FLUID, Option.QUICK_INFO_TARGET_ENTITY, Option.QUICK_INFO_LABELS, Option.QUICK_INFO_BACKGROUND };
    for (int i = 0; i < options.length; i++) {
      fgr opt = options[i];
      if (opt != null) {
        int x = this.m / 2 - 155 + i % 2 * 160;
        int y = this.n / 6 + 21 * i / 2 - 12;
        fik guielement = (fik)c((fki)opt.a(this.l.m, x, y, 150));
        guielement.a(null);
      } 
    } 
    c((fki)new GuiButtonOF(210, this.m / 2 - 155, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
    c((fki)new GuiButtonOF(211, this.m / 2 - 155 + 80, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
    c((fki)new GuiScreenButtonOF(200, this.m / 2 + 5, this.n / 6 + 168 + 11, grr.a("gui.done", new Object[0])));
    updateSubOptions();
  }
  
  protected void actionPerformed(fik guiElement) {
    updateSubOptions();
    if (!(guiElement instanceof GuiButtonOF))
      return; 
    GuiButtonOF guibutton = (GuiButtonOF)guiElement;
    if (!guibutton.j)
      return; 
    if (guibutton.id == 200) {
      this.l.m.aw();
      this.l.a(this.prevScreen);
    } 
    if (guibutton.id == 210)
      this.l.m.setAllQuickInfos(true); 
    if (guibutton.id == 211)
      this.l.m.setAllQuickInfos(false); 
    this.l.a();
  }
  
  private void updateSubOptions() {
    boolean enabled = this.settings.ofQuickInfo;
    for (fik aw : getButtonList()) {
      if (!(aw instanceof IOptionControl))
        continue; 
      IOptionControl oc = (IOptionControl)aw;
      if (oc.getControlOption() == Option.QUICK_INFO)
        continue; 
      aw.j = enabled;
    } 
  }
  
  public void j() {
    this.l.m.aw();
    super.j();
  }
  
  public void a(fhz graphicsIn, int x, int y, float partialTicks) {
    super.a(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.l.h, this.k, this.m / 2, 15, 16777215);
    this.tooltipManager.drawTooltips(graphicsIn, x, y, getButtonList());
  }
}
