package notch.net.optifine.entity.model;

import dqj;
import dup;
import fwg;
import fyj;
import fyk;
import ggy;
import ggz;
import ghj;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHangingSign extends ModelAdapter {
  private static Map<String, String> mapParts = makeMapParts();
  
  public ModelAdapterHangingSign() {
    super(dqj.i, "hanging_sign", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ghj.a(bakeModelLayer(fyj.b(dup.b)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ghj.a))
      return null; 
    ghj.a modelHangingSign = (ghj.a)model;
    if (mapParts.containsKey(modelPart)) {
      String name = mapParts.get(modelPart);
      return modelHangingSign.a.getChildModelDeep(name);
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
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.i, index, () -> new ghj(dispatcher.getContext()));
    if (!(renderer instanceof ghj))
      return null; 
    if (!Reflector.TileEntityHangingSignRenderer_hangingSignModels.exists()) {
      Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
      return null;
    } 
    Map<dup, ghj.a> hangingSignModels = (Map<dup, ghj.a>)Reflector.getFieldValue(renderer, Reflector.TileEntityHangingSignRenderer_hangingSignModels);
    if (hangingSignModels == null) {
      Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
      return null;
    } 
    if (hangingSignModels instanceof com.google.common.collect.ImmutableMap) {
      hangingSignModels = new HashMap<>(hangingSignModels);
      Reflector.TileEntityHangingSignRenderer_hangingSignModels.setValue(renderer, hangingSignModels);
    } 
    Collection<dup> types = new HashSet<>(hangingSignModels.keySet());
    for (dup type : types)
      hangingSignModels.put(type, (ghj.a)modelBase); 
    return (IEntityRenderer)renderer;
  }
}
