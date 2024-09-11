package srg.net.optifine.entity.model;

import java.util.Map;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHead extends ModelAdapter {
  private ModelLayerLocation modelLayer;
  
  private SkullBlock.Types skullBlockType;
  
  public ModelAdapterHead(String name, ModelLayerLocation modelLayer, SkullBlock.Types skullBlockType) {
    super(BlockEntityType.SKULL, name, 0.0F);
    this.modelLayer = modelLayer;
    this.skullBlockType = skullBlockType;
  }
  
  public Model makeModel() {
    return (Model)new SkullModel(bakeModelLayer(this.modelLayer));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SkullModel))
      return null; 
    SkullModel modelSkul = (SkullModel)model;
    if (modelPart.equals("head"))
      return (ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkul, 1); 
    if (modelPart.equals("root"))
      return (ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkul, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.SKULL, index, () -> new SkullBlockRenderer(dispatcher.getContext()));
    if (!(renderer instanceof SkullBlockRenderer))
      return null; 
    Map<SkullBlock.Type, SkullModelBase> models = SkullBlockRenderer.models;
    if (models == null) {
      Config.warn("Field not found: SkullBlockRenderer.models");
      return null;
    } 
    models.put(this.skullBlockType, (SkullModelBase)modelBase);
    return (IEntityRenderer)renderer;
  }
}
