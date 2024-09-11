package notch.net.optifine.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
  public static List<String> collectFiles(File folder, boolean recursive) {
    List<String> files = new ArrayList<>();
    collectFiles(folder, "", files, recursive);
    return files;
  }
  
  public static void collectFiles(File folder, String basePath, List<String> list, boolean recursive) {
    File[] files = folder.listFiles();
    if (files == null)
      return; 
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isFile()) {
        String name = basePath + basePath;
        list.add(name);
      } else if (recursive && file.isDirectory()) {
        String dirPath = basePath + basePath + "/";
        collectFiles(file, dirPath, list, recursive);
      } 
    } 
  }
}
