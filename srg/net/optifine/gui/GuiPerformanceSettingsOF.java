package srg.net.optifine.gui;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.config.Option;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;

public class GuiPerformanceSettingsOF extends GuiScreenOF {
  private Screen prevScreen;
  
  private Options settings;
  
  private TooltipManager tooltipManager = new TooltipManager((Screen)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiPerformanceSettingsOF(Screen guiscreen, Options gamesettings) {
    super((Component)Component.literal(I18n.get("of.options.performanceTitle", new Object[0])));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void init() {
    clearWidgets();
    OptionInstance[] options = { Option.RENDER_REGIONS, Option.FAST_RENDER, Option.SMART_ANIMATIONS, Option.FAST_MATH, Option.SMOOTH_FPS, Option.SMOOTH_WORLD, Option.CHUNK_UPDATES, Option.CHUNK_UPDATES_DYNAMIC, Option.LAZY_CHUNK_LOADING, this.settings.PRIORITIZE_CHUNK_UPDATES };
    for (int i = 0; i < options.length; i++) {
      OptionInstance option = options[i];
      int x = this.width / 2 - 155 + i % 2 * 160;
      int y = this.height / 6 + 21 * i / 2 - 12;
      AbstractWidget guielement = (AbstractWidget)addRenderableWidget((GuiEventListener)option.createButton(this.minecraft.options, x, y, 150));
      guielement.setTooltip(null);
    } 
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
      this.minecraft.setScreen(this.prevScreen);
    } 
  }
  
  public void removed() {
    this.minecraft.options.save();
    super.removed();
  }
  
  public void render(GuiGraphics graphicsIn, int x, int y, float partialTicks) {
    super.render(graphicsIn, x, y, partialTicks);
    drawCenteredString(graphicsIn, this.fontRenderer, this.title, this.width / 2, 15, 16777215);
    this.tooltipManager.drawTooltips(graphicsIn, x, y, getButtonList());
  }
}
