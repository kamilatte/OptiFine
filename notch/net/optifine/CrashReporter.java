package notch.net.optifine;

import ac;
import java.util.HashMap;
import java.util.Map;
import net.optifine.Config;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;
import o;
import y;

public class CrashReporter {
  public static void onCrashReport(o crashReport, ac category) {
    try {
      Throwable cause = crashReport.b();
      if (cause == null)
        return; 
      if (cause.getClass().getName().contains(".fml.client.SplashProgress"))
        return; 
      if (cause.getClass() == Throwable.class)
        return; 
      if (cause.getClass() == Exception.class && Config.equals(cause.getMessage(), "dummy"))
        return; 
      extendCrashReport(category);
      if (!Config.isTelemetryOn())
        return; 
      String url = "http://optifine.net/crashReport";
      String reportStr = makeReport(crashReport);
      byte[] content = reportStr.getBytes("ASCII");
      Object object = new Object();
      Map<Object, Object> headers = new HashMap<>();
      headers.put("OF-Version", Config.getVersion());
      headers.put("OF-Summary", makeSummary(crashReport));
      FileUploadThread fut = new FileUploadThread(url, headers, content, (IFileUploadListener)object);
      fut.setPriority(10);
      fut.start();
      Thread.sleep(1000L);
    } catch (Exception e) {
      Config.dbg(e.getClass().getName() + ": " + e.getClass().getName());
    } 
  }
  
  private static String makeReport(o crashReport) {
    StringBuffer sb = new StringBuffer();
    sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
    sb.append("Summary: " + makeSummary(crashReport) + "\n");
    sb.append("\n");
    sb.append(crashReport.a(y.a));
    sb.append("\n");
    return sb.toString();
  }
  
  private static String makeSummary(o crashReport) {
    Throwable t = crashReport.b();
    if (t == null)
      return "Unknown"; 
    StackTraceElement[] traces = t.getStackTrace();
    String firstTrace = "unknown";
    if (traces.length > 0)
      firstTrace = traces[0].toString().trim(); 
    String sum = t.getClass().getName() + ": " + t.getClass().getName() + " (" + t.getMessage() + ") [" + crashReport.a() + "]";
    return sum;
  }
  
  public static void extendCrashReport(ac cat) {
    cat.a("OptiFine Version", Config.getVersion());
    cat.a("OptiFine Build", Config.getBuild());
    if (Config.getGameSettings() != null) {
      cat.a("Render Distance Chunks", "" + Config.getChunkViewDistance());
      cat.a("Mipmaps", "" + Config.getMipmapLevels());
      cat.a("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
      cat.a("Antialiasing", "" + Config.getAntialiasingLevel());
      cat.a("Multitexture", "" + Config.isMultiTexture());
    } 
    cat.a("Shaders", Shaders.getShaderPackName());
    cat.a("OpenGlVersion", Config.openGlVersion);
    cat.a("OpenGlRenderer", Config.openGlRenderer);
    cat.a("OpenGlVendor", Config.openGlVendor);
    cat.a("CpuCount", "" + Config.getAvailableProcessors());
  }
}
