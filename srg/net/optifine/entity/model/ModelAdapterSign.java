package srg.net.optifine.entity.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
  public ModelAdapterSign() {
    super(BlockEntityType.SIGN, "sign", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new SignRenderer.SignModel(bakeModelLayer(ModelLayers.createSignModelName(WoodType.OAK)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof SignRenderer.SignModel))
      return null; 
    SignRenderer.SignModel modelSign = (SignRenderer.SignModel)model;
    if (modelPart.equals("board"))
      return modelSign.root.getChildModelDeep("sign"); 
    if (modelPart.equals("stick"))
      return modelSign.root.getChildModelDeep("stick"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "board", "stick" };
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.SIGN, index, () -> new SignRenderer(dispatcher.getContext()));
    if (!(renderer instanceof SignRenderer))
      return null; 
    if (!Reflector.TileEntitySignRenderer_signModels.exists()) {
      Config.warn("Field not found: TileEntitySignRenderer.signModels");
      return null;
    } 
    Map<WoodType, SignRenderer.SignModel> signModels = (Map<WoodType, SignRenderer.SignModel>)Reflector.getFieldValue(renderer, Reflector.TileEntitySignRenderer_signModels);
    if (signModels == null) {
      Config.warn("Field not found: TileEntitySignRenderer.signModels");
      return null;
    } 
    if (signModels instanceof com.google.common.collect.ImmutableMap) {
      signModels = new HashMap<>(signModels);
      Reflector.TileEntitySignRenderer_signModels.setValue(renderer, signModels);
    } 
    Collection<WoodType> types = new HashSet<>(signModels.keySet());
    for (WoodType type : types)
      signModels.put(type, (SignRenderer.SignModel)modelBase); 
    return (IEntityRenderer)renderer;
  }
}
