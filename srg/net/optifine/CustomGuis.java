package srg.net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.optifine.Config;
import net.optifine.CustomGuiProperties;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
  private static Minecraft mc = Config.getMinecraft();
  
  private static PlayerControllerOF playerControllerOF = null;
  
  private static CustomGuiProperties[][] guiProperties = null;
  
  public static boolean isChristmas = isChristmas();
  
  public static ResourceLocation getTextureLocation(ResourceLocation loc) {
    if (guiProperties == null)
      return loc; 
    Screen screen = mc.screen;
    if (!(screen instanceof net.minecraft.client.gui.screens.inventory.AbstractContainerScreen))
      return loc; 
    if (!loc.getNamespace().equals("minecraft") || !loc.getPath().startsWith("textures/gui/"))
      return loc; 
    if (playerControllerOF == null)
      return loc; 
    ClientLevel clientLevel = mc.level;
    if (clientLevel == null)
      return loc; 
    if (screen instanceof net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen)
      return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.player.blockPosition(), (LevelReader)clientLevel, loc, screen); 
    if (screen instanceof net.minecraft.client.gui.screens.inventory.InventoryScreen)
      return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.player.blockPosition(), (LevelReader)clientLevel, loc, screen); 
    BlockPos pos = playerControllerOF.getLastClickBlockPos();
    if (pos != null) {
      if (screen instanceof net.minecraft.client.gui.screens.inventory.AnvilScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.BeaconScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.BrewingStandScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.ContainerScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.CraftingScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.DispenserScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.EnchantmentScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.FurnaceScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.HopperScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, (LevelReader)clientLevel, loc, screen); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen)
        return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, (LevelReader)clientLevel, loc, screen); 
    } 
    Entity entity = playerControllerOF.getLastClickEntity();
    if (entity != null) {
      if (screen instanceof net.minecraft.client.gui.screens.inventory.HorseInventoryScreen)
        return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (LevelReader)clientLevel, loc); 
      if (screen instanceof net.minecraft.client.gui.screens.inventory.MerchantScreen)
        return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (LevelReader)clientLevel, loc); 
    } 
    return loc;
  }
  
  private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer container, BlockPos pos, LevelReader blockAccess, ResourceLocation loc, Screen screen) {
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
  
  private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer container, Entity entity, LevelReader blockAccess, ResourceLocation loc) {
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
    PackResources[] rps = Config.getResourcePacks();
    for (int i = rps.length - 1; i >= 0; i--) {
      PackResources rp = rps[i];
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
  
  private static void update(PackResources rp, List<List<CustomGuiProperties>> listProps) {
    String[] paths = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
    Arrays.sort((Object[])paths);
    for (int i = 0; i < paths.length; i++) {
      String name = paths[i];
      Config.dbg("CustomGuis: " + name);
      try {
        ResourceLocation locFile = new ResourceLocation(name);
        InputStream in = Config.getResourceStream(rp, PackType.CLIENT_RESOURCES, locFile);
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
