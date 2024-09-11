package notch.net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import fyk;
import java.util.Iterator;

public class ModelRendererUtils {
  public static fyk getModelRenderer(Iterator<fyk> iterator, int index) {
    if (iterator == null)
      return null; 
    if (index < 0)
      return null; 
    for (int i = 0; i < index; i++) {
      if (!iterator.hasNext())
        return null; 
      fyk fyk = iterator.next();
    } 
    if (!iterator.hasNext())
      return null; 
    fyk model = iterator.next();
    return model;
  }
  
  public static fyk getModelRenderer(ImmutableList<fyk> models, int index) {
    if (models == null)
      return null; 
    if (index < 0)
      return null; 
    if (index >= models.size())
      return null; 
    return (fyk)models.get(index);
  }
}
