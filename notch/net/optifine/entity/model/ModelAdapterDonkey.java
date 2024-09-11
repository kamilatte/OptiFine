package notch.net.optifine.entity.model;

import bsx;
import fgo;
import fuy;
import fvk;
import fwg;
import fyj;
import fyk;
import gjt;
import gkh;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.RendererCache;
import net.optifine.reflect.Reflector;

public class ModelAdapterDonkey extends ModelAdapterHorse {
  public ModelAdapterDonkey() {
    super(bsx.z, "donkey", 0.75F);
  }
  
  public fwg makeModel() {
    return (fwg)new fuy(bakeModelLayer(fyj.O));
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (!(model instanceof fuy))
      return null; 
    fuy modelHorseChests = (fuy)model;
    if (modelPart.equals("left_chest"))
      return (fyk)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 0); 
    if (modelPart.equals("right_chest"))
      return (fyk)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 1); 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> list = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    list.add("left_chest");
    list.add("right_chest");
    return list.<String>toArray(new String[list.size()]);
  }
  
  public IEntityRenderer makeEntityRender(fwg modelBase, float shadowSize, RendererCache rendererCache, int index) {
    gkh renderManager = fgo.Q().ap();
    gjt render = new gjt(renderManager.getContext(), 0.87F, fyj.O);
    render.g = (fvk)modelBase;
    render.e = shadowSize;
    return (IEntityRenderer)render;
  }
}
