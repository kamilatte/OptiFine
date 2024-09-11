package notch.net.optifine;

import akr;
import btn;
import cuq;
import fzf;
import gps;
import gpt;
import java.util.Arrays;
import net.optifine.Config;

public class ItemOverrideProperty {
  private akr location;
  
  private float[] values;
  
  public ItemOverrideProperty(akr location, float[] values) {
    this.location = location;
    this.values = (float[])values.clone();
    Arrays.sort(this.values);
  }
  
  public Integer getValueIndex(cuq stack, fzf world, btn entity) {
    gpt itemPropertyGetter = gps.a(stack, this.location);
    if (itemPropertyGetter == null)
      return null; 
    float val = itemPropertyGetter.call(stack, world, entity, 0);
    int index = Arrays.binarySearch(this.values, val);
    return Integer.valueOf(index);
  }
  
  public akr getLocation() {
    return this.location;
  }
  
  public float[] getValues() {
    return this.values;
  }
  
  public String toString() {
    return "location: " + String.valueOf(this.location) + ", values: [" + Config.arrayToString(this.values) + "]";
  }
}
