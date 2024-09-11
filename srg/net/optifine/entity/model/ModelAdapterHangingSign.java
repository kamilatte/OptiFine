package srg.net.optifine.entity.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHangingSign extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterHangingSign() {
    super(BlockEntityType.HANGING_SIGN, "hanging_sign", 0.0F);
  }
  
  public Model makeModel() {
    return (Model)new HangingSignRenderer.HangingSignModel(bakeModelLayer(ModelLayers.createHangingSignModelName(WoodType.OAK)));
  }
  
  public ModelPart getModelRenderer(Model model, String modelPart) {
    if (!(model instanceof HangingSignRenderer.HangingSignModel))
      return null; 
    HangingSignRenderer.HangingSignModel modelHangingSign = (HangingSignRenderer.HangingSignModel)model;
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelHangingSign.root.getChildModelDeep(name);
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    String[] names = (String[])mapParts.keySet().toArray((Object[])new String[0]);
    return names;
  }
  
  private static Map<String, String> makeMapParts() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("board", "board");
    map.put("plank", "plank");
    map.put("chains", "normalChains");
    map.put("chain_left1", "chainL1");
    map.put("chain_left2", "chainL2");
    map.put("chain_right1", "chainR1");
    map.put("chain_right2", "chainR2");
    map.put("chains_v", "vChains");
    return map;
  }
  
  public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
    BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().getBlockEntityRenderDispatcher();
    BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.HANGING_SIGN, index, () -> new HangingSignRenderer(dispatcher.getContext()));
    if (!(renderer instanceof HangingSignRenderer))
      return null; 
    if (!Reflector.TileEntityHangingSignRenderer_hangingSignModels.exists()) {
      Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
      return null;
    } 
    Map<WoodType, HangingSignRenderer.HangingSignModel> hangingSignModels = (Map<WoodType, HangingSignRenderer.HangingSignModel>)Reflector.getFieldValue(renderer, Reflector.TileEntityHangingSignRenderer_hangingSignModels);
    if (hangingSignModels == null) {
      Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
      return null;
    } 
    if (hangingSignModels instanceof com.google.common.collect.ImmutableMap) {
      hangingSignModels = new HashMap<>(hangingSignModels);
      Reflector.TileEntityHangingSignRenderer_hangingSignModels.setValue(renderer, hangingSignModels);
    } 
    Collection<WoodType> types = new HashSet<>(hangingSignModels.keySet());
    for (WoodType type : types)
      hangingSignModels.put(type, (HangingSignRenderer.HangingSignModel)modelBase); 
    return (IEntityRenderer)renderer;
  }
}
