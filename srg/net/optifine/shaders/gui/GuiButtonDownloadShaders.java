package srg.net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders extends GuiButtonOF {
  public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
    super(buttonID, xPos, yPos, 22, 20, "");
  }
  
  public void renderWidget(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
    if (!this.visible)
      return; 
    super.renderWidget(graphicsIn, mouseX, mouseY, partialTicks);
    ResourceLocation locTexture = new ResourceLocation("optifine/textures/icons.png");
    RenderSystem.setShaderTexture(0, locTexture);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    this;
    blit(graphicsIn, locTexture, getX() + 3, getY() + 2, 0, 0, 16, 16);
  }
}
