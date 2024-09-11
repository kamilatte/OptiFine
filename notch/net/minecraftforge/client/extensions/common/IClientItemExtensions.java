package notch.net.minecraftforge.client.extensions.common;

import btg;
import cuq;
import fbi;
import fgo;
import fhx;
import geb;
import gem;

public interface IClientItemExtensions {
  public static final net.minecraftforge.client.extensions.common.IClientItemExtensions DUMMY = (net.minecraftforge.client.extensions.common.IClientItemExtensions)new Object();
  
  static net.minecraftforge.client.extensions.common.IClientItemExtensions of(cuq itemStack) {
    return DUMMY;
  }
  
  default boolean applyForgeHandTransform(fbi poseStack, geb player, btg arm, cuq itemInHand, float partialTick, float equipProcess, float swingProcess) {
    return false;
  }
  
  default fhx getFont(cuq stack, FontContext context) {
    return null;
  }
  
  default gem getCustomRenderer() {
    return fgo.Q().ar().getBlockEntityRenderer();
  }
}
