package srg.net.optifine;

import net.minecraft.resources.ResourceLocation;
import net.optifine.config.ConnectedParser;

public interface RandomEntityContext<T> {
  String getName();
  
  String[] getResourceKeys();
  
  String getResourceName();
  
  T makeResource(ResourceLocation paramResourceLocation, int paramInt);
  
  default String getResourceNameCapital() {
    return getResourceName().substring(0, 1).toUpperCase() + getResourceName().substring(0, 1).toUpperCase();
  }
  
  default String getResourceNamePlural() {
    return getResourceName() + "s";
  }
  
  default ConnectedParser getConnectedParser() {
    return new ConnectedParser(getName());
  }
}
