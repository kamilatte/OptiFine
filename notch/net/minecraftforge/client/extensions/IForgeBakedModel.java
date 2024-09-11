package notch.net.minecraftforge.client.extensions;

import ayw;
import cun;
import cuq;
import dbz;
import dtc;
import fbi;
import gfh;
import gfw;
import gql;
import gsm;
import java.util.List;
import jd;
import ji;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;

public interface IForgeBakedModel {
  default gsm getBakedModel() {
    return (gsm)this;
  }
  
  default List<gfw> getQuads(dtc state, ji side, ayw rand, ModelData extraData) {
    return getBakedModel().a(state, side, rand);
  }
  
  default List<gfw> getQuads(dtc state, ji side, ayw rand, ModelData data, gfh renderType) {
    return getBakedModel().a(state, side, rand);
  }
  
  default boolean isAmbientOcclusion(dtc state) {
    return getBakedModel().a();
  }
  
  default boolean useAmbientOcclusion(dtc state) {
    return getBakedModel().a();
  }
  
  default boolean useAmbientOcclusion(dtc state, gfh renderType) {
    return isAmbientOcclusion(state);
  }
  
  default ModelData getModelData(dbz world, jd pos, dtc state, ModelData tileData) {
    return tileData;
  }
  
  default gql getParticleTexture(ModelData data) {
    return getBakedModel().e();
  }
  
  default gql getParticleIcon(ModelData data) {
    return self().e();
  }
  
  default List<gsm> getRenderPasses(cuq itemStack, boolean fabulous) {
    return List.of(self());
  }
  
  default List<gfh> getRenderTypes(cuq itemStack, boolean fabulous) {
    return List.of();
  }
  
  private gsm self() {
    return (gsm)this;
  }
  
  default ChunkRenderTypeSet getRenderTypes(dtc state, ayw rand, ModelData data) {
    return null;
  }
  
  default gsm applyTransform(cun transformType, fbi poseStack, boolean applyLeftHandTransform) {
    self().f().a(transformType).a(applyLeftHandTransform, poseStack);
    return self();
  }
}
