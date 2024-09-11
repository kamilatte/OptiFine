package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggy;
import ggz;
import ghf;
import net.optifine.Config;
import net.optifine.entity.model.ChestModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterTrappedChest extends ModelAdapter {
  public ModelAdapterTrappedChest() {
    super(dqj.c, "trapped_chest", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ChestModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ChestModel))
      return null; 
    ChestModel modelChest = (ChestModel)model;
    if (modelPart.equals("lid"))
      return modelChest.lid; 
    if (modelPart.equals("base"))
      return modelChest.base; 
    if (modelPart.equals("knob"))
      return modelChest.knob; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "lid", "base", "knob" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.c, index, () -> new ghf(dispatcher.getContext()));
    if (!(renderer instanceof ghf))
      return null; 
    if (!(modelBase instanceof ChestModel)) {
      Config.warn("Not a chest model: " + String.valueOf(modelBase));
      return null;
    } 
    ChestModel chestModel = (ChestModel)modelBase;
    renderer = chestModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
