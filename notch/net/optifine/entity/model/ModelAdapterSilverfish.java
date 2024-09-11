package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxe;
import fyj;
import fyk;
import gkh;
import gmk;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterSilverfish extends ModelAdapter {
  public ModelAdapterSilverfish() {
    super(bsx.aM, "silverfish", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxe(bakeModelLayer(fyj.bn));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxe))
      return null; 
    fxe modelSilverfish = (fxe)model;
    String PREFIX_BODY = "body";
    if (modelPart.startsWith(PREFIX_BODY)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSilverfish.a().getChildModelDeep("segment" + indexPart);
    } 
    String PREFIX_WINGS = "wing";
    if (modelPart.startsWith(PREFIX_WINGS)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_WINGS);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelSilverfish.a().getChildModelDeep("layer" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelSilverfish.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3", 
        "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmk render = new gmk(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
