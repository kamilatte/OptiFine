package srg.net.optifine.player;

import com.mojang.blaze3d.platform.NativeImage;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.player.CapeImageBuffer;
import net.optifine.util.TextureUtils;

public class CapeUtils {
  private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");
  
  public static void downloadCape(AbstractClientPlayer player) {
    String username = player.getNameClear();
    if (username != null && !username.isEmpty() && !username.contains("\000") && PATTERN_USERNAME.matcher(username).matches()) {
      String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
      ResourceLocation rl = new ResourceLocation("capeof/" + username);
      TextureManager textureManager = Minecraft.getInstance().getTextureManager();
      AbstractTexture tex = textureManager.getTexture(rl, null);
      if (tex != null)
        if (tex instanceof HttpTexture) {
          HttpTexture tdid = (HttpTexture)tex;
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
      ResourceLocation locEmpty = TextureUtils.LOCATION_TEXTURE_EMPTY;
      HttpTexture textureCape = new HttpTexture(null, ofCapeUrl, locEmpty, false, (Runnable)cib);
      textureCape.pipeline = true;
      textureManager.register(rl, (AbstractTexture)textureCape);
    } 
  }
  
  public static NativeImage parseCape(NativeImage img) {
    int imageWidth = 64;
    int imageHeight = 32;
    NativeImage srcImg = img;
    int srcWidth = srcImg.getWidth();
    int srcHeight = srcImg.getHeight();
    while (imageWidth < srcWidth || imageHeight < srcHeight) {
      imageWidth *= 2;
      imageHeight *= 2;
    } 
    NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);
    imgNew.copyFrom(srcImg);
    srcImg.close();
    return imgNew;
  }
  
  public static boolean isElytraCape(NativeImage imageRaw, NativeImage imageFixed) {
    return (imageRaw.getWidth() > imageFixed.getHeight());
  }
  
  public static void reloadCape(AbstractClientPlayer player) {
    String nameClear = player.getNameClear();
    ResourceLocation rl = new ResourceLocation("capeof/" + nameClear);
    TextureManager textureManager = Config.getTextureManager();
    AbstractTexture tex = textureManager.getTexture(rl);
    if (tex instanceof SimpleTexture) {
      SimpleTexture simpleTex = (SimpleTexture)tex;
      simpleTex.releaseId();
      textureManager.release(rl);
    } 
    player.setLocationOfCape(null);
    player.setElytraOfCape(false);
    downloadCape(player);
  }
}
