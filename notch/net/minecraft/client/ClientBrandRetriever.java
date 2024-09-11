package notch.net.minecraft.client;

import net.minecraft.obfuscate.DontObfuscate;
import net.optifine.reflect.Reflector;

public class ClientBrandRetriever {
  public static final String a = "vanilla";
  
  @DontObfuscate
  public static String getClientModName() {
    if (Reflector.BrandingControl_getBranding.exists())
      return Reflector.BrandingControl_getBranding.callString(new Object[0]); 
    return "optifine";
  }
}
