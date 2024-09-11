package notch.net.optifine.gui;

import fgr;
import fgs;
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
import wz;

public class GuiAnimationSettingsOF extends GuiScreenOF {
  private fod prevScreen;
  
  private fgs settings;
  
  public GuiAnimationSettingsOF(fod guiscreen, fgs gamesettings) {
    super((wz)wz.c("of.options.animationsTitle"));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void aT_() {
    p();
    fgr[] options = { 
        Option.ANIMATED_WATER, Option.ANIMATED_LAVA, Option.ANIMATED_FIRE, Option.ANIMATED_PORTAL, Option.ANIMATED_REDSTONE, Option.ANIMATED_EXPLOSION, Option.ANIMATED_FLAME, Option.ANIMATED_SMOKE, Option.VOID_PARTICLES, Option.WATER_PARTICLES, 
        Option.RAIN_SPLASH, Option.PORTAL_PARTICLES, Option.POTION_PARTICLES, Option.DRIPPING_WATER_LAVA, Option.ANIMATED_TERRAIN, Option.ANIMATED_TEXTURES, Option.FIREWORK_PARTICLES, this.settings.PARTICLES };
    for (int i = 0; i < options.length; i++) {
      fgr opt = options[i];
      int x = this.m / 2 - 155 + i % 2 * 160;
      int y = this.n / 6 + 21 * i / 2 - 12;
      fik guielement = (fik)c((fki)opt.a(this.l.m, x, y, 150));
      guielement.a(null);
    } 
    c((fki)new GuiButtonOF(210, this.m / 2 - 155, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
    c((fki)new GuiButtonOF(211, this.m / 2 - 155 + 80, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
    c((fki)new GuiScreenButtonOF(200, this.m / 2 + 5, this.n / 6 + 168 + 11, grr.a("gui.done", new Object[0])));
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
    if (guibutton.id == 210)
      this.l.m.setAllAnimations(true); 
    if (guibutton.id == 211)
      this.l.m.setAllAnimations(false); 
    this.l.a();
  }
  
  public void j() {
    this.l.m.aw();
    super.j();
  }
  
  public void a(fhz graphicsIn, int x, int y, float partialTicks) {
    super.a(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.l.h, this.k, this.m / 2, 15, 16777215);
  }
}
