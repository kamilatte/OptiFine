package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fup;
import fvk;
import fwg;
import fyj;
import fyk;
import gjm;
import gkh;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterBlaze extends ModelAdapter {
  public ModelAdapterBlaze() {
    super(bsx.i, "blaze", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fup(bakeModelLayer(fyj.m));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fup))
      return null; 
    fup modelBlaze = (fup)model;
    if (modelPart.equals("head"))
      return modelBlaze.a().getChildModelDeep("head"); 
    String PREFIX_STICK = "stick";
    if (modelPart.startsWith(PREFIX_STICK)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_STICK);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelBlaze.a().getChildModelDeep("part" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelBlaze.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", 
        "stick10", "stick11", "stick12", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjm render = new gjm(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
