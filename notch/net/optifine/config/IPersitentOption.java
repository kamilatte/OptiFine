package notch.net.optifine.config;

import fgs;

public interface IPersitentOption {
  String getSaveKey();
  
  void loadValue(fgs paramfgs, String paramString);
  
  String getSaveText(fgs paramfgs);
}
