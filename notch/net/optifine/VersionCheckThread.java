package notch.net.optifine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.ClientBrandRetriever;
import net.optifine.Config;

public class VersionCheckThread extends Thread {
  public VersionCheckThread() {
    super("VersionCheck");
  }
  
  public void run() {
    HttpURLConnection conn = null;
    try {
      Config.dbg("Checking for new version");
      URL url = new URL("http://optifine.net/version/1.21.1/HD_U.txt");
      conn = (HttpURLConnection)url.openConnection();
      boolean snooper = true;
      if (snooper) {
        conn.setRequestProperty("OF-MC-Version", "1.21.1");
        conn.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName());
        conn.setRequestProperty("OF-Edition", "HD_U");
        conn.setRequestProperty("OF-Release", "J1_pre10");
        conn.setRequestProperty("OF-Java-Version", System.getProperty("java.version"));
        conn.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
        conn.setRequestProperty("OF-OpenGL-Version", Config.openGlVersion);
        conn.setRequestProperty("OF-OpenGL-Vendor", Config.openGlVendor);
      } 
      conn.setDoInput(true);
      conn.setDoOutput(false);
      conn.connect();
      try {
        InputStream in = conn.getInputStream();
        String verStr = Config.readInputStream(in);
        in.close();
        String[] verLines = Config.tokenize(verStr, "\n\r");
        if (verLines.length < 1)
          return; 
        String newVer = verLines[0].trim();
        Config.dbg("Version found: " + newVer);
        if (Config.compareRelease(newVer, "J1_pre10") <= 0)
          return; 
        Config.setNewRelease(newVer);
      } finally {
        if (conn != null)
          conn.disconnect(); 
      } 
    } catch (Exception e) {
      Config.dbg(e.getClass().getName() + ": " + e.getClass().getName());
    } 
  }
}
