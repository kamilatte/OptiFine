package notch.net.optifine.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IteratorCache {
  private static Deque<IteratorReusable<Object>> dequeIterators = new ArrayDeque<>();
  
  static {
    for (int i = 0; i < 1000; i++) {
      IteratorReadOnly it = new IteratorReadOnly();
      dequeIterators.add(it);
    } 
  }
  
  public static Iterator<Object> getReadOnly(List list) {
    synchronized (dequeIterators) {
      IteratorReadOnly iteratorReadOnly;
      IteratorReusable<Object> it = dequeIterators.pollFirst();
      if (it == null)
        iteratorReadOnly = new IteratorReadOnly(); 
      iteratorReadOnly.setList(list);
      return (Iterator<Object>)iteratorReadOnly;
    } 
  }
  
  private static void finished(IteratorReusable<Object> iterator) {
    synchronized (dequeIterators) {
      if (dequeIterators.size() > 1000)
        return; 
      iterator.setList(null);
      dequeIterators.addLast(iterator);
    } 
  }
}
