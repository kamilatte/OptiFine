package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fyk;
import ggy;
import ggz;
import ghh;
import net.optifine.Config;
import net.optifine.entity.model.DecoratedPotModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterDecoratedPot extends ModelAdapter {
  public ModelAdapterDecoratedPot() {
    super(dqj.O, "decorated_pot", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new DecoratedPotModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof DecoratedPotModel))
      return null; 
    DecoratedPotModel modelDecoratedPot = (DecoratedPotModel)model;
    if (modelPart.equals("neck"))
      return modelDecoratedPot.neck; 
    if (modelPart.equals("front"))
      return modelDecoratedPot.frontSide; 
    if (modelPart.equals("back"))
      return modelDecoratedPot.backSide; 
    if (modelPart.equals("left"))
      return modelDecoratedPot.leftSide; 
    if (modelPart.equals("right"))
      return modelDecoratedPot.rightSide; 
    if (modelPart.equals("top"))
      return modelDecoratedPot.top; 
    if (modelPart.equals("bottom"))
      return modelDecoratedPot.bottom; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "neck", "front", "back", "left", "right", "top", "bottom" };
  }
  
  public IEntityRenderer makeEntityRender(fwg model, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.O, index, () -> new ghh(dispatcher.getContext()));
    if (!(renderer instanceof ghh))
      return null; 
    if (!(model instanceof DecoratedPotModel)) {
      Config.warn("Not a decorated pot model: " + String.valueOf(model));
      return null;
    } 
    DecoratedPotModel decoratedPotModel = (DecoratedPotModel)model;
    renderer = decoratedPotModel.updateRenderer(renderer);
    return (IEntityRenderer)renderer;
  }
}
