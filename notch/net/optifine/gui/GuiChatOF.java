package notch.net.optifine.gui;

import fmz;
import fnt;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF extends fmz {
  private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
  
  private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";
  
  public GuiChatOF(fmz guiChat) {
    super(fnt.getGuiChatText(guiChat));
  }
  
  public void b(String msg, boolean add) {
    if (checkCustomCommand(msg)) {
      this.l.l.d().a(msg);
      return;
    } 
    super.b(msg, add);
  }
  
  private boolean checkCustomCommand(String msg) {
    if (msg == null)
      return false; 
    msg = msg.trim();
    if (msg.equals("/reloadShaders")) {
      if (Config.isShaders()) {
        Shaders.uninit();
        Shaders.loadShaderPack();
      } 
      return true;
    } 
    if (msg.equals("/reloadChunks")) {
      this.l.f.f();
      return true;
    } 
    return false;
  }
}
