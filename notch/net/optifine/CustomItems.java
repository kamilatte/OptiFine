package notch.net.optifine;

import akr;
import asq;
import ass;
import brx;
import bsy;
import btn;
import csf;
import csg;
import cul;
import cuq;
import cut;
import cws;
import dac;
import dai;
import fvk;
import gge;
import glh;
import gqk;
import gsm;
import gss;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import jm;
import kq;
import lt;
import net.optifine.Config;
import net.optifine.CustomItemProperties;
import net.optifine.CustomItemsComparator;
import net.optifine.config.NbtTagValue;
import net.optifine.shaders.Shaders;
import net.optifine.util.EnchantmentUtils;
import net.optifine.util.ItemUtils;
import net.optifine.util.PotionUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import ub;

public class CustomItems {
  private static CustomItemProperties[][] itemProperties = null;
  
  private static CustomItemProperties[][] enchantmentProperties = null;
  
  private static Map mapPotionIds = null;
  
  private static gge itemModelGenerator = new gge();
  
  private static boolean useGlint = true;
  
  private static boolean renderOffHand = false;
  
  private static AtomicBoolean modelsLoaded = new AtomicBoolean(false);
  
  public static final int MASK_POTION_SPLASH = 16384;
  
  public static final int MASK_POTION_NAME = 63;
  
  public static final int MASK_POTION_EXTENDED = 64;
  
  public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
  
  public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
  
  public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
  
  public static final String DEFAULT_TEXTURE_OVERLAY = "item/potion_overlay";
  
  public static final String DEFAULT_TEXTURE_SPLASH = "item/potion_bottle_splash";
  
  public static final String DEFAULT_TEXTURE_DRINKABLE = "item/potion_bottle_drinkable";
  
  private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
  
  private static final Map<String, Integer> mapPotionDamages = makeMapPotionDamages();
  
  private static final String TYPE_POTION_NORMAL = "normal";
  
  private static final String TYPE_POTION_SPLASH = "splash";
  
  private static final String TYPE_POTION_LINGER = "linger";
  
  public static void update() {
    itemProperties = null;
    enchantmentProperties = null;
    useGlint = true;
    modelsLoaded.set(false);
    if (!Config.isCustomItems())
      return; 
    readCitProperties("optifine/cit.properties");
    asq[] rps = Config.getResourcePacks();
    for (int i = rps.length - 1; i >= 0; i--) {
      asq rp = rps[i];
      update(rp);
    } 
    update((asq)Config.getDefaultResourcePack());
    if (itemProperties.length <= 0)
      itemProperties = null; 
    if (enchantmentProperties.length <= 0)
      enchantmentProperties = null; 
  }
  
  private static void readCitProperties(String fileName) {
    try {
      akr loc = new akr(fileName);
      InputStream in = Config.getResourceStream(loc);
      if (in == null)
        return; 
      Config.dbg("CustomItems: Loading " + fileName);
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      useGlint = Config.parseBoolean(propertiesOrdered.getProperty("useGlint"), true);
    } catch (FileNotFoundException e) {
      return;
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private static void update(asq rp) {
    String[] names = ResUtils.collectFiles(rp, "optifine/cit/", ".properties", null);
    Map<String, CustomItemProperties> mapAutoProperties = makeAutoImageProperties(rp);
    if (mapAutoProperties.size() > 0) {
      Set<String> keySetAuto = mapAutoProperties.keySet();
      String[] keysAuto = keySetAuto.<String>toArray(new String[keySetAuto.size()]);
      names = (String[])Config.addObjectsToArray((Object[])names, (Object[])keysAuto);
    } 
    Arrays.sort((Object[])names);
    List<List<CustomItemProperties>> itemList = makePropertyList(itemProperties);
    List<List<CustomItemProperties>> enchantmentList = makePropertyList(enchantmentProperties);
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      Config.dbg("CustomItems: " + name);
      try {
        CustomItemProperties cip = null;
        if (mapAutoProperties.containsKey(name))
          cip = mapAutoProperties.get(name); 
        if (cip == null) {
          akr locFile = new akr(name);
          InputStream in = Config.getResourceStream(rp, ass.a, locFile);
          if (in == null) {
            Config.warn("CustomItems file not found: " + name);
          } else {
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(in);
            in.close();
            cip = new CustomItemProperties((Properties)propertiesOrdered, name);
            if (cip.isValid(name)) {
              addToItemList(cip, itemList);
              addToEnchantmentList(cip, enchantmentList);
            } 
          } 
          continue;
        } 
        if (cip.isValid(name)) {
          addToItemList(cip, itemList);
          addToEnchantmentList(cip, enchantmentList);
        } 
      } catch (FileNotFoundException e) {
        Config.warn("CustomItems file not found: " + name);
        continue;
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      } 
    } 
    itemProperties = propertyListToArray(itemList);
    enchantmentProperties = propertyListToArray(enchantmentList);
    Comparator<? super CustomItemProperties> comp = getPropertiesComparator();
    int j;
    for (j = 0; j < itemProperties.length; j++) {
      CustomItemProperties[] cips = itemProperties[j];
      if (cips != null)
        Arrays.sort(cips, comp); 
    } 
    for (j = 0; j < enchantmentProperties.length; j++) {
      CustomItemProperties[] cips = enchantmentProperties[j];
      if (cips != null)
        Arrays.sort(cips, comp); 
    } 
  }
  
  private static Comparator getPropertiesComparator() {
    return (Comparator)new Object();
  }
  
  public static void updateIcons(gqk textureMap) {
    while (!modelsLoaded.get())
      Config.sleep(100L); 
    List<CustomItemProperties> cips = getAllProperties();
    for (Iterator<CustomItemProperties> it = cips.iterator(); it.hasNext(); ) {
      CustomItemProperties cip = it.next();
      cip.updateIcons(textureMap);
    } 
  }
  
  public static void refreshIcons(gqk textureMap) {
    List<CustomItemProperties> cips = getAllProperties();
    for (Iterator<CustomItemProperties> it = cips.iterator(); it.hasNext(); ) {
      CustomItemProperties cip = it.next();
      cip.refreshIcons(textureMap);
    } 
  }
  
  public static void loadModels(gss modelBakery) {
    List<CustomItemProperties> cips = getAllProperties();
    for (Iterator<CustomItemProperties> it = cips.iterator(); it.hasNext(); ) {
      CustomItemProperties cip = it.next();
      cip.loadModels(modelBakery);
    } 
    modelsLoaded.set(true);
  }
  
  public static void updateModels() {
    List<CustomItemProperties> cips = getAllProperties();
    for (Iterator<CustomItemProperties> it = cips.iterator(); it.hasNext(); ) {
      CustomItemProperties cip = it.next();
      if (cip.type != 1)
        continue; 
      gqk textureMap = Config.getTextureMap();
      cip.updateModelTexture(textureMap, itemModelGenerator);
      cip.updateModelsFull();
    } 
  }
  
  private static List<CustomItemProperties> getAllProperties() {
    List<CustomItemProperties> list = new ArrayList<>();
    addAll(itemProperties, list);
    addAll(enchantmentProperties, list);
    return list;
  }
  
  private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
    if (cipsArr == null)
      return; 
    for (int i = 0; i < cipsArr.length; i++) {
      CustomItemProperties[] cips = cipsArr[i];
      if (cips != null)
        for (int k = 0; k < cips.length; k++) {
          CustomItemProperties cip = cips[k];
          if (cip != null)
            list.add(cip); 
        }  
    } 
  }
  
  private static Map<String, CustomItemProperties> makeAutoImageProperties(asq rp) {
    Map<String, CustomItemProperties> map = new HashMap<>();
    map.putAll(makePotionImageProperties(rp, "normal", lt.g.b(cut.sk)));
    map.putAll(makePotionImageProperties(rp, "splash", lt.g.b(cut.vo)));
    map.putAll(makePotionImageProperties(rp, "linger", lt.g.b(cut.vr)));
    return map;
  }
  
  private static Map<String, CustomItemProperties> makePotionImageProperties(asq rp, String type, akr itemId) {
    Map<String, CustomItemProperties> map = new HashMap<>();
    String typePrefix = type + "/";
    String[] prefixes = { "optifine/cit/potion/" + typePrefix, "optifine/cit/Potion/" + typePrefix };
    String[] suffixes = { ".png" };
    String[] names = ResUtils.collectFiles(rp, prefixes, suffixes);
    for (int i = 0; i < names.length; i++) {
      String path = names[i];
      String name = path;
      name = StrUtils.removePrefixSuffix(name, prefixes, suffixes);
      Properties props = makePotionProperties(name, type, itemId, path);
      if (props != null) {
        String pathProp = StrUtils.removeSuffix(path, suffixes) + ".properties";
        CustomItemProperties cip = new CustomItemProperties(props, pathProp);
        map.put(pathProp, cip);
      } 
    } 
    return map;
  }
  
  private static Properties makePotionProperties(String name, String type, akr itemId, String path) {
    if (StrUtils.endsWith(name, new String[] { "_n", "_s" }))
      return null; 
    if (name.equals("empty") && type.equals("normal")) {
      itemId = lt.g.b(cut.sl);
      PropertiesOrdered propertiesOrdered1 = new PropertiesOrdered();
      propertiesOrdered1.put("type", "item");
      propertiesOrdered1.put("items", itemId.toString());
      return (Properties)propertiesOrdered1;
    } 
    akr potionItemId = itemId;
    int[] damages = (int[])getMapPotionIds().get(name);
    if (damages == null) {
      Config.warn("Potion not found for image: " + path);
      return null;
    } 
    StringBuffer bufDamage = new StringBuffer();
    for (int i = 0; i < damages.length; i++) {
      int damage = damages[i];
      if (type.equals("splash"))
        damage |= 0x4000; 
      if (i > 0)
        bufDamage.append(" "); 
      bufDamage.append(damage);
    } 
    int damageMask = 16447;
    if (name.equals("water") || name.equals("mundane"))
      damageMask |= 0x40; 
    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
    propertiesOrdered.put("type", "item");
    propertiesOrdered.put("items", potionItemId.toString());
    propertiesOrdered.put("damage", bufDamage.toString());
    propertiesOrdered.put("damageMask", "" + damageMask);
    if (type.equals("splash")) {
      propertiesOrdered.put("texture.potion_bottle_splash", name);
    } else {
      propertiesOrdered.put("texture.potion_bottle_drinkable", name);
    } 
    return (Properties)propertiesOrdered;
  }
  
  private static Map getMapPotionIds() {
    if (mapPotionIds == null) {
      mapPotionIds = new LinkedHashMap<>();
      mapPotionIds.put("water", getPotionId(0, 0));
      mapPotionIds.put("awkward", getPotionId(0, 1));
      mapPotionIds.put("thick", getPotionId(0, 2));
      mapPotionIds.put("potent", getPotionId(0, 3));
      mapPotionIds.put("regeneration", getPotionIds(1));
      mapPotionIds.put("movespeed", getPotionIds(2));
      mapPotionIds.put("fireresistance", getPotionIds(3));
      mapPotionIds.put("poison", getPotionIds(4));
      mapPotionIds.put("heal", getPotionIds(5));
      mapPotionIds.put("nightvision", getPotionIds(6));
      mapPotionIds.put("clear", getPotionId(7, 0));
      mapPotionIds.put("bungling", getPotionId(7, 1));
      mapPotionIds.put("charming", getPotionId(7, 2));
      mapPotionIds.put("rank", getPotionId(7, 3));
      mapPotionIds.put("weakness", getPotionIds(8));
      mapPotionIds.put("damageboost", getPotionIds(9));
      mapPotionIds.put("moveslowdown", getPotionIds(10));
      mapPotionIds.put("leaping", getPotionIds(11));
      mapPotionIds.put("harm", getPotionIds(12));
      mapPotionIds.put("waterbreathing", getPotionIds(13));
      mapPotionIds.put("invisibility", getPotionIds(14));
      mapPotionIds.put("thin", getPotionId(15, 0));
      mapPotionIds.put("debonair", getPotionId(15, 1));
      mapPotionIds.put("sparkling", getPotionId(15, 2));
      mapPotionIds.put("stinky", getPotionId(15, 3));
      mapPotionIds.put("mundane", getPotionId(0, 4));
      mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
      mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
      mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
      mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
      mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
      mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
      mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
      mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
    } 
    return mapPotionIds;
  }
  
  private static int[] getPotionIds(int baseId) {
    return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
  }
  
  private static int[] getPotionId(int baseId, int subId) {
    return new int[] { baseId + subId * 16 };
  }
  
  private static int getPotionNameDamage(String name) {
    String fullName = "effect." + name;
    Set<akr> keys = lt.d.f();
    for (Iterator<akr> it = keys.iterator(); it.hasNext(); ) {
      akr rl = it.next();
      if (lt.d.d(rl)) {
        brx potion = (brx)lt.d.a(rl);
        String potionName = potion.d();
        if (fullName.equals(potionName))
          return PotionUtils.getId(potion); 
      } 
    } 
    return -1;
  }
  
  private static List<List<CustomItemProperties>> makePropertyList(CustomItemProperties[][] propsArr) {
    List<List<CustomItemProperties>> list = new ArrayList<>();
    if (propsArr != null)
      for (int i = 0; i < propsArr.length; i++) {
        CustomItemProperties[] props = propsArr[i];
        List<CustomItemProperties> propList = null;
        if (props != null)
          propList = new ArrayList<>(Arrays.asList(props)); 
        list.add(propList);
      }  
    return list;
  }
  
  private static CustomItemProperties[][] propertyListToArray(List<List> list) {
    CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
    for (int i = 0; i < list.size(); i++) {
      List subList = list.get(i);
      if (subList != null) {
        CustomItemProperties[] subArr = (CustomItemProperties[])subList.toArray((Object[])new CustomItemProperties[subList.size()]);
        Arrays.sort(subArr, (Comparator<? super CustomItemProperties>)new CustomItemsComparator());
        propArr[i] = subArr;
      } 
    } 
    return propArr;
  }
  
  private static void addToItemList(CustomItemProperties cp, List<List<CustomItemProperties>> itemList) {
    if (cp.items == null)
      return; 
    for (int i = 0; i < cp.items.length; i++) {
      int itemId = cp.items[i];
      if (itemId <= 0) {
        Config.warn("Invalid item ID: " + itemId);
      } else {
        addToList(cp, itemList, itemId);
      } 
    } 
  }
  
  private static void addToEnchantmentList(CustomItemProperties cp, List<List<CustomItemProperties>> enchantmentList) {
    if (cp.type != 2)
      return; 
    if (cp.enchantmentIds == null)
      return; 
    int countIds = getMaxEnchantmentId() + 1;
    for (int i = 0; i < countIds; i++) {
      int id = i;
      if (Config.equalsOne(id, cp.enchantmentIds))
        addToList(cp, enchantmentList, id); 
    } 
  }
  
  private static int getMaxEnchantmentId() {
    return EnchantmentUtils.getMaxEnchantmentId();
  }
  
  private static void addToList(CustomItemProperties cp, List<List<CustomItemProperties>> list, int id) {
    while (id >= list.size())
      list.add(null); 
    List<CustomItemProperties> subList = list.get(id);
    if (subList == null) {
      subList = new ArrayList<>();
      list.set(id, subList);
    } 
    subList.add(cp);
  }
  
  public static gsm getCustomItemModel(cuq itemStack, gsm model, akr modelLocation, boolean fullModel) {
    if (!fullModel && model.b())
      return model; 
    if (itemProperties == null)
      return model; 
    CustomItemProperties props = getCustomItemProperties(itemStack, 1);
    if (props == null)
      return model; 
    gsm customModel = props.getBakedModel(modelLocation, fullModel);
    if (customModel != null)
      return customModel; 
    return model;
  }
  
  public static akr getCustomArmorTexture(cuq itemStack, bsy slot, String overlay, akr locArmor) {
    if (itemProperties == null)
      return locArmor; 
    akr loc = getCustomArmorLocation(itemStack, slot, overlay);
    if (loc == null)
      return locArmor; 
    return loc;
  }
  
  private static akr getCustomArmorLocation(cuq itemStack, bsy slot, String overlay) {
    CustomItemProperties props = getCustomItemProperties(itemStack, 3);
    if (props == null)
      return null; 
    if (props.mapTextureLocations == null)
      return props.textureLocation; 
    cul item = itemStack.g();
    if (!(item instanceof csf))
      return null; 
    csf itemArmor = (csf)item;
    List<csg.a> layers = ((csg)itemArmor.h().a()).e();
    if (layers.isEmpty())
      return null; 
    String material = ((csg.a)layers.get(0)).getAssetName().a();
    int layer = (slot == bsy.d) ? 2 : 1;
    StringBuffer sb = new StringBuffer();
    sb.append("texture.");
    sb.append(material);
    sb.append("_layer_");
    sb.append(layer);
    if (overlay != null) {
      sb.append("_");
      sb.append(overlay);
    } 
    String key = sb.toString();
    akr loc = (akr)props.mapTextureLocations.get(key);
    if (loc == null)
      return props.textureLocation; 
    return loc;
  }
  
  public static akr getCustomElytraTexture(cuq itemStack, akr locElytra) {
    if (itemProperties == null)
      return locElytra; 
    CustomItemProperties props = getCustomItemProperties(itemStack, 4);
    if (props == null)
      return locElytra; 
    if (props.textureLocation == null)
      return locElytra; 
    return props.textureLocation;
  }
  
  private static CustomItemProperties getCustomItemProperties(cuq itemStack, int type) {
    CustomItemProperties[][] propertiesLocal = itemProperties;
    if (propertiesLocal == null)
      return null; 
    if (itemStack == null)
      return null; 
    cul item = itemStack.g();
    int itemId = cul.a(item);
    if (itemId >= 0 && itemId < propertiesLocal.length) {
      CustomItemProperties[] cips = propertiesLocal[itemId];
      if (cips != null)
        for (int i = 0; i < cips.length; i++) {
          CustomItemProperties cip = cips[i];
          if (cip.type == type)
            if (matchesProperties(cip, itemStack, null))
              return cip;  
        }  
    } 
    return null;
  }
  
  private static boolean matchesProperties(CustomItemProperties cip, cuq itemStack, int[][] enchantmentIdLevels) {
    cul item = itemStack.g();
    if (cip.damage != null) {
      int damage = getItemStackDamage(itemStack);
      if (damage < 0)
        return false; 
      if (cip.damageMask != 0)
        damage &= cip.damageMask; 
      if (cip.damagePercent) {
        int damageMax = itemStack.o();
        damage = (int)((damage * 100) / damageMax);
      } 
      if (!cip.damage.isInRange(damage))
        return false; 
    } 
    if (cip.stackSize != null)
      if (!cip.stackSize.isInRange(itemStack.H()))
        return false;  
    int[][] idLevels = enchantmentIdLevels;
    if (cip.enchantmentIds != null) {
      if (idLevels == null)
        idLevels = getEnchantmentIdLevels(itemStack); 
      boolean idMatch = false;
      for (int i = 0; i < idLevels.length; i++) {
        int id = idLevels[i][0];
        if (Config.equalsOne(id, cip.enchantmentIds)) {
          idMatch = true;
          break;
        } 
      } 
      if (!idMatch)
        return false; 
    } 
    if (cip.enchantmentLevels != null) {
      if (idLevels == null)
        idLevels = getEnchantmentIdLevels(itemStack); 
      boolean levelMatch = false;
      for (int i = 0; i < idLevels.length; i++) {
        int level = idLevels[i][1];
        if (cip.enchantmentLevels.isInRange(level)) {
          levelMatch = true;
          break;
        } 
      } 
      if (!levelMatch)
        return false; 
    } 
    if (cip.nbtTagValues != null) {
      ub nbt = ItemUtils.getTag(itemStack);
      for (int i = 0; i < cip.nbtTagValues.length; i++) {
        NbtTagValue ntv = cip.nbtTagValues[i];
        if (!ntv.matches(nbt))
          return false; 
      } 
    } 
    if (cip.hand != 0) {
      if (cip.hand == 1 && renderOffHand)
        return false; 
      if (cip.hand == 2 && !renderOffHand)
        return false; 
    } 
    return true;
  }
  
  private static int getItemStackDamage(cuq itemStack) {
    cul item = itemStack.g();
    if (item instanceof cvl)
      return getPotionDamage(itemStack); 
    return itemStack.n();
  }
  
  private static int getPotionDamage(cuq itemStack) {
    cws p = PotionUtils.getPotion(itemStack);
    if (p == null)
      return 0; 
    String name = PotionUtils.getPotionBaseName(p);
    if (name == null || name.equals(""))
      return 0; 
    Integer value = mapPotionDamages.get(name);
    if (value == null)
      return -1; 
    int val = value.intValue();
    if (itemStack.g() == cut.vo)
      val |= 0x4000; 
    return val;
  }
  
  private static Map<String, Integer> makeMapPotionDamages() {
    Map<String, Integer> map = new HashMap<>();
    addPotion("water", 0, false, map);
    addPotion("awkward", 16, false, map);
    addPotion("thick", 32, false, map);
    addPotion("mundane", 64, false, map);
    addPotion("regeneration", 1, true, map);
    addPotion("swiftness", 2, true, map);
    addPotion("fire_resistance", 3, true, map);
    addPotion("poison", 4, true, map);
    addPotion("healing", 5, true, map);
    addPotion("night_vision", 6, true, map);
    addPotion("weakness", 8, true, map);
    addPotion("strength", 9, true, map);
    addPotion("slowness", 10, true, map);
    addPotion("leaping", 11, true, map);
    addPotion("harming", 12, true, map);
    addPotion("water_breathing", 13, true, map);
    addPotion("invisibility", 14, true, map);
    return map;
  }
  
  private static void addPotion(String name, int value, boolean extended, Map<String, Integer> map) {
    if (extended)
      value |= 0x2000; 
    map.put("minecraft:" + name, Integer.valueOf(value));
    if (extended) {
      int valueStrong = value | 0x20;
      map.put("minecraft:strong_" + name, Integer.valueOf(valueStrong));
      int valueLong = value | 0x40;
      map.put("minecraft:long_" + name, Integer.valueOf(valueLong));
    } 
  }
  
  private static int[][] getEnchantmentIdLevels(cuq itemStack) {
    dai enchantments = (dai)itemStack.a(kq.k, dai.a);
    if (enchantments.d())
      enchantments = (dai)itemStack.a(kq.y, dai.a); 
    if (enchantments.d())
      return EMPTY_INT2_ARRAY; 
    Set<Object2IntMap.Entry<jm<dac>>> entries = enchantments.b();
    int[][] arr = new int[entries.size()][2];
    int i = 0;
    for (Object2IntMap.Entry<jm<dac>> entry : entries) {
      jm<dac> holder = (jm<dac>)entry.getKey();
      if (!holder.b())
        continue; 
      dac en = (dac)holder.a();
      int id = EnchantmentUtils.getId(en);
      int lvl = entry.getIntValue();
      arr[i][0] = id;
      arr[i][1] = lvl;
      i++;
    } 
    return arr;
  }
  
  public static boolean renderCustomEffect(glh renderItem, cuq itemStack, gsm model) {
    CustomItemProperties[][] propertiesLocal = enchantmentProperties;
    if (propertiesLocal == null)
      return false; 
    if (itemStack == null)
      return false; 
    int[][] idLevels = getEnchantmentIdLevels(itemStack);
    if (idLevels.length <= 0)
      return false; 
    Set layersRendered = null;
    boolean rendered = false;
    return rendered;
  }
  
  public static boolean renderCustomArmorEffect(btn entity, cuq itemStack, fvk model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
    CustomItemProperties[][] propertiesLocal = enchantmentProperties;
    if (propertiesLocal == null)
      return false; 
    if (Config.isShaders())
      if (Shaders.isShadowPass)
        return false;  
    if (itemStack == null)
      return false; 
    int[][] idLevels = getEnchantmentIdLevels(itemStack);
    if (idLevels.length <= 0)
      return false; 
    Set layersRendered = null;
    boolean rendered = false;
    return rendered;
  }
  
  public static boolean isUseGlint() {
    return useGlint;
  }
  
  public static void setRenderOffHand(boolean renderOffHand) {
    net.optifine.CustomItems.renderOffHand = renderOffHand;
  }
}
