package notch.net.optifine.player;

import fbi;
import fvx;
import gdy;
import gez;
import net.optifine.Config;
import net.optifine.player.PlayerItemModel;

public class PlayerConfiguration {
  private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
  
  private boolean initialized = false;
  
  public void renderPlayerItems(fvx modelBiped, gdy player, fbi matrixStackIn, gez bufferIn, int packedLightIn, int packedOverlayIn) {
    if (!this.initialized)
      return; 
    for (int i = 0; i < this.playerItemModels.length; i++) {
      PlayerItemModel model = this.playerItemModels[i];
      model.render(modelBiped, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    } 
  }
  
  public boolean isInitialized() {
    return this.initialized;
  }
  
  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }
  
  public PlayerItemModel[] getPlayerItemModels() {
    return this.playerItemModels;
  }
  
  public void addPlayerItemModel(PlayerItemModel playerItemModel) {
    this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray((Object[])this.playerItemModels, playerItemModel);
  }
}
