package notch.net.optifine.model;

import fbi;
import fyk;
import net.optifine.model.Attachment;

public class AttachmentPath {
  private Attachment attachment;
  
  private fyk[] modelParts;
  
  public AttachmentPath(Attachment attachment, fyk[] modelParts) {
    this.attachment = attachment;
    this.modelParts = modelParts;
  }
  
  public Attachment getAttachment() {
    return this.attachment;
  }
  
  public fyk[] getModelParts() {
    return this.modelParts;
  }
  
  public boolean isVisible() {
    for (int i = 0; i < this.modelParts.length; i++) {
      fyk modelPart = this.modelParts[i];
      if (!modelPart.k)
        return false; 
    } 
    return true;
  }
  
  public void applyTransform(fbi matrixStackIn) {
    for (int i = 0; i < this.modelParts.length; i++) {
      fyk modelPart = this.modelParts[i];
      modelPart.a(matrixStackIn);
    } 
    this.attachment.applyTransform(matrixStackIn);
  }
  
  public String toString() {
    return "attachment: " + String.valueOf(this.attachment.getType()) + ", parents: " + this.modelParts.length;
  }
}
