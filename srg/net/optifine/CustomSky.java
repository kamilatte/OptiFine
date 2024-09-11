package srg.net.optifine;

import com.mojang.blaze3d.vertex.PoseStack;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.optifine.Config;
import net.optifine.CustomSkyLayer;
import net.optifine.render.Blender;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;

public class CustomSky {
  private static CustomSkyLayer[][] worldSkyLayers = null;
  
  public static void reset() {
    worldSkyLayers = null;
  }
  
  public static void update() {
    reset();
    if (!Config.isCustomSky())
      return; 
    worldSkyLayers = readCustomSkies();
  }
  
  private static CustomSkyLayer[][] readCustomSkies() {
    CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
    String prefix = "optifine/sky/world";
    int lastWorldId = -1;
    for (int w = 0; w < wsls.length; w++) {
      String worldPrefix = prefix + prefix;
      List<CustomSkyLayer> listSkyLayers = new ArrayList();
      for (int j = 0; j < 1000; j++) {
        String path = worldPrefix + "/sky" + worldPrefix + ".properties";
        int countMissing = 0;
        try {
          ResourceLocation locPath = new ResourceLocation(path);
          InputStream in = Config.getResourceStream(locPath);
          if (in == null) {
            countMissing++;
            if (countMissing > 10)
              break; 
          } 
          PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
          propertiesOrdered.load(in);
          in.close();
          Config.dbg("CustomSky properties: " + path);
          String defSource = "" + j + ".png";
          CustomSkyLayer sl = new CustomSkyLayer((Properties)propertiesOrdered, defSource);
          if (sl.isValid(path)) {
            String srcPath = StrUtils.addSuffixCheck(sl.source, ".png");
            ResourceLocation locSource = new ResourceLocation(srcPath);
            AbstractTexture tex = TextureUtils.getTexture(locSource);
            if (tex == null) {
              Config.log("CustomSky: Texture not found: " + String.valueOf(locSource));
            } else {
              sl.textureId = tex.getId();
              listSkyLayers.add(sl);
              in.close();
            } 
          } 
        } catch (FileNotFoundException e) {
          countMissing++;
          if (countMissing > 10)
            break; 
        } catch (IOException e) {
          e.printStackTrace();
        } 
      } 
      if (listSkyLayers.size() > 0) {
        CustomSkyLayer[] sls = listSkyLayers.<CustomSkyLayer>toArray(new CustomSkyLayer[listSkyLayers.size()]);
        wsls[w] = sls;
        lastWorldId = w;
      } 
    } 
    if (lastWorldId < 0)
      return null; 
    int worldCount = lastWorldId + 1;
    CustomSkyLayer[][] wslsTrim = new CustomSkyLayer[worldCount][0];
    for (int i = 0; i < wslsTrim.length; i++)
      wslsTrim[i] = wsls[i]; 
    return wslsTrim;
  }
  
  public static void renderSky(Level world, PoseStack matrixStackIn, float partialTicks) {
    if (worldSkyLayers == null)
      return; 
    if (Config.isShaders())
      Shaders.setRenderStage(RenderStage.CUSTOM_SKY); 
    int dimId = WorldUtils.getDimensionId(world);
    if (dimId < 0 || dimId >= worldSkyLayers.length)
      return; 
    CustomSkyLayer[] sls = worldSkyLayers[dimId];
    if (sls == null)
      return; 
    long time = world.getDayTime();
    int timeOfDay = (int)(time % 24000L);
    float celestialAngle = world.getTimeOfDay(partialTicks);
    float rainStrength = world.getRainLevel(partialTicks);
    float thunderStrength = world.getThunderLevel(partialTicks);
    if (rainStrength > 0.0F)
      thunderStrength /= rainStrength; 
    for (int i = 0; i < sls.length; i++) {
      CustomSkyLayer sl = sls[i];
      if (sl.isActive(world, timeOfDay))
        sl.render(world, matrixStackIn, timeOfDay, celestialAngle, rainStrength, thunderStrength); 
    } 
    float rainBrightness = 1.0F - rainStrength;
    Blender.clearBlend(rainBrightness);
  }
  
  public static boolean hasSkyLayers(Level world) {
    if (worldSkyLayers == null)
      return false; 
    int dimId = WorldUtils.getDimensionId(world);
    if (dimId < 0 || dimId >= worldSkyLayers.length)
      return false; 
    CustomSkyLayer[] sls = worldSkyLayers[dimId];
    if (sls == null)
      return false; 
    return (sls.length > 0);
  }
}
