package notch.net.optifine.render;

import ayo;
import bsr;
import dqh;
import dvi;
import dvj;
import dxk;
import fzf;
import gfq;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jd;
import ji;
import kf;
import net.optifine.Config;

public class ChunkVisibility {
  public static final int MASK_FACINGS = 63;
  
  public static final ji[][] enumFacingArrays = makeEnumFacingArrays(false);
  
  public static final ji[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
  
  private static int counter = 0;
  
  private static int iMaxStatic = -1;
  
  private static int iMaxStaticFinal = 16;
  
  private static fzf worldLast = null;
  
  private static int pcxLast = Integer.MIN_VALUE;
  
  private static int pczLast = Integer.MIN_VALUE;
  
  public static int getMaxChunkY(fzf world, bsr viewEntity, int renderDistanceChunks, gfq viewFrustum) {
    int minHeight = world.I_();
    int maxHeight = world.J_();
    int minChunkHeight = minHeight >> 4;
    int pcx = ayo.a(viewEntity.dt()) >> 4;
    int pcy = ayo.a(viewEntity.dv() - minHeight) >> 4;
    int pcz = ayo.a(viewEntity.dz()) >> 4;
    int pcyMax = maxHeight - minHeight >> 4;
    pcy = Config.limit(pcy, 0, pcyMax - 1);
    long playerSectionKey = kf.c(viewEntity.do());
    dxk playerSection = world.getSectionStorage().d(playerSectionKey);
    boolean multiplayer = !Config.isIntegratedServerRunning();
    int cxStart = pcx - renderDistanceChunks;
    int cxEnd = pcx + renderDistanceChunks;
    int czStart = pcz - renderDistanceChunks;
    int czEnd = pcz + renderDistanceChunks;
    if (world != worldLast || pcx != pcxLast || pcz != pczLast) {
      counter = 0;
      iMaxStaticFinal = 16;
      worldLast = world;
      pcxLast = pcx;
      pczLast = pcz;
    } 
    if (counter == 0)
      iMaxStatic = -1; 
    int iMax = iMaxStatic;
    switch (counter) {
      case 0:
        cxEnd = pcx;
        czEnd = pcz;
        break;
      case 1:
        cxStart = pcx;
        czEnd = pcz;
        break;
      case 2:
        cxEnd = pcx;
        czStart = pcz;
        break;
      case 3:
        cxStart = pcx;
        czStart = pcz;
        break;
    } 
    for (int cx = cxStart; cx < cxEnd; cx++) {
      for (int cz = czStart; cz < czEnd; cz++) {
        dvi chunk = world.d(cx, cz);
        if (chunk.C()) {
          if (multiplayer) {
            int i = viewFrustum.getHighestUsedChunkIndex(cx, iMax, cz);
            if (i > iMax)
              iMax = i; 
          } 
        } else {
          dvj[] ebss = chunk.d();
          for (int i = ebss.length - 1; i > iMax; ) {
            dvj ebs = ebss[i];
            if (ebs == null || ebs.c()) {
              i--;
              continue;
            } 
            if (i > iMax)
              iMax = i; 
          } 
          try {
            Map<jd, dqh> mapTileEntities = chunk.G();
            if (!mapTileEntities.isEmpty()) {
              Set<jd> keys = mapTileEntities.keySet();
              for (Iterator<jd> it = keys.iterator(); it.hasNext(); ) {
                jd pos = it.next();
                int j = pos.v() - minHeight >> 4;
                if (j > iMax)
                  iMax = j; 
              } 
            } 
          } catch (ConcurrentModificationException concurrentModificationException) {}
        } 
      } 
    } 
    if (counter == 0) {
      LongSet sectionKeys = world.getSectionStorage().getSectionKeys();
      for (LongIterator it = sectionKeys.iterator(); it.hasNext(); ) {
        long sectionKey = it.nextLong();
        int sectionY = kf.c(sectionKey);
        int i = sectionY - minChunkHeight;
        if (sectionKey == playerSectionKey && i == pcy && playerSection != null && playerSection.getEntityList().size() == 1)
          continue; 
        if (i > iMax)
          iMax = i; 
      } 
    } 
    if (counter < 3) {
      iMaxStatic = iMax;
      iMax = iMaxStaticFinal;
    } else {
      iMaxStaticFinal = iMax;
      iMaxStatic = -1;
    } 
    counter = (counter + 1) % 4;
    return (iMax << 4) + minHeight;
  }
  
  public static boolean isFinished() {
    return (counter == 0);
  }
  
  private static ji[][] makeEnumFacingArrays(boolean opposite) {
    int count = 64;
    ji[][] arrs = new ji[count][];
    for (int i = 0; i < count; i++) {
      List<ji> list = new ArrayList<>();
      for (int ix = 0; ix < ji.r.length; ix++) {
        ji facing = ji.r[ix];
        ji facingMask = opposite ? facing.g() : facing;
        int mask = 1 << facingMask.ordinal();
        if ((i & mask) != 0)
          list.add(facing); 
      } 
      ji[] fs = list.<ji>toArray(new ji[list.size()]);
      arrs[i] = fs;
    } 
    return arrs;
  }
  
  public static ji[] getFacingsNotOpposite(int setDisabled) {
    int index = (setDisabled ^ 0xFFFFFFFF) & 0x3F;
    return enumFacingOppositeArrays[index];
  }
  
  public static ji[] getFacings(int setDirections) {
    int index = setDirections & 0x3F;
    return enumFacingArrays[index];
  }
  
  public static void reset() {
    worldLast = null;
  }
}
