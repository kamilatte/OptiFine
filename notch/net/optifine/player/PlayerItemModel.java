package notch.net.optifine.player;

import akr;
import faj;
import fbi;
import fbm;
import fgo;
import fvx;
import fyk;
import gdy;
import gez;
import gfh;
import gpw;
import gpy;
import java.awt.Dimension;
import net.optifine.player.PlayerItemRenderer;

public class PlayerItemModel {
  private Dimension textureSize = null;
  
  private boolean usePlayerTexture = false;
  
  private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
  
  private akr textureLocation = null;
  
  private faj textureImage = null;
  
  private gpy texture = null;
  
  private akr locationMissing = new akr("textures/block/red_wool.png");
  
  public static final int ATTACH_BODY = 0;
  
  public static final int ATTACH_HEAD = 1;
  
  public static final int ATTACH_LEFT_ARM = 2;
  
  public static final int ATTACH_RIGHT_ARM = 3;
  
  public static final int ATTACH_LEFT_LEG = 4;
  
  public static final int ATTACH_RIGHT_LEG = 5;
  
  public static final int ATTACH_CAPE = 6;
  
  public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
    this.textureSize = textureSize;
    this.usePlayerTexture = usePlayerTexture;
    this.modelRenderers = modelRenderers;
  }
  
  public void render(fvx modelBiped, gdy player, fbi matrixStackIn, gez bufferIn, int packedLightIn, int packedOverlayIn) {
    akr locTex = this.locationMissing;
    if (this.usePlayerTexture) {
      locTex = player.getSkinTextureLocation();
    } else if (this.textureLocation != null) {
      if (this.texture == null && this.textureImage != null) {
        this.texture = new gpy(this.textureImage);
        fgo.Q().aa().a(this.textureLocation, (gpw)this.texture);
      } 
      locTex = this.textureLocation;
    } else {
      locTex = this.locationMissing;
    } 
    for (int i = 0; i < this.modelRenderers.length; i++) {
      PlayerItemRenderer pir = this.modelRenderers[i];
      matrixStackIn.a();
      gfh renderType = gfh.e(locTex);
      fbm buffer = bufferIn.getBuffer(renderType);
      pir.render(modelBiped, matrixStackIn, buffer, packedLightIn, packedOverlayIn);
      matrixStackIn.b();
    } 
  }
  
  public static fyk getAttachModel(fvx modelBiped, int attachTo) {
    switch (attachTo) {
      case 0:
        return modelBiped.m;
      case 1:
        return modelBiped.k;
      case 2:
        return modelBiped.o;
      case 3:
        return modelBiped.n;
      case 4:
        return modelBiped.q;
      case 5:
        return modelBiped.p;
    } 
    return null;
  }
  
  public faj getTextureImage() {
    return this.textureImage;
  }
  
  public void setTextureImage(faj textureImage) {
    this.textureImage = textureImage;
  }
  
  public gpy getTexture() {
    return this.texture;
  }
  
  public akr getTextureLocation() {
    return this.textureLocation;
  }
  
  public void setTextureLocation(akr textureLocation) {
    this.textureLocation = textureLocation;
  }
  
  public boolean isUsePlayerTexture() {
    return this.usePlayerTexture;
  }
}
