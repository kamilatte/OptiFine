package srg.net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends AbstractTexture {
  private String texturePath;
  
  private long size = 0L;
  
  public SimpleShaderTexture(String texturePath) {
    this.texturePath = texturePath;
  }
  
  public void load(ResourceManager resourceManager) throws IOException {
    releaseId();
    InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
    if (inputStream == null)
      throw new FileNotFoundException("Shader texture not found: " + this.texturePath); 
    try {
      NativeImage nativeImage = NativeImage.read(inputStream);
      this.size = nativeImage.getSize();
      TextureMetadataSection tms = loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false));
      TextureUtil.prepareImage(getId(), nativeImage.getWidth(), nativeImage.getHeight());
      nativeImage.upload(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), tms.isBlur(), tms.isClamp(), false, true);
    } finally {
      IOUtils.closeQuietly(inputStream);
    } 
  }
  
  public static TextureMetadataSection loadTextureMetadataSection(String texturePath, TextureMetadataSection def) {
    String pathMeta = texturePath + ".mcmeta";
    String sectionName = "texture";
    InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
    if (inMeta != null) {
      BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));
      try {
        JsonObject jsonMeta = (new JsonParser()).parse(brMeta).getAsJsonObject();
        JsonObject jsonMetaTexture = jsonMeta.getAsJsonObject(sectionName);
        if (jsonMetaTexture != null) {
          TextureMetadataSection meta = TextureMetadataSection.SERIALIZER.fromJson(jsonMetaTexture);
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
