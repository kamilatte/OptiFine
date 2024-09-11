package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvo;
import fwg;
import fyj;
import fyk;
import gkh;
import gkt;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.StrUtils;

public class ModelAdapterGhast extends ModelAdapter {
  public ModelAdapterGhast() {
    super(bsx.T, "ghast", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvo(bakeModelLayer(fyj.ah));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvo))
      return null; 
    fvo modelGhast = (fvo)model;
    if (modelPart.equals("body"))
      return modelGhast.a().getChildModelDeep("body"); 
    String PREFIX_TENTACLE = "tentacle";
    if (modelPart.startsWith(PREFIX_TENTACLE)) {
      String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
      int index = Config.parseInt(numStr, -1);
      int indexPart = index - 1;
      return modelGhast.a().getChildModelDeep("tentacle" + indexPart);
    } 
    if (modelPart.equals("root"))
      return modelGhast.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { 
        "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9", 
        "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gkt render = new gkt(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
