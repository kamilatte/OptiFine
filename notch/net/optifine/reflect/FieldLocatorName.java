package notch.net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.Log;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.ReflectorClass;

public class FieldLocatorName implements IFieldLocator {
  private ReflectorClass reflectorClass = null;
  
  private String targetFieldName = null;
  
  public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
    this.reflectorClass = reflectorClass;
    this.targetFieldName = targetFieldName;
  }
  
  public Field getField() {
    Class cls = this.reflectorClass.getTargetClass();
    if (cls == null)
      return null; 
    try {
      Field targetField = getDeclaredField(cls, this.targetFieldName);
      targetField.setAccessible(true);
      return targetField;
    } catch (NoSuchFieldException e) {
      Log.log("(Reflector) Field not present: " + cls.getName() + "." + this.targetFieldName);
      return null;
    } catch (SecurityException e) {
      e.printStackTrace();
      return null;
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  private Field getDeclaredField(Class<Object> cls, String name) throws NoSuchFieldException {
    Field[] fields = cls.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      if (field.getName().equals(name))
        return field; 
    } 
    if (cls == Object.class)
      throw new NoSuchFieldException(name); 
    return getDeclaredField(cls.getSuperclass(), name);
  }
}
