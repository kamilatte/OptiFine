package notch.net.optifine.entity.model;

import dqj;
import fwg;
import fxd;
import fyj;
import fyk;
import ggy;
import ggz;
import ghm;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox extends ModelAdapter {
  public ModelAdapterShulkerBox() {
    super(dqj.x, "shulker_box", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxd(bakeModelLayer(fyj.bl));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxd))
      return null; 
    fxd modelShulker = (fxd)model;
    if (modelPart.equals("base"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0); 
    if (modelPart.equals("lid"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1); 
    if (modelPart.equals("head"))
      return (fyk)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "base", "lid", "head" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.x, index, () -> new ghm(dispatcher.getContext()));
    if (!(renderer instanceof ghm))
      return null; 
    if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
      Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
      return null;
    } 
    Reflector.setFieldValue(renderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
    return (IEntityRenderer)renderer;
  }
}
