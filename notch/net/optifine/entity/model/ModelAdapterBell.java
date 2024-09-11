package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggx;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.entity.model.BellModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBell extends ModelAdapter {
  public ModelAdapterBell() {
    super(dqj.E, "bell", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new BellModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof BellModel))
      return null; 
    BellModel modelBell = (BellModel)model;
    if (modelPart.equals("body"))
      return modelBell.bellBody; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(fwg model, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.E, index, () -> new ggx(dispatcher.getContext()));
    if (!(renderer instanceof ggx))
      return null; 
    if (!(model instanceof BellModel)) {
      Config.warn("Not a bell model: " + String.valueOf(model));
      return null;
    } 
    BellModel bellModel = (BellModel)model;
    renderer = bellModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
