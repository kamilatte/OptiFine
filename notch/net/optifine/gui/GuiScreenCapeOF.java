package notch.net.optifine.gui;

import com.mojang.authlib.exceptions.InvalidCredentialsException;
import fhz;
import fik;
import fki;
import fod;
import grr;
import java.math.BigInteger;
import java.net.URI;
import java.util.Random;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import wz;

public class GuiScreenCapeOF extends GuiScreenOF {
  private final fod parentScreen;
  
  private String message;
  
  private long messageHideTimeMs;
  
  private String linkUrl;
  
  private GuiButtonOF buttonCopyLink;
  
  public GuiScreenCapeOF(fod parentScreenIn) {
    super((wz)wz.b(grr.a("of.options.capeOF.title", new Object[0])));
    this.parentScreen = parentScreenIn;
  }
  
  protected void aT_() {
    int i = 0;
    i += 2;
    c((fki)new GuiButtonOF(210, this.m / 2 - 155, this.n / 6 + 24 * (i >> 1), 150, 20, grr.a("of.options.capeOF.openEditor", new Object[0])));
    c((fki)new GuiButtonOF(220, this.m / 2 - 155 + 160, this.n / 6 + 24 * (i >> 1), 150, 20, grr.a("of.options.capeOF.reloadCape", new Object[0])));
    i += 6;
    this.buttonCopyLink = new GuiButtonOF(230, this.m / 2 - 100, this.n / 6 + 24 * (i >> 1), 200, 20, grr.a("of.options.capeOF.copyEditorLink", new Object[0]));
    this.buttonCopyLink.k = (this.linkUrl != null);
    c((fki)this.buttonCopyLink);
    i += 4;
    c((fki)new GuiButtonOF(200, this.m / 2 - 100, this.n / 6 + 24 * (i >> 1), grr.a("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(fik guiElement) {
    if (!(guiElement instanceof GuiButtonOF))
      return; 
    GuiButtonOF button = (GuiButtonOF)guiElement;
    if (button.j) {
      if (button.id == 200)
        this.l.a(this.parentScreen); 
      if (button.id == 210)
        try {
          String userName = this.l.Y().getName();
          String userId = this.l.Y().getId().toString().replace("-", "");
          String accessToken = this.l.X().d();
          Random r1 = new Random();
          Random r2 = new Random(System.identityHashCode(new Object()));
          BigInteger random1Bi = new BigInteger(128, r1);
          BigInteger random2Bi = new BigInteger(128, r2);
          BigInteger serverBi = random1Bi.xor(random2Bi);
          String serverId = serverBi.toString(16);
          this.l.al().joinServer(this.l.Y().getId(), accessToken, serverId);
          String urlStr = "https://optifine.net/capeChange?u=" + userId + "&n=" + userName + "&s=" + serverId;
          boolean opened = Config.openWebLink(new URI(urlStr));
          if (opened) {
            showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
          } else {
            showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
            setLinkUrl(urlStr);
          } 
        } catch (InvalidCredentialsException e) {
          Config.showGuiMessage(grr.a("of.message.capeOF.error1", new Object[0]), grr.a("of.message.capeOF.error2", new Object[] { e.getMessage() }));
          Config.warn("Mojang authentication failed");
          Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
        } catch (Exception e) {
          Config.warn("Error opening OptiFine cape link");
          Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
        }  
      if (button.id == 220) {
        showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
        if (this.l.s != null) {
          long delayMs = 15000L;
          long reloadTimeMs = System.currentTimeMillis() + delayMs;
          this.l.s.setReloadCapeTimeMs(reloadTimeMs);
        } 
      } 
      if (button.id == 230)
        if (this.linkUrl != null)
          this.l.o.a(this.linkUrl);  
    } 
  }
  
  private void showMessage(String msg, long timeMs) {
    this.message = msg;
    this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
    setLinkUrl(null);
  }
  
  public void a(fhz graphicsIn, int mouseX, int mouseY, float partialTicks) {
    super.a(graphicsIn, mouseX, mouseY, partialTicks);
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, this.k, this.m / 2, 20, 16777215);
    if (this.message != null) {
      this;
      drawCenteredString(graphicsIn, this.fontRenderer, this.message, this.m / 2, this.n / 6 + 60, 16777215);
      if (System.currentTimeMillis() > this.messageHideTimeMs) {
        this.message = null;
        setLinkUrl(null);
      } 
    } 
  }
  
  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    this.buttonCopyLink.k = (linkUrl != null);
  }
}
