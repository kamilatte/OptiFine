package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fvk;
import fwg;
import fxz;
import fyj;
import fyk;
import gkh;
import gnj;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterWitch extends ModelAdapterVillager {
  public ModelAdapterWitch() {
    super(bsx.bo, "witch", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxz(bakeModelLayer(fyj.bV));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxz))
      return null; 
    fxz modelWitch = (fxz)model;
    if (modelPart.equals("mole"))
      return modelWitch.a().getChildModelDeep("mole"); 
    return super.getModelRenderer((fwg)modelWitch, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])Config.addObjectToArray((Object[])names, "mole");
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gnj render = new gnj(renderManager.getContext());
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
