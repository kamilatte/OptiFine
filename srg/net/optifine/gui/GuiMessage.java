package srg.net.optifine.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.optifine.Config;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;

public class GuiMessage extends GuiScreenOF {
  private Screen parentScreen;
  
  private Component messageLine1;
  
  private Component messageLine2;
  
  private final List<FormattedCharSequence> listLines2 = Lists.newArrayList();
  
  protected String confirmButtonText;
  
  private int ticksUntilEnable;
  
  public GuiMessage(Screen parentScreen, String line1, String line2) {
    super((Component)Component.translatable("of.options.detailsTitle"));
    this.parentScreen = parentScreen;
    this.messageLine1 = (Component)Component.literal(line1);
    this.messageLine2 = (Component)Component.literal(line2);
    this.confirmButtonText = I18n.get("gui.done", new Object[0]);
  }
  
  public void init() {
    addRenderableWidget((GuiEventListener)new GuiButtonOF(0, this.width / 2 - 100, this.height / 6 + 96, this.confirmButtonText));
    this.listLines2.clear();
    this.listLines2.addAll(this.minecraft.font.split((FormattedText)this.messageLine2, this.width - 50));
  }
  
  protected void actionPerformed(AbstractWidget button) {
    Config.getMinecraft().setScreen(this.parentScreen);
  }
  
  public void render(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
    super.render(graphicsIn, mouseX, mouseY, partialTicks);
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, this.messageLine1, this.width / 2, 70, 16777215);
    int var4 = 90;
    for (Iterator<FormattedCharSequence> var5 = this.listLines2.iterator(); var5.hasNext(); Objects.requireNonNull(this.fontRenderer), var4 += 9) {
      FormattedCharSequence line = var5.next();
      this;
      drawCenteredString(graphicsIn, this.fontRenderer, line, this.width / 2, var4, 16777215);
    } 
  }
  
  public void setButtonDelay(int ticksUntilEnable) {
    this.ticksUntilEnable = ticksUntilEnable;
    for (Iterator<Button> var2 = getButtonList().iterator(); var2.hasNext(); var3.active = false)
      Button var3 = var2.next(); 
  }
  
  public void tick() {
    super.tick();
    if (--this.ticksUntilEnable == 0)
      for (Iterator<Button> var1 = getButtonList().iterator(); var1.hasNext(); var2.active = true)
        Button var2 = var1.next();  
  }
}
