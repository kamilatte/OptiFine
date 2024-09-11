package optifine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptiFineClassTransformer implements IClassTransformer, IResourceProvider, IOptiFineResourceLocator {
  private ZipFile ofZipFile = null;
  
  private Map<String, String> patchMap = null;
  
  private Pattern[] patterns = null;
  
  public static OptiFineClassTransformer instance = null;
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  public OptiFineClassTransformer() {
    instance = this;
    try {
      dbg("OptiFine ClassTransformer");
      URL url = OptiFineClassTransformer.class.getProtectionDomain().getCodeSource().getLocation();
      URI uri = url.toURI();
      File file = new File(uri);
      this.ofZipFile = new ZipFile(file);
      dbg("OptiFine ZIP file: " + file);
      this.patchMap = Patcher.getConfigurationMap(this.ofZipFile);
      this.patterns = Patcher.getConfigurationPatterns(this.patchMap);
      OptiFineResourceLocator.setResourceLocator(this);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (this.ofZipFile == null) {
      dbg("*** Can not find the OptiFine JAR in the classpath ***");
      dbg("*** OptiFine will not be loaded! ***");
    } 
  }
  
  public byte[] transform(String name, String transformedName, byte[] bytes) {
    String nameClass = String.valueOf(name.replace('.', '/')) + ".class";
    byte[] ofBytes = getOptiFineResource(nameClass);
    if (ofBytes == null) {
      nameClass = "notch/" + nameClass;
      ofBytes = getOptiFineResource(nameClass);
    } 
    if (ofBytes != null)
      return ofBytes; 
    return bytes;
  }
  
  public synchronized InputStream getOptiFineResourceStream(String name) {
    name = Utils.removePrefix(name, "/");
    byte[] bytes = getOptiFineResource(name);
    if (bytes == null)
      return null; 
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    return in;
  }
  
  public InputStream getResourceStream(String path) {
    path = Utils.ensurePrefix(path, "/");
    return getClass().getResourceAsStream(path);
  }
  
  public synchronized byte[] getOptiFineResource(String name) {
    name = Utils.removePrefix(name, "/");
    byte[] bytes = getOptiFineResourceZip(name);
    if (bytes != null)
      return bytes; 
    bytes = getOptiFineResourcePatched(name, this);
    if (bytes != null)
      return bytes; 
    return null;
  }
  
  public synchronized byte[] getOptiFineResourceZip(String name) {
    if (this.ofZipFile == null)
      return null; 
    name = Utils.removePrefix(name, "/");
    ZipEntry ze = this.ofZipFile.getEntry(name);
    if (ze == null)
      return null; 
    try {
      InputStream in = this.ofZipFile.getInputStream(ze);
      byte[] bytes = readAll(in);
      in.close();
      if (bytes.length != ze.getSize()) {
        dbg("Invalid size, name: " + name + ", zip: " + ze.getSize() + ", stream: " + bytes.length);
        return null;
      } 
      return bytes;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public synchronized byte[] getOptiFineResourcePatched(String name, IResourceProvider resourceProvider) {
    if (this.patterns == null || this.patchMap == null || resourceProvider == null)
      return null; 
    name = Utils.removePrefix(name, "/");
    String patchName = "patch/" + name + ".xdelta";
    byte[] bytes = getOptiFineResourceZip(patchName);
    if (bytes == null)
      return null; 
    try {
      byte[] bytesPatched = Patcher.applyPatch(name, bytes, this.patterns, this.patchMap, resourceProvider);
      String fullMd5Name = "patch/" + name + ".md5";
      byte[] bytesMd5 = getOptiFineResourceZip(fullMd5Name);
      if (bytesMd5 != null) {
        String md5Str = new String(bytesMd5, "ASCII");
        byte[] md5Mod = HashUtils.getHashMd5(bytesPatched);
        String md5ModStr = HashUtils.toHexString(md5Mod);
        if (!md5Str.equals(md5ModStr))
          throw new IOException("MD5 not matching, name: " + name + ", saved: " + md5Str + ", patched: " + md5ModStr); 
      } 
      return bytesPatched;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static byte[] readAll(InputStream is) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    while (true) {
      int len = is.read(buf);
      if (len < 0)
        break; 
      baos.write(buf, 0, len);
    } 
    is.close();
    byte[] bytes = baos.toByteArray();
    return bytes;
  }
  
  private static void dbg(String str) {
    LOGGER.info("[OptiFine] " + str);
  }
}
