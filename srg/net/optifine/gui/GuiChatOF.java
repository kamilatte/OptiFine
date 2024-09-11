package srg.net.optifine.gui;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF extends ChatScreen {
  private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
  
  private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";
  
  public GuiChatOF(ChatScreen guiChat) {
    super(LoadingOverlay.getGuiChatText(guiChat));
  }
  
  public void handleChatInput(String msg, boolean add) {
    if (checkCustomCommand(msg)) {
      this.minecraft.gui.getChat().addRecentChat(msg);
      return;
    } 
    super.handleChatInput(msg, add);
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
      this.minecraft.levelRenderer.allChanged();
      return true;
    } 
    return false;
  }
}
