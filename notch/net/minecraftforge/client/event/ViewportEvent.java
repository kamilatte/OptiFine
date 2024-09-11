package notch.net.minecraftforge.client.event;

import ffy;
import ges;
import net.minecraftforge.eventbus.api.Event;

public abstract class ViewportEvent extends Event {
  public ViewportEvent(ges renderer, ffy camera, double partialTick) {}
  
  public ges getRenderer() {
    return null;
  }
  
  public ffy getCamera() {
    return null;
  }
  
  public double getPartialTick() {
    return 0.0D;
  }
}
