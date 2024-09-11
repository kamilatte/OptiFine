package notch.net.optifine;

import akr;
import bsr;
import chi;
import chl;
import cmk;
import cml;
import cmn;
import cti;
import dbz;
import dcz;
import ddw;
import dhb;
import dqc;
import dqh;
import dqo;
import dra;
import drd;
import drr;
import dtc;
import dtu;
import duf;
import fod;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jd;
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
import wz;

public class CustomGuiProperties {
  private String fileName = null;
  
  private String basePath = null;
  
  private EnumContainer container = null;
  
  private Map<akr, akr> textureLocations = null;
  
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
  
  private cti[] colors = null;
  
  private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
  
  private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
  
  private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
  
  private static final cti[] COLORS_INVALID = new cti[0];
  
  private static final akr ANVIL_GUI_TEXTURE = new akr("textures/gui/container/anvil.png");
  
  private static final akr BEACON_GUI_TEXTURE = new akr("textures/gui/container/beacon.png");
  
  private static final akr BREWING_STAND_GUI_TEXTURE = new akr("textures/gui/container/brewing_stand.png");
  
  private static final akr CHEST_GUI_TEXTURE = new akr("textures/gui/container/generic_54.png");
  
  private static final akr CRAFTING_TABLE_GUI_TEXTURE = new akr("textures/gui/container/crafting_table.png");
  
  private static final akr HORSE_GUI_TEXTURE = new akr("textures/gui/container/horse.png");
  
  private static final akr DISPENSER_GUI_TEXTURE = new akr("textures/gui/container/dispenser.png");
  
  private static final akr ENCHANTMENT_TABLE_GUI_TEXTURE = new akr("textures/gui/container/enchanting_table.png");
  
  private static final akr FURNACE_GUI_TEXTURE = new akr("textures/gui/container/furnace.png");
  
  private static final akr HOPPER_GUI_TEXTURE = new akr("textures/gui/container/hopper.png");
  
  private static final akr INVENTORY_GUI_TEXTURE = new akr("textures/gui/container/inventory.png");
  
  private static final akr SHULKER_BOX_GUI_TEXTURE = new akr("textures/gui/container/shulker_box.png");
  
  private static final akr VILLAGER_GUI_TEXTURE = new akr("textures/gui/container/villager2.png");
  
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
  
  private static cti[] parseEnumDyeColors(String str) {
    if (str == null)
      return null; 
    str = str.toLowerCase();
    String[] tokens = Config.tokenize(str, " ");
    cti[] cols = new cti[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      cti col = parseEnumDyeColor(token);
      if (col == null) {
        warn("Invalid color: " + token);
        return COLORS_INVALID;
      } 
      cols[i] = col;
    } 
    return cols;
  }
  
  private static cti parseEnumDyeColor(String str) {
    if (str == null)
      return null; 
    cti[] colors = cti.values();
    for (int i = 0; i < colors.length; i++) {
      cti enumDyeColor = colors[i];
      if (enumDyeColor.c().equals(str))
        return enumDyeColor; 
      if (enumDyeColor.b().equals(str))
        return enumDyeColor; 
    } 
    return null;
  }
  
  private static akr parseTextureLocation(String str, String basePath) {
    if (str == null)
      return null; 
    str = str.trim();
    String tex = TextureUtils.fixResourcePath(str, basePath);
    if (!tex.endsWith(".png"))
      tex = tex + ".png"; 
    return new akr(basePath + "/" + basePath);
  }
  
  private static Map<akr, akr> parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
    Map<akr, akr> map = new HashMap<>();
    String propVal = props.getProperty(property);
    if (propVal != null) {
      akr locKey = getGuiTextureLocation(container);
      akr locVal = parseTextureLocation(propVal, basePath);
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
      akr locKey = new akr(path);
      akr locVal = parseTextureLocation(val, basePath);
      map.put(locKey, locVal);
    } 
    return map;
  }
  
  private static akr getGuiTextureLocation(EnumContainer container) {
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
  
  private boolean matchesGeneral(EnumContainer ec, jd pos, dcz blockAccess) {
    if (this.container != ec)
      return false; 
    if (this.biomes != null) {
      ddw biome = (ddw)blockAccess.t(pos).a();
      if (!Matches.biome(biome, this.biomes))
        return false; 
    } 
    if (this.heights != null)
      if (!this.heights.isInRange(pos.v()))
        return false;  
    return true;
  }
  
  public boolean matchesPos(EnumContainer ec, jd pos, dcz blockAccess, fod screen) {
    if (!matchesGeneral(ec, pos, blockAccess))
      return false; 
    if (this.nbtName != null) {
      String name = getName(screen);
      if (!this.nbtName.matchesValue(name))
        return false; 
    } 
    switch (ec.ordinal()) {
      case 1:
        return matchesBeacon(pos, (dbz)blockAccess);
      case 3:
        return matchesChest(pos, (dbz)blockAccess);
      case 5:
        return matchesDispenser(pos, (dbz)blockAccess);
      case 11:
        return matchesShulker(pos, (dbz)blockAccess);
    } 
    return true;
  }
  
  public static String getName(fod screen) {
    wz itc = screen.n();
    if (itc == null)
      return null; 
    return itc.getString();
  }
  
  private boolean matchesBeacon(jd pos, dbz blockAccess) {
    dqh te = blockAccess.c_(pos);
    if (!(te instanceof dqc))
      return false; 
    dqc teb = (dqc)te;
    if (this.levels != null) {
      if (!Reflector.TileEntityBeacon_levels.exists())
        return false; 
      int l = Reflector.getFieldValueInt(teb, Reflector.TileEntityBeacon_levels, -1);
      if (!this.levels.isInRange(l))
        return false; 
    } 
    return true;
  }
  
  private boolean matchesChest(jd pos, dbz blockAccess) {
    dqh te = blockAccess.c_(pos);
    if (te instanceof dqo) {
      dqo tec = (dqo)te;
      return matchesChest(tec, pos, blockAccess);
    } 
    if (te instanceof drd) {
      drd teec = (drd)te;
      return matchesEnderChest(teec, pos, blockAccess);
    } 
    return false;
  }
  
  private boolean matchesChest(dqo tec, jd pos, dbz blockAccess) {
    dtc blockState = blockAccess.a_(pos);
    dtu chestType = blockState.b((duf)dhb.d) ? (dtu)blockState.c((duf)dhb.d) : dtu.a;
    boolean isLarge = (chestType != dtu.a);
    boolean isTrapped = tec instanceof dsb;
    boolean isChristmas = CustomGuis.isChristmas;
    boolean isEnder = false;
    return matchesChest(isLarge, isTrapped, isChristmas, isEnder);
  }
  
  private boolean matchesEnderChest(drd teec, jd pos, dbz blockAccess) {
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
  
  private boolean matchesDispenser(jd pos, dbz blockAccess) {
    dqh te = blockAccess.c_(pos);
    if (!(te instanceof dra))
      return false; 
    dra ted = (dra)te;
    if (this.variants != null) {
      EnumVariant var = getDispenserVariant(ted);
      if (!Config.equalsOne(var, (Object[])this.variants))
        return false; 
    } 
    return true;
  }
  
  private EnumVariant getDispenserVariant(dra ted) {
    if (ted instanceof drb)
      return EnumVariant.DROPPER; 
    return EnumVariant.DISPENSER;
  }
  
  private boolean matchesShulker(jd pos, dbz blockAccess) {
    dqh te = blockAccess.c_(pos);
    if (!(te instanceof drr))
      return false; 
    drr tesb = (drr)te;
    if (this.colors != null) {
      cti col = tesb.u();
      if (!Config.equalsOne(col, (Object[])this.colors))
        return false; 
    } 
    return true;
  }
  
  public boolean matchesEntity(EnumContainer ec, bsr entity, dcz blockAccess) {
    if (!matchesGeneral(ec, entity.do(), blockAccess))
      return false; 
    if (this.nbtName != null) {
      String entityName = entity.cB();
      if (!this.nbtName.matchesValue(entityName))
        return false; 
    } 
    switch (ec.ordinal()) {
      case 10:
        return matchesVillager(entity, (dbz)blockAccess);
      case 9:
        return matchesHorse(entity, (dbz)blockAccess);
    } 
    return true;
  }
  
  private boolean matchesVillager(bsr entity, dbz blockAccess) {
    if (!(entity instanceof cmk))
      return false; 
    cmk entityVillager = (cmk)entity;
    if (this.professions != null) {
      cml vd = entityVillager.gv();
      cmn vp = vd.b();
      int level = vd.c();
      if (!MatchProfession.matchesOne(vp, level, this.professions))
        return false; 
    } 
    return true;
  }
  
  private boolean matchesHorse(bsr entity, dbz blockAccess) {
    if (!(entity instanceof chi))
      return false; 
    chi ah = (chi)entity;
    if (this.variants != null) {
      EnumVariant var = getHorseVariant(ah);
      if (!Config.equalsOne(var, (Object[])this.variants))
        return false; 
    } 
    if (this.colors != null)
      if (ah instanceof chl) {
        chl el = (chl)ah;
        cti col = el.gw();
        if (!Config.equalsOne(col, (Object[])this.colors))
          return false; 
      }  
    return true;
  }
  
  private EnumVariant getHorseVariant(chi entity) {
    if (entity instanceof chk)
      return EnumVariant.HORSE; 
    if (entity instanceof chj)
      return EnumVariant.DONKEY; 
    if (entity instanceof chn)
      return EnumVariant.MULE; 
    if (entity instanceof chl)
      return EnumVariant.LLAMA; 
    return null;
  }
  
  public EnumContainer getContainer() {
    return this.container;
  }
  
  public akr getTextureLocation(akr loc) {
    akr locNew = this.textureLocations.get(loc);
    if (locNew == null)
      return loc; 
    return locNew;
  }
  
  public String toString() {
    return "name: " + this.fileName + ", container: " + String.valueOf(this.container) + ", textures: " + String.valueOf(this.textureLocations);
  }
}
