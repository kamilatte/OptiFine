package srg.net.optifine.gui;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.config.Option;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.OptionFullscreenResolution;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;

public class GuiOtherSettingsOF extends GuiScreenOF {
  private Screen prevScreen;
  
  private Options settings;
  
  private TooltipManager tooltipManager = new TooltipManager((Screen)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiOtherSettingsOF(Screen guiscreen, Options gamesettings) {
    super((Component)Component.literal(I18n.get("of.options.otherTitle", new Object[0])));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void init() {
    clearWidgets();
    OptionInstance fullscreenResolution = OptionFullscreenResolution.make();
    OptionInstance[] options = { 
        Option.LAGOMETER, Option.PROFILER, this.settings.ATTACK_INDICATOR, Option.ADVANCED_TOOLTIPS, Option.WEATHER, Option.TIME, this.settings.FULLSCREEN, Option.AUTOSAVE_TICKS, Option.SCREENSHOT_SIZE, Option.SHOW_GL_ERRORS, 
        Option.TELEMETRY, null, fullscreenResolution, null };
    for (int i = 0; i < options.length; i++) {
      OptionInstance option = options[i];
      if (option != null) {
        int x = this.width / 2 - 155 + i % 2 * 160;
        int y = this.height / 6 + 21 * i / 2 - 12;
        AbstractWidget guielement = (AbstractWidget)addRenderableWidget((GuiEventListener)option.createButton(this.minecraft.options, x, y, 150));
        guielement.setTooltip(null);
        if (option == fullscreenResolution)
          guielement.setWidth(310); 
      } 
    } 
    addRenderableWidget((GuiEventListener)new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.get("of.options.other.reset", new Object[0])));
    addRenderableWidget((GuiEventListener)new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.get("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(AbstractWidget guiElement) {
    if (!(guiElement instanceof GuiButtonOF))
      return; 
    GuiButtonOF guibutton = (GuiButtonOF)guiElement;
    if (!guibutton.active)
      return; 
    if (guibutton.id == 200) {
      this.minecraft.options.save();
      this.minecraft.getWindow().changeFullscreenVideoMode();
      this.minecraft.setScreen(this.prevScreen);
    } 
    if (guibutton.id == 210) {
      this.minecraft.options.save();
      String msg = I18n.get("of.message.other.reset", new Object[0]);
      ConfirmScreen guiyesno = new ConfirmScreen(this::confirmResult, (Component)Component.literal(msg), (Component)Component.literal(""));
      this.minecraft.setScreen((Screen)guiyesno);
    } 
  }
  
  public void removed() {
    this.minecraft.options.save();
    this.minecraft.getWindow().changeFullscreenVideoMode();
    super.removed();
  }
  
  public void confirmResult(boolean flag) {
    if (flag)
      this.minecraft.options.resetSettings(); 
    this.minecraft.setScreen((Screen)this);
  }
  
  public void render(GuiGraphics graphicsIn, int x, int y, float partialTicks) {
    super.render(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.fontRenderer, this.title, this.width / 2, 15, 16777215);
    this.tooltipManager.drawTooltips(graphicsIn, x, y, getButtonList());
  }
}
