package notch.net.optifine.shaders.gui;

import akr;
import ayo;
import com.mojang.blaze3d.systems.RenderSystem;
import fgo;
import fhz;
import fod;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.shaders.gui.GuiShaderOptions;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
  private float sliderValue;
  
  public boolean dragging;
  
  private ShaderOption shaderOption = null;
  
  private static final akr SLIDER_SPRITE = new akr("widget/slider");
  
  private static final akr HIGHLIGHTED_SPRITE = new akr("widget/slider_highlighted");
  
  private static final akr SLIDER_HANDLE_SPRITE = new akr("widget/slider_handle");
  
  private static final akr SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new akr("widget/slider_handle_highlighted");
  
  public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
    super(buttonId, x, y, w, h, shaderOption, text);
    this.sliderValue = 1.0F;
    this.shaderOption = shaderOption;
    this.sliderValue = shaderOption.getIndexNormalized();
    setMessage(GuiShaderOptions.getButtonText(shaderOption, this.g));
  }
  
  public void b(fhz graphicsIn, int mouseX, int mouseY, float partialTicks) {
    if (this.k) {
      if (this.dragging && !fod.s()) {
        this.sliderValue = (mouseX - D() + 4) / (this.g - 8);
        this.sliderValue = ayo.a(this.sliderValue, 0.0F, 1.0F);
        this.shaderOption.setIndexNormalized(this.sliderValue);
        this.sliderValue = this.shaderOption.getIndexNormalized();
        setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.g));
      } 
      fgo mc = fgo.Q();
      graphicsIn.a(1.0F, 1.0F, 1.0F, this.l);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphicsIn.a(getSprite(), D(), E(), y(), w());
      graphicsIn.a(getHandleSprite(), D() + (int)(this.sliderValue * (this.g - 8)), E(), 8, w());
      int col = this.j ? 16777215 : 10526880;
      a(graphicsIn, mc.h, col | ayo.f(this.l * 255.0F) << 24);
    } 
  }
  
  private akr getSprite() {
    return (aO_() && !this.dragging) ? HIGHLIGHTED_SPRITE : SLIDER_SPRITE;
  }
  
  private akr getHandleSprite() {
    return (!this.i && !this.dragging) ? SLIDER_HANDLE_SPRITE : SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
  }
  
  public boolean a(double mouseX, double mouseY, int button) {
    if (super.a(mouseX, mouseY, button)) {
      this.sliderValue = (float)(mouseX - (D() + 4)) / (this.g - 8);
      this.sliderValue = ayo.a(this.sliderValue, 0.0F, 1.0F);
      this.shaderOption.setIndexNormalized(this.sliderValue);
      setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.g));
      this.dragging = true;
      return true;
    } 
    return false;
  }
  
  public boolean b(double mouseX, double mouseY, int button) {
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
