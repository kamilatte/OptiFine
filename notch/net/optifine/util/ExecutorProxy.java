package notch.net.optifine.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ExecutorProxy implements ExecutorService {
  protected abstract ExecutorService delegate();
  
  public void execute(Runnable command) {
    delegate().execute(command);
  }
  
  public void shutdown() {
    delegate().shutdown();
  }
  
  public List<Runnable> shutdownNow() {
    return delegate().shutdownNow();
  }
  
  public boolean isShutdown() {
    return delegate().isShutdown();
  }
  
  public boolean isTerminated() {
    return delegate().isTerminated();
  }
  
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return delegate().awaitTermination(timeout, unit);
  }
  
  public <T> Future<T> submit(Callable<T> task) {
    return delegate().submit(task);
  }
  
  public <T> Future<T> submit(Runnable task, T result) {
    return delegate().submit(task, result);
  }
  
  public Future<?> submit(Runnable task) {
    return delegate().submit(task);
  }
  
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return delegate().invokeAll(tasks);
  }
  
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return delegate().invokeAll(tasks, timeout, unit);
  }
  
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return delegate().invokeAny(tasks);
  }
  
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return delegate().invokeAny(tasks, timeout, unit);
  }
}
