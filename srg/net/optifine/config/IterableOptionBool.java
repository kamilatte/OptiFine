package srg.net.optifine.config;

import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.optifine.Lang;
import net.optifine.config.IPersitentOption;
import net.optifine.config.IteratableOptionOF;
import net.optifine.config.ObjBooleanConsumer;
import net.optifine.config.ToBooleanFunction;

public class IterableOptionBool extends IteratableOptionOF implements IPersitentOption {
  private String resourceKey;
  
  private ToBooleanFunction<Options> getter;
  
  private ObjBooleanConsumer<Options> setter;
  
  private String saveKey;
  
  public IterableOptionBool(String resourceKey, ToBooleanFunction<Options> getter, ObjBooleanConsumer<Options> setter, String saveKey) {
    super(resourceKey);
    this.resourceKey = resourceKey;
    this.getter = getter;
    this.setter = setter;
    this.saveKey = saveKey;
  }
  
  public void nextOptionValue(int dirIn) {
    Options opts = getOptions();
    boolean val = this.getter.applyAsBool(opts);
    val = !val;
    this.setter.accept(opts, Boolean.valueOf(val));
  }
  
  public Component getOptionText() {
    Options opts = getOptions();
    String optionLabel = Lang.get(this.resourceKey) + ": ";
    boolean val = this.getter.applyAsBool(opts);
    String valueLabel = val ? Lang.getOn() : Lang.getOff();
    String label = optionLabel + optionLabel;
    return (Component)Component.literal(label);
  }
  
  public String getSaveKey() {
    return this.saveKey;
  }
  
  public void loadValue(Options opts, String s) {
    boolean val = Boolean.valueOf(s).booleanValue();
    this.setter.accept(opts, Boolean.valueOf(val));
  }
  
  public String getSaveText(Options opts) {
    boolean val = this.getter.applyAsBool(opts);
    return Boolean.toString(val);
  }
}
