package notch.net.optifine.util;

import akq;
import aqu;
import bsr;
import dcw;
import dqh;
import duy;
import dvz;
import fgo;
import fzf;
import guo;
import java.util.UUID;
import jd;
import net.optifine.Config;

public class IntegratedServerUtils {
  public static aqu getWorldServer() {
    fgo mc = Config.getMinecraft();
    fzf fzf = mc.r;
    if (fzf == null)
      return null; 
    if (!mc.T())
      return null; 
    guo is = mc.V();
    if (is == null)
      return null; 
    akq<dcw> wd = fzf.af();
    if (wd == null)
      return null; 
    try {
      aqu ws = is.a(wd);
      return ws;
    } catch (NullPointerException e) {
      return null;
    } 
  }
  
  public static bsr getEntity(UUID uuid) {
    aqu ws = getWorldServer();
    if (ws == null)
      return null; 
    bsr e = ws.a(uuid);
    return e;
  }
  
  public static dqh getTileEntity(jd pos) {
    aqu ws = getWorldServer();
    if (ws == null)
      return null; 
    duy chunk = ws.l().a(pos.u() >> 4, pos.w() >> 4, dvz.n, false);
    if (chunk == null)
      return null; 
    dqh te = chunk.c_(pos);
    return te;
  }
}
