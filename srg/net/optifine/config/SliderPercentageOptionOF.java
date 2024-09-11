package srg.net.optifine.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

public class SliderPercentageOptionOF extends OptionInstance {
  public SliderPercentageOptionOF(String name, double defVal) {
    super(name, 
        OptionInstance.noTooltip(), (labelIn, valuein) -> Options.genericValueLabel(labelIn, (Component)Component.translatable(name, new Object[] { valuein })), (OptionInstance.ValueSet)OptionInstance.UnitDouble.INSTANCE, 

        
        Double.valueOf(defVal), val -> {
        
        });
  }
  
  public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int step, int valueDef) {
    super(name, 
        OptionInstance.noTooltip(), (labelIn, valuein) -> Options.genericValueLabel(labelIn, (Component)Component.translatable(name, new Object[] { valuein })), (OptionInstance.ValueSet)(new OptionInstance.IntRange(valueMin / step, valueMax / step))
        
        .xmap(val -> Integer.valueOf(val * step), val -> val.intValue() / step), 
        Codec.intRange(valueMin, valueMax), Integer.valueOf(valueDef), val -> {
        
        });
  }
  
  public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int[] stepValues, int valueDef) {
    super(name, 
        OptionInstance.noTooltip(), (labelIn, valuein) -> Options.genericValueLabel(labelIn, (Component)Component.translatable(name, new Object[] { valuein })), (OptionInstance.ValueSet)(new OptionInstance.IntRange(0, stepValues.length - 1))
        
        .xmap(val -> Integer.valueOf(stepValues[val]), val -> Options.indexOf(val.intValue(), stepValues)), 
        Codec.intRange(valueMin, valueMax), Integer.valueOf(valueDef), val -> {
        
        });
  }
  
  public double getOptionValue() {
    Options gameSettings = (Minecraft.getInstance()).options;
    return gameSettings.getOptionFloatValueOF(this);
  }
  
  public void setOptionValue(double value) {
    Options gameSettings = (Minecraft.getInstance()).options;
    gameSettings.setOptionFloatValueOF(this, value);
  }
  
  public Component getOptionText() {
    Options gameSetings = (Minecraft.getInstance()).options;
    return gameSetings.getKeyComponentOF(this);
  }
}
