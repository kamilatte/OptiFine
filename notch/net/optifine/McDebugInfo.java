package notch.net.optifine;

import fgo;

public class McDebugInfo {
  private fgo minecraft = fgo.Q();
  
  private String mcDebug = this.minecraft.z;
  
  public boolean isChanged() {
    if (this.mcDebug == this.minecraft.z)
      return false; 
    this.mcDebug = this.minecraft.z;
    return true;
  }
}
