package notch.net.optifine.shaders.gui;

import grr;
import net.optifine.gui.GuiButtonOF;
import net.optifine.shaders.config.EnumShaderOption;

public class GuiButtonEnumShaderOption extends GuiButtonOF {
  private EnumShaderOption enumShaderOption = null;
  
  public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
    super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
    this.enumShaderOption = enumShaderOption;
  }
  
  public EnumShaderOption getEnumShaderOption() {
    return this.enumShaderOption;
  }
  
  private static String getButtonText(EnumShaderOption eso) {
    String nameText = grr.a(eso.getResourceKey(), new Object[0]) + ": ";
    switch (null.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
      case 1:
        return nameText + nameText;
      case 2:
        return nameText + nameText;
      case 3:
        return nameText + nameText;
      case 4:
        return nameText + nameText;
      case 5:
        return nameText + nameText;
      case 6:
        return nameText + nameText;
      case 7:
        return nameText + nameText;
      case 8:
        return nameText + nameText;
      case 9:
        return nameText + nameText;
      case 10:
        return nameText + nameText;
      case 11:
        return nameText + nameText;
    } 
    return nameText + nameText;
  }
  
  public void updateButtonText() {
    setMessage(getButtonText(this.enumShaderOption));
  }
  
  protected boolean j(int button) {
    return true;
  }
}
