package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import gkh;
import gmy;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecartTnt extends ModelAdapterMinecart {
  public ModelAdapterMinecartTnt() {
    super(bsx.bd, "tnt_minecart", 0.5F);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmy render = new gmy(renderManager.getContext());
    if (!Reflector.RenderMinecart_modelMinecart.exists()) {
      Config.warn("Field not found: RenderMinecart.modelMinecart");
      return null;
    } 
    Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
