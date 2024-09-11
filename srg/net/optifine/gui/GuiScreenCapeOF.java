package srg.net.optifine.gui;

import com.mojang.authlib.exceptions.InvalidCredentialsException;
import java.math.BigInteger;
import java.net.URI;
import java.util.Random;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;

public class GuiScreenCapeOF extends GuiScreenOF {
  private final Screen parentScreen;
  
  private String message;
  
  private long messageHideTimeMs;
  
  private String linkUrl;
  
  private GuiButtonOF buttonCopyLink;
  
  public GuiScreenCapeOF(Screen parentScreenIn) {
    super((Component)Component.literal(I18n.get("of.options.capeOF.title", new Object[0])));
    this.parentScreen = parentScreenIn;
  }
  
  protected void init() {
    int i = 0;
    i += 2;
    addRenderableWidget((GuiEventListener)new GuiButtonOF(210, this.width / 2 - 155, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.get("of.options.capeOF.openEditor", new Object[0])));
    addRenderableWidget((GuiEventListener)new GuiButtonOF(220, this.width / 2 - 155 + 160, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.get("of.options.capeOF.reloadCape", new Object[0])));
    i += 6;
    this.buttonCopyLink = new GuiButtonOF(230, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20, I18n.get("of.options.capeOF.copyEditorLink", new Object[0]));
    this.buttonCopyLink.visible = (this.linkUrl != null);
    addRenderableWidget((GuiEventListener)this.buttonCopyLink);
    i += 4;
    addRenderableWidget((GuiEventListener)new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.get("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(AbstractWidget guiElement) {
    if (!(guiElement instanceof GuiButtonOF))
      return; 
    GuiButtonOF button = (GuiButtonOF)guiElement;
    if (button.active) {
      if (button.id == 200)
        this.minecraft.setScreen(this.parentScreen); 
      if (button.id == 210)
        try {
          String userName = this.minecraft.getGameProfile().getName();
          String userId = this.minecraft.getGameProfile().getId().toString().replace("-", "");
          String accessToken = this.minecraft.getUser().getAccessToken();
          Random r1 = new Random();
          Random r2 = new Random(System.identityHashCode(new Object()));
          BigInteger random1Bi = new BigInteger(128, r1);
          BigInteger random2Bi = new BigInteger(128, r2);
          BigInteger serverBi = random1Bi.xor(random2Bi);
          String serverId = serverBi.toString(16);
          this.minecraft.getMinecraftSessionService().joinServer(this.minecraft.getGameProfile().getId(), accessToken, serverId);
          String urlStr = "https://optifine.net/capeChange?u=" + userId + "&n=" + userName + "&s=" + serverId;
          boolean opened = Config.openWebLink(new URI(urlStr));
          if (opened) {
            showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
          } else {
            showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
            setLinkUrl(urlStr);
          } 
        } catch (InvalidCredentialsException e) {
          Config.showGuiMessage(I18n.get("of.message.capeOF.error1", new Object[0]), I18n.get("of.message.capeOF.error2", new Object[] { e.getMessage() }));
          Config.warn("Mojang authentication failed");
          Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
        } catch (Exception e) {
          Config.warn("Error opening OptiFine cape link");
          Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
        }  
      if (button.id == 220) {
        showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
        if (this.minecraft.player != null) {
          long delayMs = 15000L;
          long reloadTimeMs = System.currentTimeMillis() + delayMs;
          this.minecraft.player.setReloadCapeTimeMs(reloadTimeMs);
        } 
      } 
      if (button.id == 230)
        if (this.linkUrl != null)
          this.minecraft.keyboardHandler.setClipboard(this.linkUrl);  
    } 
  }
  
  private void showMessage(String msg, long timeMs) {
    this.message = msg;
    this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
    setLinkUrl(null);
  }
  
  public void render(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
    super.render(graphicsIn, mouseX, mouseY, partialTicks);
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, this.title, this.width / 2, 20, 16777215);
    if (this.message != null) {
      this;
      drawCenteredString(graphicsIn, this.fontRenderer, this.message, this.width / 2, this.height / 6 + 60, 16777215);
      if (System.currentTimeMillis() > this.messageHideTimeMs) {
        this.message = null;
        setLinkUrl(null);
      } 
    } 
  }
  
  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    this.buttonCopyLink.visible = (linkUrl != null);
  }
}
