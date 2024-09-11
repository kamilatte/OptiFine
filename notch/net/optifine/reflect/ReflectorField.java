package notch.net.optifine.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.optifine.reflect.FieldLocatorFixed;
import net.optifine.reflect.FieldLocatorName;
import net.optifine.reflect.FieldLocatorType;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.util.UnsafeUtils;

public class ReflectorField implements IResolvable {
  private IFieldLocator fieldLocator = null;
  
  private boolean checked = false;
  
  private Field targetField = null;
  
  public ReflectorField(ReflectorClass reflectorClass, String targetFieldName) {
    this((IFieldLocator)new FieldLocatorName(reflectorClass, targetFieldName));
  }
  
  public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType) {
    this(reflectorClass, targetFieldType, 0);
  }
  
  public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
    this((IFieldLocator)new FieldLocatorType(reflectorClass, targetFieldType, targetFieldIndex));
  }
  
  public ReflectorField(Field field) {
    this((IFieldLocator)new FieldLocatorFixed(field));
  }
  
  public ReflectorField(IFieldLocator fieldLocator) {
    this.fieldLocator = fieldLocator;
    ReflectorResolver.register(this);
  }
  
  public Field getTargetField() {
    if (this.checked)
      return this.targetField; 
    this.checked = true;
    this.targetField = this.fieldLocator.getField();
    if (this.targetField != null)
      this.targetField.setAccessible(true); 
    return this.targetField;
  }
  
  public Object getValue() {
    return Reflector.getFieldValue(null, this);
  }
  
  public Object getValue(Object obj) {
    return Reflector.getFieldValue(obj, this);
  }
  
  public void setValue(Object value) {
    Reflector.setFieldValue(null, this, value);
  }
  
  public void setValue(Object obj, Object value) {
    Reflector.setFieldValue(obj, this, value);
  }
  
  public void setStaticIntUnsafe(int value) {
    if (!exists())
      return; 
    if (this.targetField.getType() != int.class)
      return; 
    if (!Modifier.isStatic(this.targetField.getModifiers()))
      return; 
    UnsafeUtils.setStaticInt(this.targetField, value);
  }
  
  public boolean exists() {
    return (getTargetField() != null);
  }
  
  public void resolve() {
    Field f = getTargetField();
  }
}
