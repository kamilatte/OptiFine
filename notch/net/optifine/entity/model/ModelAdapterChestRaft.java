package notch.net.optifine.entity.model;

import bsx;
import com.google.common.collect.ImmutableList;
import cov;
import fgo;
import fux;
import fwg;
import fyj;
import fyk;
import gjn;
import gkh;
import gki;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterRaft;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestRaft extends ModelAdapterRaft {
  public ModelAdapterChestRaft() {
    super(bsx.r, "chest_raft", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fux(bakeModelLayer(fyj.d(cov.b.i)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fux))
      return null; 
    fux modelChestRaft = (fux)model;
    ImmutableList<fyk> parts = modelChestRaft.c();
    if (parts != null) {
      if (modelPart.equals("chest_base"))
        return ModelRendererUtils.getModelRenderer(parts, 3); 
      if (modelPart.equals("chest_lid"))
        return ModelRendererUtils.getModelRenderer(parts, 4); 
      if (modelPart.equals("chest_knob"))
        return ModelRendererUtils.getModelRenderer(parts, 5); 
    } 
    return super.getModelRenderer((fwg)modelChestRaft, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])ArrayUtils.addObjectsToArray((Object[])names, (Object[])new String[] { "chest_base", "chest_lid", "chest_knob" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjn customRenderer = new gjn(renderManager.getContext(), true);
    gki rendererCached = rendererCache.get(bsx.r, index, () -> customRenderer);
    if (!(rendererCached instanceof gjn)) {
      Config.warn("Not a BoatRender: " + String.valueOf(rendererCached));
      return null;
    } 
    gjn renderer = (gjn)rendererCached;
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
