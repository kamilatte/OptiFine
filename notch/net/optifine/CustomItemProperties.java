package notch.net.optifine;

import akr;
import bsy;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.datafixers.util.Either;
import csf;
import csh;
import cul;
import cut;
import fgo;
import gfw;
import gfx;
import gfy;
import ggb;
import ggd;
import gge;
import ggg;
import gpw;
import gqb;
import gqk;
import gql;
import gqm;
import gsm;
import gsn;
import gsq;
import gss;
import gst;
import gsu;
import gsv;
import gsx;
import gsy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import ji;
import lt;
import net.optifine.Config;
import net.optifine.config.IParserInt;
import net.optifine.config.NbtTagValue;
import net.optifine.config.ParserEnchantmentId;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class CustomItemProperties {
  public String name = null;
  
  public String basePath = null;
  
  public int type = 1;
  
  public int[] items = null;
  
  public String texture = null;
  
  public Map<String, String> mapTextures = null;
  
  public String model = null;
  
  public Map<String, String> mapModels = null;
  
  public RangeListInt damage = null;
  
  public boolean damagePercent = false;
  
  public int damageMask = 0;
  
  public RangeListInt stackSize = null;
  
  public int[] enchantmentIds = null;
  
  public RangeListInt enchantmentLevels = null;
  
  public NbtTagValue[] nbtTagValues = null;
  
  public int hand = 0;
  
  public int blend = 1;
  
  public float speed = 0.0F;
  
  public float rotation = 0.0F;
  
  public int layer = 0;
  
  public float duration = 1.0F;
  
  public int weight = 0;
  
  public akr textureLocation = null;
  
  public Map mapTextureLocations = null;
  
  public gql sprite = null;
  
  public Map mapSprites = null;
  
  public gsm bakedModelTexture = null;
  
  public Map<String, gsm> mapBakedModelsTexture = null;
  
  public gsm bakedModelFull = null;
  
  public Map<String, gsm> mapBakedModelsFull = null;
  
  public Set<akr> modelSpriteLocations = null;
  
  private int textureWidth = 0;
  
  private int textureHeight = 0;
  
  public static final int TYPE_UNKNOWN = 0;
  
  public static final int TYPE_ITEM = 1;
  
  public static final int TYPE_ENCHANTMENT = 2;
  
  public static final int TYPE_ARMOR = 3;
  
  public static final int TYPE_ELYTRA = 4;
  
  public static final int HAND_ANY = 0;
  
  public static final int HAND_MAIN = 1;
  
  public static final int HAND_OFF = 2;
  
  public static final String INVENTORY = "inventory";
  
  public CustomItemProperties(Properties props, String path) {
    this.name = parseName(path);
    this.basePath = parseBasePath(path);
    this.type = parseType(props.getProperty("type"));
    this.items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
    this.mapModels = parseModels(props, this.basePath);
    this.model = parseModel(props.getProperty("model"), path, this.basePath, this.type, this.mapModels);
    this.mapTextures = parseTextures(props, this.basePath);
    boolean textureFromPath = (this.mapModels == null && this.model == null);
    this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath, this.type, this.mapTextures, textureFromPath);
    String damageStr = props.getProperty("damage");
    if (damageStr != null) {
      this.damagePercent = damageStr.contains("%");
      damageStr = damageStr.replace("%", "");
      this.damage = parseRangeListInt(damageStr);
      this.damageMask = parseInt(props.getProperty("damageMask"), 0);
    } 
    this.stackSize = parseRangeListInt(props.getProperty("stackSize"));
    this.enchantmentIds = parseInts(getProperty(props, new String[] { "enchantmentIDs", "enchantments" }), (IParserInt)new ParserEnchantmentId());
    this.enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
    this.nbtTagValues = parseNbtTagValues(props);
    this.hand = parseHand(props.getProperty("hand"));
    this.blend = Blender.parseBlend(props.getProperty("blend"));
    this.speed = parseFloat(props.getProperty("speed"), 0.0F);
    this.rotation = parseFloat(props.getProperty("rotation"), 0.0F);
    this.layer = parseInt(props.getProperty("layer"), 0);
    this.weight = parseInt(props.getProperty("weight"), 0);
    this.duration = parseFloat(props.getProperty("duration"), 1.0F);
  }
  
  private static String getProperty(Properties props, String... names) {
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      String val = props.getProperty(name);
      if (val != null)
        return val; 
    } 
    return null;
  }
  
  private static String parseName(String path) {
    String str = path;
    int pos = str.lastIndexOf('/');
    if (pos >= 0)
      str = str.substring(pos + 1); 
    int pos2 = str.lastIndexOf('.');
    if (pos2 >= 0)
      str = str.substring(0, pos2); 
    return str;
  }
  
  private static String parseBasePath(String path) {
    int pos = path.lastIndexOf('/');
    if (pos < 0)
      return ""; 
    return path.substring(0, pos);
  }
  
  private int parseType(String str) {
    if (str == null)
      return 1; 
    if (str.equals("item"))
      return 1; 
    if (str.equals("enchantment"))
      return 2; 
    if (str.equals("armor"))
      return 3; 
    if (str.equals("elytra"))
      return 4; 
    Config.warn("Unknown method: " + str);
    return 0;
  }
  
  private int[] parseItems(String str, String str2) {
    if (str == null)
      str = str2; 
    if (str == null)
      return null; 
    str = str.trim();
    Set<Integer> setItemIds = new TreeSet();
    String[] tokens = Config.tokenize(str, " ");
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      cul item = getItemByName(token);
      if (item == null) {
        Config.warn("Item not found: " + token);
      } else {
        int id = cul.a(item);
        if (id < 0) {
          Config.warn("Item ID not found: " + token);
        } else {
          setItemIds.add(new Integer(id));
        } 
      } 
    } 
    Integer[] integers = setItemIds.<Integer>toArray(new Integer[setItemIds.size()]);
    int[] ints = new int[integers.length];
    for (int j = 0; j < ints.length; j++)
      ints[j] = integers[j].intValue(); 
    return ints;
  }
  
  private cul getItemByName(String name) {
    akr loc = new akr(name);
    if (!lt.g.d(loc))
      return null; 
    cul item = (cul)lt.g.a(loc);
    return item;
  }
  
  private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs, boolean textureFromPath) {
    if (texStr == null)
      texStr = texStr2; 
    if (texStr == null)
      texStr = texStr3; 
    if (texStr != null) {
      String png = ".png";
      if (texStr.endsWith(png))
        texStr = texStr.substring(0, texStr.length() - png.length()); 
      texStr = fixTextureName(texStr, basePath);
      return texStr;
    } 
    if (type == 3)
      return null; 
    if (mapTexs != null) {
      String bowStandbyTex = mapTexs.get("texture.bow_standby");
      if (bowStandbyTex != null)
        return bowStandbyTex; 
    } 
    if (!textureFromPath)
      return null; 
    String str = path;
    int pos = str.lastIndexOf('/');
    if (pos >= 0)
      str = str.substring(pos + 1); 
    int pos2 = str.lastIndexOf('.');
    if (pos2 >= 0)
      str = str.substring(0, pos2); 
    str = fixTextureName(str, basePath);
    return str;
  }
  
  private static Map parseTextures(Properties props, String basePath) {
    String prefix = "texture.";
    Map<String, String> mapProps = getMatchingProperties(props, prefix);
    if (mapProps.size() <= 0)
      return null; 
    Set<String> keySet = mapProps.keySet();
    Map<Object, Object> mapTex = new LinkedHashMap<>();
    for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
      String key = it.next();
      String val = mapProps.get(key);
      val = fixTextureName(val, basePath);
      mapTex.put(key, val);
    } 
    return mapTex;
  }
  
  private static String fixTextureName(String iconName, String basePath) {
    iconName = TextureUtils.fixResourcePath(iconName, basePath);
    if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("optifine/"))
      iconName = basePath + "/" + basePath; 
    if (iconName.endsWith(".png"))
      iconName = iconName.substring(0, iconName.length() - 4); 
    if (iconName.startsWith("/"))
      iconName = iconName.substring(1); 
    return iconName;
  }
  
  private static String parseModel(String modelStr, String path, String basePath, int type, Map<String, String> mapModelNames) {
    if (modelStr != null) {
      String json = ".json";
      if (modelStr.endsWith(json))
        modelStr = modelStr.substring(0, modelStr.length() - json.length()); 
      modelStr = fixModelName(modelStr, basePath);
      return modelStr;
    } 
    if (type == 3)
      return null; 
    if (mapModelNames != null) {
      String bowStandbyModel = mapModelNames.get("model.bow_standby");
      if (bowStandbyModel != null)
        return bowStandbyModel; 
    } 
    return modelStr;
  }
  
  private static Map parseModels(Properties props, String basePath) {
    String prefix = "model.";
    Map<String, String> mapProps = getMatchingProperties(props, prefix);
    if (mapProps.size() <= 0)
      return null; 
    Set<String> keySet = mapProps.keySet();
    Map<Object, Object> mapTex = new LinkedHashMap<>();
    for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
      String key = it.next();
      String val = mapProps.get(key);
      val = fixModelName(val, basePath);
      mapTex.put(key, val);
    } 
    return mapTex;
  }
  
  private static String fixModelName(String modelName, String basePath) {
    modelName = TextureUtils.fixResourcePath(modelName, basePath);
    boolean isVanilla = (modelName.startsWith("block/") || modelName.startsWith("item/"));
    if (!modelName.startsWith(basePath) && !isVanilla && !modelName.startsWith("optifine/"))
      modelName = basePath + "/" + basePath; 
    String json = ".json";
    if (modelName.endsWith(json))
      modelName = modelName.substring(0, modelName.length() - json.length()); 
    if (modelName.startsWith("/"))
      modelName = modelName.substring(1); 
    return modelName;
  }
  
  private int parseInt(String str, int defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    int val = Config.parseInt(str, -2147483648);
    if (val == Integer.MIN_VALUE) {
      Config.warn("Invalid integer: " + str);
      return defVal;
    } 
    return val;
  }
  
  private float parseFloat(String str, float defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    float val = Config.parseFloat(str, Float.MIN_VALUE);
    if (val == Float.MIN_VALUE) {
      Config.warn("Invalid float: " + str);
      return defVal;
    } 
    return val;
  }
  
  private int[] parseInts(String str, IParserInt parser) {
    if (str == null)
      return null; 
    String[] tokens = Config.tokenize(str, " ");
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      int val = parser.parse(token, -2147483648);
      if (val == Integer.MIN_VALUE) {
        Config.warn("Invalid value: " + token);
      } else {
        list.add(Integer.valueOf(val));
      } 
    } 
    Integer[] intArr = list.<Integer>toArray(new Integer[list.size()]);
    int[] ints = Config.toPrimitive(intArr);
    return ints;
  }
  
  private RangeListInt parseRangeListInt(String str) {
    if (str == null)
      return null; 
    String[] tokens = Config.tokenize(str, " ");
    RangeListInt rangeList = new RangeListInt();
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      RangeInt range = parseRangeInt(token);
      if (range == null) {
        Config.warn("Invalid range list: " + str);
        return null;
      } 
      rangeList.addRange(range);
    } 
    return rangeList;
  }
  
  private RangeInt parseRangeInt(String str) {
    if (str == null)
      return null; 
    str = str.trim();
    int countMinus = str.length() - str.replace("-", "").length();
    if (countMinus > 1) {
      Config.warn("Invalid range: " + str);
      return null;
    } 
    String[] tokens = Config.tokenize(str, "- ");
    int[] vals = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      int val = Config.parseInt(token, -1);
      if (val < 0) {
        Config.warn("Invalid range: " + str);
        return null;
      } 
      vals[i] = val;
    } 
    if (vals.length == 1) {
      int val = vals[0];
      if (str.startsWith("-"))
        return new RangeInt(0, val); 
      if (str.endsWith("-"))
        return new RangeInt(val, 65535); 
      return new RangeInt(val, val);
    } 
    if (vals.length == 2) {
      int min = Math.min(vals[0], vals[1]);
      int max = Math.max(vals[0], vals[1]);
      return new RangeInt(min, max);
    } 
    Config.warn("Invalid range: " + str);
    return null;
  }
  
  private NbtTagValue[] parseNbtTagValues(Properties props) {
    String PREFIX_NBT = "nbt.";
    String PREFIX_COMPONENTS = "components.";
    Map<String, String> mapComponents = getMatchingProperties(props, PREFIX_COMPONENTS);
    Map<String, String> mapNbt = getMatchingProperties(props, PREFIX_NBT);
    for (Map.Entry<String, String> entry : mapNbt.entrySet()) {
      String key = entry.getKey();
      String val = entry.getValue();
      String id = key.substring(PREFIX_NBT.length());
      if (id.equals("display.Name")) {
        mapComponents.putIfAbsent(PREFIX_COMPONENTS + "minecraft:custom_name", val);
      } else if (id.equals("display.Lore")) {
        mapComponents.putIfAbsent(PREFIX_COMPONENTS + "minecraft:lore", val);
      } 
      Config.warn("Deprecated NBT check: " + key + "=" + val);
    } 
    if (mapComponents.size() <= 0)
      return null; 
    List<NbtTagValue> listNbts = new ArrayList<>();
    for (Map.Entry<String, String> entry : mapComponents.entrySet()) {
      String key = entry.getKey();
      String val = entry.getValue();
      String id = key.substring(PREFIX_COMPONENTS.length());
      id = fixNamespaces(id);
      NbtTagValue nbt = new NbtTagValue(id, val);
      listNbts.add(nbt);
    } 
    NbtTagValue[] nbts = listNbts.<NbtTagValue>toArray(new NbtTagValue[listNbts.size()]);
    return nbts;
  }
  
  private String fixNamespaces(String id) {
    int posPoint = id.indexOf('.');
    int posNameEnd = (posPoint >= 0) ? posPoint : id.length();
    if (id.indexOf(58, 0, posNameEnd) < 0)
      id = "minecraft:" + id; 
    id = id.replace("~", "minecraft:");
    return id;
  }
  
  private static Map<String, String> getMatchingProperties(Properties props, String keyPrefix) {
    Map<Object, Object> map = new LinkedHashMap<>();
    Set<Object> keySet = props.keySet();
    for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
      String key = it.next();
      String val = props.getProperty(key);
      if (key.startsWith(keyPrefix))
        map.put(key, val); 
    } 
    return (Map)map;
  }
  
  private int parseHand(String str) {
    if (str == null)
      return 0; 
    str = str.toLowerCase();
    if (str.equals("any"))
      return 0; 
    if (str.equals("main"))
      return 1; 
    if (str.equals("off"))
      return 2; 
    Config.warn("Invalid hand: " + str);
    return 0;
  }
  
  public boolean isValid(String path) {
    if (this.name == null || this.name.length() <= 0) {
      Config.warn("No name found: " + path);
      return false;
    } 
    if (this.basePath == null) {
      Config.warn("No base path found: " + path);
      return false;
    } 
    if (this.type == 0) {
      Config.warn("No type defined: " + path);
      return false;
    } 
    if (this.type == 4 && this.items == null)
      this.items = new int[] { cul.a(cut.nT) }; 
    if (this.type == 1 || this.type == 3 || this.type == 4) {
      if (this.items == null)
        this.items = detectItems(); 
      if (this.items == null) {
        Config.warn("No items defined: " + path);
        return false;
      } 
    } 
    if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
      Config.warn("No texture or model specified: " + path);
      return false;
    } 
    if (this.type == 2)
      if (this.enchantmentIds == null) {
        Config.warn("No enchantmentIDs specified: " + path);
        return false;
      }  
    return true;
  }
  
  private int[] detectItems() {
    cul item = getItemByName(this.name);
    if (item == null)
      return null; 
    int id = cul.a(item);
    if (id < 0)
      return null; 
    return new int[] { id };
  }
  
  public void updateIcons(gqk textureMap) {
    if (this.texture != null) {
      this.textureLocation = getTextureLocation(this.texture);
      if (this.type == 1) {
        akr spriteLocation = getSpriteLocation(this.textureLocation);
        this.sprite = textureMap.registerSprite(spriteLocation);
      } 
    } 
    if (this.mapTextures != null) {
      this.mapTextureLocations = new HashMap<>();
      this.mapSprites = new HashMap<>();
      Set<String> keySet = this.mapTextures.keySet();
      for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        String val = this.mapTextures.get(key);
        akr locTex = getTextureLocation(val);
        this.mapTextureLocations.put(key, locTex);
        if (this.type == 1) {
          akr locSprite = getSpriteLocation(locTex);
          gql icon = textureMap.registerSprite(locSprite);
          this.mapSprites.put(key, icon);
        } 
      } 
    } 
    for (akr loc : this.modelSpriteLocations)
      textureMap.registerSprite(loc); 
  }
  
  public void refreshIcons(gqk textureMap) {
    if (this.sprite != null)
      this.sprite = textureMap.a(this.sprite.getName()); 
    if (this.mapSprites != null) {
      Set keySet = this.mapSprites.keySet();
      for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        gql sprite = (gql)this.mapSprites.get(key);
        if (sprite == null)
          continue; 
        akr loc = sprite.getName();
        gql spriteNew = textureMap.a(loc);
        if (spriteNew == null || gqb.isMisingSprite(spriteNew))
          Config.warn("Missing CIT sprite: " + String.valueOf(loc) + ", properties: " + this.basePath); 
        this.mapSprites.put(key, spriteNew);
      } 
    } 
  }
  
  private akr getTextureLocation(String texName) {
    if (texName == null)
      return null; 
    akr resLoc = new akr(texName);
    String domain = resLoc.b();
    String path = resLoc.a();
    if (!path.contains("/"))
      path = "textures/item/" + path; 
    String filePath = path + ".png";
    akr locFile = new akr(domain, filePath);
    boolean exists = Config.hasResource(locFile);
    if (!exists)
      Config.warn("File not found: " + filePath); 
    return locFile;
  }
  
  private akr getSpriteLocation(akr resLoc) {
    String pathTex = resLoc.a();
    pathTex = StrUtils.removePrefix(pathTex, "textures/");
    pathTex = StrUtils.removeSuffix(pathTex, ".png");
    akr locTex = new akr(resLoc.b(), pathTex);
    return locTex;
  }
  
  public void updateModelTexture(gqk textureMap, gge itemModelGenerator) {
    if (this.texture == null && this.mapTextures == null)
      return; 
    String[] textures = getModelTextures();
    boolean useTint = isUseTint();
    this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, textures, useTint);
    if (this.type == 1 && this.mapTextures != null) {
      Set<String> keySet = this.mapTextures.keySet();
      for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        String tex = this.mapTextures.get(key);
        String path = StrUtils.removePrefix(key, "texture.");
        if (isSubTexture(path)) {
          String[] texNames = { tex };
          gsm modelTex = makeBakedModel(textureMap, itemModelGenerator, texNames, useTint);
          if (this.mapBakedModelsTexture == null)
            this.mapBakedModelsTexture = new HashMap<>(); 
          String location = "item/" + path;
          this.mapBakedModelsTexture.put(location, modelTex);
        } 
      } 
    } 
  }
  
  private boolean isSubTexture(String path) {
    return (path.startsWith("bow") || path.startsWith("crossbow") || path.startsWith("fishing_rod") || path.startsWith("shield"));
  }
  
  private boolean isUseTint() {
    return true;
  }
  
  private static gsm makeBakedModel(gqk textureMap, gge itemModelGenerator, String[] textures, boolean useTint) {
    String[] spriteNames = new String[textures.length];
    for (int i = 0; i < spriteNames.length; i++) {
      String texture = textures[i];
      spriteNames[i] = StrUtils.removePrefix(texture, "textures/");
    } 
    ggb modelBlockBase = makeModelBlock(spriteNames);
    ggb modelBlock = itemModelGenerator.a(net.optifine.CustomItemProperties::getSprite, modelBlockBase);
    gsm model = bakeModel(textureMap, modelBlock, useTint);
    return model;
  }
  
  public static gql getSprite(gsq material) {
    gqk atlasTexture = fgo.Q().aC().a(material.a());
    return atlasTexture.a(material.b());
  }
  
  private String[] getModelTextures() {
    if (this.type == 1 && this.items.length == 1) {
      cul item = cul.b(this.items[0]);
      boolean isPotionItem = (item == cut.sk || item == cut.vo || item == cut.vr);
      if (isPotionItem && this.damage != null && this.damage.getCountRanges() > 0) {
        RangeInt range = this.damage.getRange(0);
        int valDamage = range.getMin();
        boolean splash = ((valDamage & 0x4000) != 0);
        String texOverlay = getMapTexture(this.mapTextures, "texture.potion_overlay", "item/potion_overlay");
        String texMain = null;
        if (splash) {
          texMain = getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "item/potion_bottle_splash");
        } else {
          texMain = getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "item/potion_bottle_drinkable");
        } 
        return new String[] { texOverlay, texMain };
      } 
      if (item instanceof csf) {
        csf itemArmor = (csf)item;
        if (itemArmor.h() == csh.a) {
          String material = "leather";
          String type = "helmet";
          bsy equipmentSlot = itemArmor.m();
          if (equipmentSlot == bsy.f)
            type = "helmet"; 
          if (equipmentSlot == bsy.e)
            type = "chestplate"; 
          if (equipmentSlot == bsy.d)
            type = "leggings"; 
          if (equipmentSlot == bsy.c)
            type = "boots"; 
          String key = material + "_" + material;
          String texMain = getMapTexture(this.mapTextures, "texture." + key, "item/" + key);
          String texOverlay = getMapTexture(this.mapTextures, "texture." + key + "_overlay", "item/" + key + "_overlay");
          return new String[] { texMain, texOverlay };
        } 
      } 
    } 
    return new String[] { this.texture };
  }
  
  private String getMapTexture(Map<String, String> map, String key, String def) {
    if (map == null)
      return def; 
    String str = map.get(key);
    if (str == null)
      return def; 
    return str;
  }
  
  private static ggb makeModelBlock(String[] modelTextures) {
    StringBuffer sb = new StringBuffer();
    sb.append("{\"parent\": \"builtin/generated\",\"textures\": {");
    for (int i = 0; i < modelTextures.length; i++) {
      String modelTexture = modelTextures[i];
      if (i > 0)
        sb.append(", "); 
      sb.append("\"layer" + i + "\": \"" + modelTexture + "\"");
    } 
    sb.append("}}");
    String modelStr = sb.toString();
    ggb model = ggb.a(modelStr);
    return model;
  }
  
  private static gsm bakeModel(gqk textureMap, ggb modelBlockIn, boolean useTint) {
    gsn modelRotationIn = gsn.a;
    gsq materialParticle = modelBlockIn.c("particle");
    gql var4 = materialParticle.c();
    gsx.a var5 = (new gsx.a(modelBlockIn, ggg.a, false)).a(var4);
    Iterator<gfx> blockParts = modelBlockIn.a().iterator();
    while (blockParts.hasNext()) {
      gfx blockPart = blockParts.next();
      Iterator<ji> directions = blockPart.c.keySet().iterator();
      while (directions.hasNext()) {
        ji direction = directions.next();
        gfy blockPartFace = (gfy)blockPart.c.get(direction);
        if (!useTint)
          blockPartFace = new gfy(blockPartFace.a(), -1, blockPartFace.c(), blockPartFace.d()); 
        gsq material = modelBlockIn.c(blockPartFace.c());
        gql sprite = material.c();
        gfw quad = makeBakedQuad(blockPart, blockPartFace, sprite, direction, modelRotationIn);
        if (blockPartFace.a() == null) {
          var5.a(quad);
          continue;
        } 
        var5.a(ji.a(modelRotationIn.b().c(), blockPartFace.a()), quad);
      } 
    } 
    return var5.b();
  }
  
  private static gfw makeBakedQuad(gfx blockPart, gfy blockPartFace, gql textureAtlasSprite, ji enumFacing, gsn modelRotation) {
    ggd faceBakery = new ggd();
    return faceBakery.a(blockPart.a, blockPart.b, blockPartFace, textureAtlasSprite, enumFacing, (gsv)modelRotation, blockPart.d, blockPart.e);
  }
  
  public String toString() {
    return this.basePath + "/" + this.basePath + ", type: " + this.name + ", items: [" + this.type + "], texture: " + Config.arrayToString(this.items);
  }
  
  public float getTextureWidth(gqm textureManager) {
    if (this.textureWidth <= 0) {
      if (this.textureLocation != null) {
        gpw tex = textureManager.b(this.textureLocation);
        int texId = tex.a();
        int prevTexId = GlStateManager.getBoundTexture();
        GlStateManager._bindTexture(texId);
        this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
        GlStateManager._bindTexture(prevTexId);
      } 
      if (this.textureWidth <= 0)
        this.textureWidth = 16; 
    } 
    return this.textureWidth;
  }
  
  public float getTextureHeight(gqm textureManager) {
    if (this.textureHeight <= 0) {
      if (this.textureLocation != null) {
        gpw tex = textureManager.b(this.textureLocation);
        int texId = tex.a();
        int prevTexId = GlStateManager.getBoundTexture();
        GlStateManager._bindTexture(texId);
        this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
        GlStateManager._bindTexture(prevTexId);
      } 
      if (this.textureHeight <= 0)
        this.textureHeight = 16; 
    } 
    return this.textureHeight;
  }
  
  public gsm getBakedModel(akr modelLocation, boolean fullModel) {
    gsm bakedModel;
    Map<String, gsm> mapBakedModels;
    if (fullModel) {
      bakedModel = this.bakedModelFull;
      mapBakedModels = this.mapBakedModelsFull;
    } else {
      bakedModel = this.bakedModelTexture;
      mapBakedModels = this.mapBakedModelsTexture;
    } 
    if (modelLocation != null && mapBakedModels != null) {
      String modelPath = modelLocation.a();
      gsm customModel = mapBakedModels.get(modelPath);
      if (customModel != null)
        return customModel; 
    } 
    return bakedModel;
  }
  
  public void loadModels(gss modelBakery) {
    this.modelSpriteLocations = new LinkedHashSet<>();
    if (this.model != null)
      loadItemModel(modelBakery, this.model); 
    if (this.type == 1 && this.mapModels != null) {
      Set<String> keySet = this.mapModels.keySet();
      for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        String mod = this.mapModels.get(key);
        String path = StrUtils.removePrefix(key, "model.");
        if (isSubTexture(path))
          loadItemModel(modelBakery, mod); 
      } 
    } 
  }
  
  public void updateModelsFull() {
    gst modelManager = Config.getModelManager();
    gsm missingModel = modelManager.a();
    if (this.model != null) {
      akr locItem = getModelLocation(this.model);
      gsu mrl = new gsu(locItem, "inventory");
      this.bakedModelFull = modelManager.a(mrl);
      if (this.bakedModelFull == missingModel) {
        Config.warn("Custom Items: Model not found " + mrl.toString());
        this.bakedModelFull = null;
      } 
    } 
    if (this.type == 1 && this.mapModels != null) {
      Set<String> keySet = this.mapModels.keySet();
      for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        String key = it.next();
        String mod = this.mapModels.get(key);
        String path = StrUtils.removePrefix(key, "model.");
        if (isSubTexture(path)) {
          akr locItem = getModelLocation(mod);
          gsu mrl = new gsu(locItem, "inventory");
          gsm bm = modelManager.a(mrl);
          if (bm == missingModel) {
            Config.warn("Custom Items: Model not found " + mrl.toString());
            continue;
          } 
          if (this.mapBakedModelsFull == null)
            this.mapBakedModelsFull = new HashMap<>(); 
          String location = "item/" + path;
          this.mapBakedModelsFull.put(location, bm);
        } 
      } 
    } 
  }
  
  private void loadItemModel(gss modelBakery, String model) {
    akr locModel = getModelLocation(model);
    modelBakery.b(locModel);
    gsy um = modelBakery.a(locModel);
    if (um instanceof ggb) {
      ggb modelBlock = (ggb)um;
      if (ggd.getTextures(modelBlock) != null)
        for (Iterator<Map.Entry<String, Either<gsq, String>>> it = ggd.getTextures(modelBlock).entrySet().iterator(); it.hasNext(); ) {
          Map.Entry<String, Either<gsq, String>> entry = it.next();
          Either<gsq, String> value = entry.getValue();
          Optional<gsq> optionalMaterial = value.left();
          if (!optionalMaterial.isPresent())
            continue; 
          gsq material = optionalMaterial.get();
          akr textureLocation = material.b();
          this.modelSpriteLocations.add(textureLocation);
        }  
    } 
  }
  
  private static akr getModelLocation(String modelName) {
    return new akr(modelName);
  }
}
