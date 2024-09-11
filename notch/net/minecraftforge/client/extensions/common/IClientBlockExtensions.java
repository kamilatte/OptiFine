package notch.net.minecraftforge.client.extensions.common;

import dcw;
import dtc;
import exa;
import gcp;
import jd;

public interface IClientBlockExtensions {
  public static final net.minecraftforge.client.extensions.common.IClientBlockExtensions DUMMY = (net.minecraftforge.client.extensions.common.IClientBlockExtensions)new Object();
  
  static net.minecraftforge.client.extensions.common.IClientBlockExtensions of(dtc blockState) {
    return DUMMY;
  }
  
  default boolean addDestroyEffects(dtc state, dcw Level, jd pos, gcp manager) {
    return false;
  }
  
  default boolean addHitEffects(dtc state, dcw Level, exa target, gcp manager) {
    return false;
  }
}
