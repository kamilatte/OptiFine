package notch.net.optifine.entity.model;

import bsx;
import com.google.common.collect.ImmutableList;
import cov;
import fgo;
import fwg;
import fww;
import fyj;
import fyk;
import gjn;
import gkh;
import gki;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;

public class ModelAdapterRaft extends ModelAdapter {
  public ModelAdapterRaft() {
    super(bsx.k, "raft", 0.5F);
  }
  
  protected ModelAdapterRaft(bsx entityType, String name, float shadowSize) {
    super(entityType, name, shadowSize);
  }
  
  public fwg makeModel() {
    return (fwg)new fww(bakeModelLayer(fyj.c(cov.b.i)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fww))
      return null; 
    fww modelRaft = (fww)model;
    ImmutableList<fyk> parts = modelRaft.c();
    if (parts != null) {
      if (modelPart.equals("bottom"))
        return ModelRendererUtils.getModelRenderer(parts, 0); 
      if (modelPart.equals("paddle_left"))
        return ModelRendererUtils.getModelRenderer(parts, 1); 
      if (modelPart.equals("paddle_right"))
        return ModelRendererUtils.getModelRenderer(parts, 2); 
    } 
    return null;
  }
  
  public String[] getModelRendererNames() {
    return new String[] { "bottom", "paddle_left", "paddle_right" };
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjn customRenderer = new gjn(renderManager.getContext(), false);
    gki rendererCached = rendererCache.get(bsx.k, index, () -> customRenderer);
    if (!(rendererCached instanceof gjn)) {
      Config.warn("Not a BoatRender: " + String.valueOf(rendererCached));
      return null;
    } 
    gjn renderer = (gjn)rendererCached;
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
