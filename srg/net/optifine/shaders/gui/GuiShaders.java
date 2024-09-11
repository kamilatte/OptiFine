package srg.net.optifine.shaders.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
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
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;
import net.optifine.shaders.gui.GuiShaderOptions;
import net.optifine.shaders.gui.GuiSlotShaders;

public class GuiShaders extends GuiScreenOF {
  protected Screen parentGui;
  
  private TooltipManager tooltipManager = new TooltipManager((Screen)this, (TooltipProvider)new TooltipProviderEnumShaderOptions());
  
  private int updateTimer = -1;
  
  private GuiSlotShaders shaderList;
  
  private boolean saved = false;
  
  private static float[] QUALITY_MULTIPLIERS = new float[] { 
      0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 
      1.6666666F, 1.8F, 2.0F };
  
  private static String[] QUALITY_MULTIPLIER_NAMES = new String[] { 
      "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", 
      "1.66x", "1.8x", "2x" };
  
  private static float QUALITY_MULTIPLIER_DEFAULT = 1.0F;
  
  private static float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
  
  private static String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
  
  private static float HAND_DEPTH_DEFAULT = 0.125F;
  
  public static final int EnumOS_UNKNOWN = 0;
  
  public static final int EnumOS_WINDOWS = 1;
  
  public static final int EnumOS_OSX = 2;
  
  public static final int EnumOS_SOLARIS = 3;
  
  public static final int EnumOS_LINUX = 4;
  
  public GuiShaders(Screen par1GuiScreen, Options par2GameSettings) {
    super((Component)Component.literal(I18n.get("of.options.shadersTitle", new Object[0])));
    this.parentGui = par1GuiScreen;
  }
  
  public void init() {
    if (Shaders.shadersConfig == null)
      Shaders.loadConfig(); 
    int btnWidth = 120;
    int btnHeight = 20;
    int btnX = this.width - btnWidth - 10;
    int baseY = 30;
    int stepY = 20;
    int shaderListWidth = this.width - btnWidth - 20;
    this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.height, baseY, this.height - 50, 16);
    addWidget((GuiEventListener)this.shaderList);
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
    addRenderableWidget((GuiEventListener)new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
    int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
    int xFolder = shaderListWidth / 4 - btnFolderWidth / 2;
    int yFolder = this.height - 25;
    addRenderableWidget((GuiEventListener)new GuiButtonOF(201, xFolder, yFolder, btnFolderWidth - 22 + 1, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
    addRenderableWidget((GuiEventListener)new GuiButtonDownloadShaders(210, xFolder + btnFolderWidth - 22 - 1, yFolder));
    addRenderableWidget((GuiEventListener)new GuiButtonOF(202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.height - 25, btnFolderWidth, btnHeight, I18n.get("gui.done", new Object[0])));
    addRenderableWidget((GuiEventListener)new GuiButtonOF(203, btnX, this.height - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
    setFocused((GuiEventListener)this.shaderList);
    updateButtons();
  }
  
  public void updateButtons() {
    boolean shaderActive = Config.isShaders();
    for (Iterator<AbstractWidget> it = getButtonList().iterator(); it.hasNext(); ) {
      AbstractWidget guiElement = it.next();
      if (!(guiElement instanceof GuiButtonOF))
        continue; 
      GuiButtonOF button = (GuiButtonOF)guiElement;
      if (button.id == 201 || button.id == 202 || button.id == 210)
        continue; 
      if (button.id == EnumShaderOption.ANTIALIASING.ordinal())
        continue; 
      button.active = shaderActive;
    } 
  }
  
  protected void actionPerformed(AbstractWidget button) {
    actionPerformed(button, false);
  }
  
  protected void actionPerformedRightClick(AbstractWidget button) {
    actionPerformed(button, true);
  }
  
  private void actionPerformed(AbstractWidget guiElement, boolean rightClick) {
    if (!guiElement.active)
      return; 
    if (!(guiElement instanceof GuiButtonEnumShaderOption)) {
      String var2;
      boolean var8;
      GuiShaderOptions gui;
      if (rightClick)
        return; 
      if (!(guiElement instanceof GuiButtonOF))
        return; 
      GuiButtonOF button = (GuiButtonOF)guiElement;
      switch (button.id) {
        case 201:
          switch (getOSType()) {
            case 2:
              try {
                Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath() });
                return;
              } catch (IOException var7) {
                var7.printStackTrace();
                break;
              } 
            case 1:
              var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderPacksDir.getAbsolutePath() });
              try {
                Runtime.getRuntime().exec(var2);
                return;
              } catch (IOException var6) {
                var6.printStackTrace();
                break;
              } 
          } 
          var8 = false;
          try {
            URI uri = (new File(this.minecraft.gameDirectory, "shaderpacks")).toURI();
            Util.getPlatform().openUri(uri);
          } catch (Throwable var5) {
            var5.printStackTrace();
            var8 = true;
          } 
          if (var8) {
            Config.dbg("Opening via system class!");
            Util.getPlatform().openUri("file://" + Shaders.shaderPacksDir.getAbsolutePath());
          } 
          break;
        case 202:
          Shaders.storeConfig();
          this.saved = true;
          this.minecraft.setScreen(this.parentGui);
          break;
        case 203:
          gui = new GuiShaderOptions((Screen)this, Config.getGameSettings());
          Config.getMinecraft().setScreen((Screen)gui);
          break;
        case 210:
          try {
            URI uri = new URI("http://optifine.net/shaderPacks");
            Util.getPlatform().openUri(uri);
          } catch (Throwable throwable) {
            throwable.printStackTrace();
          } 
          break;
      } 
      return;
    } 
    GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)guiElement;
    switch (null.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[gbeso.getEnumShaderOption().ordinal()]) {
      case 1:
        Shaders.nextAntialiasingLevel(!rightClick);
        if (hasShiftDown())
          Shaders.configAntialiasingLevel = 0; 
        Shaders.uninit();
        break;
      case 2:
        Shaders.configNormalMap = !Shaders.configNormalMap;
        if (hasShiftDown())
          Shaders.configNormalMap = true; 
        Shaders.uninit();
        this.minecraft.delayTextureReload();
        break;
      case 3:
        Shaders.configSpecularMap = !Shaders.configSpecularMap;
        if (hasShiftDown())
          Shaders.configSpecularMap = true; 
        Shaders.uninit();
        this.minecraft.delayTextureReload();
        break;
      case 4:
        Shaders.configRenderResMul = getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, hasShiftDown());
        Shaders.uninit();
        Shaders.scheduleResize();
        break;
      case 5:
        Shaders.configShadowResMul = getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, hasShiftDown());
        Shaders.uninit();
        Shaders.scheduleResizeShadow();
        break;
      case 6:
        Shaders.configHandDepthMul = getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, HAND_DEPTH_DEFAULT, !rightClick, hasShiftDown());
        Shaders.uninit();
        break;
      case 7:
        Shaders.configOldHandLight.nextValue(!rightClick);
        if (hasShiftDown())
          Shaders.configOldHandLight.resetValue(); 
        Shaders.uninit();
        break;
      case 8:
        Shaders.configOldLighting.nextValue(!rightClick);
        if (hasShiftDown())
          Shaders.configOldLighting.resetValue(); 
        Shaders.updateBlockLightLevel();
        Shaders.uninit();
        this.minecraft.delayTextureReload();
        break;
      case 9:
        Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
        break;
      case 10:
        Shaders.configCloudShadow = !Shaders.configCloudShadow;
        break;
      case 11:
        Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
        Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
        gbeso.setMessage("Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB]);
        ShadersTex.updateTextureMinMagFilter();
        break;
      case 12:
        Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
        gbeso.setMessage("Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN]);
        ShadersTex.updateTextureMinMagFilter();
        break;
      case 13:
        Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
        gbeso.setMessage("Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS]);
        ShadersTex.updateTextureMinMagFilter();
        break;
      case 14:
        Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
        gbeso.setMessage("ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum));
        ShadersTex.updateTextureMinMagFilter();
        break;
    } 
    gbeso.updateButtonText();
  }
  
  public void removed() {
    if (!this.saved) {
      Shaders.storeConfig();
      this.saved = true;
    } 
    super.removed();
  }
  
  public void render(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
    super.renderBackground(graphicsIn, mouseX, mouseY, partialTicks);
    this.shaderList.render(graphicsIn, mouseX, mouseY, partialTicks);
    if (this.updateTimer <= 0) {
      this.shaderList.updateList();
      this.updateTimer += 20;
    } 
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, this.title, this.width / 2, 15, 16777215);
    String info = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
    int infoWidth = this.fontRenderer.width(info);
    if (infoWidth < this.width - 5) {
      this;
      drawCenteredString(graphicsIn, this.fontRenderer, info, this.width / 2, this.height - 40, 10526880);
    } else {
      this;
      drawString(graphicsIn, this.fontRenderer, info, 5, this.height - 40, 10526880);
    } 
    super.render(graphicsIn, mouseX, mouseY, partialTicks);
    this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, getButtonList());
  }
  
  public void renderBackground(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {}
  
  public void tick() {
    super.tick();
    this.updateTimer--;
  }
  
  public Minecraft getMc() {
    return this.minecraft;
  }
  
  public void drawCenteredString(GuiGraphics graphicsIn, String text, int x, int y, int color) {
    this;
    drawCenteredString(graphicsIn, this.fontRenderer, text, x, y, color);
  }
  
  public static String toStringOnOff(boolean value) {
    String on = Lang.getOn();
    String off = Lang.getOff();
    return value ? on : off;
  }
  
  public static String toStringAa(int value) {
    if (value == 2)
      return "FXAA 2x"; 
    if (value == 4)
      return "FXAA 4x"; 
    return Lang.getOff();
  }
  
  public static String toStringValue(float val, float[] values, String[] names) {
    int index = getValueIndex(val, values);
    return names[index];
  }
  
  private float getNextValue(float val, float[] values, float valDef, boolean forward, boolean reset) {
    if (reset)
      return valDef; 
    int index = getValueIndex(val, values);
    if (forward) {
      index++;
      if (index >= values.length)
        index = 0; 
    } else {
      index--;
      if (index < 0)
        index = values.length - 1; 
    } 
    return values[index];
  }
  
  public static int getValueIndex(float val, float[] values) {
    for (int i = 0; i < values.length; i++) {
      float value = values[i];
      if (value >= val)
        return i; 
    } 
    return values.length - 1;
  }
  
  public static String toStringQuality(float val) {
    return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
  }
  
  public static String toStringHandDepth(float val) {
    return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
  }
  
  public static int getOSType() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("win"))
      return 1; 
    if (osName.contains("mac"))
      return 2; 
    if (osName.contains("solaris"))
      return 3; 
    if (osName.contains("sunos"))
      return 3; 
    if (osName.contains("linux"))
      return 4; 
    if (osName.contains("unix"))
      return 4; 
    return 0;
  }
}
