package srg.net.optifine.reflect;

import net.optifine.Log;
import net.optifine.reflect.FieldLocatorTypes;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorField;
import net.optifine.reflect.ReflectorMethod;
import net.optifine.reflect.ReflectorResolver;

public class ReflectorClass implements IResolvable {
  private String targetClassName = null;
  
  private boolean checked = false;
  
  private Class targetClass = null;
  
  public ReflectorClass(String targetClassName) {
    this.targetClassName = targetClassName;
    ReflectorResolver.register(this);
  }
  
  public ReflectorClass(Class targetClass) {
    this.targetClass = targetClass;
    this.targetClassName = targetClass.getName();
    this.checked = true;
  }
  
  public Class getTargetClass() {
    if (this.checked)
      return this.targetClass; 
    this.checked = true;
    try {
      this.targetClass = Class.forName(this.targetClassName);
    } catch (ClassNotFoundException e) {
      Log.log("(Reflector) Class not present: " + this.targetClassName);
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return this.targetClass;
  }
  
  public boolean exists() {
    return (getTargetClass() != null);
  }
  
  public String getTargetClassName() {
    return this.targetClassName;
  }
  
  public boolean isInstance(Object obj) {
    if (getTargetClass() == null)
      return false; 
    return getTargetClass().isInstance(obj);
  }
  
  public ReflectorField makeField(String name) {
    return new ReflectorField(this, name);
  }
  
  public ReflectorField makeField(Class type) {
    return new ReflectorField(this, type);
  }
  
  public ReflectorField makeField(IFieldLocator loc) {
    return new ReflectorField(loc);
  }
  
  public ReflectorField makeFieldTypes(Class preType, Class type, Class postTypes, String errorName) {
    if (getTargetClass() == null)
      return null; 
    return new ReflectorField((IFieldLocator)new FieldLocatorTypes(getTargetClass(), new Class[] { preType }, type, new Class[] { postTypes }, errorName));
  }
  
  public ReflectorMethod makeMethod(String name) {
    return new ReflectorMethod(this, name);
  }
  
  public ReflectorMethod makeMethod(String name, Class[] paramTypes) {
    return new ReflectorMethod(this, name, paramTypes);
  }
  
  public ReflectorConstructor makeConstructor(Class[] paramTypes) {
    return new ReflectorConstructor(this, paramTypes);
  }
  
  public void resolve() {
    Class cls = getTargetClass();
  }
}
