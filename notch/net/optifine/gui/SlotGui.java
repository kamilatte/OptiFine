package notch.net.optifine.gui;

import akr;
import ayo;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fbd;
import fbg;
import fbk;
import fbn;
import fgo;
import fhz;
import fkg;
import fki;
import fmg;
import fmi;
import fod;
import ges;
import java.util.Collections;
import java.util.List;
import net.optifine.util.TextureUtils;

public abstract class SlotGui extends fkg implements fmg {
  public static final akr WHITE_TEXTURE_LOCATION = TextureUtils.WHITE_TEXTURE_LOCATION;
  
  public static final akr MENU_LIST_BACKGROUND = new akr("textures/gui/menu_list_background.png");
  
  public static final akr INWORLD_MENU_LIST_BACKGROUND = new akr("textures/gui/inworld_menu_list_background.png");
  
  protected static final int NO_DRAG = -1;
  
  protected static final int DRAG_OUTSIDE = -2;
  
  protected final fgo minecraft;
  
  protected int width;
  
  protected int height;
  
  protected int y0;
  
  protected int y1;
  
  protected int x1;
  
  protected int x0;
  
  protected final int itemHeight;
  
  protected boolean centerListVertically = true;
  
  protected int yDrag = -2;
  
  protected double yo;
  
  protected boolean visible = true;
  
  protected boolean renderSelection = true;
  
  protected boolean renderHeader;
  
  protected int headerHeight;
  
  private boolean scrolling;
  
  public SlotGui(fgo mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
    this.minecraft = mcIn;
    this.width = width;
    this.height = height;
    this.y0 = topIn;
    this.y1 = bottomIn;
    this.itemHeight = slotHeightIn;
    this.x0 = 0;
    this.x1 = width;
  }
  
  public void setRenderSelection(boolean p_setRenderSelection_1_) {
    this.renderSelection = p_setRenderSelection_1_;
  }
  
  protected void setRenderHeader(boolean p_setRenderHeader_1_, int p_setRenderHeader_2_) {
    this.renderHeader = p_setRenderHeader_1_;
    this.headerHeight = p_setRenderHeader_2_;
    if (!p_setRenderHeader_1_)
      this.headerHeight = 0; 
  }
  
  public void setVisible(boolean p_setVisible_1_) {
    this.visible = p_setVisible_1_;
  }
  
  public boolean isVisible() {
    return this.visible;
  }
  
  protected abstract int getItemCount();
  
  public List<? extends fki> aK_() {
    return Collections.emptyList();
  }
  
  protected boolean selectItem(int p_selectItem_1_, int p_selectItem_2_, double p_selectItem_3_, double p_selectItem_5_) {
    return true;
  }
  
  protected abstract boolean isSelectedItem(int paramInt);
  
  protected int getMaxPosition() {
    return getItemCount() * this.itemHeight + this.headerHeight;
  }
  
  protected abstract void renderBackground();
  
  protected void updateItemPosition(int p_updateItemPosition_1_, int p_updateItemPosition_2_, int p_updateItemPosition_3_, float p_updateItemPosition_4_) {}
  
  protected abstract void renderItem(fhz paramfhz, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat);
  
  protected void renderHeader(int p_renderHeader_1_, int p_renderHeader_2_, fbk p_renderHeader_3_) {}
  
  protected void clickedHeader(int p_clickedHeader_1_, int p_clickedHeader_2_) {}
  
  protected void renderDecorations(int p_renderDecorations_1_, int p_renderDecorations_2_) {}
  
  public int getItemAtPosition(double p_getItemAtPosition_1_, double p_getItemAtPosition_3_) {
    int i = this.x0 + this.width / 2 - getRowWidth() / 2;
    int j = this.x0 + this.width / 2 + getRowWidth() / 2;
    int k = ayo.a(p_getItemAtPosition_3_ - this.y0) - this.headerHeight + (int)this.yo - 4;
    int l = k / this.itemHeight;
    return (p_getItemAtPosition_1_ < getScrollbarPosition() && p_getItemAtPosition_1_ >= i && p_getItemAtPosition_1_ <= j && l >= 0 && k >= 0 && l < getItemCount()) ? l : -1;
  }
  
  protected void capYPosition() {
    this.yo = ayo.a(this.yo, 0.0D, getMaxScroll());
  }
  
  public int getMaxScroll() {
    return Math.max(0, getMaxPosition() - this.y1 - this.y0 - 4);
  }
  
  public void centerScrollOn(int p_centerScrollOn_1_) {
    this.yo = (p_centerScrollOn_1_ * this.itemHeight + this.itemHeight / 2 - (this.y1 - this.y0) / 2);
    capYPosition();
  }
  
  public int getScroll() {
    return (int)this.yo;
  }
  
  public boolean isMouseInList(double p_isMouseInList_1_, double p_isMouseInList_3_) {
    return (p_isMouseInList_3_ >= this.y0 && p_isMouseInList_3_ <= this.y1 && p_isMouseInList_1_ >= this.x0 && p_isMouseInList_1_ <= this.x1);
  }
  
  public int getScrollBottom() {
    return (int)this.yo - this.height - this.headerHeight;
  }
  
  public void scroll(int p_scroll_1_) {
    this.yo += p_scroll_1_;
    capYPosition();
    this.yDrag = -2;
  }
  
  public void render(fhz graphicsIn, int p_render_1_, int p_render_2_, float p_render_3_) {
    if (this.visible) {
      renderBackground();
      int i = getScrollbarPosition();
      int j = i + 6;
      capYPosition();
      fbk tessellator = fbk.b();
      RenderSystem.setShader(ges::r);
      akr menuListBackground = (this.minecraft.r != null) ? INWORLD_MENU_LIST_BACKGROUND : MENU_LIST_BACKGROUND;
      RenderSystem.setShaderTexture(0, menuListBackground);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      float f = 32.0F;
      fbd bufferbuilder = tessellator.a(fbn.c.h, fbg.j);
      bufferbuilder.a(this.x0, this.y1, 0.0F).a(this.x0 / 32.0F, (this.y1 + (int)this.yo) / 32.0F).a(32, 32, 32, 255);
      bufferbuilder.a(this.x1, this.y1, 0.0F).a(this.x1 / 32.0F, (this.y1 + (int)this.yo) / 32.0F).a(32, 32, 32, 255);
      bufferbuilder.a(this.x1, this.y0, 0.0F).a(this.x1 / 32.0F, (this.y0 + (int)this.yo) / 32.0F).a(32, 32, 32, 255);
      bufferbuilder.a(this.x0, this.y0, 0.0F).a(this.x0 / 32.0F, (this.y0 + (int)this.yo) / 32.0F).a(32, 32, 32, 255);
      tessellator.draw(bufferbuilder);
      RenderSystem.disableBlend();
      int k = this.x0 + this.width / 2 - getRowWidth() / 2 + 2;
      int l = this.y0 + 4 - (int)this.yo;
      if (this.renderHeader)
        renderHeader(k, l, tessellator); 
      renderList(graphicsIn, k, l, p_render_1_, p_render_2_, p_render_3_);
      RenderSystem.disableDepthTest();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
      RenderSystem.setShader(ges::r);
      RenderSystem.setShaderTexture(0, WHITE_TEXTURE_LOCATION);
      RenderSystem.disableTexture();
      int delta = 2;
      int j1 = getMaxScroll();
      if (j1 > 0) {
        int k1 = (int)(((this.y1 - this.y0) * (this.y1 - this.y0)) / getMaxPosition());
        k1 = ayo.a(k1, 32, this.y1 - this.y0 - 8);
        int l1 = (int)this.yo * (this.y1 - this.y0 - k1) / j1 + this.y0;
        if (l1 < this.y0)
          l1 = this.y0; 
        bufferbuilder = tessellator.a(fbn.c.h, fbg.j);
        bufferbuilder.a(i, this.y1, 0.0F).a(0.0F, 1.0F).a(0, 0, 0, 255);
        bufferbuilder.a(j, this.y1, 0.0F).a(1.0F, 1.0F).a(0, 0, 0, 255);
        bufferbuilder.a(j, this.y0, 0.0F).a(1.0F, 0.0F).a(0, 0, 0, 255);
        bufferbuilder.a(i, this.y0, 0.0F).a(0.0F, 0.0F).a(0, 0, 0, 255);
        tessellator.draw(bufferbuilder);
        bufferbuilder = tessellator.a(fbn.c.h, fbg.j);
        bufferbuilder.a(i, (l1 + k1), 0.0F).a(0.0F, 1.0F).a(128, 128, 128, 255);
        bufferbuilder.a(j, (l1 + k1), 0.0F).a(1.0F, 1.0F).a(128, 128, 128, 255);
        bufferbuilder.a(j, l1, 0.0F).a(1.0F, 0.0F).a(128, 128, 128, 255);
        bufferbuilder.a(i, l1, 0.0F).a(0.0F, 0.0F).a(128, 128, 128, 255);
        tessellator.draw(bufferbuilder);
        bufferbuilder = tessellator.a(fbn.c.h, fbg.j);
        bufferbuilder.a(i, (l1 + k1 - 1), 0.0F).a(0.0F, 1.0F).a(192, 192, 192, 255);
        bufferbuilder.a((j - 1), (l1 + k1 - 1), 0.0F).a(1.0F, 1.0F).a(192, 192, 192, 255);
        bufferbuilder.a((j - 1), l1, 0.0F).a(1.0F, 0.0F).a(192, 192, 192, 255);
        bufferbuilder.a(i, l1, 0.0F).a(0.0F, 0.0F).a(192, 192, 192, 255);
        tessellator.draw(bufferbuilder);
      } 
      renderDecorations(p_render_1_, p_render_2_);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
    } 
  }
  
  protected void updateScrollingState(double p_updateScrollingState_1_, double p_updateScrollingState_3_, int p_updateScrollingState_5_) {
    this.scrolling = (p_updateScrollingState_5_ == 0 && p_updateScrollingState_1_ >= getScrollbarPosition() && p_updateScrollingState_1_ < (getScrollbarPosition() + 6));
  }
  
  public boolean a(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
    updateScrollingState(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    if (isVisible() && isMouseInList(p_mouseClicked_1_, p_mouseClicked_3_)) {
      int i = getItemAtPosition(p_mouseClicked_1_, p_mouseClicked_3_);
      if (i == -1 && p_mouseClicked_5_ == 0) {
        clickedHeader((int)(p_mouseClicked_1_ - (this.x0 + this.width / 2 - getRowWidth() / 2)), (int)(p_mouseClicked_3_ - this.y0) + (int)this.yo - 4);
        return true;
      } 
      if (i != -1 && selectItem(i, p_mouseClicked_5_, p_mouseClicked_1_, p_mouseClicked_3_)) {
        if (aK_().size() > i)
          a(aK_().get(i)); 
        b_(true);
        return true;
      } 
      return this.scrolling;
    } 
    return false;
  }
  
  public boolean b(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
    if (aN_() != null)
      aN_().b(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_); 
    return false;
  }
  
  public boolean a(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
    if (super.a(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_))
      return true; 
    if (isVisible() && p_mouseDragged_5_ == 0 && this.scrolling) {
      if (p_mouseDragged_3_ < this.y0) {
        this.yo = 0.0D;
      } else if (p_mouseDragged_3_ > this.y1) {
        this.yo = getMaxScroll();
      } else {
        double d0 = getMaxScroll();
        if (d0 < 1.0D)
          d0 = 1.0D; 
        int i = (int)(((this.y1 - this.y0) * (this.y1 - this.y0)) / getMaxPosition());
        i = ayo.a(i, 32, this.y1 - this.y0 - 8);
        double d1 = d0 / (this.y1 - this.y0 - i);
        if (d1 < 1.0D)
          d1 = 1.0D; 
        this.yo += p_mouseDragged_8_ * d1;
        capYPosition();
      } 
      return true;
    } 
    return false;
  }
  
  public boolean a(double mouseX, double mouseY, double deltaH, double deltaV) {
    if (!isVisible())
      return false; 
    this.yo -= deltaV * this.itemHeight / 2.0D;
    return true;
  }
  
  public boolean a(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
    if (!isVisible())
      return false; 
    if (super.a(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_))
      return true; 
    if (p_keyPressed_1_ == 264) {
      moveSelection(1);
      return true;
    } 
    if (p_keyPressed_1_ == 265) {
      moveSelection(-1);
      return true;
    } 
    return false;
  }
  
  protected void moveSelection(int p_moveSelection_1_) {}
  
  public boolean a(char p_charTyped_1_, int p_charTyped_2_) {
    return !isVisible() ? false : super.a(p_charTyped_1_, p_charTyped_2_);
  }
  
  public boolean c(double p_isMouseOver_1_, double p_isMouseOver_3_) {
    return isMouseInList(p_isMouseOver_1_, p_isMouseOver_3_);
  }
  
  public int getRowWidth() {
    return 220;
  }
  
  protected void renderList(fhz graphicsIn, int p_renderList_1_, int p_renderList_2_, int p_renderList_3_, int p_renderList_4_, float p_renderList_5_) {
    int i = getItemCount();
    fbk tessellator = fbk.b();
    graphicsIn.c(this.x0, this.y0, this.x1, this.y1);
    for (int j = 0; j < i; j++) {
      int k = p_renderList_2_ + j * this.itemHeight + this.headerHeight;
      int l = this.itemHeight - 4;
      if (k > this.y1 || k + l < this.y0)
        updateItemPosition(j, p_renderList_1_, k, p_renderList_5_); 
      if (this.renderSelection && isSelectedItem(j)) {
        int i1 = this.x0 + this.width / 2 - getRowWidth() / 2;
        int j1 = this.x0 + this.width / 2 + getRowWidth() / 2;
        RenderSystem.disableTexture();
        RenderSystem.setShader(ges::o);
        float f = isFocusedNow() ? 1.0F : 0.5F;
        RenderSystem.setShaderColor(f, f, f, 1.0F);
        fbd bufferbuilder = tessellator.a(fbn.c.h, fbg.e);
        bufferbuilder.a(i1, (k + l + 2), 0.0F);
        bufferbuilder.a(j1, (k + l + 2), 0.0F);
        bufferbuilder.a(j1, (k - 2), 0.0F);
        bufferbuilder.a(i1, (k - 2), 0.0F);
        tessellator.draw(bufferbuilder);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
        bufferbuilder = tessellator.a(fbn.c.h, fbg.e);
        bufferbuilder.a((i1 + 1), (k + l + 1), 0.0F);
        bufferbuilder.a((j1 - 1), (k + l + 1), 0.0F);
        bufferbuilder.a((j1 - 1), (k - 1), 0.0F);
        bufferbuilder.a((i1 + 1), (k - 1), 0.0F);
        tessellator.draw(bufferbuilder);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableTexture();
      } 
      if (k + this.itemHeight >= this.y0 && k <= this.y1)
        renderItem(graphicsIn, j, p_renderList_1_, k, l, p_renderList_3_, p_renderList_4_, p_renderList_5_); 
    } 
    graphicsIn.f();
  }
  
  protected boolean isFocusedNow() {
    return false;
  }
  
  protected int getScrollbarPosition() {
    return this.width / 2 + 124;
  }
  
  protected void renderHoleBackground(int p_renderHoleBackground_1_, int p_renderHoleBackground_2_, int p_renderHoleBackground_3_, int p_renderHoleBackground_4_) {
    fbk tessellator = fbk.b();
    RenderSystem.setShader(ges::r);
    RenderSystem.setShaderTexture(0, fod.f);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    float f = 32.0F;
    fbd bufferbuilder = tessellator.a(fbn.c.h, fbg.j);
    bufferbuilder.a(this.x0, p_renderHoleBackground_2_, 0.0F).a(0.0F, p_renderHoleBackground_2_ / 32.0F).a(64, 64, 64, p_renderHoleBackground_4_);
    bufferbuilder.a((this.x0 + this.width), p_renderHoleBackground_2_, 0.0F).a(this.width / 32.0F, p_renderHoleBackground_2_ / 32.0F).a(64, 64, 64, p_renderHoleBackground_4_);
    bufferbuilder.a((this.x0 + this.width), p_renderHoleBackground_1_, 0.0F).a(this.width / 32.0F, p_renderHoleBackground_1_ / 32.0F).a(64, 64, 64, p_renderHoleBackground_3_);
    bufferbuilder.a(this.x0, p_renderHoleBackground_1_, 0.0F).a(0.0F, p_renderHoleBackground_1_ / 32.0F).a(64, 64, 64, p_renderHoleBackground_3_);
    tessellator.draw(bufferbuilder);
  }
  
  public void setLeftPos(int p_setLeftPos_1_) {
    this.x0 = p_setLeftPos_1_;
    this.x1 = p_setLeftPos_1_ + this.width;
  }
  
  public int getItemHeight() {
    return this.itemHeight;
  }
  
  public fmg.a u() {
    return fmg.a.b;
  }
  
  public void b(fmi output) {}
}
