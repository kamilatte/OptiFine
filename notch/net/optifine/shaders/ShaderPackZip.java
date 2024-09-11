package notch.net.optifine.shaders;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.optifine.Config;
import net.optifine.shaders.IShaderPack;
import net.optifine.util.StrUtils;

public class ShaderPackZip implements IShaderPack {
  protected File packFile;
  
  protected ZipFile packZipFile;
  
  protected String baseFolder;
  
  public ShaderPackZip(String name, File file) {
    this.packFile = file;
    this.packZipFile = null;
    this.baseFolder = "";
  }
  
  public void close() {
    if (this.packZipFile == null)
      return; 
    try {
      this.packZipFile.close();
    } catch (Exception exception) {}
    this.packZipFile = null;
  }
  
  public InputStream getResourceAsStream(String resName) {
    try {
      if (this.packZipFile == null) {
        this.packZipFile = new ZipFile(this.packFile);
        this.baseFolder = detectBaseFolder(this.packZipFile);
      } 
      String name = StrUtils.removePrefix(resName, "/");
      if (name.contains(".."))
        name = resolveRelative(name); 
      ZipEntry entry = this.packZipFile.getEntry(this.baseFolder + this.baseFolder);
      if (entry == null)
        return null; 
      return this.packZipFile.getInputStream(entry);
    } catch (Exception excp) {
      return null;
    } 
  }
  
  private String resolveRelative(String name) {
    Deque<String> stack = new ArrayDeque<>();
    String[] parts = Config.tokenize(name, "/");
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      if (part.equals("..")) {
        if (stack.isEmpty())
          return ""; 
        stack.removeLast();
      } else {
        stack.add(part);
      } 
    } 
    String path = Joiner.on('/').join(stack);
    return path;
  }
  
  private String detectBaseFolder(ZipFile zip) {
    ZipEntry entryShaders = zip.getEntry("shaders/");
    if (entryShaders != null)
      if (entryShaders.isDirectory())
        return "";  
    Pattern patternFolderShaders = Pattern.compile("([^/]+/)shaders/");
    Enumeration<? extends ZipEntry> entries = zip.entries();
    while (entries.hasMoreElements()) {
      ZipEntry entry = entries.nextElement();
      String name = entry.getName();
      Matcher matcher = patternFolderShaders.matcher(name);
      if (matcher.matches()) {
        String base = matcher.group(1);
        if (base == null)
          continue; 
        if (base.equals("shaders/"))
          return ""; 
        return base;
      } 
    } 
    return "";
  }
  
  public boolean hasDirectory(String resName) {
    try {
      if (this.packZipFile == null) {
        this.packZipFile = new ZipFile(this.packFile);
        this.baseFolder = detectBaseFolder(this.packZipFile);
      } 
      String name = StrUtils.removePrefix(resName, "/");
      ZipEntry entry = this.packZipFile.getEntry(this.baseFolder + this.baseFolder);
      if (entry == null)
        return false; 
      return true;
    } catch (IOException e) {
      return false;
    } 
  }
  
  public String getName() {
    return this.packFile.getName();
  }
}
