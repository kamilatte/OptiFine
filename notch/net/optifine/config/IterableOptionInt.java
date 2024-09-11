package notch.net.optifine.config;

import fgs;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.config.IPersitentOption;
import net.optifine.config.IteratableOptionOF;
import net.optifine.config.OptionValueInt;
import wz;

public class IterableOptionInt extends IteratableOptionOF implements IPersitentOption {
  private String resourceKey;
  
  private OptionValueInt[] values;
  
  private ToIntFunction<fgs> getter;
  
  private ObjIntConsumer<fgs> setter;
  
  private String saveKey;
  
  public IterableOptionInt(String resourceKey, OptionValueInt[] values, ToIntFunction<fgs> getter, ObjIntConsumer<fgs> setter, String saveKey) {
    super(resourceKey);
    this.resourceKey = resourceKey;
    this.values = values;
    this.getter = getter;
    this.setter = setter;
    this.saveKey = saveKey;
  }
  
  public void nextOptionValue(int dirIn) {
    fgs opts = getOptions();
    int value = this.getter.applyAsInt(opts);
    int index = getValueIndex(value);
    int indexNext = index + dirIn;
    if (indexNext < getIndexMin() || indexNext > getIndexMax())
      indexNext = (dirIn > 0) ? getIndexMin() : getIndexMax(); 
    int valueNext = this.values[indexNext].getValue();
    this.setter.accept(opts, valueNext);
  }
  
  public wz getOptionText() {
    fgs opts = getOptions();
    String optionLabel = Lang.get(this.resourceKey) + ": ";
    int value = this.getter.applyAsInt(opts);
    OptionValueInt optionValue = getOptionValue(value);
    if (optionValue == null)
      return (wz)wz.b(optionLabel + "???"); 
    String valueLabel = Lang.get(optionValue.getResourceKey());
    String label = optionLabel + optionLabel;
    return (wz)wz.b(label);
  }
  
  public String getSaveKey() {
    return this.saveKey;
  }
  
  public void loadValue(fgs opts, String s) {
    int val = Config.parseInt(s, -1);
    if (getOptionValue(val) == null)
      val = this.values[0].getValue(); 
    this.setter.accept(opts, val);
  }
  
  public String getSaveText(fgs opts) {
    int value = this.getter.applyAsInt(opts);
    return Integer.toString(value);
  }
  
  private OptionValueInt getOptionValue(int value) {
    int index = getValueIndex(value);
    if (index < 0)
      return null; 
    return this.values[index];
  }
  
  private int getValueIndex(int value) {
    for (int i = 0; i < this.values.length; i++) {
      OptionValueInt ovi = this.values[i];
      if (ovi.getValue() == value)
        return i; 
    } 
    return -1;
  }
  
  private int getIndexMin() {
    return 0;
  }
  
  private int getIndexMax() {
    return this.values.length - 1;
  }
}
