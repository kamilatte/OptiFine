package notch.net.optifine.shaders.config;

import java.util.Properties;
import net.optifine.Config;
import org.apache.commons.lang3.ArrayUtils;

public class Property {
  private int defaultValue = 0;
  
  private String propertyName = null;
  
  private String[] propertyValues = null;
  
  private String userName = null;
  
  private String[] userValues = null;
  
  private int value = 0;
  
  public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
    this.propertyName = propertyName;
    this.propertyValues = propertyValues;
    this.userName = userName;
    this.userValues = userValues;
    this.defaultValue = defaultValue;
    if (propertyValues.length != userValues.length)
      throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length); 
    if (defaultValue < 0 || defaultValue >= propertyValues.length)
      throw new IllegalArgumentException("Invalid default value: " + defaultValue); 
    this.value = defaultValue;
  }
  
  public boolean setPropertyValue(String propVal) {
    if (propVal == null) {
      this.value = this.defaultValue;
      return false;
    } 
    this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
    if (this.value < 0 || this.value >= this.propertyValues.length) {
      this.value = this.defaultValue;
      return false;
    } 
    return true;
  }
  
  public void nextValue(boolean forward) {
    int min = 0;
    int max = this.propertyValues.length - 1;
    this.value = Config.limit(this.value, min, max);
    if (forward) {
      this.value++;
      if (this.value > max)
        this.value = min; 
    } else {
      this.value--;
      if (this.value < min)
        this.value = max; 
    } 
  }
  
  public void setValue(int val) {
    this.value = val;
    if (this.value < 0 || this.value >= this.propertyValues.length)
      this.value = this.defaultValue; 
  }
  
  public int getValue() {
    return this.value;
  }
  
  public String getUserValue() {
    return this.userValues[this.value];
  }
  
  public String getPropertyValue() {
    return this.propertyValues[this.value];
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public String getPropertyName() {
    return this.propertyName;
  }
  
  public void resetValue() {
    this.value = this.defaultValue;
  }
  
  public boolean loadFrom(Properties props) {
    resetValue();
    if (props == null)
      return false; 
    String str = props.getProperty(this.propertyName);
    if (str == null)
      return false; 
    return setPropertyValue(str);
  }
  
  public void saveTo(Properties props) {
    if (props == null)
      return; 
    props.setProperty(getPropertyName(), getPropertyValue());
  }
  
  public String toString() {
    return this.propertyName + "=" + this.propertyName + " [" + getPropertyValue() + "], value: " + Config.arrayToString((Object[])this.propertyValues);
  }
}
