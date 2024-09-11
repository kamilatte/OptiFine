package srg.net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntityContext;
import net.optifine.RandomEntityRule;
import net.optifine.config.ConnectedParser;
import net.optifine.util.PropertiesOrdered;

public class RandomEntityProperties<T> {
  private String name = null;
  
  private String basePath = null;
  
  private RandomEntityContext<T> context;
  
  private T[] resources = null;
  
  private RandomEntityRule<T>[] rules = null;
  
  private int matchingRuleIndex = -1;
  
  public RandomEntityProperties(String path, ResourceLocation baseLoc, int[] variants, RandomEntityContext<T> context) {
    ConnectedParser cp = new ConnectedParser(context.getName());
    this.name = cp.parseName(path);
    this.basePath = cp.parseBasePath(path);
    this.context = context;
    this.resources = (T[])new Object[variants.length];
    for (int i = 0; i < variants.length; i++) {
      int index = variants[i];
      this.resources[i] = (T)context.makeResource(baseLoc, index);
    } 
  }
  
  public RandomEntityProperties(Properties props, String path, ResourceLocation baseResLoc, RandomEntityContext<T> context) {
    ConnectedParser cp = context.getConnectedParser();
    this.name = cp.parseName(path);
    this.basePath = cp.parseBasePath(path);
    this.context = context;
    this.rules = parseRules(props, path, baseResLoc);
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getBasePath() {
    return this.basePath;
  }
  
  public T[] getResources() {
    return this.resources;
  }
  
  public List<T> getAllResources() {
    List<T> list = new ArrayList<>();
    if (this.resources != null)
      list.addAll(Arrays.asList(this.resources)); 
    if (this.rules != null)
      for (int i = 0; i < this.rules.length; i++) {
        RandomEntityRule<T> rule = this.rules[i];
        if (rule.getResources() != null)
          list.addAll(Arrays.asList((T[])rule.getResources())); 
      }  
    return list;
  }
  
  public T getResource(IRandomEntity randomEntity, T resDef) {
    this.matchingRuleIndex = 0;
    if (this.rules != null)
      for (int i = 0; i < this.rules.length; ) {
        RandomEntityRule<T> rule = this.rules[i];
        if (!rule.matches(randomEntity)) {
          i++;
          continue;
        } 
        this.matchingRuleIndex = rule.getIndex();
        return (T)rule.getResource(randomEntity.getId(), resDef);
      }  
    if (this.resources != null) {
      int randomId = randomEntity.getId();
      int index = randomId % this.resources.length;
      return this.resources[index];
    } 
    return resDef;
  }
  
  private RandomEntityRule<T>[] parseRules(Properties props, String pathProps, ResourceLocation baseResLoc) {
    List<RandomEntityRule<T>> list = new ArrayList();
    int maxIndex = 10;
    for (int i = 0; i < maxIndex; i++) {
      int index = i + 1;
      String valTextures = null;
      String[] keys = this.context.getResourceKeys();
      for (String key : keys) {
        valTextures = props.getProperty(key + "." + key);
        if (valTextures != null)
          break; 
      } 
      if (valTextures != null) {
        RandomEntityRule<T> rule = new RandomEntityRule(props, pathProps, baseResLoc, index, valTextures, this.context);
        list.add(rule);
        maxIndex = index + 10;
      } 
    } 
    return (RandomEntityRule<T>[])list.<RandomEntityRule>toArray(new RandomEntityRule[list.size()]);
  }
  
  public boolean isValid(String path) {
    String resNamePlural = this.context.getResourceNamePlural();
    if (this.resources == null && this.rules == null) {
      Config.warn("No " + resNamePlural + " specified: " + path);
      return false;
    } 
    if (this.rules != null)
      for (int i = 0; i < this.rules.length; i++) {
        RandomEntityRule<T> rule = this.rules[i];
        if (!rule.isValid(path))
          return false; 
      }  
    if (this.resources != null)
      for (int i = 0; i < this.resources.length; i++) {
        T res = this.resources[i];
        if (res == null)
          return false; 
      }  
    return true;
  }
  
  public boolean isDefault() {
    if (this.rules != null)
      return false; 
    if (this.resources != null)
      return false; 
    return true;
  }
  
  public int getMatchingRuleIndex() {
    return this.matchingRuleIndex;
  }
  
  public static net.optifine.RandomEntityProperties parse(ResourceLocation propLoc, ResourceLocation resLoc, RandomEntityContext<?> context) {
    String contextName = context.getName();
    try {
      String path = propLoc.getPath();
      Config.dbg(contextName + ": " + contextName + ", properties: " + resLoc.getPath());
      InputStream in = Config.getResourceStream(propLoc);
      if (in == null) {
        Config.warn(contextName + ": Properties not found: " + contextName);
        return null;
      } 
      PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
      propertiesOrdered.load(in);
      in.close();
      net.optifine.RandomEntityProperties rep = new net.optifine.RandomEntityProperties((Properties)propertiesOrdered, path, resLoc, context);
      if (!rep.isValid(path))
        return null; 
      return rep;
    } catch (FileNotFoundException e) {
      Config.warn(contextName + ": File not found: " + contextName);
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public String toString() {
    return this.name + ", path: " + this.name;
  }
}
