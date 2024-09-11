package notch.net.optifine.shaders.gui;

import akr;
import com.mojang.blaze3d.systems.RenderSystem;
import fhz;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders extends GuiButtonOF {
  public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
    super(buttonID, xPos, yPos, 22, 20, "");
  }
  
  public void b(fhz graphicsIn, int mouseX, int mouseY, float partialTicks) {
    if (!this.k)
      return; 
    super.b(graphicsIn, mouseX, mouseY, partialTicks);
    akr locTexture = new akr("optifine/textures/icons.png");
    RenderSystem.setShaderTexture(0, locTexture);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    this;
    blit(graphicsIn, locTexture, D() + 3, E() + 2, 0, 0, 16, 16);
  }
}
