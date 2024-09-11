package notch.net.optifine.gui;

import fgr;
import fim;
import net.optifine.gui.IOptionControl;
import wz;

public class GuiOptionButtonOF extends fim implements IOptionControl {
  private final fgr option;
  
  public GuiOptionButtonOF(int xIn, int yIn, int widthIn, int heightIn, fgr optionIn, wz textIn, fim.c pressableIn, fim.b narrationIn) {
    super(xIn, yIn, widthIn, heightIn, textIn, pressableIn, narrationIn);
    this.option = optionIn;
  }
  
  public fgr getOption() {
    return this.option;
  }
  
  public fgr getControlOption() {
    return this.option;
  }
}
