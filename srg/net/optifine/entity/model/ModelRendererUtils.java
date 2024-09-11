package srg.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import net.minecraft.client.model.geom.ModelPart;

public class ModelRendererUtils {
  public static ModelPart getModelRenderer(Iterator<ModelPart> iterator, int index) {
    if (iterator == null)
      return null; 
    if (index < 0)
      return null; 
    for (int i = 0; i < index; i++) {
      if (!iterator.hasNext())
        return null; 
      ModelPart modelPart = iterator.next();
    } 
    if (!iterator.hasNext())
      return null; 
    ModelPart model = iterator.next();
    return model;
  }
  
  public static ModelPart getModelRenderer(ImmutableList<ModelPart> models, int index) {
    if (models == null)
      return null; 
    if (index < 0)
      return null; 
    if (index >= models.size())
      return null; 
    return (ModelPart)models.get(index);
  }
}
