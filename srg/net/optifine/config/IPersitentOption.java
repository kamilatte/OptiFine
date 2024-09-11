package srg.net.optifine.config;

import net.minecraft.client.Options;

public interface IPersitentOption {
  String getSaveKey();
  
  void loadValue(Options paramOptions, String paramString);
  
  String getSaveText(Options paramOptions);
}
