package srg.net.optifine.player;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;
import net.optifine.http.IFileDownloadListener;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerConfigurationReceiver;

public class PlayerConfigurations {
  private static Map mapConfigurations = null;
  
  private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
  
  private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
  
  public static void renderPlayerItems(HumanoidModel modelBiped, AbstractClientPlayer player, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, int packedOverlayIn) {
    PlayerConfiguration cfg = getPlayerConfiguration(player);
    if (cfg != null)
      cfg.renderPlayerItems(modelBiped, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn); 
  }
  
  public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer player) {
    if (reloadPlayerItems)
      if (System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
        LocalPlayer localPlayer = (Minecraft.getInstance()).player;
        if (localPlayer != null) {
          setPlayerConfiguration(localPlayer.getNameClear(), null);
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
