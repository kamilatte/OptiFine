package notch.net.optifine.player;

import fbi;
import fgo;
import fvx;
import gdy;
import geb;
import gez;
import java.util.HashMap;
import java.util.Map;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;
import net.optifine.http.IFileDownloadListener;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerConfigurationReceiver;

public class PlayerConfigurations {
  private static Map mapConfigurations = null;
  
  private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
  
  private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
  
  public static void renderPlayerItems(fvx modelBiped, gdy player, fbi matrixStackIn, gez bufferIn, int packedLightIn, int packedOverlayIn) {
    PlayerConfiguration cfg = getPlayerConfiguration(player);
    if (cfg != null)
      cfg.renderPlayerItems(modelBiped, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn); 
  }
  
  public static synchronized PlayerConfiguration getPlayerConfiguration(gdy player) {
    if (reloadPlayerItems)
      if (System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
        geb geb = (fgo.Q()).s;
        if (geb != null) {
          setPlayerConfiguration(geb.getNameClear(), null);
          timeReloadPlayerItemsMs = System.currentTimeMillis();
        } 
      }  
    String name = player.getNameClear();
    if (name == null)
      return null; 
    PlayerConfiguration pc = (PlayerConfiguration)getMapConfigurations().get(name);
    if (pc == null) {
      pc = new PlayerConfiguration();
      getMapConfigurations().put(name, pc);
      PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
      String url = HttpUtils.getPlayerItemsUrl() + "/users/" + HttpUtils.getPlayerItemsUrl() + ".cfg";
      FileDownloadThread fdt = new FileDownloadThread(url, (IFileDownloadListener)pcl);
      fdt.start();
    } 
    return pc;
  }
  
  public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
    getMapConfigurations().put(player, pc);
  }
  
  private static Map getMapConfigurations() {
    if (mapConfigurations == null)
      mapConfigurations = new HashMap<>(); 
    return mapConfigurations;
  }
}
