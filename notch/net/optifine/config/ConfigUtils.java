package notch.net.optifine.config;

import akr;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.optifine.Config;
import net.optifine.util.PropertiesOrdered;

public class ConfigUtils {
  public static String readString(String fileName, String property) {
    Properties props = readProperties(fileName);
    if (props == null)
      return null; 
    String val = props.getProperty(property);
    if (val != null)
      val = val.trim(); 
    return val;
  }
  
  public static Properties readProperties(String fileName) {
    try {
      akr loc = new akr(fileName);
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return null; 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      return (Properties)propertiesOrdered;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      Config.warn("Error parsing: " + fileName);
      Config.warn(e.getClass().getName() + ": " + e.getClass().getName());
      return null;
    } 
  }
}
