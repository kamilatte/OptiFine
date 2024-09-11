package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggy;
import ggz;
import ghf;
import net.optifine.Config;
import net.optifine.entity.model.ChestLargeModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterChestLarge extends ModelAdapter {
  public ModelAdapterChestLarge() {
    super(dqj.b, "chest_large", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new ChestLargeModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ChestLargeModel))
      return null; 
    ChestLargeModel modelChest = (ChestLargeModel)model;
    if (modelPart.equals("lid_left"))
      return modelChest.lid_left; 
    if (modelPart.equals("base_left"))
      return modelChest.base_left; 
    if (modelPart.equals("knob_left"))
      return modelChest.knob_left; 
    if (modelPart.equals("lid_right"))
      return modelChest.lid_right; 
    if (modelPart.equals("base_right"))
      return modelChest.base_right; 
    if (modelPart.equals("knob_right"))
      return modelChest.knob_right; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "lid_left", "base_left", "knob_left", "lid_right", "base_right", "knob_right" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.b, index, () -> new ghf(dispatcher.getContext()));
    if (!(renderer instanceof ghf))
      return null; 
    if (!(modelBase instanceof ChestLargeModel)) {
      Config.warn("Not a large chest model: " + String.valueOf(modelBase));
      return null;
    } 
    ChestLargeModel chestModel = (ChestLargeModel)modelBase;
    renderer = chestModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
