package notch.net.optifine.player;

import bsr;
import fbi;
import fvx;
import gdy;
import gez;
import gmf;
import gov;
import gpo;
import gqc;
import net.optifine.Config;
import net.optifine.player.PlayerConfigurations;

public class PlayerItemsLayer extends gov {
  private gpo renderPlayer = null;
  
  public PlayerItemsLayer(gpo renderPlayer) {
    super((gmf)renderPlayer);
    this.renderPlayer = renderPlayer;
  }
  
  public void a(fbi matrixStackIn, gez bufferIn, int packedLightIn, bsr entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    renderEquippedItems(entitylivingbaseIn, matrixStackIn, bufferIn, packedLightIn, gqc.d);
  }
  
  protected void renderEquippedItems(bsr entityLiving, fbi matrixStackIn, gez bufferIn, int packedLightIn, int packedOverlayIn) {
    if (!Config.isShowCapes())
      return; 
    if (entityLiving.ci())
      return; 
    if (!(entityLiving instanceof gdy))
      return; 
    gdy player = (gdy)entityLiving;
    fvx modelBipedMain = (fvx)this.renderPlayer.a();
    PlayerConfigurations.renderPlayerItems(modelBipedMain, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
  }
}
