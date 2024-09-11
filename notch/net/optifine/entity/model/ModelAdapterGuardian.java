package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvr;
import fwg;
import fyj;
import fyk;
import gkh;
import gkx;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterGuardian extends ModelAdapter {
  public ModelAdapterGuardian() {
    super(bsx.Y, "guardian", 0.5F);
  }
  
  public ModelAdapterGuardian(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fvr(bakeModelLayer(fyj.an));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvr))
      return null; 
    fvr modelGuardian = (fvr)model;
    if (modelPart.equals("body"))
      return modelGuardian.a().getChildModelDeep("head"); 
    if (modelPart.equals("eye"))
      return modelGuardian.a().getChildModelDeep("eye"); 
    String PREFIX_SPINE = "spine";
    if (modelPart.startsWith(PREFIX_SPINE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_SPINE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGuardian.a().getChildModelDeep("spike" + indexPart);
    } 
    String PREFIX_TAIL = "tail";
    if (modelPart.startsWith(PREFIX_TAIL)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TAIL);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGuardian.a().getChildModelDeep("tail" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelGuardian.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "eye", "spine1", "spine2", "spine3", "spine4", "spine5", "spine6", "spine7", "spine8", 
        "spine9", "spine10", "spine11", "spine12", "tail1", "tail2", "tail3", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkx render = new gkx(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
