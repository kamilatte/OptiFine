package srg.net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomPanoramaProperties;
import net.optifine.util.MathUtils;
import net.optifine.util.PropertiesOrdered;

public class CustomPanorama {
  private static CustomPanoramaProperties customPanoramaProperties = null;
  
  private static final Random random = new Random();
  
  public static CustomPanoramaProperties getCustomPanoramaProperties() {
    return customPanoramaProperties;
  }
  
  public static void update() {
    PropertiesOrdered propertiesOrdered;
    customPanoramaProperties = null;
    String[] folders = getPanoramaFolders();
    if (folders.length <= 1)
      return; 
    Properties[] properties = getPanoramaProperties(folders);
    int[] weights = getWeights(properties);
    int index = getRandomIndex(weights);
    String folder = folders[index];
    Properties props = properties[index];
    if (props == null)
      props = properties[0]; 
    if (props == null)
      propertiesOrdered = new PropertiesOrdered(); 
    CustomPanoramaProperties cpp = new CustomPanoramaProperties(folder, (Properties)propertiesOrdered);
    customPanoramaProperties = cpp;
  }
  
  private static String[] getPanoramaFolders() {
    List<String> listFolders = new ArrayList<>();
    listFolders.add("textures/gui/title/background");
    for (int i = 0; i < 100; i++) {
      String folder = "optifine/gui/background" + i;
      String path = folder + "/panorama_0.png";
      ResourceLocation loc = new ResourceLocation(path);
      if (Config.hasResource(loc))
        listFolders.add(folder); 
    } 
    String[] folders = listFolders.<String>toArray(new String[listFolders.size()]);
    return folders;
  }
  
  private static Properties[] getPanoramaProperties(String[] folders) {
    Properties[] propsArr = new Properties[folders.length];
    for (int i = 0; i < folders.length; i++) {
      String folder = folders[i];
      if (i == 0) {
        folder = "optifine/gui";
      } else {
        Config.dbg("CustomPanorama: " + folder);
      } 
      ResourceLocation propLoc = new ResourceLocation(folder + "/background.properties");
      try {
        InputStream in = Config.getResourceStream(propLoc);
        if (in != null) {
          PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
          propertiesOrdered.load(in);
          Config.dbg("CustomPanorama: " + propLoc.getPath());
          propsArr[i] = (Properties)propertiesOrdered;
          in.close();
        } 
      } catch (IOException iOException) {}
    } 
    return propsArr;
  }
  
  private static int[] getWeights(Properties[] properties) {
    int[] weights = new int[properties.length];
    for (int i = 0; i < weights.length; i++) {
      Properties prop = properties[i];
      if (prop == null)
        prop = properties[0]; 
      if (prop == null) {
        weights[i] = 1;
      } else {
        String str = prop.getProperty("weight", null);
        weights[i] = Config.parseInt(str, 1);
      } 
    } 
    return weights;
  }
  
  private static int getRandomIndex(int[] weights) {
    int sumWeights = MathUtils.getSum(weights);
    int r = random.nextInt(sumWeights);
    int sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += weights[i];
      if (sum > r)
        return i; 
    } 
    return weights.length - 1;
  }
}
