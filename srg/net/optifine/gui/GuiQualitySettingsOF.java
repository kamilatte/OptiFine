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

public class GuiQualitySettingsOF extends GuiScreenOF {
  private Screen prevScreen;
  
  private Options settings;
  
  private TooltipManager tooltipManager = new TooltipManager((Screen)this, (TooltipProvider)new TooltipProviderOptions());
  
  public GuiQualitySettingsOF(Screen guiscreen, Options gamesettings) {
    super((Component)Component.literal(I18n.get("of.options.qualityTitle", new Object[0])));
    this.prevScreen = guiscreen;
    this.settings = gamesettings;
  }
  
  public void init() {
    clearWidgets();
    OptionInstance[] options = { 
        this.settings.MIPMAP_LEVELS, Option.MIPMAP_TYPE, Option.AF_LEVEL, Option.AA_LEVEL, Option.EMISSIVE_TEXTURES, Option.RANDOM_ENTITIES, Option.BETTER_GRASS, Option.BETTER_SNOW, Option.CUSTOM_FONTS, Option.CUSTOM_COLORS, 
        Option.CONNECTED_TEXTURES, Option.NATURAL_TEXTURES, Option.CUSTOM_SKY, Option.CUSTOM_ITEMS, Option.CUSTOM_ENTITY_MODELS, Option.CUSTOM_GUIS, this.settings.SCREEN_EFFECT_SCALE, this.settings.FOV_EFFECT_SCALE };
    for (int i = 0; i < options.length; i++) {
      OptionInstance opt = options[i];
      int x = this.width / 2 - 155 + i % 2 * 160;
      int y = this.height / 6 + 21 * i / 2 - 12;
      AbstractWidget guielement = (AbstractWidget)addRenderableWidget((GuiEventListener)opt.createButton(this.minecraft.options, x, y, 150));
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
