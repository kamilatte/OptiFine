package srg.net.optifine;

import java.util.Properties;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class RandomEntityRule<T> {
  private String pathProps = null;
  
  private ResourceLocation baseResLoc = null;
  
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
  
  private DyeColor[] colors = null;
  
  private Boolean baby = null;
  
  private RangeListInt moonPhases = null;
  
  private RangeListInt dayTimes = null;
  
  private Weather[] weatherList = null;
  
  private RangeListInt sizes = null;
  
  public NbtTagValue[] nbtTagValues = null;
  
  public MatchBlock[] blocks = null;
  
  public RandomEntityRule(Properties props, String pathProps, ResourceLocation baseResLoc, int index, String valTextures, RandomEntityContext<T> context) {
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
      BlockPos pos = randomEntity.getSpawnPosition();
      if (pos != null)
        if (!this.heights.isInRange(pos.getY()))
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
        Entity entity = rme.getEntity();
        if (entity instanceof Villager) {
          Villager entityVillager = (Villager)entity;
          VillagerData vd = entityVillager.getVillagerData();
          VillagerProfession vp = vd.getProfession();
          int level = vd.getLevel();
          if (!MatchProfession.matchesOne(vp, level, this.professions))
            return false; 
        } 
      }  
    if (this.colors != null) {
      DyeColor col = randomEntity.getColor();
      if (col != null)
        if (!Config.equalsOne(col, (Object[])this.colors))
          return false;  
    } 
    if (this.baby != null)
      if (randomEntity instanceof RandomEntity) {
        RandomEntity rme = (RandomEntity)randomEntity;
        Entity entity = rme.getEntity();
        if (entity instanceof LivingEntity) {
          LivingEntity livingEntity = (LivingEntity)entity;
          if (livingEntity.isBaby() != this.baby.booleanValue())
            return false; 
        } 
      }  
    if (this.moonPhases != null) {
      ClientLevel clientLevel = (Config.getMinecraft()).level;
      if (clientLevel != null) {
        int moonPhase = clientLevel.getMoonPhase();
        if (!this.moonPhases.isInRange(moonPhase))
          return false; 
      } 
    } 
    if (this.dayTimes != null) {
      ClientLevel clientLevel = (Config.getMinecraft()).level;
      if (clientLevel != null) {
        int dayTime = (int)(clientLevel.getDayTime() % 24000L);
        if (!this.dayTimes.isInRange(dayTime))
          return false; 
      } 
    } 
    if (this.weatherList != null) {
      ClientLevel clientLevel = (Config.getMinecraft()).level;
      if (clientLevel != null) {
        Weather weather = Weather.getWeather((Level)clientLevel, 0.0F);
        if (!ArrayUtils.contains((Object[])this.weatherList, weather))
          return false; 
      } 
    } 
    if (this.sizes != null)
      if (randomEntity instanceof RandomEntity) {
        RandomEntity rme = (RandomEntity)randomEntity;
        Entity entity = rme.getEntity();
        int size = getEntitySize(entity);
        if (size >= 0)
          if (!this.sizes.isInRange(size))
            return false;  
      }  
    if (this.nbtTagValues != null) {
      CompoundTag nbt = randomEntity.getNbtTag();
      if (nbt != null)
        for (int i = 0; i < this.nbtTagValues.length; i++) {
          NbtTagValue ntv = this.nbtTagValues[i];
          if (!ntv.matches(nbt))
            return false; 
        }  
    } 
    if (this.blocks != null) {
      BlockState blockState = randomEntity.getBlockState();
      if (blockState != null)
        if (!Matches.block(blockState, this.blocks))
          return false;  
    } 
    return true;
  }
  
  public static DyeColor getEntityColor(Entity entity) {
    if (entity instanceof Wolf) {
      Wolf entityWolf = (Wolf)entity;
      if (!entityWolf.isTame())
        return null; 
      return entityWolf.getCollarColor();
    } 
    if (entity instanceof Cat) {
      Cat entityCat = (Cat)entity;
      if (!entityCat.isTame())
        return null; 
      return entityCat.getCollarColor();
    } 
    if (entity instanceof Sheep) {
      Sheep entitySheep = (Sheep)entity;
      return entitySheep.getColor();
    } 
    if (entity instanceof Llama) {
      Llama entityLlama = (Llama)entity;
      return entityLlama.getSwag();
    } 
    return null;
  }
  
  public static DyeColor getBlockEntityColor(BlockEntity entity) {
    if (entity instanceof BedBlockEntity) {
      BedBlockEntity entityBed = (BedBlockEntity)entity;
      return entityBed.getColor();
    } 
    if (entity instanceof ShulkerBoxBlockEntity) {
      ShulkerBoxBlockEntity entityShulkerBox = (ShulkerBoxBlockEntity)entity;
      return entityShulkerBox.getColor();
    } 
    return null;
  }
  
  private int getEntitySize(Entity entity) {
    if (entity instanceof Slime) {
      Slime entitySlime = (Slime)entity;
      return entitySlime.getSize() - 1;
    } 
    if (entity instanceof Phantom) {
      Phantom entityPhantom = (Phantom)entity;
      return entityPhantom.getPhantomSize();
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
