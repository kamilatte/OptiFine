package notch.net.optifine.config;

import com.mojang.serialization.Codec;
import fgo;
import fgr;
import fgs;
import wz;

public class SliderPercentageOptionOF extends fgr {
  public SliderPercentageOptionOF(String name, double defVal) {
    super(name, 
        fgr.a(), (labelIn, valuein) -> fgs.a(labelIn, (wz)wz.a(name, new Object[] { valuein })), (fgr.n)fgr.m.a, 

        
        Double.valueOf(defVal), val -> {
        
        });
  }
  
  public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int step, int valueDef) {
    super(name, 
        fgr.a(), (labelIn, valuein) -> fgs.a(labelIn, (wz)wz.a(name, new Object[] { valuein })), (fgr.n)(new fgr.f(valueMin / step, valueMax / step))
        
        .a(val -> Integer.valueOf(val * step), val -> val.intValue() / step), 
        Codec.intRange(valueMin, valueMax), Integer.valueOf(valueDef), val -> {
        
        });
  }
  
  public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int[] stepValues, int valueDef) {
    super(name, 
        fgr.a(), (labelIn, valuein) -> fgs.a(labelIn, (wz)wz.a(name, new Object[] { valuein })), (fgr.n)(new fgr.f(0, stepValues.length - 1))
        
        .a(val -> Integer.valueOf(stepValues[val]), val -> fgs.indexOf(val.intValue(), stepValues)), 
        Codec.intRange(valueMin, valueMax), Integer.valueOf(valueDef), val -> {
        
        });
  }
  
  public double getOptionValue() {
    fgs gameSettings = (fgo.Q()).m;
    return gameSettings.getOptionFloatValueOF(this);
  }
  
  public void setOptionValue(double value) {
    fgs gameSettings = (fgo.Q()).m;
    gameSettings.setOptionFloatValueOF(this, value);
  }
  
  public wz getOptionText() {
    fgs gameSetings = (fgo.Q()).m;
    return gameSetings.getKeyComponentOF(this);
  }
}
