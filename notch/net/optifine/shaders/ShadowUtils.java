package notch.net.optifine.shaders;

import ayo;
import bsr;
import fzf;
import gfq;
import gia;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import jd;
import net.optifine.shaders.IteratorRenderChunks;
import net.optifine.shaders.Shaders;

public class ShadowUtils {
  public static Iterator<gia.b> makeShadowChunkIterator(fzf world, double partialTicks, bsr viewEntity, int renderDistanceChunks, gfq viewFrustum) {
    float shadowRenderDistance = Shaders.getShadowRenderDistance();
    if (shadowRenderDistance <= 0.0F || shadowRenderDistance >= ((renderDistanceChunks - 1) * 16)) {
      List<gia.b> listChunks = Arrays.asList(viewFrustum.f);
      Iterator<gia.b> iterator = listChunks.iterator();
      return iterator;
    } 
    int shadowDistanceChunks = ayo.f(shadowRenderDistance / 16.0F) + 1;
    float car = world.a((float)partialTicks);
    float sunTiltRad = Shaders.sunPathRotation * ayo.deg2Rad;
    float sar = (car > ayo.PId2 && car < 3.0F * ayo.PId2) ? (car + 3.1415927F) : car;
    float dx = -ayo.a(sar);
    float dy = ayo.b(sar) * ayo.b(sunTiltRad);
    float dz = -ayo.b(sar) * ayo.a(sunTiltRad);
    jd posEntity = new jd(ayo.a(viewEntity.dt()) >> 4, ayo.a(viewEntity.dv()) >> 4, ayo.a(viewEntity.dz()) >> 4);
    jd posStart = posEntity.b((int)(-dx * shadowDistanceChunks), (int)(-dy * shadowDistanceChunks), (int)(-dz * shadowDistanceChunks));
    jd posEnd = posEntity.b((int)(dx * renderDistanceChunks), (int)(dy * renderDistanceChunks), (int)(dz * renderDistanceChunks));
    IteratorRenderChunks it = new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
    return (Iterator<gia.b>)it;
  }
}
