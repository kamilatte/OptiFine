package notch.net.optifine.entity.model;

import dqj;
import fus;
import fwg;
import fyj;
import ggy;
import ggz;
import ghk;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBook;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterBookLectern extends ModelAdapterBook {
  public ModelAdapterBookLectern() {
    super(dqj.D, "lectern_book", 0.0F);
  }
  
  public fwg makeModel() {
    return (fwg)new fus(bakeModelLayer(fyj.r));
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    ggy dispatcher = Config.getMinecraft().aq();
    ggz renderer = rendererCache.get(dqj.D, index, () -> new ghk(dispatcher.getContext()));
    if (!(renderer instanceof ghk))
      return null; 
    if (!Reflector.TileEntityLecternRenderer_modelBook.exists()) {
      Config.warn("Field not found: TileEntityLecternRenderer.modelBook");
      return null;
    } 
    Reflector.setFieldValue(renderer, Reflector.TileEntityLecternRenderer_modelBook, modelBase);
    return (IEntityRenderer)renderer;
  }
}
