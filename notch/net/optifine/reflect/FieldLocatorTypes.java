package notch.net.optifine.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.optifine.Log;
import net.optifine.reflect.IFieldLocator;

public class FieldLocatorTypes implements IFieldLocator {
  private Field field = null;
  
  public FieldLocatorTypes(Class cls, Class[] preTypes, Class<?> type, Class[] postTypes, String errorName) {
    Field[] fs = cls.getDeclaredFields();
    List<Class<?>> types = new ArrayList<>();
    for (int i = 0; i < fs.length; i++) {
      Field field = fs[i];
      types.add(field.getType());
    } 
    List<Class<?>> typesMatch = new ArrayList<>();
    typesMatch.addAll(Arrays.asList(preTypes));
    typesMatch.add(type);
    typesMatch.addAll(Arrays.asList(postTypes));
    int index = Collections.indexOfSubList(types, typesMatch);
    if (index < 0) {
      Log.log("(Reflector) Field not found: " + errorName);
      return;
    } 
    int index2 = Collections.indexOfSubList(types.subList(index + 1, types.size()), typesMatch);
    if (index2 >= 0) {
      Log.log("(Reflector) More than one match found for field: " + errorName);
      return;
    } 
    int indexField = index + preTypes.length;
    this.field = fs[indexField];
  }
  
  public Field getField() {
    return this.field;
  }
}
