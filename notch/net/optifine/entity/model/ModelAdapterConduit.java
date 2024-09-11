package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggy;
import ggz;
import ghg;
import net.optifine.Config;
import net.optifine.entity.model.ConduitModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterConduit extends ModelAdapter {
  public ModelAdapterConduit() {
    super(dqj.z, "conduit", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ConduitModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ConduitModel))
      return null; 
    ConduitModel modelConduit = (ConduitModel)model;
    if (modelPart.equals("eye"))
      return modelConduit.eye; 
    if (modelPart.equals("wind"))
      return modelConduit.wind; 
    if (modelPart.equals("base"))
      return modelConduit.base; 
    if (modelPart.equals("cage"))
      return modelConduit.cage; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "eye", "wind", "base", "cage" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.z, index, () -> new ghg(dispatcher.getContext()));
    if (!(renderer instanceof ghg))
      return null; 
    if (!(modelBase instanceof ConduitModel)) {
      Config.warn("Not a conduit model: " + String.valueOf(modelBase));
      return null;
    } 
    ConduitModel conduitModel = (ConduitModel)modelBase;
    renderer = conduitModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
