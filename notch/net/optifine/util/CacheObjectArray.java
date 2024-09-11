package notch.net.optifine.util;

import dtc;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import net.optifine.Config;

public class CacheObjectArray {
  private static ArrayDeque<int[]> arrays = (ArrayDeque)new ArrayDeque<>();
  
  private static int maxCacheSize = 10;
  
  private static synchronized int[] allocateArray(int size) {
    int[] ints = arrays.pollLast();
    if (ints == null || ints.length < size)
      ints = new int[size]; 
    return ints;
  }
  
  public static synchronized void freeArray(int[] ints) {
    if (arrays.size() >= maxCacheSize)
      return; 
    arrays.add(ints);
  }
  
  public static void main(String[] args) throws Exception {
    int size = 4096;
    int count = 500000;
    testNew(size, count);
    testClone(size, count);
    testNewObj(size, count);
    testCloneObj(size, count);
    testNewObjDyn(dtc.class, size, count);
    long timeNew = testNew(size, count);
    long timeClone = testClone(size, count);
    long timeNewObj = testNewObj(size, count);
    long timeCloneObj = testCloneObj(size, count);
    long timeNewObjDyn = testNewObjDyn(dtc.class, size, count);
    Config.dbg("New: " + timeNew);
    Config.dbg("Clone: " + timeClone);
    Config.dbg("NewObj: " + timeNewObj);
    Config.dbg("CloneObj: " + timeCloneObj);
    Config.dbg("NewObjDyn: " + timeNewObjDyn);
  }
  
  private static long testClone(int size, int count) {
    long timeStart = System.currentTimeMillis();
    int[] template = new int[size];
    for (int i = 0; i < count; i++)
      int[] arrayOfInt = (int[])template.clone(); 
    long timeEnd = System.currentTimeMillis();
    return timeEnd - timeStart;
  }
  
  private static long testNew(int size, int count) {
    long timeStart = System.currentTimeMillis();
    for (int i = 0; i < count; i++)
      int[] arrayOfInt = (int[])Array.newInstance(int.class, size); 
    long timeEnd = System.currentTimeMillis();
    return timeEnd - timeStart;
  }
  
  private static long testCloneObj(int size, int count) {
    long timeStart = System.currentTimeMillis();
    dtc[] template = new dtc[size];
    for (int i = 0; i < count; i++)
      dtc[] arrayOfDtc = (dtc[])template.clone(); 
    long timeEnd = System.currentTimeMillis();
    return timeEnd - timeStart;
  }
  
  private static long testNewObj(int size, int count) {
    long timeStart = System.currentTimeMillis();
    for (int i = 0; i < count; i++)
      dtc[] arrayOfDtc = new dtc[size]; 
    long timeEnd = System.currentTimeMillis();
    return timeEnd - timeStart;
  }
  
  private static long testNewObjDyn(Class<?> cls, int size, int count) {
    long timeStart = System.currentTimeMillis();
    for (int i = 0; i < count; i++)
      Object[] arrayOfObject = (Object[])Array.newInstance(cls, size); 
    long timeEnd = System.currentTimeMillis();
    return timeEnd - timeStart;
  }
}
