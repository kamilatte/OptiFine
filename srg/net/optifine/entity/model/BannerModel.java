package srg.net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BannerModel extends Model {
  public ModelPart bannerSlate;
  
  public ModelPart bannerStand;
  
  public ModelPart bannerTop;
  
  public BannerModel() {
    super(RenderType::entityCutoutNoCull);
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BannerRenderer renderer = new BannerRenderer(dispatcher.getContext());
    this.bannerSlate = (ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 0);
    this.bannerStand = (ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 1);
    this.bannerTop = (ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 2);
  }
  
  public BlockEntityRenderer updateRenderer(BlockEntityRenderer renderer) {
    if (!Reflector.TileEntityBannerRenderer_modelRenderers.exists()) {
      Config.warn("Field not found: TileEntityBannerRenderer.modelRenderers");
      return null;
    } 
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 0, this.bannerSlate);
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 1, this.bannerStand);
    Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 2, this.bannerTop);
    return renderer;
  }
  
  public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
