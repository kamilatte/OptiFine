package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fwg;
import fyk;
import gkd;
import gkh;
import gki;
import net.optifine.Config;
import net.optifine.entity.model.EnderCrystalModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterEnderCrystal extends ModelAdapter {
  public ModelAdapterEnderCrystal() {
    this("end_crystal");
  }
  
  protected ModelAdapterEnderCrystal(String name) {
    super(bsx.E, name, 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new EnderCrystalModel();
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof EnderCrystalModel))
      return null; 
    EnderCrystalModel modelEnderCrystal = (EnderCrystalModel)model;
    if (modelPart.equals("cube"))
      return modelEnderCrystal.cube; 
    if (modelPart.equals("glass"))
      return modelEnderCrystal.glass; 
    if (modelPart.equals("base"))
      return modelEnderCrystal.base; 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "cube", "glass", "base" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gki renderObj = rendererCache.get(bsx.E, index, () -> new gkd(renderManager.getContext()));
    if (!(renderObj instanceof gkd)) {
      Config.warn("Not an instance of RenderEnderCrystal: " + String.valueOf(renderObj));
      return null;
    } 
    gkd render = (gkd)renderObj;
    if (!(modelBase instanceof EnderCrystalModel)) {
      Config.warn("Not a EnderCrystalModel model: " + String.valueOf(modelBase));
      return null;
    } 
    EnderCrystalModel enderCrystalModel = (EnderCrystalModel)modelBase;
    render = enderCrystalModel.updateRenderer(render);
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
