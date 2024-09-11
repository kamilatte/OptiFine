package srg.net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.optifine.shaders.IteratorRenderChunks;
import net.optifine.shaders.Shaders;

public class ShadowUtils {
  public static Iterator<SectionRenderDispatcher.RenderSection> makeShadowChunkIterator(ClientLevel world, double partialTicks, Entity viewEntity, int renderDistanceChunks, ViewArea viewFrustum) {
    float shadowRenderDistance = Shaders.getShadowRenderDistance();
    if (shadowRenderDistance <= 0.0F || shadowRenderDistance >= ((renderDistanceChunks - 1) * 16)) {
      List<SectionRenderDispatcher.RenderSection> listChunks = Arrays.asList(viewFrustum.sections);
      Iterator<SectionRenderDispatcher.RenderSection> iterator = listChunks.iterator();
      return iterator;
    } 
    int shadowDistanceChunks = Mth.ceil(shadowRenderDistance / 16.0F) + 1;
    float car = world.getSunAngle((float)partialTicks);
    float sunTiltRad = Shaders.sunPathRotation * Mth.deg2Rad;
    float sar = (car > Mth.PId2 && car < 3.0F * Mth.PId2) ? (car + 3.1415927F) : car;
    float dx = -Mth.sin(sar);
    float dy = Mth.cos(sar) * Mth.cos(sunTiltRad);
    float dz = -Mth.cos(sar) * Mth.sin(sunTiltRad);
    BlockPos posEntity = new BlockPos(Mth.floor(viewEntity.getX()) >> 4, Mth.floor(viewEntity.getY()) >> 4, Mth.floor(viewEntity.getZ()) >> 4);
    BlockPos posStart = posEntity.offset((int)(-dx * shadowDistanceChunks), (int)(-dy * shadowDistanceChunks), (int)(-dz * shadowDistanceChunks));
    BlockPos posEnd = posEntity.offset((int)(dx * renderDistanceChunks), (int)(dy * renderDistanceChunks), (int)(dz * renderDistanceChunks));
    IteratorRenderChunks it = new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
    return (Iterator<SectionRenderDispatcher.RenderSection>)it;
  }
}
