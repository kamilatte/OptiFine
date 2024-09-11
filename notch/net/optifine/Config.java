package notch.net.optifine;

import ad;
import akr;
import asq;
import ass;
import asu;
import atm;
import atp;
import atw;
import auc;
import aue;
import blr;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import fac;
import fgh;
import fgo;
import fgs;
import fnt;
import fod;
import ges;
import gex;
import gia;
import gkh;
import gqk;
import gqm;
import grr;
import gst;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import jd;
import net.optifine.ComparableVersion;
import net.optifine.DynamicLights;
import net.optifine.GlErrors;
import net.optifine.VersionCheckThread;
import net.optifine.config.GlVersion;
import net.optifine.gui.GuiMessage;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import wz;

public class Config {
  public static final String OF_NAME = "OptiFine";
  
  public static final String MC_VERSION = "1.21.1";
  
  public static final String OF_EDITION = "HD_U";
  
  public static final String OF_RELEASE = "J1_pre10";
  
  public static final String VERSION = "OptiFine_1.21.1_HD_U_J1_pre10";
  
  private static String build = null;
  
  private static String newRelease = null;
  
  private static boolean notify64BitJava = false;
  
  public static String openGlVersion = null;
  
  public static String openGlRenderer = null;
  
  public static String openGlVendor = null;
  
  public static String[] openGlExtensions = null;
  
  public static GlVersion glVersion = null;
  
  public static GlVersion glslVersion = null;
  
  public static int minecraftVersionInt = -1;
  
  private static fgs gameSettings = null;
  
  private static fgo minecraft = fgo.Q();
  
  private static boolean initialized = false;
  
  private static Thread minecraftThread = null;
  
  private static int antialiasingLevel = 0;
  
  private static int availableProcessors = 0;
  
  public static boolean zoomMode = false;
  
  public static boolean zoomSmoothCamera = false;
  
  private static int texturePackClouds = 0;
  
  private static boolean fullscreenModeChecked = false;
  
  private static boolean desktopModeChecked = false;
  
  public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
  
  private static final Logger LOGGER = LogManager.getLogger();
  
  public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
  
  private static String mcDebugLast = null;
  
  private static int fpsMinLast = 0;
  
  private static int chunkUpdatesLast = 0;
  
  private static gqk textureMapTerrain;
  
  private static long timeLastFrameMs;
  
  private static long averageFrameTimeMs;
  
  private static long lastFrameTimeMs;
  
  private static boolean showFrameTime = Boolean.getBoolean("frame.time");
  
  public static String getVersion() {
    return "OptiFine_1.21.1_HD_U_J1_pre10";
  }
  
  public static String getVersionDebug() {
    StringBuffer sb = new StringBuffer(32);
    if (isDynamicLights()) {
      sb.append("DL: ");
      sb.append(String.valueOf(DynamicLights.getCount()));
      sb.append(", ");
    } 
    sb.append("OptiFine_1.21.1_HD_U_J1_pre10");
    String shaderPack = Shaders.getShaderPackName();
    if (shaderPack != null) {
      sb.append(", ");
      sb.append(shaderPack);
    } 
    return sb.toString();
  }
  
  public static void initGameSettings(fgs settings) {
    if (gameSettings != null)
      return; 
    gameSettings = settings;
    updateAvailableProcessors();
    ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
    String forgeVer = (String)Reflector.ForgeVersion_getVersion.call(new Object[0]);
    if (forgeVer != null) {
      dbg("Forge version: " + forgeVer);
      ComparableVersion cv = new ComparableVersion(forgeVer);
      ComparableVersion cvMax = new ComparableVersion("47.0.3");
      if (cv.compareTo(cvMax) > 1) {
        dbg("Forge version above " + String.valueOf(cvMax) + ", antialiasing disabled");
        gameSettings.ofAaLevel = 0;
      } 
    } 
    antialiasingLevel = gameSettings.ofAaLevel;
  }
  
  public static void initDisplay() {
    checkInitialized();
    minecraftThread = Thread.currentThread();
    updateThreadPriorities();
    Shaders.startup(fgo.Q());
  }
  
  public static void checkInitialized() {
    if (initialized)
      return; 
    if (fgo.Q().aM() == null)
      return; 
    initialized = true;
    checkOpenGlCaps();
    startVersionCheckThread();
  }
  
  private static void checkOpenGlCaps() {
    log("");
    log(getVersion());
    log("Build: " + getBuild());
    log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
    log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
    log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
    log("LWJGL: " + GLFW.glfwGetVersionString());
    openGlVersion = GL11.glGetString(7938);
    openGlRenderer = GL11.glGetString(7937);
    openGlVendor = GL11.glGetString(7936);
    log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
    log("OpenGL Version: " + getOpenGlVersionString());
    int maxTexSize = TextureUtils.getGLMaximumTextureSize();
    dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
  }
  
  public static String getBuild() {
    if (build == null)
      try {
        InputStream in = getOptiFineResourceStream("/buildof.txt");
        if (in == null)
          return null; 
        build = readLines(in)[0];
      } catch (Exception e) {
        warn(e.getClass().getName() + ": " + e.getClass().getName());
        build = "";
      }  
    return build;
  }
  
  public static InputStream getOptiFineResourceStream(String name) {
    InputStream in = ReflectorForge.getOptiFineResourceStream(name);
    if (in != null)
      return in; 
    return net.optifine.Config.class.getResourceAsStream(name);
  }
  
  public static int getMinecraftVersionInt() {
    if (minecraftVersionInt < 0) {
      String mcVer = "1.21.1";
      if (mcVer.contains("-"))
        mcVer = tokenize(mcVer, "-")[0]; 
      String[] verStrs = tokenize(mcVer, ".");
      int ver = 0;
      if (verStrs.length > 0)
        ver += 10000 * parseInt(verStrs[0], 0); 
      if (verStrs.length > 1)
        ver += 100 * parseInt(verStrs[1], 0); 
      if (verStrs.length > 2)
        ver += 1 * parseInt(verStrs[2], 0); 
      minecraftVersionInt = ver;
    } 
    return minecraftVersionInt;
  }
  
  public static String getOpenGlVersionString() {
    GlVersion ver = getGlVersion();
    String verStr = "" + ver.getMajor() + "." + ver.getMajor() + "." + ver.getMinor();
    return verStr;
  }
  
  private static GlVersion getGlVersionLwjgl() {
    GLCapabilities glCapabilities = GL.getCapabilities();
    if (glCapabilities.OpenGL44)
      return new GlVersion(4, 4); 
    if (glCapabilities.OpenGL43)
      return new GlVersion(4, 3); 
    if (glCapabilities.OpenGL42)
      return new GlVersion(4, 2); 
    if (glCapabilities.OpenGL41)
      return new GlVersion(4, 1); 
    if (glCapabilities.OpenGL40)
      return new GlVersion(4, 0); 
    if (glCapabilities.OpenGL33)
      return new GlVersion(3, 3); 
    if (glCapabilities.OpenGL32)
      return new GlVersion(3, 2); 
    if (glCapabilities.OpenGL31)
      return new GlVersion(3, 1); 
    if (glCapabilities.OpenGL30)
      return new GlVersion(3, 0); 
    if (glCapabilities.OpenGL21)
      return new GlVersion(2, 1); 
    if (glCapabilities.OpenGL20)
      return new GlVersion(2, 0); 
    if (glCapabilities.OpenGL15)
      return new GlVersion(1, 5); 
    if (glCapabilities.OpenGL14)
      return new GlVersion(1, 4); 
    if (glCapabilities.OpenGL13)
      return new GlVersion(1, 3); 
    if (glCapabilities.OpenGL12)
      return new GlVersion(1, 2); 
    if (glCapabilities.OpenGL11)
      return new GlVersion(1, 1); 
    return new GlVersion(1, 0);
  }
  
  public static GlVersion getGlVersion() {
    if (glVersion == null) {
      String verStr = GL11.glGetString(7938);
      glVersion = parseGlVersion(verStr, null);
      if (glVersion == null)
        glVersion = getGlVersionLwjgl(); 
      if (glVersion == null)
        glVersion = new GlVersion(1, 0); 
    } 
    return glVersion;
  }
  
  public static GlVersion getGlslVersion() {
    if (glslVersion == null) {
      String verStr = GL11.glGetString(35724);
      glslVersion = parseGlVersion(verStr, null);
      if (glslVersion == null)
        glslVersion = new GlVersion(1, 10); 
    } 
    return glslVersion;
  }
  
  public static GlVersion parseGlVersion(String versionString, GlVersion def) {
    try {
      if (versionString == null)
        return def; 
      Pattern REGEXP_VERSION = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
      Matcher matcher = REGEXP_VERSION.matcher(versionString);
      if (!matcher.matches())
        return def; 
      int major = Integer.parseInt(matcher.group(1));
      int minor = Integer.parseInt(matcher.group(2));
      int release = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
      String suffix = matcher.group(5);
      return new GlVersion(major, minor, release, suffix);
    } catch (Exception e) {
      error("", e);
      return def;
    } 
  }
  
  public static String[] getOpenGlExtensions() {
    if (openGlExtensions == null)
      openGlExtensions = detectOpenGlExtensions(); 
    return openGlExtensions;
  }
  
  private static String[] detectOpenGlExtensions() {
    try {
      GlVersion ver = getGlVersion();
      if (ver.getMajor() >= 3) {
        int countExt = GL11.glGetInteger(33309);
        if (countExt > 0) {
          String[] exts = new String[countExt];
          for (int i = 0; i < countExt; i++)
            exts[i] = GL30.glGetStringi(7939, i); 
          return exts;
        } 
      } 
    } catch (Exception e) {
      error("", e);
    } 
    try {
      String extStr = GL11.glGetString(7939);
      String[] exts = extStr.split(" ");
      return exts;
    } catch (Exception e) {
      error("", e);
      return new String[0];
    } 
  }
  
  public static void updateThreadPriorities() {
    updateAvailableProcessors();
    int ELEVATED_PRIORITY = 8;
    if (isSingleProcessor()) {
      if (isSmoothWorld()) {
        minecraftThread.setPriority(10);
        setThreadPriority("Server thread", 1);
      } else {
        minecraftThread.setPriority(5);
        setThreadPriority("Server thread", 5);
      } 
    } else {
      minecraftThread.setPriority(10);
      setThreadPriority("Server thread", 5);
    } 
  }
  
  private static void setThreadPriority(String prefix, int priority) {
    try {
      ThreadGroup tg = Thread.currentThread().getThreadGroup();
      if (tg == null)
        return; 
      int num = (tg.activeCount() + 10) * 2;
      Thread[] ts = new Thread[num];
      tg.enumerate(ts, false);
      for (int i = 0; i < ts.length; i++) {
        Thread t = ts[i];
        if (t != null)
          if (t.getName().startsWith(prefix))
            t.setPriority(priority);  
      } 
    } catch (Throwable e) {
      warn(e.getClass().getName() + ": " + e.getClass().getName());
    } 
  }
  
  public static boolean isMinecraftThread() {
    return (Thread.currentThread() == minecraftThread);
  }
  
  private static void startVersionCheckThread() {
    VersionCheckThread vct = new VersionCheckThread();
    vct.start();
  }
  
  public static boolean isMipmaps() {
    return (((Integer)gameSettings.C().c()).intValue() > 0);
  }
  
  public static int getMipmapLevels() {
    return ((Integer)gameSettings.C().c()).intValue();
  }
  
  public static int getMipmapType() {
    switch (gameSettings.ofMipmapType) {
      case 0:
        return 9986;
      case 1:
        return 9986;
      case 2:
        if (isMultiTexture())
          return 9985; 
        return 9986;
      case 3:
        if (isMultiTexture())
          return 9987; 
        return 9986;
    } 
    return 9986;
  }
  
  public static boolean isUseAlphaFunc() {
    float alphaFuncLevel = getAlphaFuncLevel();
    return (alphaFuncLevel > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F);
  }
  
  public static float getAlphaFuncLevel() {
    return DEF_ALPHA_FUNC_LEVEL.floatValue();
  }
  
  public static boolean isFogOff() {
    return (gameSettings.ofFogType == 3);
  }
  
  public static boolean isFogOn() {
    return (gameSettings.ofFogType != 3);
  }
  
  public static float getFogStart() {
    return gameSettings.ofFogStart;
  }
  
  public static void detail(String s) {
    if (logDetail)
      LOGGER.info("[OptiFine] " + s); 
  }
  
  public static void dbg(String s) {
    LOGGER.info("[OptiFine] " + s);
  }
  
  public static void warn(String s) {
    LOGGER.warn("[OptiFine] " + s);
  }
  
  public static void warn(String s, Throwable t) {
    LOGGER.warn("[OptiFine] " + s, t);
  }
  
  public static void error(String s) {
    LOGGER.error("[OptiFine] " + s);
  }
  
  public static void error(String s, Throwable t) {
    LOGGER.error("[OptiFine] " + s, t);
  }
  
  public static void log(String s) {
    dbg(s);
  }
  
  public static int getUpdatesPerFrame() {
    return gameSettings.ofChunkUpdates;
  }
  
  public static boolean isDynamicUpdates() {
    return gameSettings.ofChunkUpdatesDynamic;
  }
  
  public static boolean isGraphicsFancy() {
    return (gameSettings.j().c() != fgh.a);
  }
  
  public static boolean isGraphicsFabulous() {
    return (gameSettings.j().c() == fgh.c);
  }
  
  public static boolean isRainFancy() {
    if (gameSettings.ofRain == 0)
      return isGraphicsFancy(); 
    return (gameSettings.ofRain == 2);
  }
  
  public static boolean isRainOff() {
    return (gameSettings.ofRain == 3);
  }
  
  public static boolean isCloudsFancy() {
    if (gameSettings.ofClouds != 0)
      return (gameSettings.ofClouds == 2); 
    if (isShaders())
      if (!Shaders.shaderPackClouds.isDefault())
        return Shaders.shaderPackClouds.isFancy();  
    if (texturePackClouds != 0)
      return (texturePackClouds == 2); 
    return isGraphicsFancy();
  }
  
  public static boolean isCloudsOff() {
    if (gameSettings.ofClouds != 0)
      return (gameSettings.ofClouds == 3); 
    if (isShaders())
      if (!Shaders.shaderPackClouds.isDefault())
        return Shaders.shaderPackClouds.isOff();  
    if (texturePackClouds != 0)
      return (texturePackClouds == 3); 
    return false;
  }
  
  public static void updateTexturePackClouds() {
    texturePackClouds = 0;
    aue rm = getResourceManager();
    if (rm == null)
      return; 
    try {
      InputStream in = rm.getResourceOrThrow(new akr("optifine/color.properties")).d();
      if (in == null)
        return; 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      String cloudStr = propertiesOrdered.getProperty("clouds");
      if (cloudStr == null)
        return; 
      dbg("Texture pack clouds: " + cloudStr);
      cloudStr = cloudStr.toLowerCase();
      if (cloudStr.equals("fast"))
        texturePackClouds = 1; 
      if (cloudStr.equals("fancy"))
        texturePackClouds = 2; 
      if (cloudStr.equals("off") || cloudStr.equals("none"))
        texturePackClouds = 3; 
    } catch (Exception exception) {}
  }
  
  public static gst getModelManager() {
    return (minecraft.ar()).modelManager;
  }
  
  public static boolean isTreesFancy() {
    if (gameSettings.ofTrees == 0)
      return isGraphicsFancy(); 
    return (gameSettings.ofTrees != 1);
  }
  
  public static boolean isTreesSmart() {
    return (gameSettings.ofTrees == 4);
  }
  
  public static boolean isCullFacesLeaves() {
    if (gameSettings.ofTrees == 0)
      return !isGraphicsFancy(); 
    return (gameSettings.ofTrees == 4);
  }
  
  public static int limit(int val, int min, int max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static long limit(long val, long min, long max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static float limit(float val, float min, float max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static double limit(double val, double min, double max) {
    if (val < min)
      return min; 
    if (val > max)
      return max; 
    return val;
  }
  
  public static float limitTo1(float val) {
    if (val < 0.0F)
      return 0.0F; 
    if (val > 1.0F)
      return 1.0F; 
    return val;
  }
  
  public static boolean isAnimatedWater() {
    return (gameSettings.ofAnimatedWater != 2);
  }
  
  public static boolean isGeneratedWater() {
    return (gameSettings.ofAnimatedWater == 1);
  }
  
  public static boolean isAnimatedPortal() {
    return gameSettings.ofAnimatedPortal;
  }
  
  public static boolean isAnimatedLava() {
    return (gameSettings.ofAnimatedLava != 2);
  }
  
  public static boolean isGeneratedLava() {
    return (gameSettings.ofAnimatedLava == 1);
  }
  
  public static boolean isAnimatedFire() {
    return gameSettings.ofAnimatedFire;
  }
  
  public static boolean isAnimatedRedstone() {
    return gameSettings.ofAnimatedRedstone;
  }
  
  public static boolean isAnimatedExplosion() {
    return gameSettings.ofAnimatedExplosion;
  }
  
  public static boolean isAnimatedFlame() {
    return gameSettings.ofAnimatedFlame;
  }
  
  public static boolean isAnimatedSmoke() {
    return gameSettings.ofAnimatedSmoke;
  }
  
  public static boolean isVoidParticles() {
    return gameSettings.ofVoidParticles;
  }
  
  public static boolean isWaterParticles() {
    return gameSettings.ofWaterParticles;
  }
  
  public static boolean isRainSplash() {
    return gameSettings.ofRainSplash;
  }
  
  public static boolean isPortalParticles() {
    return gameSettings.ofPortalParticles;
  }
  
  public static boolean isPotionParticles() {
    return gameSettings.ofPotionParticles;
  }
  
  public static boolean isFireworkParticles() {
    return gameSettings.ofFireworkParticles;
  }
  
  public static float getAmbientOcclusionLevel() {
    if (isShaders())
      if (Shaders.aoLevel >= 0.0F)
        return Shaders.aoLevel;  
    return (float)gameSettings.ofAoLevel;
  }
  
  public static String listToString(List list) {
    return listToString(list, ", ");
  }
  
  public static String listToString(List list, String separator) {
    if (list == null)
      return ""; 
    StringBuffer buf = new StringBuffer(list.size() * 5);
    for (int i = 0; i < list.size(); i++) {
      Object obj = list.get(i);
      if (i > 0)
        buf.append(separator); 
      buf.append(String.valueOf(obj));
    } 
    return buf.toString();
  }
  
  public static String arrayToString(Object[] arr) {
    return arrayToString(arr, ", ");
  }
  
  public static String arrayToString(Object[] arr, String separator) {
    if (arr == null)
      return ""; 
    StringBuffer buf = new StringBuffer(arr.length * 5);
    for (int i = 0; i < arr.length; i++) {
      Object obj = arr[i];
      if (i > 0)
        buf.append(separator); 
      buf.append(String.valueOf(obj));
    } 
    return buf.toString();
  }
  
  public static String arrayToString(int[] arr) {
    return arrayToString(arr, ", ");
  }
  
  public static String arrayToString(int[] arr, String separator) {
    if (arr == null)
      return ""; 
    StringBuffer buf = new StringBuffer(arr.length * 5);
    for (int i = 0; i < arr.length; i++) {
      int x = arr[i];
      if (i > 0)
        buf.append(separator); 
      buf.append(String.valueOf(x));
    } 
    return buf.toString();
  }
  
  public static String arrayToString(float[] arr) {
    return arrayToString(arr, ", ");
  }
  
  public static String arrayToString(float[] arr, String separator) {
    if (arr == null)
      return ""; 
    StringBuffer buf = new StringBuffer(arr.length * 5);
    for (int i = 0; i < arr.length; i++) {
      float x = arr[i];
      if (i > 0)
        buf.append(separator); 
      buf.append(String.valueOf(x));
    } 
    return buf.toString();
  }
  
  public static fgo getMinecraft() {
    return minecraft;
  }
  
  public static gqm getTextureManager() {
    return minecraft.aa();
  }
  
  public static aue getResourceManager() {
    return minecraft.ab();
  }
  
  public static InputStream getResourceStream(akr location) throws IOException {
    return getResourceStream(minecraft.ab(), location);
  }
  
  public static InputStream getResourceStream(aue resourceManager, akr location) throws IOException {
    auc res = resourceManager.getResourceOrThrow(location);
    if (res == null)
      return null; 
    return res.d();
  }
  
  public static auc getResource(akr location) throws IOException {
    return minecraft.ab().getResourceOrThrow(location);
  }
  
  public static boolean hasResource(akr location) {
    if (location == null)
      return false; 
    asq rp = getDefiningResourcePack(location);
    return (rp != null);
  }
  
  public static boolean hasResource(aue resourceManager, akr location) {
    try {
      auc res = resourceManager.getResourceOrThrow(location);
      return (res != null);
    } catch (IOException e) {
      return false;
    } 
  }
  
  public static boolean hasResource(asq rp, akr loc) {
    if (rp == null || loc == null)
      return false; 
    atw<InputStream> supplier = rp.a(ass.a, loc);
    return (supplier != null);
  }
  
  public static asq[] getResourcePacks() {
    atp rep = minecraft.ac();
    Collection<atm> packInfos = rep.f();
    List<asq> list = new ArrayList();
    for (Iterator<atm> it = packInfos.iterator(); it.hasNext(); ) {
      atm rpic = it.next();
      asq rp = rpic.f();
      if (rp == getDefaultResourcePack())
        continue; 
      list.add(rp);
    } 
    asq[] rps = list.<asq>toArray(new asq[list.size()]);
    return rps;
  }
  
  public static String getResourcePackNames() {
    if (minecraft.ab() == null)
      return ""; 
    asq[] rps = getResourcePacks();
    if (rps.length <= 0)
      return getDefaultResourcePack().b(); 
    String[] names = new String[rps.length];
    for (int i = 0; i < rps.length; i++)
      names[i] = rps[i].b(); 
    String nameStr = arrayToString((Object[])names);
    return nameStr;
  }
  
  public static asu getDefaultResourcePack() {
    return minecraft.ad();
  }
  
  public static boolean isFromDefaultResourcePack(akr loc) {
    return (getDefiningResourcePack(loc) == getDefaultResourcePack());
  }
  
  public static asq getDefiningResourcePack(akr location) {
    atp rep = minecraft.ac();
    Collection<atm> packInfos = rep.f();
    List<atm> entries = (List<atm>)packInfos;
    for (int i = entries.size() - 1; i >= 0; i--) {
      atm entry = entries.get(i);
      asq rp = entry.f();
      if (rp.a(ass.a, location) != null)
        return rp; 
    } 
    return null;
  }
  
  public static InputStream getResourceStream(asq rp, ass type, akr location) throws IOException {
    atw<InputStream> supplier = rp.a(type, location);
    if (supplier == null)
      return null; 
    return (InputStream)supplier.get();
  }
  
  public static gex getRenderGlobal() {
    return minecraft.f;
  }
  
  public static gex getWorldRenderer() {
    return minecraft.f;
  }
  
  public static ges getGameRenderer() {
    return minecraft.j;
  }
  
  public static gkh getEntityRenderDispatcher() {
    return minecraft.ap();
  }
  
  public static boolean isBetterGrass() {
    return (gameSettings.ofBetterGrass != 3);
  }
  
  public static boolean isBetterGrassFancy() {
    return (gameSettings.ofBetterGrass == 2);
  }
  
  public static boolean isWeatherEnabled() {
    return gameSettings.ofWeather;
  }
  
  public static boolean isSkyEnabled() {
    return gameSettings.ofSky;
  }
  
  public static boolean isSunMoonEnabled() {
    return gameSettings.ofSunMoon;
  }
  
  public static boolean isSunTexture() {
    if (!isSunMoonEnabled())
      return false; 
    if (isShaders())
      if (!Shaders.isSun())
        return false;  
    return true;
  }
  
  public static boolean isMoonTexture() {
    if (!isSunMoonEnabled())
      return false; 
    if (isShaders())
      if (!Shaders.isMoon())
        return false;  
    return true;
  }
  
  public static boolean isVignetteEnabled() {
    if (isShaders())
      if (!Shaders.isVignette())
        return false;  
    if (gameSettings.ofVignette == 0)
      return isGraphicsFancy(); 
    return (gameSettings.ofVignette == 2);
  }
  
  public static boolean isStarsEnabled() {
    return gameSettings.ofStars;
  }
  
  public static void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      error("", e);
    } 
  }
  
  public static boolean isTimeDayOnly() {
    return (gameSettings.ofTime == 1);
  }
  
  public static boolean isTimeDefault() {
    return (gameSettings.ofTime == 0);
  }
  
  public static boolean isTimeNightOnly() {
    return (gameSettings.ofTime == 2);
  }
  
  public static int getAnisotropicFilterLevel() {
    return gameSettings.ofAfLevel;
  }
  
  public static boolean isAnisotropicFiltering() {
    return (getAnisotropicFilterLevel() > 1);
  }
  
  public static int getAntialiasingLevel() {
    return antialiasingLevel;
  }
  
  public static boolean isAntialiasing() {
    return (getAntialiasingLevel() > 0);
  }
  
  public static boolean isAntialiasingConfigured() {
    return ((getGameSettings()).ofAaLevel > 0);
  }
  
  public static boolean isMultiTexture() {
    if (getAnisotropicFilterLevel() > 1)
      return true; 
    if (getAntialiasingLevel() > 0)
      return true; 
    return false;
  }
  
  public static boolean between(int val, int min, int max) {
    return (val >= min && val <= max);
  }
  
  public static boolean between(float val, float min, float max) {
    return (val >= min && val <= max);
  }
  
  public static boolean between(double val, double min, double max) {
    return (val >= min && val <= max);
  }
  
  public static boolean isDrippingWaterLava() {
    return gameSettings.ofDrippingWaterLava;
  }
  
  public static boolean isBetterSnow() {
    return gameSettings.ofBetterSnow;
  }
  
  public static int parseInt(String str, int defVal) {
    try {
      if (str == null)
        return defVal; 
      str = str.trim();
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static int parseHexInt(String str, int defVal) {
    try {
      if (str == null)
        return defVal; 
      str = str.trim();
      if (str.startsWith("0x"))
        str = str.substring(2); 
      return Integer.parseInt(str, 16);
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static float parseFloat(String str, float defVal) {
    try {
      if (str == null)
        return defVal; 
      str = str.trim();
      return Float.parseFloat(str);
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static boolean parseBoolean(String str, boolean defVal) {
    try {
      if (str == null)
        return defVal; 
      str = str.trim();
      return Boolean.parseBoolean(str);
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static Boolean parseBoolean(String str, Boolean defVal) {
    try {
      if (str == null)
        return defVal; 
      str = str.trim().toLowerCase();
      if (str.equals("true"))
        return Boolean.TRUE; 
      if (str.equals("false"))
        return Boolean.FALSE; 
      return defVal;
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static String[] tokenize(String str, String delim) {
    StringTokenizer tok = new StringTokenizer(str, delim);
    List<String> list = new ArrayList();
    while (tok.hasMoreTokens()) {
      String token = tok.nextToken();
      list.add(token);
    } 
    String[] strs = list.<String>toArray(new String[list.size()]);
    return strs;
  }
  
  public static boolean isAnimatedTerrain() {
    return gameSettings.ofAnimatedTerrain;
  }
  
  public static boolean isAnimatedTextures() {
    return gameSettings.ofAnimatedTextures;
  }
  
  public static boolean isSwampColors() {
    return gameSettings.ofSwampColors;
  }
  
  public static boolean isRandomEntities() {
    return gameSettings.ofRandomEntities;
  }
  
  public static void checkGlError(String loc) {
    int errorCode = GlStateManager._getError();
    if (errorCode != 0 && GlErrors.isEnabled(errorCode)) {
      String errorText = getGlErrorString(errorCode);
      String messageLog = String.format("OpenGL error: %s (%s), at: %s", new Object[] { Integer.valueOf(errorCode), errorText, loc });
      error(messageLog);
      if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
        String message = grr.a("of.message.openglError", new Object[] { Integer.valueOf(errorCode), errorText });
        minecraft.l.d().a((wz)wz.b(message));
      } 
    } 
  }
  
  public static boolean isSmoothBiomes() {
    return (((Integer)gameSettings.E().c()).intValue() > 0);
  }
  
  public static int getBiomeBlendRadius() {
    return ((Integer)gameSettings.E().c()).intValue();
  }
  
  public static boolean isCustomColors() {
    return gameSettings.ofCustomColors;
  }
  
  public static boolean isCustomSky() {
    return gameSettings.ofCustomSky;
  }
  
  public static boolean isCustomFonts() {
    return gameSettings.ofCustomFonts;
  }
  
  public static boolean isShowCapes() {
    return gameSettings.ofShowCapes;
  }
  
  public static boolean isConnectedTextures() {
    return (gameSettings.ofConnectedTextures != 3);
  }
  
  public static boolean isNaturalTextures() {
    return gameSettings.ofNaturalTextures;
  }
  
  public static boolean isEmissiveTextures() {
    return gameSettings.ofEmissiveTextures;
  }
  
  public static boolean isConnectedTexturesFancy() {
    return (gameSettings.ofConnectedTextures == 2);
  }
  
  public static boolean isFastRender() {
    return gameSettings.ofFastRender;
  }
  
  public static boolean isShaders() {
    return Shaders.shaderPackLoaded;
  }
  
  public static boolean isShadersShadows() {
    return (isShaders() && Shaders.hasShadowMap);
  }
  
  public static String[] readLines(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    return readLines(fis);
  }
  
  public static String[] readLines(InputStream is) throws IOException {
    List<String> list = new ArrayList();
    InputStreamReader isr = new InputStreamReader(is, "ASCII");
    BufferedReader br = new BufferedReader(isr);
    while (true) {
      String line = br.readLine();
      if (line == null)
        break; 
      list.add(line);
    } 
    String[] lines = list.<String>toArray(new String[list.size()]);
    return lines;
  }
  
  public static String readFile(File file) throws IOException {
    FileInputStream fin = new FileInputStream(file);
    return readInputStream(fin, "ASCII");
  }
  
  public static String readInputStream(InputStream in) throws IOException {
    return readInputStream(in, "ASCII");
  }
  
  public static String readInputStream(InputStream in, String encoding) throws IOException {
    InputStreamReader inr = new InputStreamReader(in, encoding);
    BufferedReader br = new BufferedReader(inr);
    StringBuffer sb = new StringBuffer();
    while (true) {
      String line = br.readLine();
      if (line == null)
        break; 
      sb.append(line);
      sb.append("\n");
    } 
    in.close();
    return sb.toString();
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
  
  public static fgs getGameSettings() {
    return gameSettings;
  }
  
  public static String getNewRelease() {
    return newRelease;
  }
  
  public static void setNewRelease(String newRelease) {
    net.optifine.Config.newRelease = newRelease;
  }
  
  public static int compareRelease(String rel1, String rel2) {
    String[] rels1 = splitRelease(rel1);
    String[] rels2 = splitRelease(rel2);
    String branch1 = rels1[0];
    String branch2 = rels2[0];
    if (!branch1.equals(branch2))
      return branch1.compareTo(branch2); 
    int rev1 = parseInt(rels1[1], -1);
    int rev2 = parseInt(rels2[1], -1);
    if (rev1 != rev2)
      return rev1 - rev2; 
    String suf1 = rels1[2];
    String suf2 = rels2[2];
    if (!suf1.equals(suf2)) {
      if (suf1.isEmpty())
        return 1; 
      if (suf2.isEmpty())
        return -1; 
    } 
    return suf1.compareTo(suf2);
  }
  
  private static String[] splitRelease(String relStr) {
    if (relStr == null || relStr.length() <= 0)
      return new String[] { "", "", "" }; 
    Pattern p = Pattern.compile("([A-Z])([0-9]+)(.*)");
    Matcher m = p.matcher(relStr);
    if (!m.matches())
      return new String[] { "", "", "" }; 
    String branch = normalize(m.group(1));
    String revision = normalize(m.group(2));
    String suffix = normalize(m.group(3));
    return new String[] { branch, revision, suffix };
  }
  
  public static int intHash(int x) {
    x = x ^ 0x3D ^ x >> 16;
    x += x << 3;
    x ^= x >> 4;
    x *= 668265261;
    x ^= x >> 15;
    return x;
  }
  
  public static int getRandom(jd blockPos, int face) {
    int rand = intHash(face + 37);
    rand = intHash(rand + blockPos.u());
    rand = intHash(rand + blockPos.w());
    rand = intHash(rand + blockPos.v());
    return rand;
  }
  
  public static int getAvailableProcessors() {
    return availableProcessors;
  }
  
  public static void updateAvailableProcessors() {
    availableProcessors = Runtime.getRuntime().availableProcessors();
  }
  
  public static boolean isSingleProcessor() {
    return (getAvailableProcessors() <= 1);
  }
  
  public static boolean isSmoothWorld() {
    return gameSettings.ofSmoothWorld;
  }
  
  public static boolean isLazyChunkLoading() {
    return gameSettings.ofLazyChunkLoading;
  }
  
  public static boolean isDynamicFov() {
    return gameSettings.ofDynamicFov;
  }
  
  public static boolean isAlternateBlocks() {
    return gameSettings.ofAlternateBlocks;
  }
  
  public static int getChunkViewDistance() {
    if (gameSettings == null)
      return 10; 
    int chunkDistance = ((Integer)gameSettings.e().c()).intValue();
    return chunkDistance;
  }
  
  public static boolean equals(Object o1, Object o2) {
    if (o1 == o2)
      return true; 
    if (o1 == null)
      return false; 
    return o1.equals(o2);
  }
  
  public static boolean equalsOne(Object a, Object[] bs) {
    if (bs == null)
      return false; 
    for (int i = 0; i < bs.length; i++) {
      Object b = bs[i];
      if (equals(a, b))
        return true; 
    } 
    return false;
  }
  
  public static boolean equalsOne(int val, int[] vals) {
    for (int i = 0; i < vals.length; i++) {
      if (vals[i] == val)
        return true; 
    } 
    return false;
  }
  
  public static boolean isSameOne(Object a, Object[] bs) {
    if (bs == null)
      return false; 
    for (int i = 0; i < bs.length; i++) {
      Object b = bs[i];
      if (a == b)
        return true; 
    } 
    return false;
  }
  
  public static String normalize(String s) {
    if (s == null)
      return ""; 
    return s;
  }
  
  private static ByteBuffer readIconImage(InputStream is) throws IOException {
    BufferedImage var2 = ImageIO.read(is);
    int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
    ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
    int[] var5 = var3;
    int var6 = var3.length;
    for (int var7 = 0; var7 < var6; var7++) {
      int var8 = var5[var7];
      var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
    } 
    var4.flip();
    return var4;
  }
  
  public static Object[] addObjectToArray(Object[] arr, Object obj) {
    if (arr == null)
      throw new NullPointerException("The given array is NULL"); 
    int arrLen = arr.length;
    int newLen = arrLen + 1;
    Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
    System.arraycopy(arr, 0, newArr, 0, arrLen);
    newArr[arrLen] = obj;
    return newArr;
  }
  
  public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
    List<Object> list = new ArrayList(Arrays.asList(arr));
    list.add(index, obj);
    Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
    return list.toArray(newArr);
  }
  
  public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
    if (arr == null)
      throw new NullPointerException("The given array is NULL"); 
    if (objs.length == 0)
      return arr; 
    int arrLen = arr.length;
    int newLen = arrLen + objs.length;
    Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
    System.arraycopy(arr, 0, newArr, 0, arrLen);
    System.arraycopy(objs, 0, newArr, arrLen, objs.length);
    return newArr;
  }
  
  public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
    List list = new ArrayList(Arrays.asList(arr));
    list.remove(obj);
    Object[] newArr = collectionToArray(list, arr.getClass().getComponentType());
    return newArr;
  }
  
  public static Object[] collectionToArray(Collection coll, Class<?> elementClass) {
    if (coll == null)
      return null; 
    if (elementClass == null)
      return null; 
    if (elementClass.isPrimitive())
      throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + String.valueOf(elementClass)); 
    Object[] array = (Object[])Array.newInstance(elementClass, coll.size());
    return coll.toArray(array);
  }
  
  public static boolean isCustomItems() {
    return gameSettings.ofCustomItems;
  }
  
  public static String getFpsString() {
    int fps = getFpsAverage();
    int fpsMin = getFpsMin();
    if (showFrameTime) {
      String timeMs = String.format("%.1f", new Object[] { Double.valueOf(1000.0D / limit(fps, 1, 2147483647)) });
      String timeMaxMs = String.format("%.1f", new Object[] { Double.valueOf(1000.0D / limit(fpsMin, 1, 2147483647)) });
      String str1 = timeMs + "/" + timeMs + " ms";
      return str1;
    } 
    String fpsStr = "" + fps + "/" + fps + " fps";
    return fpsStr;
  }
  
  public static boolean isShowFrameTime() {
    return showFrameTime;
  }
  
  public static int getFpsAverage() {
    int fps = Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
    return fps;
  }
  
  public static int getFpsMin() {
    return fpsMinLast;
  }
  
  public static int getChunkUpdates() {
    return chunkUpdatesLast;
  }
  
  public static void updateFpsMin() {
    blr sl = minecraft.aN().getFrameTimeLogger();
    if (sl.d() <= 0)
      return; 
    int fps = Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
    if (fps <= 0)
      fps = 1; 
    long timeAvgNs = (long)(1.0D / fps * 1.0E9D);
    long timeMaxNs = timeAvgNs;
    long timeTotalNs = 0L;
    for (int ix = sl.d() - 1; ix > 0 && timeTotalNs < 1.0E9D; ix--) {
      long timeNs = sl.a(ix);
      if (timeNs > timeMaxNs)
        timeMaxNs = timeNs; 
      timeTotalNs += timeNs;
    } 
    double timeMaxSec = timeMaxNs / 1.0E9D;
    fpsMinLast = (int)(1.0D / timeMaxSec);
  }
  
  private static void updateChunkUpdates() {
    chunkUpdatesLast = gia.renderChunksUpdated;
    gia.renderChunksUpdated = 0;
  }
  
  public static int getBitsOs() {
    String progFiles86 = System.getenv("ProgramFiles(X86)");
    if (progFiles86 != null)
      return 64; 
    return 32;
  }
  
  public static int getBitsJre() {
    String[] propNames = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
    for (int i = 0; i < propNames.length; i++) {
      String propName = propNames[i];
      String propVal = System.getProperty(propName);
      if (propVal != null)
        if (propVal.contains("64"))
          return 64;  
    } 
    return 32;
  }
  
  public static boolean isNotify64BitJava() {
    return notify64BitJava;
  }
  
  public static void setNotify64BitJava(boolean flag) {
    notify64BitJava = flag;
  }
  
  public static boolean isConnectedModels() {
    return false;
  }
  
  public static void showGuiMessage(String line1, String line2) {
    GuiMessage gui = new GuiMessage(minecraft.y, line1, line2);
    minecraft.a((fod)gui);
  }
  
  public static int[] addIntToArray(int[] intArray, int intValue) {
    return addIntsToArray(intArray, new int[] { intValue });
  }
  
  public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
    if (intArray == null || copyFrom == null)
      throw new NullPointerException("The given array is NULL"); 
    int arrLen = intArray.length;
    int newLen = arrLen + copyFrom.length;
    int[] newArray = new int[newLen];
    System.arraycopy(intArray, 0, newArray, 0, arrLen);
    for (int index = 0; index < copyFrom.length; index++)
      newArray[index + arrLen] = copyFrom[index]; 
    return newArray;
  }
  
  public static void writeFile(File file, String str) throws IOException {
    FileOutputStream fos = new FileOutputStream(file);
    byte[] bytes = str.getBytes("ASCII");
    fos.write(bytes);
    fos.close();
  }
  
  public static void setTextureMap(gqk textureMapTerrain) {
    net.optifine.Config.textureMapTerrain = textureMapTerrain;
  }
  
  public static gqk getTextureMap() {
    return textureMapTerrain;
  }
  
  public static boolean isDynamicLights() {
    return (gameSettings.ofDynamicLights != 3);
  }
  
  public static boolean isDynamicLightsFast() {
    return (gameSettings.ofDynamicLights == 1);
  }
  
  public static boolean isDynamicHandLight() {
    if (!isDynamicLights())
      return false; 
    if (isShaders())
      return Shaders.isDynamicHandLight(); 
    return true;
  }
  
  public static boolean isCustomEntityModels() {
    return gameSettings.ofCustomEntityModels;
  }
  
  public static boolean isCustomGuis() {
    return gameSettings.ofCustomGuis;
  }
  
  public static int getScreenshotSize() {
    return gameSettings.ofScreenshotSize;
  }
  
  public static int[] toPrimitive(Integer[] arr) {
    if (arr == null)
      return null; 
    if (arr.length == 0)
      return new int[0]; 
    int[] intArr = new int[arr.length];
    for (int i = 0; i < intArr.length; i++)
      intArr[i] = arr[i].intValue(); 
    return intArr;
  }
  
  public static boolean isRenderRegions() {
    if (isMultiTexture())
      return false; 
    return (gameSettings.ofRenderRegions && GlStateManager.vboRegions);
  }
  
  public static boolean isVbo() {
    return GLX.useVbo();
  }
  
  public static boolean isSmoothFps() {
    return gameSettings.ofSmoothFps;
  }
  
  public static boolean openWebLink(URI uri) {
    ad.setExceptionOpenUrl(null);
    ad.k().a(uri);
    Exception error = ad.getExceptionOpenUrl();
    return (error == null);
  }
  
  public static boolean isShowGlErrors() {
    return gameSettings.ofShowGlErrors;
  }
  
  public static String arrayToString(boolean[] arr, String separator) {
    if (arr == null)
      return ""; 
    StringBuffer buf = new StringBuffer(arr.length * 5);
    for (int i = 0; i < arr.length; i++) {
      boolean x = arr[i];
      if (i > 0)
        buf.append(separator); 
      buf.append(String.valueOf(x));
    } 
    return buf.toString();
  }
  
  public static boolean isIntegratedServerRunning() {
    if (minecraft.V() == null)
      return false; 
    if (!minecraft.T())
      return false; 
    return true;
  }
  
  public static IntBuffer createDirectIntBuffer(int capacity) {
    return fac.a(capacity << 2).asIntBuffer();
  }
  
  public static PointerBuffer createDirectPointerBuffer(int capacity) {
    return PointerBuffer.allocateDirect(capacity);
  }
  
  public static String getGlErrorString(int err) {
    switch (err) {
      case 0:
        return "No error";
      case 1280:
        return "Invalid enum";
      case 1281:
        return "Invalid value";
      case 1282:
        return "Invalid operation";
      case 1286:
        return "Invalid framebuffer operation";
      case 1285:
        return "Out of memory";
      case 1284:
        return "Stack underflow";
      case 1283:
        return "Stack overflow";
    } 
    return "Unknown";
  }
  
  public static boolean isKeyDown(int key) {
    return (GLFW.glfwGetKey(minecraft.aM().j(), key) == 1);
  }
  
  public static boolean isTrue(Boolean val) {
    return (val != null && val.booleanValue());
  }
  
  public static boolean isFalse(Boolean val) {
    return (val != null && !val.booleanValue());
  }
  
  public static boolean isReloadingResources() {
    if (minecraft.aK() == null)
      return false; 
    if (minecraft.aK() instanceof fnt) {
      fnt rlpg = (fnt)minecraft.aK();
      if (rlpg.isFadeOut())
        return false; 
    } 
    return true;
  }
  
  public static boolean isQuadsToTriangles() {
    if (!isShaders())
      return false; 
    return !Shaders.canRenderQuads();
  }
  
  public static void frameStart() {
    long timeNowMs = System.currentTimeMillis();
    long frameTimeMs = timeNowMs - timeLastFrameMs;
    timeLastFrameMs = timeNowMs;
    frameTimeMs = limit(frameTimeMs, 1L, 1000L);
    averageFrameTimeMs = (averageFrameTimeMs + frameTimeMs) / 2L;
    averageFrameTimeMs = limit(averageFrameTimeMs, 1L, 1000L);
    lastFrameTimeMs = frameTimeMs;
    if (minecraft.z != mcDebugLast) {
      mcDebugLast = minecraft.z;
      updateFpsMin();
      updateChunkUpdates();
    } 
  }
  
  public static long getLastFrameTimeMs() {
    return lastFrameTimeMs;
  }
  
  public static long getAverageFrameTimeMs() {
    return averageFrameTimeMs;
  }
  
  public static float getAverageFrameTimeSec() {
    float frameTimeSec = (float)getAverageFrameTimeMs() / 1000.0F;
    return frameTimeSec;
  }
  
  public static long getAverageFrameFps() {
    long frameFps = 1000L / getAverageFrameTimeMs();
    return frameFps;
  }
  
  public static void checkNull(Object obj, String msg) throws NullPointerException {
    if (obj == null)
      throw new NullPointerException(msg); 
  }
  
  public static boolean isTelemetryOn() {
    return (gameSettings.ofTelemetry != 2);
  }
  
  public static boolean isTelemetryAnonymous() {
    return (gameSettings.ofTelemetry == 1);
  }
}
