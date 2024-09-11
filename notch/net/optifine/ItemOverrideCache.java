package notch.net.optifine;

import akr;
import btn;
import com.google.common.primitives.Floats;
import cuq;
import fzf;
import ggf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.optifine.Config;
import net.optifine.ItemOverrideProperty;
import net.optifine.reflect.Reflector;
import net.optifine.util.CompoundKey;

public class ItemOverrideCache {
  private ItemOverrideProperty[] itemOverrideProperties;
  
  private Map<CompoundKey, Integer> mapModelIndexes = new HashMap<>();
  
  public static final Integer INDEX_NONE = new Integer(-1);
  
  public ItemOverrideCache(ItemOverrideProperty[] itemOverrideProperties) {
    this.itemOverrideProperties = itemOverrideProperties;
  }
  
  public Integer getModelIndex(cuq stack, fzf world, btn entity) {
    CompoundKey valueKey = getValueKey(stack, world, entity);
    if (valueKey == null)
      return null; 
    return this.mapModelIndexes.get(valueKey);
  }
  
  public void putModelIndex(cuq stack, fzf world, btn entity, Integer index) {
    CompoundKey valueKey = getValueKey(stack, world, entity);
    if (valueKey == null)
      return; 
    this.mapModelIndexes.put(valueKey, index);
  }
  
  private CompoundKey getValueKey(cuq stack, fzf world, btn entity) {
    Integer[] indexes = new Integer[this.itemOverrideProperties.length];
    for (int i = 0; i < indexes.length; i++) {
      Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
      if (index == null)
        return null; 
      indexes[i] = index;
    } 
    return new CompoundKey((Object[])indexes);
  }
  
  public static net.optifine.ItemOverrideCache make(List<ggf> overrides) {
    if (overrides.isEmpty())
      return null; 
    if (!Reflector.ItemOverride_listResourceValues.exists())
      return null; 
    Map<akr, Set<Float>> propertyValues = new LinkedHashMap<>();
    for (Iterator<ggf> it = overrides.iterator(); it.hasNext(); ) {
      ggf itemOverride = it.next();
      List<ggf.b> resourceValues = (List<ggf.b>)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_listResourceValues);
      for (ggf.b resourceValue : resourceValues) {
        akr loc = resourceValue.a();
        float val = resourceValue.b();
        Set<Float> setValues = propertyValues.get(loc);
        if (setValues == null) {
          setValues = new HashSet<>();
          propertyValues.put(loc, setValues);
        } 
        setValues.add(Float.valueOf(val));
      } 
    } 
    List<ItemOverrideProperty> listProps = new ArrayList<>();
    Set<akr> setPropertyLocations = propertyValues.keySet();
    for (Iterator<akr> iterator = setPropertyLocations.iterator(); iterator.hasNext(); ) {
      akr loc = iterator.next();
      Set<Float> setValues = propertyValues.get(loc);
      float[] values = Floats.toArray(setValues);
      ItemOverrideProperty prop = new ItemOverrideProperty(loc, values);
      listProps.add(prop);
    } 
    ItemOverrideProperty[] props = listProps.<ItemOverrideProperty>toArray(new ItemOverrideProperty[listProps.size()]);
    net.optifine.ItemOverrideCache cache = new net.optifine.ItemOverrideCache(props);
    logCache(props, overrides);
    return cache;
  }
  
  private static void logCache(ItemOverrideProperty[] props, List<ggf> overrides) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < props.length; i++) {
      ItemOverrideProperty prop = props[i];
      if (sb.length() > 0)
        sb.append(", "); 
      sb.append(String.valueOf(prop.getLocation()) + "=" + String.valueOf(prop.getLocation()));
    } 
    if (overrides.size() > 0)
      sb.append(" -> " + String.valueOf(((ggf)overrides.get(0)).a()) + " ..."); 
    Config.dbg("ItemOverrideCache: " + sb.toString());
  }
  
  public String toString() {
    return "properties: " + this.itemOverrideProperties.length + ", modelIndexes: " + this.mapModelIndexes.size();
  }
}
