package notch.net.optifine.config;

import fgo;
import fgr;
import fgs;
import wy;
import wz;

public class IteratableOptionOF extends fgr {
  public IteratableOptionOF(String nameIn) {
    super(nameIn, 
        fgr.a(), (labelIn, valueIn) -> ((Boolean)valueIn).booleanValue() ? wy.b : wy.c, (fgr.n)fgr.a, 
        
        Boolean.valueOf(false), valueIn -> {
        
        });
  }
  
  public void nextOptionValue(int dirIn) {
    fgs gameSetings = (fgo.Q()).m;
    gameSetings.setOptionValueOF(this, dirIn);
  }
  
  public wz getOptionText() {
    fgs gameSetings = (fgo.Q()).m;
    return gameSetings.getKeyComponentOF(this);
  }
  
  protected fgs getOptions() {
    return (fgo.Q()).m;
  }
}
