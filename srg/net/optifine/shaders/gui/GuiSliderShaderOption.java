package srg.net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.shaders.gui.GuiShaderOptions;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
  private float sliderValue;
  
  public boolean dragging;
  
  private ShaderOption shaderOption = null;
  
  private static final ResourceLocation SLIDER_SPRITE = new ResourceLocation("widget/slider");
  
  private static final ResourceLocation HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_highlighted");
  
  private static final ResourceLocation SLIDER_HANDLE_SPRITE = new ResourceLocation("widget/slider_handle");
  
  private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_handle_highlighted");
  
  public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
    super(buttonId, x, y, w, h, shaderOption, text);
    this.sliderValue = 1.0F;
    this.shaderOption = shaderOption;
    this.sliderValue = shaderOption.getIndexNormalized();
    setMessage(GuiShaderOptions.getButtonText(shaderOption, this.width));
  }
  
  public void renderWidget(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
    if (this.visible) {
      if (this.dragging && !Screen.hasShiftDown()) {
        this.sliderValue = (mouseX - getX() + 4) / (this.width - 8);
        this.sliderValue = Mth.clamp(this.sliderValue, 0.0F, 1.0F);
        this.shaderOption.setIndexNormalized(this.sliderValue);
        this.sliderValue = this.shaderOption.getIndexNormalized();
        setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.width));
      } 
      Minecraft mc = Minecraft.getInstance();
      graphicsIn.setColor(1.0F, 1.0F, 1.0F, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphicsIn.blitSprite(getSprite(), getX(), getY(), getWidth(), getHeight());
      graphicsIn.blitSprite(getHandleSprite(), getX() + (int)(this.sliderValue * (this.width - 8)), getY(), 8, getHeight());
      int col = this.active ? 16777215 : 10526880;
      renderString(graphicsIn, mc.font, col | Mth.ceil(this.alpha * 255.0F) << 24);
    } 
  }
  
  private ResourceLocation getSprite() {
    return (isFocused() && !this.dragging) ? HIGHLIGHTED_SPRITE : SLIDER_SPRITE;
  }
  
  private ResourceLocation getHandleSprite() {
    return (!this.isHovered && !this.dragging) ? SLIDER_HANDLE_SPRITE : SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
  }
  
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (super.mouseClicked(mouseX, mouseY, button)) {
      this.sliderValue = (float)(mouseX - (getX() + 4)) / (this.width - 8);
      this.sliderValue = Mth.clamp(this.sliderValue, 0.0F, 1.0F);
      this.shaderOption.setIndexNormalized(this.sliderValue);
      setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.width));
      this.dragging = true;
      return true;
    } 
    return false;
  }
  
  public boolean mouseReleased(double mouseX, double mouseY, int button) {
    this.dragging = false;
    return true;
  }
  
  public void valueChanged() {
    this.sliderValue = this.shaderOption.getIndexNormalized();
  }
  
  public boolean isSwitchable() {
    return false;
  }
}
