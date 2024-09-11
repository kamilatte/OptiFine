package notch.net.minecraftforge.client.extensions;

import fbi;
import gfw;

public interface IForgeVertexConsumer {
  default void putBulkData(fbi.a matrixStack, gfw bakedQuad, float red, float green, float blue, int lightmapCoord, int overlayColor, boolean readExistingColor) {}
  
  default void putBulkData(fbi.a pose, gfw bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor) {}
}
