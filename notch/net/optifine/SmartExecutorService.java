package notch.net.optifine;

import java.util.concurrent.ExecutorService;
import net.optifine.util.ExecutorProxy;

public class SmartExecutorService extends ExecutorProxy {
  private ExecutorService executor;
  
  public SmartExecutorService(ExecutorService executor) {
    this.executor = executor;
  }
  
  protected ExecutorService delegate() {
    return this.executor;
  }
  
  public void execute(Runnable command) {
    Object object = new Object(this, command);
    super.execute((Runnable)object);
  }
}
