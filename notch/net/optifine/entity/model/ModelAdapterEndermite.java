package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvj;
import fvk;
import fwg;
import fyj;
import fyk;
import gkg;
import gkh;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterEndermite extends ModelAdapter {
  public ModelAdapterEndermite() {
    super(bsx.I, "endermite", 0.3F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvj(bakeModelLayer(fyj.Z));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvj))
      return null; 
    fvj modelEnderMite = (fvj)model;
    String PREFIX_BODY = "body";
    if (modelPart.startsWith(PREFIX_BODY)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelEnderMite.a().getChildModelDeep("segment" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelEnderMite.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body1", "body2", "body3", "body4", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkg render = new gkg(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
