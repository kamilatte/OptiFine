package srg.net.optifine;

import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
  
  public Integer getModelIndex(ItemStack stack, ClientLevel world, LivingEntity entity) {
    CompoundKey valueKey = getValueKey(stack, world, entity);
    if (valueKey == null)
      return null; 
    return this.mapModelIndexes.get(valueKey);
  }
  
  public void putModelIndex(ItemStack stack, ClientLevel world, LivingEntity entity, Integer index) {
    CompoundKey valueKey = getValueKey(stack, world, entity);
    if (valueKey == null)
      return; 
    this.mapModelIndexes.put(valueKey, index);
  }
  
  private CompoundKey getValueKey(ItemStack stack, ClientLevel world, LivingEntity entity) {
    Integer[] indexes = new Integer[this.itemOverrideProperties.length];
    for (int i = 0; i < indexes.length; i++) {
      Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
      if (index == null)
        return null; 
      indexes[i] = index;
    } 
    return new CompoundKey((Object[])indexes);
  }
  
  public static net.optifine.ItemOverrideCache make(List<ItemOverride> overrides) {
    if (overrides.isEmpty())
      return null; 
    if (!Reflector.ItemOverride_listResourceValues.exists())
      return null; 
    Map<ResourceLocation, Set<Float>> propertyValues = new LinkedHashMap<>();
    for (Iterator<ItemOverride> it = overrides.iterator(); it.hasNext(); ) {
      ItemOverride itemOverride = it.next();
      List<ItemOverride.Predicate> resourceValues = (List<ItemOverride.Predicate>)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_listResourceValues);
      for (ItemOverride.Predicate resourceValue : resourceValues) {
        ResourceLocation loc = resourceValue.getProperty();
        float val = resourceValue.getValue();
        Set<Float> setValues = propertyValues.get(loc);
        if (setValues == null) {
          setValues = new HashSet<>();
          propertyValues.put(loc, setValues);
        } 
        setValues.add(Float.valueOf(val));
      } 
    } 
    List<ItemOverrideProperty> listProps = new ArrayList<>();
    Set<ResourceLocation> setPropertyLocations = propertyValues.keySet();
    for (Iterator<ResourceLocation> iterator = setPropertyLocations.iterator(); iterator.hasNext(); ) {
      ResourceLocation loc = iterator.next();
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
  
  private static void logCache(ItemOverrideProperty[] props, List<ItemOverride> overrides) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < props.length; i++) {
      ItemOverrideProperty prop = props[i];
      if (sb.length() > 0)
        sb.append(", "); 
      sb.append(String.valueOf(prop.getLocation()) + "=" + String.valueOf(prop.getLocation()));
    } 
    if (overrides.size() > 0)
      sb.append(" -> " + String.valueOf(((ItemOverride)overrides.get(0)).getModel()) + " ..."); 
    Config.dbg("ItemOverrideCache: " + sb.toString());
  }
  
  public String toString() {
    return "properties: " + this.itemOverrideProperties.length + ", modelIndexes: " + this.mapModelIndexes.size();
  }
}
