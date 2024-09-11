package notch.net.optifine.entity.model;

import bsx;
import fwg;
import fwp;
import fyk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.entity.model.ModelAdapterBiped;

public abstract class ModelAdapterPlayer extends ModelAdapterBiped {
  protected ModelAdapterPlayer(bsx type, String name, float shadowSize) {
    super(type, name, shadowSize);
  }
  
  public fyk getModelRenderer(fwg model, String modelPart) {
    if (model instanceof fwp) {
      fwp playerModel = (fwp)model;
      if (modelPart.equals("left_sleeve"))
        return playerModel.b; 
      if (modelPart.equals("right_sleeve"))
        return playerModel.w; 
      if (modelPart.equals("left_pants"))
        return playerModel.x; 
      if (modelPart.equals("right_pants"))
        return playerModel.y; 
      if (modelPart.equals("jacket"))
        return playerModel.z; 
    } 
    return super.getModelRenderer(model, modelPart);
  }
  
  public String[] getModelRendererNames() {
    List<String> names = new ArrayList<>(Arrays.asList(super.getModelRendererNames()));
    names.add("left_sleeve");
    names.add("right_sleeve");
    names.add("left_pants");
    names.add("right_pants");
    names.add("jacket");
    return names.<String>toArray(new String[names.size()]);
  }
}
