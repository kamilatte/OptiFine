package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwa;
import fwg;
import fyj;
import fyk;
import gkh;
import gln;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterMagmaCube extends ModelAdapter {
  public ModelAdapterMagmaCube() {
    super(bsx.ap, "magma_cube", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fwa(bakeModelLayer(fyj.aB));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fwa))
      return null; 
    fwa modelMagmaCube = (fwa)model;
    if (modelPart.equals("core"))
      return modelMagmaCube.a().getChildModelDeep("inside_cube"); 
    String PREFIX_SEGMENT = "segment";
    if (modelPart.startsWith(PREFIX_SEGMENT)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_SEGMENT);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelMagmaCube.a().getChildModelDeep("cube" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelMagmaCube.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gln render = new gln(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
