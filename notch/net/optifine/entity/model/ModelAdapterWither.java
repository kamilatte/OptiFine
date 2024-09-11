package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fya;
import fyj;
import fyk;
import gkh;
import gnk;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWither extends ModelAdapter {
  public ModelAdapterWither() {
    super(bsx.bp, "wither", 0.5F);
  }
  
  public ModelAdapterWither(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fya(bakeModelLayer(fyj.bW));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fya))
      return null; 
    fya modelWither = (fya)model;
    if (modelPart.equals("body1"))
      return modelWither.a().getChildModelDeep("shoulders"); 
    if (modelPart.equals("body2"))
      return modelWither.a().getChildModelDeep("ribcage"); 
    if (modelPart.equals("body3"))
      return modelWither.a().getChildModelDeep("tail"); 
    if (modelPart.equals("head1"))
      return modelWither.a().getChildModelDeep("center_head"); 
    if (modelPart.equals("head2"))
      return modelWither.a().getChildModelDeep("right_head"); 
    if (modelPart.equals("head3"))
      return modelWither.a().getChildModelDeep("left_head"); 
    if (modelPart.equals("root"))
      return modelWither.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body1", "body2", "body3", "head1", "head2", "head3", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnk render = new gnk(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
