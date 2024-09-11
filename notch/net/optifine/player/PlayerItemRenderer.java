package notch.net.optifine.player;

import fbi;
import fbm;
import fvx;
import fyk;
import net.optifine.player.PlayerItemModel;

public class PlayerItemRenderer {
  private int attachTo = 0;
  
  private fyk modelRenderer = null;
  
  public PlayerItemRenderer(int attachTo, fyk modelRenderer) {
    this.attachTo = attachTo;
    this.modelRenderer = modelRenderer;
  }
  
  public fyk getModelRenderer() {
    return this.modelRenderer;
  }
  
  public void render(fvx modelBiped, fbi matrixStackIn, fbm bufferIn, int packedLightIn, int packedOverlayIn) {
    fyk attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
    if (attachModel != null)
      attachModel.a(matrixStackIn); 
    this.modelRenderer.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
  }
}
