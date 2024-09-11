package notch.net.optifine.reflect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.optifine.reflect.IResolvable;

public class ReflectorResolver {
  private static final List<IResolvable> RESOLVABLES = Collections.synchronizedList(new ArrayList<>());
  
  private static boolean resolved = false;
  
  protected static void register(IResolvable resolvable) {
    if (!resolved) {
      RESOLVABLES.add(resolvable);
      return;
    } 
    resolvable.resolve();
  }
  
  public static void resolve() {
    if (resolved)
      return; 
    for (IResolvable resolvable : RESOLVABLES)
      resolvable.resolve(); 
    resolved = true;
  }
}
