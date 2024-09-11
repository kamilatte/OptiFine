package notch.net.optifine.entity.model;

import bsx;
import com.google.common.collect.ImmutableList;
import cov;
import fgo;
import fuw;
import fwg;
import fyj;
import fyk;
import gjn;
import gkh;
import gki;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.entity.model.RendererCache;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestBoat extends ModelAdapterBoat {
  public ModelAdapterChestBoat() {
    super(bsx.r, "chest_boat", 0.5F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuw(bakeModelLayer(fyj.d(cov.b.a)));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuw))
      return null; 
    fuw modelChestBoat = (fuw)model;
    ImmutableList<fyk> parts = modelChestBoat.b();
    if (parts != null) {
      if (modelPart.equals("chest_base"))
        return ModelRendererUtils.getModelRenderer(parts, 7); 
      if (modelPart.equals("chest_lid"))
        return ModelRendererUtils.getModelRenderer(parts, 8); 
      if (modelPart.equals("chest_knob"))
        return ModelRendererUtils.getModelRenderer(parts, 9); 
    } 
    return super.getModelRenderer((fwg)modelChestBoat, modelPart);
  }
  
  public String[] getModelRendererNames() {
    String[] names = super.getModelRendererNames();
    names = (String[])ArrayUtils.addObjectsToArray((Object[])names, (Object[])new String[] { "chest_base", "chest_lid", "chest_knob" });
    return names;
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjn renderer = new gjn(renderManager.getContext(), true);
    rendererCache.put(bsx.r, index, (gki)renderer);
    return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
  }
}
