package notch.net.optifine.entity.model;

import akr;
import bsx;
import fgo;
import fvk;
import fwg;
import fxi;
import fyj;
import fyk;
import gkh;
import gki;
import gmf;
import gmm;
import gov;
import gpa;
import java.util.List;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterSlimeOuter extends ModelAdapter {
  public ModelAdapterSlimeOuter() {
    super(bsx.aP, "slime_outer", 0.25F);
  }
  
  public fwg makeModel() {
    return (fwg)new fxi(bakeModelLayer(fyj.bu));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fxi))
      return null; 
    fxi modelSlime = (fxi)model;
    if (modelPart.equals("body"))
      return modelSlime.a().getChildModelDeep("cube"); 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "body" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gmm customRenderer = new gmm(renderManager.getContext());
    customRenderer.g = (fvk)new fxi(bakeModelLayer(fyj.bu));
    customRenderer.e = 0.25F;
    gki render = rendererCache.get(bsx.aP, index, () -> customRenderer);
    if (!(render instanceof gmm)) {
      Config.warn("Not a SlimeRenderer: " + String.valueOf(render));
      return null;
    } 
    gmm renderSlime = (gmm)render;
    gpa layer = new gpa((gmf)renderSlime, renderManager.getContext().f());
    layer.a = (fvk)modelBase;
    renderSlime.removeLayers(gpa.class);
    renderSlime.a((gov)layer);
    return (IEntityRenderer)renderSlime;
  }
  
  public boolean setTextureLocation(IEntityRenderer er, akr textureLocation) {
    gmm renderer = (gmm)er;
    List<gpa> layers = renderer.getLayers(gpa.class);
    for (gpa layer : layers)
      layer.customTextureLocation = textureLocation; 
    return true;
  }
}
