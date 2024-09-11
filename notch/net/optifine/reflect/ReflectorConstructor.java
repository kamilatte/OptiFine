package notch.net.optifine.reflect;

import java.lang.reflect.Constructor;
import net.optifine.Log;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.util.ArrayUtils;

public class ReflectorConstructor implements IResolvable {
  private ReflectorClass reflectorClass = null;
  
  private Class[] parameterTypes = null;
  
  private boolean checked = false;
  
  private Constructor targetConstructor = null;
  
  public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
    this.reflectorClass = reflectorClass;
    this.parameterTypes = parameterTypes;
    ReflectorResolver.register(this);
  }
  
  public Constructor getTargetConstructor() {
    if (this.checked)
      return this.targetConstructor; 
    this.checked = true;
    Class cls = this.reflectorClass.getTargetClass();
    if (cls == null)
      return null; 
    try {
      this.targetConstructor = findConstructor(cls, this.parameterTypes);
      if (this.targetConstructor == null)
        Log.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + ArrayUtils.arrayToString((Object[])this.parameterTypes)); 
      if (this.targetConstructor != null)
        this.targetConstructor.setAccessible(true); 
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return this.targetConstructor;
  }
  
  private static Constructor findConstructor(Class cls, Class[] paramTypes) {
    Constructor[] cs = (Constructor[])cls.getDeclaredConstructors();
    for (int i = 0; i < cs.length; i++) {
      Constructor c = cs[i];
      Class[] types = c.getParameterTypes();
      if (Reflector.matchesTypes(paramTypes, types))
        return c; 
    } 
    return null;
  }
  
  public boolean exists() {
    if (this.checked)
      return (this.targetConstructor != null); 
    return (getTargetConstructor() != null);
  }
  
  public void deactivate() {
    this.checked = true;
    this.targetConstructor = null;
  }
  
  public Object newInstance(Object... params) {
    return Reflector.newInstance(this, params);
  }
  
  public void resolve() {
    Constructor c = getTargetConstructor();
  }
}
