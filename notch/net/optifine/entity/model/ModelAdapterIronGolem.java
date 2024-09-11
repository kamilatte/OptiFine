package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fvz;
import fwg;
import fyj;
import fyk;
import gkh;
import gle;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterIronGolem extends ModelAdapter {
  public ModelAdapterIronGolem() {
    super(bsx.af, "iron_golem", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fvz(bakeModelLayer(fyj.aw));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fvz))
      return null; 
    fvz modelIronGolem = (fvz)model;
    if (modelPart.equals("head"))
      return modelIronGolem.a().b("head"); 
    if (modelPart.equals("body"))
      return modelIronGolem.a().b("body"); 
    if (modelPart.equals("right_arm"))
      return modelIronGolem.a().b("right_arm"); 
    if (modelPart.equals("left_arm"))
      return modelIronGolem.a().b("left_arm"); 
    if (modelPart.equals("left_leg"))
      return modelIronGolem.a().b("left_leg"); 
    if (modelPart.equals("right_leg"))
      return modelIronGolem.a().b("right_leg"); 
    if (modelPart.equals("root"))
      return modelIronGolem.a(); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "head", "body", "right_arm", "left_arm", "left_leg", "right_leg", "root" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gle render = new gle(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
