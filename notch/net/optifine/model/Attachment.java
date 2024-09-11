package notch.net.optifine.model;

import com.google.gson.JsonObject;
import fbi;
import net.optifine.model.AttachmentType;
import net.optifine.util.Json;

public class Attachment {
  private AttachmentType type;
  
  private float[] translate;
  
  public Attachment(AttachmentType type, float[] translate) {
    this.type = type;
    this.translate = translate;
  }
  
  public AttachmentType getType() {
    return this.type;
  }
  
  public float[] getTranslate() {
    return this.translate;
  }
  
  public void applyTransform(fbi matrixStackIn) {
    if (this.translate[0] != 0.0F || this.translate[1] != 0.0F || this.translate[2] != 0.0F)
      matrixStackIn.a(this.translate[0], this.translate[1], this.translate[2]); 
  }
  
  public String toString() {
    return String.valueOf(this.type) + ", translate: " + String.valueOf(this.type);
  }
  
  public static net.optifine.model.Attachment parse(JsonObject jo, AttachmentType type) {
    if (jo == null)
      return null; 
    if (type == null)
      return null; 
    float[] translate = Json.parseFloatArray(jo.get(type.getName()), 3, null);
    if (translate == null)
      return null; 
    return new net.optifine.model.Attachment(type, translate);
  }
}
