package notch.net.optifine.player;

import akr;
import faj;
import fgo;
import gdy;
import gpw;
import gpz;
import gqe;
import gqm;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.player.CapeImageBuffer;
import net.optifine.util.TextureUtils;

public class CapeUtils {
  private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");
  
  public static void downloadCape(gdy player) {
    String username = player.getNameClear();
    if (username != null && !username.isEmpty() && !username.contains("\000") && PATTERN_USERNAME.matcher(username).matches()) {
      String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
      akr rl = new akr("capeof/" + username);
      gqm textureManager = fgo.Q().aa();
      gpw tex = textureManager.b(rl, null);
      if (tex != null)
        if (tex instanceof gpz) {
          gpz tdid = (gpz)tex;
          if (tdid.imageFound != null) {
            if (tdid.imageFound.booleanValue()) {
              player.setLocationOfCape(rl);
              if (tdid.getProcessTask() instanceof CapeImageBuffer) {
                CapeImageBuffer capeImageBuffer = (CapeImageBuffer)tdid.getProcessTask();
                player.setElytraOfCape(capeImageBuffer.isElytraOfCape());
              } 
            } 
            return;
          } 
        }  
      CapeImageBuffer cib = new CapeImageBuffer(player, rl);
      akr locEmpty = TextureUtils.LOCATION_TEXTURE_EMPTY;
      gpz textureCape = new gpz(null, ofCapeUrl, locEmpty, false, (Runnable)cib);
      textureCape.pipeline = true;
      textureManager.a(rl, (gpw)textureCape);
    } 
  }
  
  public static faj parseCape(faj img) {
    int imageWidth = 64;
    int imageHeight = 32;
    faj srcImg = img;
    int srcWidth = srcImg.a();
    int srcHeight = srcImg.b();
    while (imageWidth < srcWidth || imageHeight < srcHeight) {
      imageWidth *= 2;
      imageHeight *= 2;
    } 
    faj imgNew = new faj(imageWidth, imageHeight, true);
    imgNew.a(srcImg);
    srcImg.close();
    return imgNew;
  }
  
  public static boolean isElytraCape(faj imageRaw, faj imageFixed) {
    return (imageRaw.a() > imageFixed.b());
  }
  
  public static void reloadCape(gdy player) {
    String nameClear = player.getNameClear();
    akr rl = new akr("capeof/" + nameClear);
    gqm textureManager = Config.getTextureManager();
    gpw tex = textureManager.b(rl);
    if (tex instanceof gqe) {
      gqe simpleTex = (gqe)tex;
      simpleTex.b();
      textureManager.c(rl);
    } 
    player.setLocationOfCape(null);
    player.setElytraOfCape(false);
    downloadCape(player);
  }
}
