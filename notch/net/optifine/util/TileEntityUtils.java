package notch.net.optifine.util;

import bqw;
import dcc;
import dqc;
import dqh;
import drc;
import jd;
import net.optifine.reflect.Reflector;
import net.optifine.util.IntegratedServerUtils;
import wz;
import xn;

public class TileEntityUtils {
  public static String getTileEntityName(dcc blockAccess, jd blockPos) {
    dqh te = blockAccess.c_(blockPos);
    return getTileEntityName(te);
  }
  
  public static String getTileEntityName(dqh te) {
    if (!(te instanceof bqw))
      return null; 
    bqw iwn = (bqw)te;
    updateTileEntityName(te);
    if (!iwn.ai())
      return null; 
    return iwn.aj().getString();
  }
  
  public static void updateTileEntityName(dqh te) {
    xn xn;
    jd pos = te.aD_();
    wz name = getTileEntityRawName(te);
    if (name != null)
      return; 
    wz nameServer = getServerTileEntityRawName(pos);
    if (nameServer == null)
      xn = wz.b(""); 
    setTileEntityRawName(te, (wz)xn);
  }
  
  public static wz getServerTileEntityRawName(jd blockPos) {
    dqh tes = IntegratedServerUtils.getTileEntity(blockPos);
    if (tes == null)
      return null; 
    wz itc = getTileEntityRawName(tes);
    return itc;
  }
  
  public static wz getTileEntityRawName(dqh te) {
    if (te instanceof bqw)
      return ((bqw)te).aj(); 
    if (te instanceof dqc)
      return (wz)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName); 
    return null;
  }
  
  public static boolean setTileEntityRawName(dqh te, wz name) {
    if (te instanceof dqb) {
      Reflector.BaseContainerBlockEntity_customName.setValue(te, name);
      return true;
    } 
    if (te instanceof dpw) {
      Reflector.BannerBlockEntity_customName.setValue(te, name);
      return true;
    } 
    if (te instanceof drc) {
      ((drc)te).a(name);
      return true;
    } 
    if (te instanceof dqc) {
      ((dqc)te).a(name);
      return true;
    } 
    return false;
  }
}
