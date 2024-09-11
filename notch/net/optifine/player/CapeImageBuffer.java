package notch.net.optifine.player;

import akr;
import faj;
import gdy;
import net.optifine.player.CapeUtils;

public class CapeImageBuffer implements Runnable {
  private gdy player;
  
  private akr resourceLocation;
  
  private boolean elytraOfCape;
  
  public CapeImageBuffer(gdy player, akr resourceLocation) {
    this.player = player;
    this.resourceLocation = resourceLocation;
  }
  
  public void run() {}
  
  public faj parseUserSkin(faj imageRaw) {
    faj image = CapeUtils.parseCape(imageRaw);
    this.elytraOfCape = CapeUtils.isElytraCape(imageRaw, image);
    return image;
  }
  
  public void skinAvailable() {
    if (this.player != null) {
      this.player.setLocationOfCape(this.resourceLocation);
      this.player.setElytraOfCape(this.elytraOfCape);
    } 
    cleanup();
  }
  
  public void cleanup() {
    this.player = null;
  }
  
  public boolean isElytraOfCape() {
    return this.elytraOfCape;
  }
}
