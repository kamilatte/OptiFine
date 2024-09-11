package srg.net.optifine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.optifine.Config;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ResUtils {
  public static String[] collectFiles(String prefix, String suffix) {
    return collectFiles(new String[] { prefix }, new String[] { suffix });
  }
  
  public static String[] collectFiles(String[] prefixes, String[] suffixes) {
    Set<String> setPaths = new LinkedHashSet<>();
    PackResources[] rps = Config.getResourcePacks();
    for (int i = 0; i < rps.length; i++) {
      PackResources rp = rps[i];
      String[] ps = collectFiles(rp, prefixes, suffixes, null);
      setPaths.addAll(Arrays.asList(ps));
    } 
    String[] paths = setPaths.<String>toArray(new String[setPaths.size()]);
    return paths;
  }
  
  public static String[] collectFiles(PackResources rp, String prefix, String suffix, String[] defaultPaths) {
    return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
  }
  
  public static String[] collectFiles(PackResources rp, String[] prefixes, String[] suffixes) {
    return collectFiles(rp, prefixes, suffixes, null);
  }
  
  public static String[] collectFiles(PackResources rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
    if (rp instanceof CompositePackResources) {
      CompositePackResources crp = (CompositePackResources)rp;
      List<String> pathsList = new ArrayList<>();
      for (PackResources subRp : crp.packResourcesStack) {
        String[] subPaths = collectFiles(subRp, prefixes, suffixes, defaultPaths);
        pathsList.addAll(Arrays.asList(subPaths));
      } 
      String[] paths = pathsList.<String>toArray(new String[pathsList.size()]);
      return paths;
    } 
    if (rp instanceof net.minecraft.server.packs.VanillaPackResources)
      return collectFilesFixed(rp, defaultPaths); 
    File tpFile = null;
    if (rp instanceof FilePackResources) {
      FilePackResources fpr = (FilePackResources)rp;
      tpFile = fpr.getFile();
    } else if (rp instanceof PathPackResources) {
      PathPackResources ppr = (PathPackResources)rp;
      tpFile = ppr.root.toFile();
    } else {
      Config.warn("Unknown resource pack type: " + String.valueOf(rp));
      return new String[0];
    } 
    if (tpFile == null)
      return new String[0]; 
    if (tpFile.isDirectory())
      return collectFilesFolder(tpFile, "", prefixes, suffixes); 
    if (tpFile.isFile())
      return collectFilesZIP(tpFile, prefixes, suffixes); 
    Config.warn("Unknown resource pack file: " + String.valueOf(tpFile));
    return new String[0];
  }
  
  private static String[] collectFilesFixed(PackResources rp, String[] paths) {
    if (paths == null)
      return new String[0]; 
    List<String> list = new ArrayList();
    for (int i = 0; i < paths.length; i++) {
      String path = paths[i];
      if (!isLowercase(path)) {
        Config.warn("Skipping non-lowercase path: " + path);
      } else {
        ResourceLocation loc = new ResourceLocation(path);
        if (Config.hasResource(rp, loc))
          list.add(path); 
      } 
    } 
    String[] pathArr = list.<String>toArray(new String[list.size()]);
    return pathArr;
  }
  
  private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
    List<String> list = new ArrayList();
    String prefixAssets = "assets/minecraft/";
    File[] files = tpFile.listFiles();
    if (files == null)
      return new String[0]; 
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isFile()) {
        String name = basePath + basePath;
        if (name.startsWith(prefixAssets)) {
          name = name.substring(prefixAssets.length());
          if (StrUtils.startsWith(name, prefixes))
            if (StrUtils.endsWith(name, suffixes))
              if (!isLowercase(name)) {
                Config.warn("Skipping non-lowercase path: " + name);
              } else {
                list.add(name);
              }   
        } 
      } else if (file.isDirectory()) {
        String dirPath = basePath + basePath + "/";
        String[] arrayOfString = collectFilesFolder(file, dirPath, prefixes, suffixes);
        for (int n = 0; n < arrayOfString.length; n++) {
          String name = arrayOfString[n];
          list.add(name);
        } 
      } 
    } 
    String[] names = list.<String>toArray(new String[list.size()]);
    return names;
  }
  
  private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
    List<String> list = new ArrayList();
    String prefixAssets = "assets/minecraft/";
    try {
      ZipFile zf = new ZipFile(tpFile);
      Enumeration<? extends ZipEntry> en = zf.entries();
      while (en.hasMoreElements()) {
        ZipEntry ze = en.nextElement();
        String name = ze.getName();
        if (!name.startsWith(prefixAssets))
          continue; 
        name = name.substring(prefixAssets.length());
        if (!StrUtils.startsWith(name, prefixes))
          continue; 
        if (!StrUtils.endsWith(name, suffixes))
          continue; 
        if (!isLowercase(name)) {
          Config.warn("Skipping non-lowercase path: " + name);
          continue;
        } 
        list.add(name);
      } 
      zf.close();
      String[] names = list.<String>toArray(new String[list.size()]);
      return names;
    } catch (IOException e) {
      e.printStackTrace();
      return new String[0];
    } 
  }
  
  private static boolean isLowercase(String str) {
    return str.equals(str.toLowerCase(Locale.ROOT));
  }
  
  public static Properties readProperties(String path, String module) {
    ResourceLocation loc = new ResourceLocation(path);
    try {
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return null; 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      Config.dbg(module + ": Loading " + module);
      return (Properties)propertiesOrdered;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      Config.warn(module + ": Error reading " + module);
      return null;
    } 
  }
  
  public static Properties readProperties(InputStream in, String module) {
    if (in == null)
      return null; 
    try {
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      return (Properties)propertiesOrdered;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      return null;
    } 
  }
}
