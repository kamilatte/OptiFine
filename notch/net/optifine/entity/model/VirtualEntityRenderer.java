package notch.net.optifine.entity.model;

import akr;
import bsx;
import dqj;
import fwg;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public class VirtualEntityRenderer implements IEntityRenderer {
  private fwg model;
  
  private Either<bsx, dqj> type;
  
  private akr locationTextureCustom;
  
  public VirtualEntityRenderer(fwg model) {
    this.model = model;
  }
  
  public fwg getModel() {
    return this.model;
  }
  
  public Either<bsx, dqj> getType() {
    return this.type;
  }
  
  public void setType(Either<bsx, dqj> type) {
    this.type = type;
  }
  
  public akr getLocationTextureCustom() {
    return this.locationTextureCustom;
  }
  
  public void setLocationTextureCustom(akr locationTextureCustom) {
    this.locationTextureCustom = locationTextureCustom;
  }
}
