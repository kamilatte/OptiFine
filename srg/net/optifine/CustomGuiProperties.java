package srg.net.optifine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;
import net.optifine.Config;
import net.optifine.CustomGuis;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class CustomGuiProperties {
  private String fileName = null;
  
  private String basePath = null;
  
  private EnumContainer container = null;
  
  private Map<ResourceLocation, ResourceLocation> textureLocations = null;
  
  private NbtTagValue nbtName = null;
  
  private BiomeId[] biomes = null;
  
  private RangeListInt heights = null;
  
  private Boolean large = null;
  
  private Boolean trapped = null;
  
  private Boolean christmas = null;
  
  private Boolean ender = null;
  
  private RangeListInt levels = null;
  
  private MatchProfession[] professions = null;
  
  private EnumVariant[] variants = null;
  
  private DyeColor[] colors = null;
  
  private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
  
  private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
  
  private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
  
  private static final DyeColor[] COLORS_INVALID = new DyeColor[0];
  
  private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
  
  private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
  
  private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
  
  private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
  
  private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
  
  private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
  
  private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
  
  private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
  
  private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
  
  private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
  
  private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
  
  private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
  
  private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
  
  public CustomGuiProperties(Properties props, String path) {
    ConnectedParser cp = new ConnectedParser("CustomGuis");
    this.fileName = cp.parseName(path);
    this.basePath = cp.parseBasePath(path);
    this.container = (EnumContainer)cp.parseEnum(props.getProperty("container"), (Enum[])EnumContainer.values(), "container");
    this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
    this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name"));
    this.biomes = cp.parseBiomes(props.getProperty("biomes"));
    this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
    this.large = cp.parseBooleanObject(props.getProperty("large"));
    this.trapped = cp.parseBooleanObject(props.getProperty("trapped"));
    this.christmas = cp.parseBooleanObject(props.getProperty("christmas"));
    this.ender = cp.parseBooleanObject(props.getProperty("ender"));
    this.levels = cp.parseRangeListInt(props.getProperty("levels"));
    this.professions = cp.parseProfessions(props.getProperty("professions"));
    EnumVariant[] vars = getContainerVariants(this.container);
    this.variants = (EnumVariant[])cp.parseEnums(props.getProperty("variants"), (Enum[])vars, "variants", (Enum[])VARIANTS_INVALID);
    this.colors = parseEnumDyeColors(props.getProperty("colors"));
  }
  
  private static EnumVariant[] getContainerVariants(EnumContainer cont) {
    if (cont == EnumContainer.HORSE)
      return VARIANTS_HORSE; 
    if (cont == EnumContainer.DISPENSER)
      return VARIANTS_DISPENSER; 
    return new EnumVariant[0];
  }
  
  private static DyeColor[] parseEnumDyeColors(String str) {
    if (str == null)
      return null; 
    str = str.toLowerCase();
    String[] tokens = Config.tokenize(str, " ");
    DyeColor[] cols = new DyeColor[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      DyeColor col = parseEnumDyeColor(token);
      if (col == null) {
        warn("Invalid color: " + token);
        return COLORS_INVALID;
      } 
      cols[i] = col;
    } 
    return cols;
  }
  
  private static DyeColor parseEnumDyeColor(String str) {
    if (str == null)
      return null; 
    DyeColor[] colors = DyeColor.values();
    for (int i = 0; i < colors.length; i++) {
      DyeColor enumDyeColor = colors[i];
      if (enumDyeColor.getSerializedName().equals(str))
        return enumDyeColor; 
      if (enumDyeColor.getName().equals(str))
        return enumDyeColor; 
    } 
    return null;
  }
  
  private static ResourceLocation parseTextureLocation(String str, String basePath) {
    if (str == null)
      return null; 
    str = str.trim();
    String tex = TextureUtils.fixResourcePath(str, basePath);
    if (!tex.endsWith(".png"))
      tex = tex + ".png"; 
    return new ResourceLocation(basePath + "/" + basePath);
  }
  
  private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
    Map<ResourceLocation, ResourceLocation> map = new HashMap<>();
    String propVal = props.getProperty(property);
    if (propVal != null) {
      ResourceLocation locKey = getGuiTextureLocation(container);
      ResourceLocation locVal = parseTextureLocation(propVal, basePath);
      if (locKey != null && locVal != null)
        map.put(locKey, locVal); 
    } 
    String keyPrefix = property + ".";
    Set<Object> keys = props.keySet();
    for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
      String key = it.next();
      if (!key.startsWith(keyPrefix))
        continue; 
      String pathRel = key.substring(keyPrefix.length());
      pathRel = pathRel.replace('\\', '/');
      pathRel = StrUtils.removePrefixSuffix(pathRel, "/", ".png");
      String path = pathPrefix + pathPrefix + ".png";
      String val = props.getProperty(key);
      ResourceLocation locKey = new ResourceLocation(path);
      ResourceLocation locVal = parseTextureLocation(val, basePath);
      map.put(locKey, locVal);
    } 
    return map;
  }
  
  private static ResourceLocation getGuiTextureLocation(EnumContainer container) {
    if (container == null)
      return null; 
    switch (container.ordinal()) {
      case 0:
        return ANVIL_GUI_TEXTURE;
      case 1:
        return BEACON_GUI_TEXTURE;
      case 2:
        return BREWING_STAND_GUI_TEXTURE;
      case 3:
        return CHEST_GUI_TEXTURE;
      case 4:
        return CRAFTING_TABLE_GUI_TEXTURE;
      case 12:
        return null;
      case 5:
        return DISPENSER_GUI_TEXTURE;
      case 6:
        return ENCHANTMENT_TABLE_GUI_TEXTURE;
      case 7:
        return FURNACE_GUI_TEXTURE;
      case 8:
        return HOPPER_GUI_TEXTURE;
      case 9:
        return HORSE_GUI_TEXTURE;
      case 13:
        return INVENTORY_GUI_TEXTURE;
      case 11:
        return SHULKER_BOX_GUI_TEXTURE;
      case 10:
        return VILLAGER_GUI_TEXTURE;
    } 
    return null;
  }
  
  public boolean isValid(String path) {
    if (this.fileName == null || this.fileName.length() <= 0) {
      warn("No name found: " + path);
      return false;
    } 
    if (this.basePath == null) {
      warn("No base path found: " + path);
      return false;
    } 
    if (this.container == null) {
      warn("No container found: " + path);
      return false;
    } 
    if (this.textureLocations.isEmpty()) {
      warn("No texture found: " + path);
      return false;
    } 
    if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
      warn("Invalid professions or careers: " + path);
      return false;
    } 
    if (this.variants == VARIANTS_INVALID) {
      warn("Invalid variants: " + path);
      return false;
    } 
    if (this.colors == COLORS_INVALID) {
      warn("Invalid colors: " + path);
      return false;
    } 
    return true;
  }
  
  private static void warn(String str) {
    Config.warn("[CustomGuis] " + str);
  }
  
  private boolean matchesGeneral(EnumContainer ec, BlockPos pos, LevelReader blockAccess) {
    if (this.container != ec)
      return false; 
    if (this.biomes != null) {
      Biome biome = (Biome)blockAccess.getBiome(pos).value();
      if (!Matches.biome(biome, this.biomes))
        return false; 
    } 
    if (this.heights != null)
      if (!this.heights.isInRange(pos.getY()))
        return false;  
    return true;
  }
  
  public boolean matchesPos(EnumContainer ec, BlockPos pos, LevelReader blockAccess, Screen screen) {
    if (!matchesGeneral(ec, pos, blockAccess))
      return false; 
    if (this.nbtName != null) {
      String name = getName(screen);
      if (!this.nbtName.matchesValue(name))
        return false; 
    } 
    switch (ec.ordinal()) {
      case 1:
        return matchesBeacon(pos, (BlockAndTintGetter)blockAccess);
      case 3:
        return matchesChest(pos, (BlockAndTintGetter)blockAccess);
      case 5:
        return matchesDispenser(pos, (BlockAndTintGetter)blockAccess);
      case 11:
        return matchesShulker(pos, (BlockAndTintGetter)blockAccess);
    } 
    return true;
  }
  
  public static String getName(Screen screen) {
    Component itc = screen.getTitle();
    if (itc == null)
      return null; 
    return itc.getString();
  }
  
  private boolean matchesBeacon(BlockPos pos, BlockAndTintGetter blockAccess) {
    BlockEntity te = blockAccess.getBlockEntity(pos);
    if (!(te instanceof BeaconBlockEntity))
      return false; 
    BeaconBlockEntity teb = (BeaconBlockEntity)te;
    if (this.levels != null) {
      if (!Reflector.TileEntityBeacon_levels.exists())
        return false; 
      int l = Reflector.getFieldValueInt(teb, Reflector.TileEntityBeacon_levels, -1);
      if (!this.levels.isInRange(l))
        return false; 
    } 
    return true;
  }
  
  private boolean matchesChest(BlockPos pos, BlockAndTintGetter blockAccess) {
    BlockEntity te = blockAccess.getBlockEntity(pos);
    if (te instanceof ChestBlockEntity) {
      ChestBlockEntity tec = (ChestBlockEntity)te;
      return matchesChest(tec, pos, blockAccess);
    } 
    if (te instanceof EnderChestBlockEntity) {
      EnderChestBlockEntity teec = (EnderChestBlockEntity)te;
      return matchesEnderChest(teec, pos, blockAccess);
    } 
    return false;
  }
  
  private boolean matchesChest(ChestBlockEntity tec, BlockPos pos, BlockAndTintGetter blockAccess) {
    BlockState blockState = blockAccess.getBlockState(pos);
    ChestType chestType = blockState.hasProperty((Property)ChestBlock.TYPE) ? (ChestType)blockState.getValue((Property)ChestBlock.TYPE) : ChestType.SINGLE;
    boolean isLarge = (chestType != ChestType.SINGLE);
    boolean isTrapped = tec instanceof net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
    boolean isChristmas = CustomGuis.isChristmas;
    boolean isEnder = false;
    return matchesChest(isLarge, isTrapped, isChristmas, isEnder);
  }
  
  private boolean matchesEnderChest(EnderChestBlockEntity teec, BlockPos pos, BlockAndTintGetter blockAccess) {
    return matchesChest(false, false, false, true);
  }
  
  private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
    if (this.large != null)
      if (this.large.booleanValue() != isLarge)
        return false;  
    if (this.trapped != null)
      if (this.trapped.booleanValue() != isTrapped)
        return false;  
    if (this.christmas != null)
      if (this.christmas.booleanValue() != isChristmas)
        return false;  
    if (this.ender != null)
      if (this.ender.booleanValue() != isEnder)
        return false;  
    return true;
  }
  
  private boolean matchesDispenser(BlockPos pos, BlockAndTintGetter blockAccess) {
    BlockEntity te = blockAccess.getBlockEntity(pos);
    if (!(te instanceof DispenserBlockEntity))
      return false; 
    DispenserBlockEntity ted = (DispenserBlockEntity)te;
    if (this.variants != null) {
      EnumVariant var = getDispenserVariant(ted);
      if (!Config.equalsOne(var, (Object[])this.variants))
        return false; 
    } 
    return true;
  }
  
  private EnumVariant getDispenserVariant(DispenserBlockEntity ted) {
    if (ted instanceof net.minecraft.world.level.block.entity.DropperBlockEntity)
      return EnumVariant.DROPPER; 
    return EnumVariant.DISPENSER;
  }
  
  private boolean matchesShulker(BlockPos pos, BlockAndTintGetter blockAccess) {
    BlockEntity te = blockAccess.getBlockEntity(pos);
    if (!(te instanceof ShulkerBoxBlockEntity))
      return false; 
    ShulkerBoxBlockEntity tesb = (ShulkerBoxBlockEntity)te;
    if (this.colors != null) {
      DyeColor col = tesb.getColor();
      if (!Config.equalsOne(col, (Object[])this.colors))
        return false; 
    } 
    return true;
  }
  
  public boolean matchesEntity(EnumContainer ec, Entity entity, LevelReader blockAccess) {
    if (!matchesGeneral(ec, entity.blockPosition(), blockAccess))
      return false; 
    if (this.nbtName != null) {
      String entityName = entity.getScoreboardName();
      if (!this.nbtName.matchesValue(entityName))
        return false; 
    } 
    switch (ec.ordinal()) {
      case 10:
        return matchesVillager(entity, (BlockAndTintGetter)blockAccess);
      case 9:
        return matchesHorse(entity, (BlockAndTintGetter)blockAccess);
    } 
    return true;
  }
  
  private boolean matchesVillager(Entity entity, BlockAndTintGetter blockAccess) {
    if (!(entity instanceof Villager))
      return false; 
    Villager entityVillager = (Villager)entity;
    if (this.professions != null) {
      VillagerData vd = entityVillager.getVillagerData();
      VillagerProfession vp = vd.getProfession();
      int level = vd.getLevel();
      if (!MatchProfession.matchesOne(vp, level, this.professions))
        return false; 
    } 
    return true;
  }
  
  private boolean matchesHorse(Entity entity, BlockAndTintGetter blockAccess) {
    if (!(entity instanceof AbstractHorse))
      return false; 
    AbstractHorse ah = (AbstractHorse)entity;
    if (this.variants != null) {
      EnumVariant var = getHorseVariant(ah);
      if (!Config.equalsOne(var, (Object[])this.variants))
        return false; 
    } 
    if (this.colors != null)
      if (ah instanceof Llama) {
        Llama el = (Llama)ah;
        DyeColor col = el.getSwag();
        if (!Config.equalsOne(col, (Object[])this.colors))
          return false; 
      }  
    return true;
  }
  
  private EnumVariant getHorseVariant(AbstractHorse entity) {
    if (entity instanceof net.minecraft.world.entity.animal.horse.Horse)
      return EnumVariant.HORSE; 
    if (entity instanceof net.minecraft.world.entity.animal.horse.Donkey)
      return EnumVariant.DONKEY; 
    if (entity instanceof net.minecraft.world.entity.animal.horse.Mule)
      return EnumVariant.MULE; 
    if (entity instanceof Llama)
      return EnumVariant.LLAMA; 
    return null;
  }
  
  public EnumContainer getContainer() {
    return this.container;
  }
  
  public ResourceLocation getTextureLocation(ResourceLocation loc) {
    ResourceLocation locNew = this.textureLocations.get(loc);
    if (locNew == null)
      return loc; 
    return locNew;
  }
  
  public String toString() {
    return "name: " + this.fileName + ", container: " + String.valueOf(this.container) + ", textures: " + String.valueOf(this.textureLocations);
  }
}
