package notch.net.minecraftforge.client.extensions.common;

import brx;
import brz;
import fhy;
import fhz;
import fpl;

public interface IClientMobEffectExtensions {
  public static final net.minecraftforge.client.extensions.common.IClientMobEffectExtensions DUMMY = (net.minecraftforge.client.extensions.common.IClientMobEffectExtensions)new Object();
  
  static net.minecraftforge.client.extensions.common.IClientMobEffectExtensions of(brz mobeffectinstance) {
    return DUMMY;
  }
  
  static net.minecraftforge.client.extensions.common.IClientMobEffectExtensions of(brx effect) {
    return DUMMY;
  }
  
  default boolean isVisibleInInventory(brz instance) {
    return true;
  }
  
  default boolean isVisibleInGui(brz instance) {
    return true;
  }
  
  default boolean renderInventoryIcon(brz instance, fpl<?> screen, fhz guiGraphics, int x, int y, int blitOffset) {
    return false;
  }
  
  default boolean renderInventoryText(brz instance, fpl<?> screen, fhz guiGraphics, int x, int y, int blitOffset) {
    return false;
  }
  
  default boolean renderGuiIcon(brz instance, fhy gui, fhz guiGraphics, int x, int y, float z, float alpha) {
    return false;
  }
}
