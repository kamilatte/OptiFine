package notch.net.minecraftforge.fml.common.registry;

import akr;

public interface RegistryDelegate<T> {
  T get();
  
  akr name();
  
  Class<T> type();
}
