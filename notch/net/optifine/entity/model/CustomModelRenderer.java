package notch.net.optifine.entity.model;

import fyk;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomModelRenderer {
  private String modelPart;
  
  private boolean attach;
  
  private fyk modelRenderer;
  
  private ModelUpdater modelUpdater;
  
  public CustomModelRenderer(String modelPart, boolean attach, fyk modelRenderer, ModelUpdater modelUpdater) {
    this.modelPart = modelPart;
    this.attach = attach;
    this.modelRenderer = modelRenderer;
    this.modelUpdater = modelUpdater;
  }
  
  public fyk getModelRenderer() {
    return this.modelRenderer;
  }
  
  public String getModelPart() {
    return this.modelPart;
  }
  
  public boolean isAttach() {
    return this.attach;
  }
  
  public ModelUpdater getModelUpdater() {
    return this.modelUpdater;
  }
}
