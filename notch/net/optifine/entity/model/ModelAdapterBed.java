package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggw;
import ggy;
import ggz;
import net.optifine.Config;
import net.optifine.entity.model.BedModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterBed extends ModelAdapter {
  public ModelAdapterBed() {
    super(dqj.y, "bed", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new BedModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof BedModel))
      return null; 
    BedModel modelBed = (BedModel)model;
    if (modelPart.equals("head"))
      return modelBed.headPiece; 
    if (modelPart.equals("foot"))
      return modelBed.footPiece; 
    fyk[] legs = modelBed.legs;
    if (legs != null) {
      if (modelPart.equals("leg1"))
        return legs[0]; 
      if (modelPart.equals("leg2"))
        return legs[1]; 
      if (modelPart.equals("leg3"))
        return legs[2]; 
      if (modelPart.equals("leg4"))
        return legs[3]; 
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "foot", "leg1", "leg2", "leg3", "leg4" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.y, index, () -> new ggw(dispatcher.getContext()));
    if (!(renderer instanceof ggw))
      return null; 
    if (!(modelBase instanceof BedModel)) {
      Config.warn("Not a BedModel: " + String.valueOf(modelBase));
      return null;
    } 
    BedModel bedModel = (BedModel)modelBase;
    renderer = bedModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
