package srg.net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BellModel extends Model {
  public ModelPart bellBody;
  
  public BellModel() {
    super(RenderType::entityCutoutNoCull);
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BellRenderer renderer = new BellRenderer(dispatcher.getContext());
    this.bellBody = (ModelPart)Reflector.TileEntityBellRenderer_modelRenderer.getValue(renderer);
  }
  
  public BlockEntityRenderer updateRenderer(BlockEntityRenderer renderer) {
    if (!Reflector.TileEntityBellRenderer_modelRenderer.exists()) {
      Config.warn("Field not found: TileEntityBellRenderer.modelRenderer");
      return null;
    } 
    Reflector.TileEntityBellRenderer_modelRenderer.setValue(renderer, this.bellBody);
    return renderer;
  }
  
  public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {}
}
