package notch.net.optifine;

import akr;
import net.optifine.config.ConnectedParser;

public interface RandomEntityContext<T> {
  String getName();
  
  String[] getResourceKeys();
  
  String getResourceName();
  
  T makeResource(akr paramakr, int paramInt);
  
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
