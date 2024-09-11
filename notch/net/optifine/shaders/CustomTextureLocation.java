package notch.net.optifine.shaders;

import akr;
import fgo;
import gpw;
import gqe;
import gqm;
import net.optifine.shaders.ICustomTexture;
import net.optifine.shaders.MultiTexID;

public class CustomTextureLocation implements ICustomTexture {
  private int textureUnit = -1;
  
  private akr location;
  
  private int variant = 0;
  
  private gpw texture;
  
  public static final int VARIANT_BASE = 0;
  
  public static final int VARIANT_NORMAL = 1;
  
  public static final int VARIANT_SPECULAR = 2;
  
  public CustomTextureLocation(int textureUnit, akr location, int variant) {
    this.textureUnit = textureUnit;
    this.location = location;
    this.variant = variant;
  }
  
  public gpw getTexture() {
    if (this.texture == null) {
      gqm textureManager = fgo.Q().aa();
      this.texture = textureManager.b(this.location);
      if (this.texture == null) {
        this.texture = (gpw)new gqe(this.location);
        textureManager.a(this.location, this.texture);
        this.texture = textureManager.b(this.location);
      } 
    } 
    return this.texture;
  }
  
  public void reloadTexture() {
    this.texture = null;
  }
  
  public int getTextureId() {
    gpw tex = getTexture();
    if (this.variant != 0)
      if (tex instanceof gpw) {
        gpw at = tex;
        MultiTexID mtid = at.multiTex;
        if (mtid != null) {
          if (this.variant == 1)
            return mtid.norm; 
          if (this.variant == 2)
            return mtid.spec; 
        } 
      }  
    return tex.a();
  }
  
  public int getTextureUnit() {
    return this.textureUnit;
  }
  
  public void deleteTexture() {}
  
  public String toString() {
    return "textureUnit: " + this.textureUnit + ", location: " + String.valueOf(this.location) + ", glTextureId: " + String.valueOf((this.texture != null) ? Integer.valueOf(this.texture.a()) : "");
  }
}
