package notch.net.optifine;

import akr;
import asq;
import ass;
import bsr;
import dcz;
import fgo;
import fod;
import fzf;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import jd;
import net.optifine.Config;
import net.optifine.CustomGuiProperties;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
  private static fgo mc = Config.getMinecraft();
  
  private static PlayerControllerOF playerControllerOF = null;
  
  private static CustomGuiProperties[][] guiProperties = null;
  
  public static boolean isChristmas = isChristmas();
  
  public static akr getTextureLocation(akr loc) {
    if (guiProperties == null)
      return loc; 
    fod screen = mc.y;
    if (!(screen instanceof fot))
      return loc; 
    if (!loc.b().equals("minecraft") || !loc.a().startsWith("textures/gui/"))
      return loc; 
    if (playerControllerOF == null)
      return loc; 
    fzf fzf = mc.r;
    if (fzf == null)
      return loc; 
    if (screen instanceof fpi)
      return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.s.do(), (dcz)fzf, loc, screen); 
    if (screen instanceof fpt)
      return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.s.do(), (dcz)fzf, loc, screen); 
    jd pos = playerControllerOF.getLastClickBlockPos();
    if (pos != null) {
      if (screen instanceof fow)
        return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fox)
        return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpb)
        return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpe)
        return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpg)
        return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpk)
        return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpn)
        return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpo)
        return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fpr)
        return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, (dcz)fzf, loc, screen); 
      if (screen instanceof fqc)
        return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, (dcz)fzf, loc, screen); 
    } 
    bsr entity = playerControllerOF.getLastClickEntity();
    if (entity != null) {
      if (screen instanceof fps)
        return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (dcz)fzf, loc); 
      if (screen instanceof fpz)
        return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (dcz)fzf, loc); 
    } 
    return loc;
  }
  
  private static akr getTexturePos(CustomGuiProperties.EnumContainer container, jd pos, dcz blockAccess, akr loc, fod screen) {
    CustomGuiProperties[] props = guiProperties[container.ordinal()];
    if (props == null)
      return loc; 
    for (int i = 0; i < props.length; ) {
      CustomGuiProperties prop = props[i];
      if (!prop.matchesPos(container, pos, blockAccess, screen)) {
        i++;
        continue;
      } 
      return prop.getTextureLocation(loc);
    } 
    return loc;
  }
  
  private static akr getTextureEntity(CustomGuiProperties.EnumContainer container, bsr entity, dcz blockAccess, akr loc) {
    CustomGuiProperties[] props = guiProperties[container.ordinal()];
    if (props == null)
      return loc; 
    for (int i = 0; i < props.length; i++) {
      CustomGuiProperties prop = props[i];
      if (prop.matchesEntity(container, entity, blockAccess))
        return prop.getTextureLocation(loc); 
    } 
    return loc;
  }
  
  public static void update() {
    guiProperties = null;
    if (!Config.isCustomGuis())
      return; 
    List<List<CustomGuiProperties>> listProps = new ArrayList<>();
    asq[] rps = Config.getResourcePacks();
    for (int i = rps.length - 1; i >= 0; i--) {
      asq rp = rps[i];
      update(rp, listProps);
    } 
    guiProperties = propertyListToArray(listProps);
  }
  
  private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
    if (listProps.isEmpty())
      return null; 
    CustomGuiProperties[][] cgps = new CustomGuiProperties[(CustomGuiProperties.EnumContainer.values()).length][];
    for (int i = 0; i < cgps.length; i++) {
      if (listProps.size() > i) {
        List<CustomGuiProperties> subList = listProps.get(i);
        if (subList != null) {
          CustomGuiProperties[] subArr = subList.<CustomGuiProperties>toArray(new CustomGuiProperties[subList.size()]);
          cgps[i] = subArr;
        } 
      } 
    } 
    return cgps;
  }
  
  private static void update(asq rp, List<List<CustomGuiProperties>> listProps) {
    String[] paths = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
    Arrays.sort((Object[])paths);
    for (int i = 0; i < paths.length; i++) {
      String name = paths[i];
      Config.dbg("CustomGuis: " + name);
      try {
        akr locFile = new akr(name);
        InputStream in = Config.getResourceStream(rp, ass.a, locFile);
        if (in == null) {
          Config.warn("CustomGuis file not found: " + name);
        } else {
          PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
          propertiesOrdered.load(in);
          in.close();
          CustomGuiProperties cgp = new CustomGuiProperties((Properties)propertiesOrdered, name);
          if (cgp.isValid(name))
            addToList(cgp, listProps); 
        } 
      } catch (FileNotFoundException e) {
        Config.warn("CustomGuis file not found: " + name);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
    if (cgp.getContainer() == null) {
      warn("Invalid container: " + String.valueOf(cgp.getContainer()));
      return;
    } 
    int indexContainer = cgp.getContainer().ordinal();
    while (listProps.size() <= indexContainer)
      listProps.add(null); 
    List<CustomGuiProperties> subList = listProps.get(indexContainer);
    if (subList == null) {
      subList = new ArrayList<>();
      listProps.set(indexContainer, subList);
    } 
    subList.add(cgp);
  }
  
  public static PlayerControllerOF getPlayerControllerOF() {
    return playerControllerOF;
  }
  
  public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
    net.optifine.CustomGuis.playerControllerOF = playerControllerOF;
  }
  
  private static boolean isChristmas() {
    Calendar calendar = Calendar.getInstance();
    if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
      return true; 
    return false;
  }
  
  private static void warn(String str) {
    Config.warn("[CustomGuis] " + str);
  }
}
