package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxm;
import fyj;
import fyk;
import gkh;
import gmr;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterSquid extends ModelAdapter {
  public ModelAdapterSquid() {
    super(bsx.aX, "squid", 0.7F);
  }
  
  protected ModelAdapterSquid(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fxm(bakeModelLayer(fyj.bz));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxm))
      return null; 
    fxm modelSquid = (fxm)model;
    if (modelPart.equals("body"))
      return modelSquid.a().getChildModelDeep("body"); 
    String PREFIX_TENTACLE = "tentacle";
    if (modelPart.startsWith(PREFIX_TENTACLE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSquid.a().getChildModelDeep("tentacle" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelSquid.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmr render = new gmr(renderManager.getContext(), (fxm)modelBase);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
