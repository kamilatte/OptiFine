package notch.net.optifine.entity.model;

import dqj;
import dup;
import fwg;
import fyj;
import fyk;
import ggy;
import ggz;
import ghn;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
  public ModelAdapterSign() {
    super(dqj.h, "sign", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ghn.a(bakeModelLayer(fyj.a(dup.b)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ghn.a))
      return null; 
    ghn.a modelSign = (ghn.a)model;
    if (modelPart.equals("board"))
      return modelSign.a.getChildModelDeep("sign"); 
    if (modelPart.equals("stick"))
      return modelSign.a.getChildModelDeep("stick"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "board", "stick" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.h, index, () -> new ghn(dispatcher.getContext()));
    if (!(renderer instanceof ghn))
      return null; 
    if (!Reflector.TileEntitySignRenderer_signModels.exists()) {
      Config.warn("Field not found: TileEntitySignRenderer.signModels");
      return null;
    } 
    Map<dup, ghn.a> signModels = (Map<dup, ghn.a>)Reflector.getFieldValue(renderer, Reflector.TileEntitySignRenderer_signModels);
    if (signModels == null) {
      Config.warn("Field not found: TileEntitySignRenderer.signModels");
      return null;
    } 
    if (signModels instanceof com.google.common.collect.ImmutableMap) {
      signModels = new HashMap<>(signModels);
      Reflector.TileEntitySignRenderer_signModels.setValue(renderer, signModels);
    } 
    Collection<dup> types = new HashSet<>(signModels.keySet());
    for (dup type : types)
      signModels.put(type, (ghn.a)modelBase); 
    return (IEntityRenderer)renderer;
  }
}
