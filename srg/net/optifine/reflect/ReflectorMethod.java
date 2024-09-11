package srg.net.optifine.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.optifine.Log;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;

public class ReflectorMethod implements IResolvable {
  private ReflectorClass reflectorClass = null;
  
  private String targetMethodName = null;
  
  private Class[] targetMethodParameterTypes = null;
  
  private boolean checked = false;
  
  private Method targetMethod = null;
  
  public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
    this(reflectorClass, targetMethodName, null);
  }
  
  public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
    this.reflectorClass = reflectorClass;
    this.targetMethodName = targetMethodName;
    this.targetMethodParameterTypes = targetMethodParameterTypes;
    ReflectorResolver.register(this);
  }
  
  public Method getTargetMethod() {
    if (this.checked)
      return this.targetMethod; 
    this.checked = true;
    Class cls = this.reflectorClass.getTargetClass();
    if (cls == null)
      return null; 
    try {
      if (this.targetMethodParameterTypes == null) {
        Method[] ms = getMethods(cls, this.targetMethodName);
        if (ms.length <= 0) {
          Log.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
          return null;
        } 
        if (ms.length > 1) {
          Log.warn("(Reflector) More than one method found: " + cls.getName() + "." + this.targetMethodName);
          for (int i = 0; i < ms.length; i++) {
            Method m = ms[i];
            Log.warn("(Reflector)  - " + String.valueOf(m));
          } 
          return null;
        } 
        this.targetMethod = ms[0];
      } else {
        this.targetMethod = getMethod(cls, this.targetMethodName, this.targetMethodParameterTypes);
      } 
      if (this.targetMethod == null) {
        Log.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
        return null;
      } 
      this.targetMethod.setAccessible(true);
      return this.targetMethod;
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public boolean exists() {
    if (this.checked)
      return (this.targetMethod != null); 
    return (getTargetMethod() != null);
  }
  
  public Class getReturnType() {
    Method tm = getTargetMethod();
    if (tm == null)
      return null; 
    return tm.getReturnType();
  }
  
  public void deactivate() {
    this.checked = true;
    this.targetMethod = null;
  }
  
  public Object call(Object... params) {
    return Reflector.call(this, params);
  }
  
  public boolean callBoolean(Object... params) {
    return Reflector.callBoolean(this, params);
  }
  
  public int callInt(Object... params) {
    return Reflector.callInt(this, params);
  }
  
  public long callLong(Object... params) {
    return Reflector.callLong(this, params);
  }
  
  public float callFloat(Object... params) {
    return Reflector.callFloat(this, params);
  }
  
  public double callDouble(Object... params) {
    return Reflector.callDouble(this, params);
  }
  
  public String callString(Object... params) {
    return Reflector.callString(this, params);
  }
  
  public Object call(Object param) {
    return Reflector.call(this, new Object[] { param });
  }
  
  public boolean callBoolean(Object param) {
    return Reflector.callBoolean(this, new Object[] { param });
  }
  
  public int callInt(Object param) {
    return Reflector.callInt(this, new Object[] { param });
  }
  
  public long callLong(Object param) {
    return Reflector.callLong(this, new Object[] { param });
  }
  
  public float callFloat(Object param) {
    return Reflector.callFloat(this, new Object[] { param });
  }
  
  public double callDouble(Object param) {
    return Reflector.callDouble(this, new Object[] { param });
  }
  
  public String callString1(Object param) {
    return Reflector.callString(this, new Object[] { param });
  }
  
  public void callVoid(Object... params) {
    Reflector.callVoid(this, params);
  }
  
  public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
    Method[] ms = cls.getDeclaredMethods();
    for (int i = 0; i < ms.length; i++) {
      Method m = ms[i];
      if (m.getName().equals(methodName)) {
        Class[] types = m.getParameterTypes();
        if (Reflector.matchesTypes(paramTypes, types))
          return m; 
      } 
    } 
    return null;
  }
  
  public static Method[] getMethods(Class cls, String methodName) {
    List<Method> listMethods = new ArrayList();
    Method[] ms = cls.getDeclaredMethods();
    for (int i = 0; i < ms.length; i++) {
      Method m = ms[i];
      if (m.getName().equals(methodName))
        listMethods.add(m); 
    } 
    Method[] methods = listMethods.<Method>toArray(new Method[listMethods.size()]);
    return methods;
  }
  
  public void resolve() {
    Method m = getTargetMethod();
  }
}
