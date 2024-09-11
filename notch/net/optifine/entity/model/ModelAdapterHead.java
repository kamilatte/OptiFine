package notch.net.optifine.entity.model;

import dnb;
import dqj;
import fwg;
import fxg;
import fxh;
import fyi;
import fyk;
import ggy;
import ggz;
import gho;
import java.util.Map;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterHead extends ModelAdapter {
  private fyi modelLayer;
  
  private dnb.b skullBlockType;
  
  public ModelAdapterHead(String name, fyi modelLayer, dnb.b skullBlockType) {
    super(dqj.p, name, 0.0F);
    this.modelLayer = modelLayer;
    this.skullBlockType = skullBlockType;
  }
  
  public fwg makeModel() {
    return (fwg)new fxg(bakeModelLayer(this.modelLayer));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxg))
      return null; 
    fxg modelSkul = (fxg)model;
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelSkull_renderers.getValue(modelSkul, 1); 
    if (modelPart.equals("root"))
      return (fyk)Reflector.ModelSkull_renderers.getValue(modelSkul, 0); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.p, index, () -> new gho(dispatcher.getContext()));
    if (!(renderer instanceof gho))
      return null; 
    Map<dnb.a, fxh> models = gho.models;
    if (models == null) {
      Config.warn("Field not found: SkullBlockRenderer.models");
      return null;
    } 
    models.put(this.skullBlockType, (fxh)modelBase);
    return (IEntityRenderer)renderer;
  }
}
