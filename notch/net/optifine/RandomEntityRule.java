package notch.net.optifine;

import akr;
import bsr;
import btn;
import cfh;
import cga;
import cgh;
import chl;
import ckf;
import ckm;
import cmk;
import cml;
import cmn;
import cti;
import dcw;
import dqd;
import dqh;
import drr;
import dtc;
import fzf;
import java.util.Properties;
import jd;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntity;
import net.optifine.RandomEntityContext;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.Weather;
import net.optifine.util.ArrayUtils;
import net.optifine.util.MathUtils;
import ub;

public class RandomEntityRule<T> {
  private String pathProps = null;
  
  private akr baseResLoc = null;
  
  private int index;
  
  private RandomEntityContext<T> context;
  
  private int[] textures = null;
  
  private T[] resources = null;
  
  private int[] weights = null;
  
  private BiomeId[] biomes = null;
  
  private RangeListInt heights = null;
  
  private RangeListInt healthRange = null;
  
  private boolean healthPercent = false;
  
  private NbtTagValue nbtName = null;
  
  public int[] sumWeights = null;
  
  public int sumAllWeights = 1;
  
  private MatchProfession[] professions = null;
  
  private cti[] colors = null;
  
  private Boolean baby = null;
  
  private RangeListInt moonPhases = null;
  
  private RangeListInt dayTimes = null;
  
  private Weather[] weatherList = null;
  
  private RangeListInt sizes = null;
  
  public NbtTagValue[] nbtTagValues = null;
  
  public MatchBlock[] blocks = null;
  
  public RandomEntityRule(Properties props, String pathProps, akr baseResLoc, int index, String valTextures, RandomEntityContext<T> context) {
    this.pathProps = pathProps;
    this.baseResLoc = baseResLoc;
    this.index = index;
    this.context = context;
    ConnectedParser cp = context.getConnectedParser();
    this.textures = cp.parseIntList(valTextures);
    this.weights = cp.parseIntList(props.getProperty("weights." + index));
    this.biomes = cp.parseBiomes(props.getProperty("biomes." + index));
    this.heights = cp.parseRangeListIntNeg(props.getProperty("heights." + index));
    if (this.heights == null)
      this.heights = parseMinMaxHeight(props, index); 
    String healthStr = props.getProperty("health." + index);
    if (healthStr != null) {
      this.healthPercent = healthStr.contains("%");
      healthStr = healthStr.replace("%", "");
      this.healthRange = cp.parseRangeListInt(healthStr);
    } 
    this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
    this.professions = cp.parseProfessions(props.getProperty("professions." + index));
    this.colors = cp.parseDyeColors(props.getProperty("colors." + index), "color", ConnectedParser.DYE_COLORS_INVALID);
    if (this.colors == null)
      this.colors = cp.parseDyeColors(props.getProperty("collarColors." + index), "collar color", ConnectedParser.DYE_COLORS_INVALID); 
    this.baby = cp.parseBooleanObject(props.getProperty("baby." + index));
    this.moonPhases = cp.parseRangeListInt(props.getProperty("moonPhase." + index));
    this.dayTimes = cp.parseRangeListInt(props.getProperty("dayTime." + index));
    this.weatherList = cp.parseWeather(props.getProperty("weather." + index), "weather." + index, null);
    this.sizes = cp.parseRangeListInt(props.getProperty("sizes." + index));
    this.nbtTagValues = cp.parseNbtTagValues(props, "nbt." + index + ".");
    this.blocks = cp.parseMatchBlocks(props.getProperty("blocks." + index));
  }
  
  public int getIndex() {
    return this.index;
  }
  
  private RangeListInt parseMinMaxHeight(Properties props, int index) {
    String minHeightStr = props.getProperty("minHeight." + index);
    String maxHeightStr = props.getProperty("maxHeight." + index);
    if (minHeightStr == null && maxHeightStr == null)
      return null; 
    int minHeight = 0;
    if (minHeightStr != null) {
      minHeight = Config.parseInt(minHeightStr, -1);
      if (minHeight < 0) {
        Config.warn("Invalid minHeight: " + minHeightStr);
        return null;
      } 
    } 
    int maxHeight = 256;
    if (maxHeightStr != null) {
      maxHeight = Config.parseInt(maxHeightStr, -1);
      if (maxHeight < 0) {
        Config.warn("Invalid maxHeight: " + maxHeightStr);
        return null;
      } 
    } 
    if (maxHeight < 0) {
      Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
      return null;
    } 
    RangeListInt list = new RangeListInt();
    list.addRange(new RangeInt(minHeight, maxHeight));
    return list;
  }
  
  public boolean isValid(String path) {
    String resourceName = this.context.getResourceName();
    String resourceNamePlural = this.context.getResourceNamePlural();
    if (this.textures == null || this.textures.length == 0) {
      Config.warn("Invalid " + resourceNamePlural + " for rule: " + this.index);
      return false;
    } 
    this.resources = (T[])new Object[this.textures.length];
    for (int i = 0; i < this.textures.length; i++) {
      int index = this.textures[i];
      T res = (T)this.context.makeResource(this.baseResLoc, index);
      if (res == null)
        return false; 
      this.resources[i] = res;
    } 
    if (this.weights != null) {
      if (this.weights.length > this.resources.length) {
        Config.warn("More weights defined than " + resourceNamePlural + ", trimming weights: " + path);
        int[] weights2 = new int[this.resources.length];
        System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
        this.weights = weights2;
      } 
      if (this.weights.length < this.resources.length) {
        Config.warn("Less weights defined than " + resourceNamePlural + ", expanding weights: " + path);
        int[] weights2 = new int[this.resources.length];
        System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
        int avgWeight = MathUtils.getAverage(this.weights);
        for (int k = this.weights.length; k < weights2.length; k++)
          weights2[k] = avgWeight; 
        this.weights = weights2;
      } 
      this.sumWeights = new int[this.weights.length];
      int sum = 0;
      for (int j = 0; j < this.weights.length; j++) {
        if (this.weights[j] < 0) {
          Config.warn("Invalid weight: " + this.weights[j]);
          return false;
        } 
        sum += this.weights[j];
        this.sumWeights[j] = sum;
      } 
      this.sumAllWeights = sum;
      if (this.sumAllWeights <= 0) {
        Config.warn("Invalid sum of all weights: " + sum);
        this.sumAllWeights = 1;
      } 
    } 
    if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
      Config.warn("Invalid professions or careers: " + path);
      return false;
    } 
    if (this.colors == ConnectedParser.DYE_COLORS_INVALID) {
      Config.warn("Invalid colors: " + path);
      return false;
    } 
    return true;
  }
  
  public boolean matches(IRandomEntity randomEntity) {
    if (this.biomes != null)
      if (!Matches.biome(randomEntity.getSpawnBiome(), this.biomes))
        return false;  
    if (this.heights != null) {
      jd pos = randomEntity.getSpawnPosition();
      if (pos != null)
        if (!this.heights.isInRange(pos.v()))
          return false;  
    } 
    if (this.healthRange != null) {
      int health = randomEntity.getHealth();
      if (this.healthPercent) {
        int healthMax = randomEntity.getMaxHealth();
        if (healthMax > 0)
          health = (int)((health * 100) / healthMax); 
      } 
      if (!this.healthRange.isInRange(health))
        return false; 
    } 
    if (this.nbtName != null) {
      String name = randomEntity.getName();
      if (!this.nbtName.matchesValue(name))
        return false; 
    } 
    if (this.professions != null)
      if (randomEntity instanceof RandomEntity) {
        RandomEntity rme = (RandomEntity)randomEntity;
        bsr entity = rme.getEntity();
        if (entity instanceof cmk) {
          cmk entityVillager = (cmk)entity;
          cml vd = entityVillager.gv();
          cmn vp = vd.b();
          int level = vd.c();
          if (!MatchProfession.matchesOne(vp, level, this.professions))
            return false; 
        } 
      }  
    if (this.colors != null) {
      cti col = randomEntity.getColor();
      if (col != null)
        if (!Config.equalsOne(col, (Object[])this.colors))
          return false;  
    } 
    if (this.baby != null)
      if (randomEntity instanceof RandomEntity) {
        RandomEntity rme = (RandomEntity)randomEntity;
        bsr entity = rme.getEntity();
        if (entity instanceof btn) {
          btn livingEntity = (btn)entity;
          if (livingEntity.o_() != this.baby.booleanValue())
            return false; 
        } 
      }  
    if (this.moonPhases != null) {
      fzf fzf = (Config.getMinecraft()).r;
      if (fzf != null) {
        int moonPhase = fzf.ar();
        if (!this.moonPhases.isInRange(moonPhase))
          return false; 
      } 
    } 
    if (this.dayTimes != null) {
      fzf fzf = (Config.getMinecraft()).r;
      if (fzf != null) {
        int dayTime = (int)(fzf.aa() % 24000L);
        if (!this.dayTimes.isInRange(dayTime))
          return false; 
      } 
    } 
    if (this.weatherList != null) {
      fzf fzf = (Config.getMinecraft()).r;
      if (fzf != null) {
        Weather weather = Weather.getWeather((dcw)fzf, 0.0F);
        if (!ArrayUtils.contains((Object[])this.weatherList, weather))
          return false; 
      } 
    } 
    if (this.sizes != null)
      if (randomEntity instanceof RandomEntity) {
        RandomEntity rme = (RandomEntity)randomEntity;
        bsr entity = rme.getEntity();
        int size = getEntitySize(entity);
        if (size >= 0)
          if (!this.sizes.isInRange(size))
            return false;  
      }  
    if (this.nbtTagValues != null) {
      ub nbt = randomEntity.getNbtTag();
      if (nbt != null)
        for (int i = 0; i < this.nbtTagValues.length; i++) {
          NbtTagValue ntv = this.nbtTagValues[i];
          if (!ntv.matches(nbt))
            return false; 
        }  
    } 
    if (this.blocks != null) {
      dtc blockState = randomEntity.getBlockState();
      if (blockState != null)
        if (!Matches.block(blockState, this.blocks))
          return false;  
    } 
    return true;
  }
  
  public static cti getEntityColor(bsr entity) {
    if (entity instanceof cgh) {
      cgh entityWolf = (cgh)entity;
      if (!entityWolf.s())
        return null; 
      return entityWolf.gz();
    } 
    if (entity instanceof cfh) {
      cfh entityCat = (cfh)entity;
      if (!entityCat.s())
        return null; 
      return entityCat.gx();
    } 
    if (entity instanceof cga) {
      cga entitySheep = (cga)entity;
      return entitySheep.t();
    } 
    if (entity instanceof chl) {
      chl entityLlama = (chl)entity;
      return entityLlama.gw();
    } 
    return null;
  }
  
  public static cti getBlockEntityColor(dqh entity) {
    if (entity instanceof dqd) {
      dqd entityBed = (dqd)entity;
      return entityBed.c();
    } 
    if (entity instanceof drr) {
      drr entityShulkerBox = (drr)entity;
      return entityShulkerBox.u();
    } 
    return null;
  }
  
  private int getEntitySize(bsr entity) {
    if (entity instanceof ckm) {
      ckm entitySlime = (ckm)entity;
      return entitySlime.gl() - 1;
    } 
    if (entity instanceof ckf) {
      ckf entityPhantom = (ckf)entity;
      return entityPhantom.s();
    } 
    return -1;
  }
  
  public T getResource(int randomId, T resDef) {
    if (this.resources == null || this.resources.length == 0)
      return resDef; 
    int index = getResourceIndex(randomId);
    return this.resources[index];
  }
  
  private int getResourceIndex(int randomId) {
    int index = 0;
    if (this.weights == null) {
      index = randomId % this.resources.length;
    } else {
      int randWeight = randomId % this.sumAllWeights;
      for (int i = 0; i < this.sumWeights.length; i++) {
        if (this.sumWeights[i] > randWeight) {
          index = i;
          break;
        } 
      } 
    } 
    return index;
  }
  
  public T[] getResources() {
    return this.resources;
  }
}
