package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fyk;
import gkh;
import gmx;
import java.util.ArrayList;
import java.util.HashMap;
import net.optifine.entity.model.ArrowModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterArrow extends ModelAdapter {
  public ModelAdapterArrow() {
    super(bsx.e, "arrow", 0.0F);
  }
  
  protected ModelAdapterArrow(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new ArrowModel(new fyk(new ArrayList(), new HashMap<>()));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof ArrowModel))
      return null; 
    ArrowModel modelArrow = (ArrowModel)model;
    if (modelPart.equals("body"))
      return modelArrow.body; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmx render = new gmx(renderManager.getContext());
    render.model = (ArrowModel)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
