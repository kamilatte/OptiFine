package notch.net.optifine.shaders;

import aue;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.TextureUtil;
import faj;
import gpw;
import gsi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends gpw {
  private String texturePath;
  
  private long size = 0L;
  
  public SimpleShaderTexture(String texturePath) {
    this.texturePath = texturePath;
  }
  
  public void a(aue resourceManager) throws IOException {
    b();
    InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
    if (inputStream == null)
      throw new FileNotFoundException("Shader texture not found: " + this.texturePath); 
    try {
      faj nativeImage = faj.a(inputStream);
      this.size = nativeImage.getSize();
      gsi tms = loadTextureMetadataSection(this.texturePath, new gsi(false, false));
      TextureUtil.prepareImage(a(), nativeImage.a(), nativeImage.b());
      nativeImage.a(0, 0, 0, 0, 0, nativeImage.a(), nativeImage.b(), tms.a(), tms.b(), false, true);
    } finally {
      IOUtils.closeQuietly(inputStream);
    } 
  }
  
  public static gsi loadTextureMetadataSection(String texturePath, gsi def) {
    String pathMeta = texturePath + ".mcmeta";
    String sectionName = "texture";
    InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
    if (inMeta != null) {
      BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));
      try {
        JsonObject jsonMeta = (new JsonParser()).parse(brMeta).getAsJsonObject();
        JsonObject jsonMetaTexture = jsonMeta.getAsJsonObject(sectionName);
        if (jsonMetaTexture != null) {
          gsi meta = gsi.a.b(jsonMetaTexture);
          if (meta != null)
            return meta; 
        } 
      } catch (RuntimeException re) {
        SMCLog.warning("Error reading metadata: " + pathMeta);
        SMCLog.warning(re.getClass().getName() + ": " + re.getClass().getName());
      } finally {
        IOUtils.closeQuietly(brMeta);
        IOUtils.closeQuietly(inMeta);
      } 
    } 
    return def;
  }
  
  public long getSize() {
    return this.size;
  }
}
