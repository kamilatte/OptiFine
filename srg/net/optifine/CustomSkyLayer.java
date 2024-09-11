package srg.net.optifine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.optifine.Config;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.MathUtils;
import net.optifine.util.NumUtils;
import net.optifine.util.SmoothFloat;
import net.optifine.util.TextureUtils;
import org.joml.Matrix4f;

public class CustomSkyLayer {
  public String source = null;
  
  private int startFadeIn = -1;
  
  private int endFadeIn = -1;
  
  private int startFadeOut = -1;
  
  private int endFadeOut = -1;
  
  private int blend = 1;
  
  private boolean rotate = false;
  
  private float speed = 1.0F;
  
  private float[] axis = DEFAULT_AXIS;
  
  private RangeListInt days = null;
  
  private int daysLoop = 8;
  
  private boolean weatherClear = true;
  
  private boolean weatherRain = false;
  
  private boolean weatherThunder = false;
  
  public BiomeId[] biomes = null;
  
  public RangeListInt heights = null;
  
  private float transition = 1.0F;
  
  private SmoothFloat smoothPositionBrightness = null;
  
  public int textureId = -1;
  
  private Level lastWorld = null;
  
  public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
  
  private static final String WEATHER_CLEAR = "clear";
  
  private static final String WEATHER_RAIN = "rain";
  
  private static final String WEATHER_THUNDER = "thunder";
  
  public CustomSkyLayer(Properties props, String defSource) {
    ConnectedParser cp = new ConnectedParser("CustomSky");
    this.source = props.getProperty("source", defSource);
    this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
    this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
    this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
    this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
    this.blend = Blender.parseBlend(props.getProperty("blend"));
    this.rotate = parseBoolean(props.getProperty("rotate"), true);
    this.speed = parseFloat(props.getProperty("speed"), 1.0F);
    this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
    this.days = cp.parseRangeListInt(props.getProperty("days"));
    this.daysLoop = cp.parseInt(props.getProperty("daysLoop"), 8);
    List<String> weatherList = parseWeatherList(props.getProperty("weather", "clear"));
    this.weatherClear = weatherList.contains("clear");
    this.weatherRain = weatherList.contains("rain");
    this.weatherThunder = weatherList.contains("thunder");
    this.biomes = cp.parseBiomes(props.getProperty("biomes"));
    this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
    this.transition = parseFloat(props.getProperty("transition"), 1.0F);
  }
  
  private List<String> parseWeatherList(String str) {
    List<String> weatherAllowedList = Arrays.asList(new String[] { "clear", "rain", "thunder" });
    List<String> weatherList = new ArrayList<>();
    String[] weatherStrs = Config.tokenize(str, " ");
    for (int i = 0; i < weatherStrs.length; i++) {
      String token = weatherStrs[i];
      if (!weatherAllowedList.contains(token)) {
        Config.warn("Unknown weather: " + token);
      } else {
        weatherList.add(token);
      } 
    } 
    return weatherList;
  }
  
  private int parseTime(String str) {
    if (str == null)
      return -1; 
    String[] strs = Config.tokenize(str, ":");
    if (strs.length != 2) {
      Config.warn("Invalid time: " + str);
      return -1;
    } 
    String hourStr = strs[0];
    String minStr = strs[1];
    int hour = Config.parseInt(hourStr, -1);
    int min = Config.parseInt(minStr, -1);
    if (hour < 0 || hour > 23 || min < 0 || min > 59) {
      Config.warn("Invalid time: " + str);
      return -1;
    } 
    hour -= 6;
    if (hour < 0)
      hour += 24; 
    int time = hour * 1000 + (int)(min / 60.0D * 1000.0D);
    return time;
  }
  
  private boolean parseBoolean(String str, boolean defVal) {
    if (str == null)
      return defVal; 
    if (str.toLowerCase().equals("true"))
      return true; 
    if (str.toLowerCase().equals("false"))
      return false; 
    Config.warn("Unknown boolean: " + str);
    return defVal;
  }
  
  private float parseFloat(String str, float defVal) {
    if (str == null)
      return defVal; 
    float val = Config.parseFloat(str, Float.MIN_VALUE);
    if (val == Float.MIN_VALUE) {
      Config.warn("Invalid value: " + str);
      return defVal;
    } 
    return val;
  }
  
  private float[] parseAxis(String str, float[] defVal) {
    if (str == null)
      return defVal; 
    String[] strs = Config.tokenize(str, " ");
    if (strs.length != 3) {
      Config.warn("Invalid axis: " + str);
      return defVal;
    } 
    float[] fs = new float[3];
    for (int i = 0; i < strs.length; i++) {
      fs[i] = Config.parseFloat(strs[i], Float.MIN_VALUE);
      if (fs[i] == Float.MIN_VALUE) {
        Config.warn("Invalid axis: " + str);
        return defVal;
      } 
    } 
    float ax = fs[0];
    float ay = fs[1];
    float az = fs[2];
    if (ax * ax + ay * ay + az * az < 1.0E-5F) {
      Config.warn("Invalid axis values: " + str);
      return defVal;
    } 
    float[] as = { az, ay, -ax };
    return as;
  }
  
  public boolean isValid(String path) {
    if (this.source == null) {
      Config.warn("No source texture: " + path);
      return false;
    } 
    this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
    if (this.startFadeIn < 0 && this.endFadeIn < 0 && this.startFadeOut < 0 && this.endFadeOut < 0) {
      this.startFadeIn = 0;
      this.endFadeIn = 0;
      this.startFadeOut = 24000;
      this.endFadeOut = 24000;
    } 
    if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
      Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
      return false;
    } 
    int timeFadeIn = normalizeTime(this.endFadeIn - this.startFadeIn);
    if (this.startFadeOut < 0) {
      this.startFadeOut = normalizeTime(this.endFadeOut - timeFadeIn);
      if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn))
        this.startFadeOut = this.endFadeIn; 
    } 
    int timeOn = normalizeTime(this.startFadeOut - this.endFadeIn);
    int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
    int timeOff = normalizeTime(this.startFadeIn - this.endFadeOut);
    int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
    if (timeSum != 0 && timeSum != 24000) {
      Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + timeSum);
      return false;
    } 
    if (this.speed < 0.0F) {
      Config.warn("Invalid speed: " + this.speed);
      return false;
    } 
    if (this.daysLoop <= 0) {
      Config.warn("Invalid daysLoop: " + this.daysLoop);
      return false;
    } 
    return true;
  }
  
  private int normalizeTime(int timeMc) {
    while (timeMc >= 24000)
      timeMc -= 24000; 
    while (timeMc < 0)
      timeMc += 24000; 
    return timeMc;
  }
  
  public void render(Level world, PoseStack matrixStackIn, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
    float positionBrightness = getPositionBrightness(world);
    float weatherBrightness = getWeatherBrightness(rainStrength, thunderStrength);
    float fadeBrightness = getFadeBrightness(timeOfDay);
    float brightness = positionBrightness * weatherBrightness * fadeBrightness;
    brightness = Config.limit(brightness, 0.0F, 1.0F);
    if (brightness < 1.0E-4F)
      return; 
    RenderSystem.setShaderTexture(0, this.textureId);
    Blender.setupBlend(this.blend, brightness);
    matrixStackIn.pushPose();
    if (this.rotate) {
      float angleDayStart = 0.0F;
      if (this.speed != Math.round(this.speed)) {
        long worldDay = (world.getDayTime() + 18000L) / 24000L;
        double anglePerDay = (this.speed % 1.0F);
        double angleDayNow = worldDay * anglePerDay;
        angleDayStart = (float)(angleDayNow % 1.0D);
      } 
      matrixStackIn.rotateDeg(360.0F * (angleDayStart + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
    } 
    Tesselator tess = Tesselator.getInstance();
    matrixStackIn.rotateDegXp(90.0F);
    matrixStackIn.rotateDegZp(-90.0F);
    renderSide(matrixStackIn, tess, 4);
    matrixStackIn.pushPose();
    matrixStackIn.rotateDegXp(90.0F);
    renderSide(matrixStackIn, tess, 1);
    matrixStackIn.popPose();
    matrixStackIn.pushPose();
    matrixStackIn.rotateDegXp(-90.0F);
    renderSide(matrixStackIn, tess, 0);
    matrixStackIn.popPose();
    matrixStackIn.rotateDegZp(90.0F);
    renderSide(matrixStackIn, tess, 5);
    matrixStackIn.rotateDegZp(90.0F);
    renderSide(matrixStackIn, tess, 2);
    matrixStackIn.rotateDegZp(90.0F);
    renderSide(matrixStackIn, tess, 3);
    matrixStackIn.popPose();
  }
  
  private float getPositionBrightness(Level world) {
    if (this.biomes == null && this.heights == null)
      return 1.0F; 
    float positionBrightness = getPositionBrightnessRaw(world);
    if (this.smoothPositionBrightness == null)
      this.smoothPositionBrightness = new SmoothFloat(positionBrightness, this.transition); 
    positionBrightness = this.smoothPositionBrightness.getSmoothValue(positionBrightness);
    return positionBrightness;
  }
  
  private float getPositionBrightnessRaw(Level world) {
    Entity renderViewEntity = Minecraft.getInstance().getCameraEntity();
    if (renderViewEntity == null)
      return 0.0F; 
    BlockPos pos = renderViewEntity.blockPosition();
    if (this.biomes != null) {
      Biome biome = (Biome)world.getBiome(pos).value();
      if (biome == null)
        return 0.0F; 
      if (!Matches.biome(biome, this.biomes))
        return 0.0F; 
    } 
    if (this.heights != null)
      if (!this.heights.isInRange(pos.getY()))
        return 0.0F;  
    return 1.0F;
  }
  
  private float getWeatherBrightness(float rainStrength, float thunderStrength) {
    float clearBrightness = 1.0F - rainStrength;
    float rainBrightness = rainStrength - thunderStrength;
    float thunderBrightness = thunderStrength;
    float weatherBrightness = 0.0F;
    if (this.weatherClear)
      weatherBrightness += clearBrightness; 
    if (this.weatherRain)
      weatherBrightness += rainBrightness; 
    if (this.weatherThunder)
      weatherBrightness += thunderBrightness; 
    weatherBrightness = NumUtils.limit(weatherBrightness, 0.0F, 1.0F);
    return weatherBrightness;
  }
  
  private float getFadeBrightness(int timeOfDay) {
    if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
      int timeFadeIn = normalizeTime(this.endFadeIn - this.startFadeIn);
      int timeDiff = normalizeTime(timeOfDay - this.startFadeIn);
      return timeDiff / timeFadeIn;
    } 
    if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut))
      return 1.0F; 
    if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
      int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
      int timeDiff = normalizeTime(timeOfDay - this.startFadeOut);
      return 1.0F - timeDiff / timeFadeOut;
    } 
    return 0.0F;
  }
  
  private void renderSide(PoseStack matrixStackIn, Tesselator tess, int side) {
    float tx = (side % 3) / 3.0F;
    float ty = (side / 3) / 2.0F;
    Matrix4f matrix4f = matrixStackIn.last().pose();
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    BufferBuilder wr = tess.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    addVertex(matrix4f, wr, -100.0F, -100.0F, -100.0F, tx, ty);
    addVertex(matrix4f, wr, -100.0F, -100.0F, 100.0F, tx, ty + 0.5F);
    addVertex(matrix4f, wr, 100.0F, -100.0F, 100.0F, tx + 0.33333334F, ty + 0.5F);
    addVertex(matrix4f, wr, 100.0F, -100.0F, -100.0F, tx + 0.33333334F, ty);
    BufferUploader.drawWithShader(wr.buildOrThrow());
  }
  
  private void addVertex(Matrix4f matrix4f, BufferBuilder buffer, float x, float y, float z, float u, float v) {
    float xt = MathUtils.getTransformX(matrix4f, x, y, z);
    float yt = MathUtils.getTransformY(matrix4f, x, y, z);
    float zt = MathUtils.getTransformZ(matrix4f, x, y, z);
    buffer.addVertex(xt, yt, zt).setUv(u, v);
  }
  
  public boolean isActive(Level world, int timeOfDay) {
    if (world != this.lastWorld) {
      this.lastWorld = world;
      this.smoothPositionBrightness = null;
    } 
    if (timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn))
      return false; 
    if (this.days != null) {
      long time = world.getDayTime();
      long timeShift = time - this.startFadeIn;
      while (timeShift < 0L)
        timeShift += (24000 * this.daysLoop); 
      int day = (int)(timeShift / 24000L);
      int dayOfLoop = day % this.daysLoop;
      if (!this.days.isInRange(dayOfLoop))
        return false; 
    } 
    return true;
  }
  
  private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
    if (timeStart <= timeEnd)
      return (timeOfDay >= timeStart && timeOfDay <= timeEnd); 
    return (timeOfDay >= timeStart || timeOfDay <= timeEnd);
  }
  
  public String toString() {
    return this.source + ", " + this.source + "-" + this.startFadeIn + " " + this.endFadeIn + "-" + this.startFadeOut;
  }
}
