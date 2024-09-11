package srg.net.optifine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.optifine.reflect.Reflector;
import net.optifine.util.IntegratedServerUtils;

public class TileEntityUtils {
  public static String getTileEntityName(BlockGetter blockAccess, BlockPos blockPos) {
    BlockEntity te = blockAccess.getBlockEntity(blockPos);
    return getTileEntityName(te);
  }
  
  public static String getTileEntityName(BlockEntity te) {
    if (!(te instanceof Nameable))
      return null; 
    Nameable iwn = (Nameable)te;
    updateTileEntityName(te);
    if (!iwn.hasCustomName())
      return null; 
    return iwn.getCustomName().getString();
  }
  
  public static void updateTileEntityName(BlockEntity te) {
    MutableComponent mutableComponent;
    BlockPos pos = te.getBlockPos();
    Component name = getTileEntityRawName(te);
    if (name != null)
      return; 
    Component nameServer = getServerTileEntityRawName(pos);
    if (nameServer == null)
      mutableComponent = Component.literal(""); 
    setTileEntityRawName(te, (Component)mutableComponent);
  }
  
  public static Component getServerTileEntityRawName(BlockPos blockPos) {
    BlockEntity tes = IntegratedServerUtils.getTileEntity(blockPos);
    if (tes == null)
      return null; 
    Component itc = getTileEntityRawName(tes);
    return itc;
  }
  
  public static Component getTileEntityRawName(BlockEntity te) {
    if (te instanceof Nameable)
      return ((Nameable)te).getCustomName(); 
    if (te instanceof BeaconBlockEntity)
      return (Component)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName); 
    return null;
  }
  
  public static boolean setTileEntityRawName(BlockEntity te, Component name) {
    if (te instanceof net.minecraft.world.level.block.entity.BaseContainerBlockEntity) {
      Reflector.BaseContainerBlockEntity_customName.setValue(te, name);
      return true;
    } 
    if (te instanceof net.minecraft.world.level.block.entity.BannerBlockEntity) {
      Reflector.BannerBlockEntity_customName.setValue(te, name);
      return true;
    } 
    if (te instanceof EnchantingTableBlockEntity) {
      ((EnchantingTableBlockEntity)te).setCustomName(name);
      return true;
    } 
    if (te instanceof BeaconBlockEntity) {
      ((BeaconBlockEntity)te).setCustomName(name);
      return true;
    } 
    return false;
  }
}
