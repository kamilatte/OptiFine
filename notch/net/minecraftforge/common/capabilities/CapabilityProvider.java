package notch.net.minecraftforge.common.capabilities;

import jo;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import ub;

public abstract class CapabilityProvider<B> {
  protected CapabilityProvider(Class<B> baseClass) {}
  
  public final void gatherCapabilities() {}
  
  protected final CapabilityDispatcher getCapabilities() {
    return null;
  }
  
  protected final void deserializeCaps(jo.a registryAccess, ub tag) {}
  
  protected final ub serializeCaps(jo.a registryAccess) {
    return null;
  }
  
  public void invalidateCaps() {}
}
