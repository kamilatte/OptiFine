package notch.net.optifine;

import dbz;
import dcc;
import dfy;
import dga;
import dgi;
import dnc;
import dnt;
import dtc;
import dtn;
import dub;
import duf;
import duk;
import gsm;
import jd;
import net.optifine.Config;

public class BetterSnow {
  private static gsm modelSnowLayer = null;
  
  public static void update() {
    modelSnowLayer = Config.getMinecraft().ao().a().b(dga.dN.o());
  }
  
  public static gsm getModelSnowLayer() {
    return modelSnowLayer;
  }
  
  public static dtc getStateSnowLayer() {
    return dga.dN.o();
  }
  
  public static boolean shouldRender(dbz lightReader, dtc blockState, jd blockPos) {
    if (!(lightReader instanceof dcc))
      return false; 
    dbz dbz1 = lightReader;
    if (!checkBlock((dcc)dbz1, blockState, blockPos))
      return false; 
    return hasSnowNeighbours((dcc)dbz1, blockPos);
  }
  
  private static boolean hasSnowNeighbours(dcc blockAccess, jd pos) {
    dfy blockSnow = dga.dN;
    if (blockAccess.a_(pos.f()).b() == blockSnow || blockAccess
      .a_(pos.g()).b() == blockSnow || blockAccess
      .a_(pos.h()).b() == blockSnow || blockAccess
      .a_(pos.i()).b() == blockSnow) {
      dtc bsDown = blockAccess.a_(pos.e());
      if (bsDown.i(blockAccess, pos))
        return true; 
      dfy blockDown = bsDown.b();
      if (blockDown instanceof dnt)
        return (bsDown.c((duf)dnt.c) == dub.a); 
      if (blockDown instanceof dnc)
        return (bsDown.c((duf)dnc.b) == duk.a); 
    } 
    return false;
  }
  
  private static boolean checkBlock(dcc blockAccess, dtc blockState, jd blockPos) {
    if (blockState.i(blockAccess, blockPos))
      return false; 
    dfy block = blockState.b();
    if (block == dga.dP)
      return false; 
    if (block instanceof dgh)
      if (block instanceof dig || block instanceof dja || block instanceof dla || block instanceof dmn || block instanceof doe)
        return true;  
    if (block instanceof diw || block instanceof dix || block instanceof djb || block instanceof dhu || block instanceof dnz || block instanceof dox)
      return true; 
    if (block instanceof dmd)
      return true; 
    if (block instanceof dnt)
      return (blockState.c((duf)dnt.c) == dub.a); 
    if (block instanceof dnc)
      return (blockState.c((duf)dnc.b) == duk.a); 
    if (block instanceof dgi)
      return (blockState.c((duf)dgi.K) != dtn.a); 
    if (block instanceof djt)
      return true; 
    if (block instanceof dke)
      return true; 
    if (block instanceof dkl)
      return true; 
    if (block instanceof dor)
      return true; 
    if (block instanceof dov)
      return true; 
    return false;
  }
}
