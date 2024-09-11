package notch.net.optifine.util;

import zg;

public class PacketRunnable implements Runnable {
  private zg packet;
  
  private Runnable runnable;
  
  public PacketRunnable(zg packet, Runnable runnable) {
    this.packet = packet;
    this.runnable = runnable;
  }
  
  public void run() {
    this.runnable.run();
  }
  
  public zg getPacket() {
    return this.packet;
  }
  
  public String toString() {
    return "PacketRunnable: " + String.valueOf(this.packet);
  }
}
