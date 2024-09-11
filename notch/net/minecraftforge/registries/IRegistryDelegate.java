package notch.net.minecraftforge.registries;

import akr;

public interface IRegistryDelegate<T> {
  T get();
  
  akr name();
  
  Class<T> type();
}
