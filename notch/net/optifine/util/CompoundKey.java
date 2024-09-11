package notch.net.optifine.util;

import net.optifine.Config;

public class CompoundKey {
  private Object[] keys;
  
  private int hashcode = 0;
  
  public CompoundKey(Object[] keys) {
    this.keys = (Object[])keys.clone();
  }
  
  public CompoundKey(Object k1, Object k2) {
    this(new Object[] { k1, k2 });
  }
  
  public CompoundKey(Object k1, Object k2, Object k3) {
    this(new Object[] { k1, k2, k3 });
  }
  
  public CompoundKey(Object k1, Object k2, Object k3, Object k4) {
    this(new Object[] { k1, k2, k3, k4 });
  }
  
  public int hashCode() {
    if (this.hashcode == 0) {
      this.hashcode = 7;
      for (int i = 0; i < this.keys.length; i++) {
        Object key = this.keys[i];
        if (key != null)
          this.hashcode = 31 * this.hashcode + key.hashCode(); 
      } 
    } 
    return this.hashcode;
  }
  
  public boolean equals(Object obj) {
    if (obj == null)
      return false; 
    if (obj == this)
      return true; 
    if (!(obj instanceof net.optifine.util.CompoundKey))
      return false; 
    net.optifine.util.CompoundKey ck = (net.optifine.util.CompoundKey)obj;
    Object[] ckKeys = ck.getKeys();
    if (ckKeys.length != this.keys.length)
      return false; 
    for (int i = 0; i < this.keys.length; i++) {
      if (!compareKeys(this.keys[i], ckKeys[i]))
        return false; 
    } 
    return true;
  }
  
  private static boolean compareKeys(Object key1, Object key2) {
    if (key1 == key2)
      return true; 
    if (key1 == null)
      return false; 
    if (key2 == null)
      return false; 
    return key1.equals(key2);
  }
  
  private Object[] getKeys() {
    return this.keys;
  }
  
  public Object[] getKeysCopy() {
    return (Object[])this.keys.clone();
  }
  
  public String toString() {
    return "[" + Config.arrayToString(this.keys) + "]";
  }
}
