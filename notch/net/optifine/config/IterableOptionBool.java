package notch.net.optifine.config;

import fgs;
import net.optifine.Lang;
import net.optifine.config.IPersitentOption;
import net.optifine.config.IteratableOptionOF;
import net.optifine.config.ObjBooleanConsumer;
import net.optifine.config.ToBooleanFunction;
import wz;

public class IterableOptionBool extends IteratableOptionOF implements IPersitentOption {
  private String resourceKey;
  
  private ToBooleanFunction<fgs> getter;
  
  private ObjBooleanConsumer<fgs> setter;
  
  private String saveKey;
  
  public IterableOptionBool(String resourceKey, ToBooleanFunction<fgs> getter, ObjBooleanConsumer<fgs> setter, String saveKey) {
    super(resourceKey);
    this.resourceKey = resourceKey;
    this.getter = getter;
    this.setter = setter;
    this.saveKey = saveKey;
  }
  
  public void nextOptionValue(int dirIn) {
    fgs opts = getOptions();
    boolean val = this.getter.applyAsBool(opts);
    val = !val;
    this.setter.accept(opts, Boolean.valueOf(val));
  }
  
  public wz getOptionText() {
    fgs opts = getOptions();
    String optionLabel = Lang.get(this.resourceKey) + ": ";
    boolean val = this.getter.applyAsBool(opts);
    String valueLabel = val ? Lang.getOn() : Lang.getOff();
    String label = optionLabel + optionLabel;
    return (wz)wz.b(label);
  }
  
  public String getSaveKey() {
    return this.saveKey;
  }
  
  public void loadValue(fgs opts, String s) {
    boolean val = Boolean.valueOf(s).booleanValue();
    this.setter.accept(opts, Boolean.valueOf(val));
  }
  
  public String getSaveText(fgs opts) {
    boolean val = this.getter.applyAsBool(opts);
    return Boolean.toString(val);
  }
}
