package optifine;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;

public class OptiFineTransformer implements ITransformer<ClassNode>, IResourceProvider, IOptiFineResourceLocator {
  private static final Logger LOGGER = LogManager.getLogger();
  
  private ZipFile ofZipFile;
  
  private String forgeJarUrlStr;
  
  private Map<String, String> patchMap = null;
  
  private Pattern[] patterns = null;
  
  public static final String PREFIX_SRG = "srg/";
  
  public static final String SUFFIX_CLASS = ".class";
  
  public static final String PREFIX_PATCH_SRG = "patch/srg/";
  
  public static final String SUFFIX_CLASS_XDELTA = ".class.xdelta";
  
  public static final String PREFIX_OPTIFINE = "optifine/";
  
  private final boolean hasTargetPreClass;
  
  public OptiFineTransformer(ZipFile ofZipFile, IEnvironment env) {
    this.ofZipFile = ofZipFile;
    this.hasTargetPreClass = hasTargetPreClass(env);
    if (this.hasTargetPreClass) {
      LOGGER.info("Target.PRE_CLASS is available");
    } else {
      LOGGER.info("Target.PRE_CLASS is not available");
    } 
    try {
      this.patchMap = Patcher.getConfigurationMap(ofZipFile);
      this.patterns = Patcher.getConfigurationPatterns(this.patchMap);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    try {
      Class<?> cls = Class.forName("net.minecraft.client.Minecraft");
      URL forgeJarUrl = cls.getProtectionDomain().getCodeSource().getLocation();
      this.forgeJarUrlStr = forgeJarUrl.toString();
      LOGGER.info("Forge JAR URL: " + this.forgeJarUrlStr);
    } catch (Exception e) {
      LOGGER.info("Forge JAR not available");
    } 
  }
  
  public TransformerVoteResult castVote(ITransformerVotingContext context) {
    return TransformerVoteResult.YES;
  }
  
  public TargetType<ClassNode> getTargetType() {
    return TargetType.PRE_CLASS;
  }
  
  public Set<ITransformer.Target<ClassNode>> targets() {
    Set<ITransformer.Target<ClassNode>> set = new HashSet<>();
    String[] names = getResourceNames("srg/", ".class");
    String[] namesPatch = getResourceNames("patch/srg/", ".class.xdelta");
    names = (String[])Utils.addObjectsToArray((Object[])names, (Object[])namesPatch);
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      name = Utils.removePrefix(name, new String[] { "srg/", "patch/srg/" });
      name = Utils.removeSuffix(name, new String[] { ".class", ".class.xdelta" });
      if (!name.startsWith("net/optifine/")) {
        ITransformer.Target<ClassNode> itt = getTargetClass(name);
        set.add(itt);
      } 
    } 
    LOGGER.info("Targets: " + set.size());
    return set;
  }
  
  private ITransformer.Target getTargetClass(String name) {
    if (this.hasTargetPreClass)
      return getTargetPreClass(name); 
    return ITransformer.Target.targetClass(name);
  }
  
  private ITransformer.Target getTargetPreClass(String name) {
    return ITransformer.Target.targetPreClass(name);
  }
  
  private static boolean hasTargetPreClass(IEnvironment env) {
    Optional<String> mlVer = env.getProperty(IEnvironment.Keys.MLSPEC_VERSION.get());
    if (!mlVer.isPresent())
      return false; 
    String[] parts = Utils.tokenize(mlVer.get(), ".");
    if (parts.length <= 0)
      return false; 
    String majorStr = parts[0];
    int major = Utils.parseInt(majorStr, -1);
    if (major < 7)
      return false; 
    return true;
  }
  
  public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
    ClassNode classNode = input;
    String className = context.getClassName();
    String classNameZip = className.replace('.', '/');
    byte[] bytes = getOptiFineResource("srg/" + classNameZip + ".class");
    if (bytes != null) {
      InputStream in = new ByteArrayInputStream(bytes);
      ClassNode classNodeNew = loadClass(in);
      if (classNodeNew != null) {
        debugClass(classNodeNew);
        AccessFixer.fixMemberAccess(classNode, classNodeNew);
        classNode = classNodeNew;
      } 
    } 
    return classNode;
  }
  
  private void debugClass(ClassNode classNode) {}
  
  private ClassNode loadClass(InputStream in) {
    try {
      ClassReader cr = new ClassReader(in);
      ClassNode cn = new ClassNode(393216);
      cr.accept((ClassVisitor)cn, 0);
      return cn;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  private String[] getResourceNames(String prefix, String suffix) {
    List<String> list = new ArrayList<>();
    Enumeration<? extends ZipEntry> entries = this.ofZipFile.entries();
    while (entries.hasMoreElements()) {
      ZipEntry zipEntry = entries.nextElement();
      String name = zipEntry.getName();
      if (!name.startsWith(prefix))
        continue; 
      if (!name.endsWith(suffix))
        continue; 
      list.add(name);
    } 
    String[] names = list.<String>toArray(new String[list.size()]);
    return names;
  }
  
  private byte[] getOptiFineResource(String name) {
    try {
      InputStream in = getOptiFineResourceStream(name);
      if (in == null)
        return null; 
      byte[] bytes = Utils.readAll(in);
      in.close();
      return bytes;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public synchronized InputStream getOptiFineResourceStream(String name) {
    name = Utils.removePrefix(name, "/");
    InputStream in = getOptiFineResourceStreamZip(name);
    if (in != null)
      return in; 
    in = getOptiFineResourceStreamPatched(name);
    if (in != null)
      return in; 
    return null;
  }
  
  public InputStream getResourceStream(String path) {
    path = Utils.removePrefix(path, "/");
    try {
      Enumeration<URL> urlsEnum = ClassLoader.getSystemClassLoader().getResources(path);
      while (urlsEnum.hasMoreElements()) {
        URL url = urlsEnum.nextElement();
        if (this.forgeJarUrlStr != null && url.getPath().startsWith(this.forgeJarUrlStr))
          continue; 
        return url.openStream();
      } 
      return null;
    } catch (IOException e) {
      return null;
    } 
  }
  
  public synchronized InputStream getOptiFineResourceStreamZip(String name) {
    if (this.ofZipFile == null)
      return null; 
    name = Utils.removePrefix(name, "/");
    ZipEntry ze = this.ofZipFile.getEntry(name);
    if (ze == null)
      return null; 
    try {
      InputStream in = this.ofZipFile.getInputStream(ze);
      return in;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public synchronized byte[] getOptiFineResourceZip(String name) {
    InputStream in = getOptiFineResourceStreamZip(name);
    if (in == null)
      return null; 
    try {
      byte[] bytes = Utils.readAll(in);
      return bytes;
    } catch (IOException e) {
      return null;
    } 
  }
  
  public synchronized InputStream getOptiFineResourceStreamPatched(String name) {
    byte[] bytes = getOptiFineResourcePatched(name);
    if (bytes == null)
      return null; 
    return new ByteArrayInputStream(bytes);
  }
  
  public synchronized byte[] getOptiFineResourcePatched(String name) {
    if (this.patterns == null || this.patchMap == null)
      return null; 
    name = Utils.removePrefix(name, "/");
    String patchName = "patch/" + name + ".xdelta";
    byte[] bytes = getOptiFineResourceZip(patchName);
    if (bytes == null)
      return null; 
    try {
      byte[] bytesPatched = Patcher.applyPatch(name, bytes, this.patterns, this.patchMap, this);
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
}
