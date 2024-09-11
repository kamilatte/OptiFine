package notch.net.optifine.config;

import aa;
import akr;
import azk;
import bsx;
import cmn;
import cti;
import cul;
import dfy;
import dtc;
import duf;
import gfh;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import jh;
import ji;
import lt;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.config.BiomeId;
import net.optifine.config.INameGetter;
import net.optifine.config.MatchBlock;
import net.optifine.config.MatchProfession;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.Weather;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.EntityTypeUtils;
import net.optifine.util.ItemUtils;

public class ConnectedParser {
  private String context = null;
  
  public static final MatchProfession[] PROFESSIONS_INVALID = new MatchProfession[0];
  
  public static final cti[] DYE_COLORS_INVALID = new cti[0];
  
  private static Map<akr, BiomeId> MAP_BIOMES_COMPACT = null;
  
  private static final INameGetter<Enum> NAME_GETTER_ENUM = (INameGetter<Enum>)new Object();
  
  private static final INameGetter<cti> NAME_GETTER_DYE_COLOR = (INameGetter<cti>)new Object();
  
  private static final Pattern PATTERN_RANGE_SEPARATOR = Pattern.compile("(\\d|\\))-(\\d|\\()");
  
  public ConnectedParser(String context) {
    this.context = context;
  }
  
  public String parseName(String path) {
    String str = path;
    int pos = str.lastIndexOf('/');
    if (pos >= 0)
      str = str.substring(pos + 1); 
    int pos2 = str.lastIndexOf('.');
    if (pos2 >= 0)
      str = str.substring(0, pos2); 
    return str;
  }
  
  public String parseBasePath(String path) {
    int pos = path.lastIndexOf('/');
    if (pos < 0)
      return ""; 
    return path.substring(0, pos);
  }
  
  public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
    if (propMatchBlocks == null)
      return null; 
    List list = new ArrayList();
    String[] blockStrs = Config.tokenize(propMatchBlocks, " ");
    for (int i = 0; i < blockStrs.length; i++) {
      String blockStr = blockStrs[i];
      MatchBlock[] arrayOfMatchBlock = parseMatchBlock(blockStr);
      if (arrayOfMatchBlock != null)
        list.addAll(Arrays.asList(arrayOfMatchBlock)); 
    } 
    MatchBlock[] mbs = (MatchBlock[])list.toArray((Object[])new MatchBlock[list.size()]);
    return mbs;
  }
  
  public dtc parseBlockState(String str, dtc def) {
    MatchBlock[] mbs = parseMatchBlock(str);
    if (mbs == null)
      return def; 
    if (mbs.length != 1)
      return def; 
    MatchBlock mb = mbs[0];
    int blockId = mb.getBlockId();
    dfy block = (dfy)lt.e.a(blockId);
    return block.o();
  }
  
  public MatchBlock[] parseMatchBlock(String blockStr) {
    if (blockStr == null)
      return null; 
    blockStr = blockStr.trim();
    if (blockStr.length() <= 0)
      return null; 
    String[] parts = Config.tokenize(blockStr, ":");
    String domain = "minecraft";
    int blockIndex = 0;
    if (parts.length > 1 && isFullBlockName(parts)) {
      domain = parts[0];
      blockIndex = 1;
    } else {
      domain = "minecraft";
      blockIndex = 0;
    } 
    String blockPart = parts[blockIndex];
    String[] params = Arrays.<String>copyOfRange(parts, blockIndex + 1, parts.length);
    dfy[] blocks = parseBlockPart(domain, blockPart);
    if (blocks == null)
      return null; 
    MatchBlock[] datas = new MatchBlock[blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      dfy block = blocks[i];
      int blockId = lt.e.a(block);
      int[] metadatas = null;
      if (params.length > 0) {
        metadatas = parseBlockMetadatas(block, params);
        if (metadatas == null)
          return null; 
      } 
      MatchBlock bd = new MatchBlock(blockId, metadatas);
      datas[i] = bd;
    } 
    return datas;
  }
  
  public boolean isFullBlockName(String[] parts) {
    if (parts.length <= 1)
      return false; 
    String part1 = parts[1];
    if (part1.length() < 1)
      return false; 
    if (part1.contains("="))
      return false; 
    return true;
  }
  
  public boolean startsWithDigit(String str) {
    if (str == null)
      return false; 
    if (str.length() < 1)
      return false; 
    char ch = str.charAt(0);
    return Character.isDigit(ch);
  }
  
  public dfy[] parseBlockPart(String domain, String blockPart) {
    String fullName = domain + ":" + domain;
    akr fullLoc = makeResourceLocation(fullName);
    if (fullLoc == null)
      return null; 
    dfy block = BlockUtils.getBlock(fullLoc);
    if (block == null) {
      warn("Block not found for name: " + fullName);
      return null;
    } 
    dfy[] blocks = { block };
    return blocks;
  }
  
  private akr makeResourceLocation(String str) {
    try {
      akr loc = new akr(str);
      return loc;
    } catch (aa e) {
      warn("Invalid resource location: " + e.getMessage());
      return null;
    } 
  }
  
  private akr makeResourceLocation(String namespace, String path) {
    try {
      akr loc = new akr(namespace, path);
      return loc;
    } catch (aa e) {
      warn("Invalid resource location: " + e.getMessage());
      return null;
    } 
  }
  
  public int[] parseBlockMetadatas(dfy block, String[] params) {
    if (params.length <= 0)
      return null; 
    dtc stateDefault = block.o();
    Collection properties = stateDefault.B();
    Map<duf, List<Comparable>> mapPropValues = new HashMap<>();
    for (int i = 0; i < params.length; i++) {
      String param = params[i];
      if (param.length() > 0) {
        String[] parts = Config.tokenize(param, "=");
        if (parts.length != 2) {
          warn("Invalid block property: " + param);
          return null;
        } 
        String key = parts[0];
        String valStr = parts[1];
        duf prop = ConnectedProperties.getProperty(key, properties);
        if (prop == null) {
          warn("Property not found: " + key + ", block: " + String.valueOf(block));
          return null;
        } 
        List<Comparable> list = mapPropValues.get(key);
        if (list == null) {
          list = new ArrayList<>();
          mapPropValues.put(prop, list);
        } 
        String[] vals = Config.tokenize(valStr, ",");
        for (int v = 0; v < vals.length; v++) {
          String val = vals[v];
          Comparable propVal = parsePropertyValue(prop, val);
          if (propVal == null) {
            warn("Property value not found: " + val + ", property: " + key + ", block: " + String.valueOf(block));
            return null;
          } 
          list.add(propVal);
        } 
      } 
    } 
    if (mapPropValues.isEmpty())
      return null; 
    List<Integer> listMetadatas = new ArrayList<>();
    int metaCount = BlockUtils.getMetadataCount(block);
    for (int md = 0; md < metaCount; md++) {
      try {
        dtc bs = BlockUtils.getBlockState(block, md);
        if (matchState(bs, mapPropValues))
          listMetadatas.add(Integer.valueOf(md)); 
      } catch (IllegalArgumentException illegalArgumentException) {}
    } 
    if (listMetadatas.size() == metaCount)
      return null; 
    int[] metadatas = new int[listMetadatas.size()];
    for (int j = 0; j < metadatas.length; j++)
      metadatas[j] = ((Integer)listMetadatas.get(j)).intValue(); 
    return metadatas;
  }
  
  public static Comparable parsePropertyValue(duf prop, String valStr) {
    Class valueClass = prop.g();
    Comparable valueObj = parseValue(valStr, valueClass);
    if (valueObj == null) {
      Collection propertyValues = prop.a();
      valueObj = getPropertyValue(valStr, propertyValues);
    } 
    return valueObj;
  }
  
  public static Comparable getPropertyValue(String value, Collection propertyValues) {
    for (Iterator<Comparable> it = propertyValues.iterator(); it.hasNext(); ) {
      Comparable obj = it.next();
      if (getValueName(obj).equals(value))
        return obj; 
    } 
    return null;
  }
  
  private static Object getValueName(Comparable obj) {
    if (obj instanceof azk) {
      azk iss = (azk)obj;
      return iss.c();
    } 
    return obj.toString();
  }
  
  public static Comparable parseValue(String str, Class<String> cls) {
    if (cls == String.class)
      return str; 
    if (cls == Boolean.class)
      return Boolean.valueOf(str); 
    if (cls == Float.class)
      return Float.valueOf(str); 
    if (cls == Double.class)
      return Double.valueOf(str); 
    if (cls == Integer.class)
      return Integer.valueOf(str); 
    if (cls == Long.class)
      return Long.valueOf(str); 
    return null;
  }
  
  public boolean matchState(dtc bs, Map<duf, List<Comparable>> mapPropValues) {
    Set<duf> keys = mapPropValues.keySet();
    for (Iterator<duf> it = keys.iterator(); it.hasNext(); ) {
      duf prop = it.next();
      List<Comparable> vals = mapPropValues.get(prop);
      Comparable bsVal = bs.c(prop);
      if (bsVal == null)
        return false; 
      if (!vals.contains(bsVal))
        return false; 
    } 
    return true;
  }
  
  public BiomeId[] parseBiomes(String str) {
    if (str == null)
      return null; 
    str = str.trim();
    boolean negative = false;
    if (str.startsWith("!")) {
      negative = true;
      str = str.substring(1);
    } 
    String[] biomeNames = Config.tokenize(str, " ");
    List<BiomeId> list = new ArrayList<>();
    for (int i = 0; i < biomeNames.length; i++) {
      String biomeName = biomeNames[i];
      BiomeId biome = getBiomeId(biomeName);
      if (biome == null) {
        warn("Biome not found: " + biomeName);
      } else {
        list.add(biome);
      } 
    } 
    if (negative) {
      Set<akr> allBiomes = new HashSet<>(BiomeUtils.getLocations());
      for (BiomeId bi : list)
        allBiomes.remove(bi.getResourceLocation()); 
      list = BiomeUtils.getBiomeIds(allBiomes);
    } 
    BiomeId[] biomeArr = list.<BiomeId>toArray(new BiomeId[list.size()]);
    return biomeArr;
  }
  
  public BiomeId getBiomeId(String biomeName) {
    biomeName = biomeName.toLowerCase();
    akr biomeLoc = makeResourceLocation(biomeName);
    if (biomeLoc != null) {
      BiomeId biomeId = BiomeUtils.getBiomeId(biomeLoc);
      if (biomeId != null)
        return biomeId; 
    } 
    String biomeNameCompact = biomeName.replace(" ", "").replace("_", "");
    akr biomeLocCompact = makeResourceLocation(biomeNameCompact);
    if (MAP_BIOMES_COMPACT == null) {
      MAP_BIOMES_COMPACT = new HashMap<>();
      Set<akr> biomeIds = BiomeUtils.getLocations();
      for (Iterator<akr> it = biomeIds.iterator(); it.hasNext(); ) {
        akr loc = it.next();
        BiomeId biomeCompact = BiomeUtils.getBiomeId(loc);
        if (biomeCompact == null)
          continue; 
        String pathCompact = loc.a().replace(" ", "").replace("_", "").toLowerCase();
        akr locCompact = makeResourceLocation(loc.b(), pathCompact);
        if (locCompact == null)
          continue; 
        MAP_BIOMES_COMPACT.put(locCompact, biomeCompact);
      } 
    } 
    BiomeId biome = MAP_BIOMES_COMPACT.get(biomeLocCompact);
    if (biome != null)
      return biome; 
    return null;
  }
  
  public int parseInt(String str, int defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    int num = Config.parseInt(str, -1);
    if (num < 0) {
      warn("Invalid number: " + str);
      return defVal;
    } 
    return num;
  }
  
  public int parseIntNeg(String str, int defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    int num = Config.parseInt(str, -2147483648);
    if (num == Integer.MIN_VALUE) {
      warn("Invalid number: " + str);
      return defVal;
    } 
    return num;
  }
  
  public int[] parseIntList(String str) {
    if (str == null)
      return null; 
    List<Integer> list = new ArrayList<>();
    String[] intStrs = Config.tokenize(str, " ,");
    for (int i = 0; i < intStrs.length; i++) {
      String intStr = intStrs[i];
      if (intStr.contains("-")) {
        String[] subStrs = Config.tokenize(intStr, "-");
        if (subStrs.length != 2) {
          warn("Invalid interval: " + intStr + ", when parsing: " + str);
        } else {
          int min = Config.parseInt(subStrs[0], -1);
          int max = Config.parseInt(subStrs[1], -1);
          if (min < 0 || max < 0 || min > max) {
            warn("Invalid interval: " + intStr + ", when parsing: " + str);
          } else {
            for (int n = min; n <= max; n++)
              list.add(Integer.valueOf(n)); 
          } 
        } 
      } else {
        int val = Config.parseInt(intStr, -1);
        if (val < 0) {
          warn("Invalid number: " + intStr + ", when parsing: " + str);
        } else {
          list.add(Integer.valueOf(val));
        } 
      } 
    } 
    int[] ints = new int[list.size()];
    for (int j = 0; j < ints.length; j++)
      ints[j] = ((Integer)list.get(j)).intValue(); 
    return ints;
  }
  
  public boolean[] parseFaces(String str, boolean[] defVal) {
    if (str == null)
      return defVal; 
    EnumSet<ji> setFaces = EnumSet.allOf(ji.class);
    String[] faceStrs = Config.tokenize(str, " ,");
    for (int i = 0; i < faceStrs.length; i++) {
      String faceStr = faceStrs[i];
      if (faceStr.equals("sides")) {
        setFaces.add(ji.c);
        setFaces.add(ji.d);
        setFaces.add(ji.e);
        setFaces.add(ji.f);
      } else if (faceStr.equals("all")) {
        setFaces.addAll(Arrays.asList(ji.r));
      } else {
        ji face = parseFace(faceStr);
        if (face != null)
          setFaces.add(face); 
      } 
    } 
    boolean[] faces = new boolean[ji.r.length];
    for (int j = 0; j < faces.length; j++)
      faces[j] = setFaces.contains(ji.r[j]); 
    return faces;
  }
  
  public ji parseFace(String str) {
    str = str.toLowerCase();
    if (str.equals("bottom") || str.equals("down"))
      return ji.a; 
    if (str.equals("top") || str.equals("up"))
      return ji.b; 
    if (str.equals("north"))
      return ji.c; 
    if (str.equals("south"))
      return ji.d; 
    if (str.equals("east"))
      return ji.f; 
    if (str.equals("west"))
      return ji.e; 
    Config.warn("Unknown face: " + str);
    return null;
  }
  
  public void dbg(String str) {
    Config.dbg(this.context + ": " + this.context);
  }
  
  public void warn(String str) {
    Config.warn(this.context + ": " + this.context);
  }
  
  public RangeListInt parseRangeListInt(String str) {
    if (str == null)
      return null; 
    RangeListInt list = new RangeListInt();
    String[] parts = Config.tokenize(str, " ,");
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      RangeInt ri = parseRangeInt(part);
      if (ri == null)
        return null; 
      list.addRange(ri);
    } 
    return list;
  }
  
  public RangeListInt parseRangeListIntNeg(String str) {
    if (str == null)
      return null; 
    RangeListInt list = new RangeListInt();
    String[] parts = Config.tokenize(str, " ,");
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      RangeInt ri = parseRangeIntNeg(part);
      if (ri == null)
        return null; 
      list.addRange(ri);
    } 
    return list;
  }
  
  private RangeInt parseRangeInt(String str) {
    if (str == null)
      return null; 
    if (str.indexOf('-') >= 0) {
      String[] parts = Config.tokenize(str, "-");
      if (parts.length != 2) {
        warn("Invalid range: " + str);
        return null;
      } 
      int min = Config.parseInt(parts[0], -1);
      int max = Config.parseInt(parts[1], -1);
      if (min < 0 || max < 0) {
        warn("Invalid range: " + str);
        return null;
      } 
      return new RangeInt(min, max);
    } 
    int val = Config.parseInt(str, -1);
    if (val < 0) {
      warn("Invalid integer: " + str);
      return null;
    } 
    return new RangeInt(val, val);
  }
  
  private RangeInt parseRangeIntNeg(String str) {
    if (str == null)
      return null; 
    if (str.indexOf("=") >= 0) {
      warn("Invalid range: " + str);
      return null;
    } 
    String strEq = PATTERN_RANGE_SEPARATOR.matcher(str).replaceAll("$1=$2");
    if (strEq.indexOf('=') >= 0) {
      String[] parts = Config.tokenize(strEq, "=");
      if (parts.length != 2) {
        warn("Invalid range: " + str);
        return null;
      } 
      int min = Config.parseInt(stripBrackets(parts[0]), -2147483648);
      int max = Config.parseInt(stripBrackets(parts[1]), -2147483648);
      if (min == Integer.MIN_VALUE || max == Integer.MIN_VALUE) {
        warn("Invalid range: " + str);
        return null;
      } 
      return new RangeInt(min, max);
    } 
    int val = Config.parseInt(stripBrackets(str), -2147483648);
    if (val == Integer.MIN_VALUE) {
      warn("Invalid integer: " + str);
      return null;
    } 
    return new RangeInt(val, val);
  }
  
  private static String stripBrackets(String str) {
    if (str.startsWith("(") && str.endsWith(")"))
      return str.substring(1, str.length() - 1); 
    return str;
  }
  
  public boolean parseBoolean(String str, boolean defVal) {
    if (str == null)
      return defVal; 
    String strLower = str.toLowerCase().trim();
    if (strLower.equals("true"))
      return true; 
    if (strLower.equals("false"))
      return false; 
    warn("Invalid boolean: " + str);
    return defVal;
  }
  
  public Boolean parseBooleanObject(String str) {
    if (str == null)
      return null; 
    String strLower = str.toLowerCase().trim();
    if (strLower.equals("true"))
      return Boolean.TRUE; 
    if (strLower.equals("false"))
      return Boolean.FALSE; 
    warn("Invalid boolean: " + str);
    return null;
  }
  
  public static int parseColor(String str, int defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    try {
      int val = Integer.parseInt(str, 16) & 0xFFFFFF;
      return val;
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public static int parseColor4(String str, int defVal) {
    if (str == null)
      return defVal; 
    str = str.trim();
    try {
      int val = (int)(Long.parseLong(str, 16) & 0xFFFFFFFFFFFFFFFFL);
      return val;
    } catch (NumberFormatException e) {
      return defVal;
    } 
  }
  
  public gfh parseBlockRenderLayer(String str, gfh def) {
    if (str == null)
      return def; 
    str = str.toLowerCase().trim();
    gfh[] layers = gfh.CHUNK_RENDER_TYPES;
    for (int i = 0; i < layers.length; i++) {
      gfh layer = layers[i];
      if (str.equals(layer.getName().toLowerCase()))
        return layer; 
    } 
    return def;
  }
  
  public <T> T parseObject(String str, T[] objs, INameGetter nameGetter, String property) {
    if (str == null)
      return null; 
    String strLower = str.toLowerCase().trim();
    for (int i = 0; i < objs.length; i++) {
      T obj = objs[i];
      String name = nameGetter.getName(obj);
      if (name != null)
        if (name.toLowerCase().equals(strLower))
          return obj;  
    } 
    warn("Invalid " + property + ": " + str);
    return null;
  }
  
  public <T> T[] parseObjects(String str, T[] objs, INameGetter nameGetter, String property, T[] errValue) {
    if (str == null)
      return null; 
    str = str.toLowerCase().trim();
    String[] parts = Config.tokenize(str, " ");
    T[] arr = (T[])Array.newInstance(objs.getClass().getComponentType(), parts.length);
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      T obj = parseObject(part, objs, nameGetter, property);
      if (obj == null)
        return errValue; 
      arr[i] = obj;
    } 
    return arr;
  }
  
  public Enum parseEnum(String str, Enum[] enums, String property) {
    return parseObject(str, enums, NAME_GETTER_ENUM, property);
  }
  
  public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
    return parseObjects(str, enums, NAME_GETTER_ENUM, property, errValue);
  }
  
  public cti[] parseDyeColors(String str, String property, cti[] errValue) {
    return parseObjects(str, cti.values(), NAME_GETTER_DYE_COLOR, property, errValue);
  }
  
  public Weather[] parseWeather(String str, String property, Weather[] errValue) {
    return parseObjects(str, Weather.values(), NAME_GETTER_ENUM, property, errValue);
  }
  
  public NbtTagValue[] parseNbtTagValues(Properties props, String prefix) {
    List<NbtTagValue> listNbts = new ArrayList<>();
    Set<Object> keySet = props.keySet();
    for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
      String key = it.next();
      if (!key.startsWith(prefix))
        continue; 
      String val = (String)props.get(key);
      String id = key.substring(prefix.length());
      NbtTagValue nbt = new NbtTagValue(id, val);
      listNbts.add(nbt);
    } 
    if (listNbts.isEmpty())
      return null; 
    NbtTagValue[] nbts = listNbts.<NbtTagValue>toArray(new NbtTagValue[listNbts.size()]);
    return nbts;
  }
  
  public NbtTagValue parseNbtTagValue(String path, String value) {
    if (path == null || value == null)
      return null; 
    return new NbtTagValue(path, value);
  }
  
  public MatchProfession[] parseProfessions(String profStr) {
    if (profStr == null)
      return null; 
    List<MatchProfession> list = new ArrayList<>();
    String[] tokens = Config.tokenize(profStr, " ");
    for (int i = 0; i < tokens.length; i++) {
      String str = tokens[i];
      MatchProfession prof = parseProfession(str);
      if (prof == null) {
        warn("Invalid profession: " + str);
        return PROFESSIONS_INVALID;
      } 
      list.add(prof);
    } 
    if (list.isEmpty())
      return null; 
    MatchProfession[] arr = list.<MatchProfession>toArray(new MatchProfession[list.size()]);
    return arr;
  }
  
  private MatchProfession parseProfession(String str) {
    String strProf = str;
    String strLevels = null;
    int pos = str.lastIndexOf(':');
    if (pos >= 0) {
      String part1 = str.substring(0, pos);
      String part2 = str.substring(pos + 1);
      if (part2.isEmpty() || part2.matches("[0-9].*")) {
        strProf = part1;
        strLevels = part2;
      } 
    } 
    cmn prof = parseVillagerProfession(strProf);
    if (prof == null)
      return null; 
    int[] levels = parseIntList(strLevels);
    MatchProfession mp = new MatchProfession(prof, levels);
    return mp;
  }
  
  private cmn parseVillagerProfession(String str) {
    if (str == null)
      return null; 
    str = str.toLowerCase();
    akr loc = makeResourceLocation(str);
    if (loc == null)
      return null; 
    jh jh = lt.x;
    if (!jh.d(loc))
      return null; 
    return (cmn)jh.a(loc);
  }
  
  public int[] parseItems(String str) {
    str = str.trim();
    Set<Integer> setIds = new TreeSet<>();
    String[] tokens = Config.tokenize(str, " ");
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      akr loc = makeResourceLocation(token);
      if (loc != null) {
        cul item = ItemUtils.getItem(loc);
        if (item == null) {
          warn("Item not found: " + token);
        } else {
          int id = ItemUtils.getId(item);
          if (id < 0) {
            warn("Item has no ID: " + String.valueOf(item) + ", name: " + token);
          } else {
            setIds.add(new Integer(id));
          } 
        } 
      } 
    } 
    Integer[] integers = setIds.<Integer>toArray(new Integer[setIds.size()]);
    int[] ints = Config.toPrimitive(integers);
    return ints;
  }
  
  public int[] parseEntities(String str) {
    str = str.trim();
    Set<Integer> setIds = new TreeSet<>();
    String[] tokens = Config.tokenize(str, " ");
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      akr loc = makeResourceLocation(token);
      if (loc != null) {
        bsx type = EntityTypeUtils.getEntityType(loc);
        if (type == null) {
          warn("Entity not found: " + token);
        } else {
          int id = lt.f.a(type);
          if (id < 0) {
            warn("Entity has no ID: " + String.valueOf(type) + ", name: " + token);
          } else {
            setIds.add(new Integer(id));
          } 
        } 
      } 
    } 
    Integer[] integers = setIds.<Integer>toArray(new Integer[setIds.size()]);
    int[] ints = Config.toPrimitive(integers);
    return ints;
  }
}
